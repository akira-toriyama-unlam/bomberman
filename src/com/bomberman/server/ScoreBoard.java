package com.bomberman.server;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.bomberman.client.Window;
import com.bomberman.database.HibernateConfiguration;
import com.bomberman.entities.Bomb;
import com.bomberman.entities.Destructible;
import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.Direction;
import com.bomberman.entities.Entity;
import com.bomberman.entities.Explosion;
import com.bomberman.entities.ExplosionDirection;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;
import com.bomberman.services.CollisionManager;
import com.bomberman.services.DirectionMessage;

public class ScoreBoard extends Observable implements GameActionPerformed {

	private GameMap map;
	private final static int MOVEMENT_ERROR = 2;
	private Timer timer;

	public ScoreBoard() {
		this.map = new GameMap(this);
		this.generateBaseMap();

		initializeReSend();

		HibernateConfiguration.createSessionFactory();
	}

	private void initializeReSend() {
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				actionPerformed();
			}
		}, 200, 1);
	}

	@Override
	public void actionPerformed() {
		this.map.setObjects(map.getObjects().stream().filter(e -> !e.isDestroyed()).collect(Collectors.toList()));
		this.map.setPlayers(map.getPlayers().stream().filter(e -> !e.isDestroyed()).collect(Collectors.toList()));
		this.setChanged();
		this.notifyObservers(map);
	}

	@Override
	public Player newPlayer() {

		Optional<Player> player = getNewPlayer();

		if (player.isPresent()) {
			SessionFactory factory = HibernateConfiguration.getSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			try {
				// session.save(player.get())
				// tx.commit();
			} catch (HibernateException e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		}

		return player.get();
	}

	private Optional<Player> getNewPlayer() {
		int playersCount = this.map.getPlayers().size();

		switch (playersCount) {
		case 0:
			return Optional.of(new Player(40, 40, 0, "uno", "uno"));
		case 1:
			return Optional.of(new Player(760, 40, 2, "dos", "dos"));
		case 2:
			return Optional.of(new Player(40, 520, 3, "tres", "tres"));
		case 3:
			return Optional.of(new Player(760, 520, 4, "cuatro", "cuatro"));
		default:
			return Optional.ofNullable(null);
		}
	}

	@Override
	public void movementMessageReceived(Player player, DirectionMessage message) {
		Direction direction = message.getDirection();
		Player currentPlayer = this.map.getPlayers().stream().filter(p -> p.equals(player)).findFirst().orElse(null);
		if (currentPlayer == null) {
			return;
		}
		if (this.canMove(player.getX(), player.getY(), direction)) {
			switch (direction) {
			case UP:
				currentPlayer.setY(currentPlayer.getY() - Player.MOVEMENT_UNIT);
				break;
			case DOWN:
				currentPlayer.setY(currentPlayer.getY() + Player.MOVEMENT_UNIT);
				break;
			case LEFT:
				currentPlayer.setX(currentPlayer.getX() - Player.MOVEMENT_UNIT);
				break;
			case RIGHT:
				currentPlayer.setX(currentPlayer.getX() + Player.MOVEMENT_UNIT);
				break;
			default:
				break;
			}
		}

		currentPlayer.animate(direction);
		// this.actionPerformed();
	}

	@Override
	public void stopMovementMessageReceived(Player player) {
		Player currentPlayer = this.map.getPlayers().stream().filter(p -> p.equals(player)).findFirst().orElse(null);
		if (currentPlayer != null) {
			currentPlayer.setMoving(false);
		}

		// this.actionPerformed();
	}

	@Override
	public void bombMessageReceived(Player player, DirectionMessage message) {
		Player current = this.map.getPlayers().stream().filter(p -> p.equals(player)).findFirst().orElse(null);
		current.placeBomb(this);
		// this.actionPerformed();
	}

	@Override
	public void playerDisconected(Player player) {
		this.map.getPlayers().remove(player);
		// this.actionPerformed();
	}

	@Override
	public boolean placeBomb(Bomb bomb) {
		Entity entity = this.map.getAtPosition(bomb.getX(), bomb.getY());

		if (entity == null) {
			this.map.addObject(bomb);
			return true;
		}
		return false;
	}

	@Override
	public void explodeBomb(Bomb bomb) {
		CollisionManager manager = new CollisionManager();

		List<Entity> entitiesToRemove = manager.getEntitiesToRemove(this.map.getPlayers(), this.map.getObjects(), bomb);

		addExplosionsToMap(bomb);

		this.map.getObjects().stream().filter(o -> o.isExplosion()).forEach(o -> entitiesToRemove.add(o));

		// destroy players in range

		entitiesToRemove.forEach(e -> {
			if (e.isPlayer()) {
				((Destructible) e).destroy();
			}
		});

		// destroy tiles in range
		entitiesToRemove.stream().filter(e -> e.isDestructibleTile()).forEach(t -> ((DestructibleTile) t).destroy());

		// destroy recursive bombs
		entitiesToRemove.stream().filter(o -> !o.isDestroyed() && !o.isPainted() && o.isBomb() && !o.equals(bomb))
				.forEach(b -> {
					Bomb currentBomb = (Bomb) b;
					currentBomb.setPainted(true);
					currentBomb.cancelTimer();
					currentBomb.destroy();
				});
		// destroy tiles in range
		entitiesToRemove.stream().filter(e -> e.isDestructibleTile()).forEach(t -> ((DestructibleTile) t).destroy());

		// remove players after animation
		this.removeEntitiesAfterAnimation(this.map.getPlayers(),
				entitiesToRemove.stream().filter(e -> e.isPlayer()).collect(Collectors.toList()));

		// remove entities after animation
		this.removeEntitiesAfterAnimation(this.map.getObjects(), entitiesToRemove);
	}

	@Override
	public void addObjectToMap(Entity entity) {
		this.map.addObject(entity);
	}

	private void addExplosionsToMap(Bomb bomb) {
		for (ExplosionDirection direction : bomb.getExplosionDirections()) {
			Explosion.addExplosionToMap(this, bomb, direction);
		}
	}

	private boolean crashWithLimits(double x, double y, Direction direction) {
		switch (direction) {
		case RIGHT:
			return (x + Player.MOVEMENT_UNIT) > (Window.WIDTH - Player.HEIGHT - Tile.SIZE);
		case LEFT:
			return (x - Player.MOVEMENT_UNIT - Tile.SIZE) < 0;
		case UP:
			return (y - Player.MOVEMENT_UNIT - Tile.SIZE) < 0;
		case DOWN:
			return (y + Player.MOVEMENT_UNIT) > (Window.HEIGHT - Player.HEIGHT - Tile.SIZE);
		default:
			return false;
		}
	}

	private boolean between(double i, double minValueInclusive, double maxValueInclusive) {
		return (i >= minValueInclusive && i <= maxValueInclusive);
	}

	private boolean betweenY(Entity o, double y) {
		return between(y + MOVEMENT_ERROR, o.getY() + MOVEMENT_ERROR, o.getY() + Tile.SIZE - MOVEMENT_ERROR) || between(
				y + Player.HEIGHT - MOVEMENT_ERROR, o.getY() + MOVEMENT_ERROR, o.getY() + Tile.SIZE - MOVEMENT_ERROR);
	}

	private boolean betweenX(Entity o, double x) {
		return between(x + MOVEMENT_ERROR, o.getX() + MOVEMENT_ERROR, o.getX() + Tile.SIZE - MOVEMENT_ERROR) || between(
				x + Player.HEIGHT - MOVEMENT_ERROR, o.getX() + MOVEMENT_ERROR, o.getX() + Tile.SIZE - MOVEMENT_ERROR);
	}

	private boolean canMoveRight(double x, double y) {
		return this.map.getObjects().stream().filter(o -> betweenY(o, y)).noneMatch(o -> o.getX() == x + Tile.SIZE);
	}

	private boolean canMoveLeft(double x, double y) {
		return this.map.getObjects().stream().filter(o -> betweenY(o, y)).noneMatch(o -> o.getX() == x - Tile.SIZE);
	}

	private boolean canMoveUp(double x, double y) {
		return this.map.getObjects().stream().filter(o -> betweenX(o, x)).noneMatch(o -> o.getY() == y - Tile.SIZE);
	}

	private boolean canMoveDown(double x, double y) {
		return this.map.getObjects().stream().filter(o -> betweenX(o, x)).noneMatch(o -> o.getY() == y + Tile.SIZE);
	}

	public boolean canMove(double x, double y, Direction direction) {
		if (!crashWithLimits(x, y, direction)) {
			switch (direction) {
			case RIGHT:
				return canMoveRight(x, y);
			case LEFT:
				return canMoveLeft(x, y);
			case UP:
				return canMoveUp(x, y);
			case DOWN:
				return canMoveDown(x, y);
			default:
				return false;
			}
		}
		return false;
	}

	private void removeEntitiesAfterAnimation(List<? extends Entity> sourceEntities,
			List<? extends Entity> entitiesToRemove) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				entitiesToRemove.stream().forEach(t -> {
					if (t.isBomb()) {
						((Bomb) t).cancelTimer();
					}
					t.setPainted(true);
					actionPerformed();
				});
				try {
					Thread.sleep(200);
					// sourceEntities.removeAll(entitiesToRemove); // Could be a problem
					for (Entity e : sourceEntities) {
						if (entitiesToRemove.contains(e)) {
							e.setDestroyed(true);
						}
					}
					actionPerformed();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	private void addTileToMap(int x, int y, boolean destroy) {
		if (destroy) {
			this.map.addObject(new DestructibleTile(x, y));
		} else {
			this.map.addObject(new Tile(x, y));
		}
	}

	private void generateBaseMap() {
		addTileToMap(40, 80, true);
		// addTileToMap(80,40,true);
		addTileToMap(80, 360, true);
		addTileToMap(80, 200, true);
		addTileToMap(120, 200, true);
		addTileToMap(200, 120, true);
		addTileToMap(280, 160, true);
		addTileToMap(40, 240, true);
		addTileToMap(200, 200, true);
		addTileToMap(400, 40, true);
		addTileToMap(360, 40, true);
		addTileToMap(400, 200, true);
		addTileToMap(280, 280, true);
		addTileToMap(280, 320, true);
		addTileToMap(320, 360, true);
		addTileToMap(480, 360, true);
		addTileToMap(520, 240, true);
		addTileToMap(360, 120, true);
		addTileToMap(440, 280, true);

		// Border limits
		for (int i = 0; i < 800; i += 40) {
			addTileToMap(i, 0, false);
			addTileToMap(0, i, false);
			addTileToMap(800, i, false);
			addTileToMap(i, 560, false);
		}

		// Inner non-breaking tiles
		for (int i = 80; i < 1040; i += 80) {
			addTileToMap(80, i, false);
			addTileToMap(160, i, false);
			addTileToMap(240, i, false);
			addTileToMap(320, i, false);
			addTileToMap(400, i, false);
			addTileToMap(480, i, false);
			addTileToMap(560, i, false);
			addTileToMap(640, i, false);
			addTileToMap(720, i, false);
			addTileToMap(800, i, false);
			addTileToMap(880, i, false);
		}

	}

}
