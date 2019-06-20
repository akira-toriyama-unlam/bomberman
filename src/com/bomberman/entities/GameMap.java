package com.bomberman.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import com.bomberman.graphics.Sprite;

public class GameMap implements InteractionListener {

	protected String name;
	protected List<Entity> objects;
	protected List<Player> players;
	protected int width;
	protected int height;	protected final static int MOVEMENT_ERROR = 2; // This constant is used for fixing the movement into the Jpanel.
	protected final static double BOMB_ERROR = 0.01; // This constant is used for fixing bomb range error.

	public GameMap(String name, int width, int height) {
		this.name = name;
		this.objects = new ArrayList<>();
		this.players = new ArrayList<>();
		this.width = width;
		this.height = height;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean canMove(double x, double y, Direction direction) {
		if(!crashWithLimits(x, y, direction)) {
			switch (direction) {
				case RIGHT:
					return canMoveRight(x, y);
				case LEFT:
					return canMoveLeft(x,y); 
				case UP:
					return canMoveUp(x,y);
				case DOWN:
					return canMoveDown(x,y); 
				default:
					return false;
			}
		}
		return false;
	}

	public void addObject(Entity obj) {
		Entity entity = getAtPosition(obj.getX(), obj.getY());
		if(entity == null) {
			this.objects.add(obj);
		}
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public List<Entity> getObjects() {
		return this.objects;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	 
	public Entity getAtPosition(double x, double y) {
		return this.objects.stream().filter(o -> o.x == x && o.y == y).findFirst().orElse(null);
	}
	
	@Override
	public void movement(Player player, Direction direction) {
		if (this.canMove(player.x, player.y, direction)) {
			switch (direction) {
			case UP:
				player.y -= Player.MOVEMENT_UNIT;
				break;
			case DOWN:
				player.y += Player.MOVEMENT_UNIT;
				break;
			case LEFT:
				player.x -= Player.MOVEMENT_UNIT;
				break;
			case RIGHT:
				player.x += Player.MOVEMENT_UNIT;
				break;
			}
		}
	}
	
	@Override
	public void bombPlaced(Bomb bomb) {
		objects.add(bomb);
	}

	private boolean crashWithLimits(double x, double y, Direction direction) {
		switch (direction) {
			case RIGHT:
				return (x + Player.MOVEMENT_UNIT) > (width - Player.HEIGHT - Tile.SIZE);
			case LEFT:
				return (x - Player.MOVEMENT_UNIT - Tile.SIZE) < 0;
			case UP:
				return (y - Player.MOVEMENT_UNIT - Tile.SIZE) < 0;
			case DOWN:
				return (y + Player.MOVEMENT_UNIT) > (height - Player.HEIGHT - Tile.SIZE);
			default:
				return false;
		}
	}
	
	private boolean between(double i,  double minValueInclusive, double maxValueInclusive) {
	    return (i >= minValueInclusive && i <= maxValueInclusive);
	}
	
	private boolean betweenY(Entity o, double y) {
		return between(y + MOVEMENT_ERROR, o.getY() + MOVEMENT_ERROR, o.getY() + Tile.SIZE - MOVEMENT_ERROR) ||
				between(y + Player.HEIGHT - MOVEMENT_ERROR, o.getY() + MOVEMENT_ERROR, o.getY() + Tile.SIZE - MOVEMENT_ERROR);
	}
	
	private boolean betweenX(Entity o, double x) {
		return between(x + MOVEMENT_ERROR, o.getX() + MOVEMENT_ERROR, o.getX() + Tile.SIZE - MOVEMENT_ERROR) ||
				between(x + Player.HEIGHT - MOVEMENT_ERROR, o.getX() + MOVEMENT_ERROR, o.getX() + Tile.SIZE - MOVEMENT_ERROR);
	}
	
	private boolean canMoveRight(double x, double y) {
		return this.objects.stream().filter(o -> betweenY(o, y)).noneMatch(o -> o.getX() == x + Tile.SIZE);  
	}
	
	private boolean canMoveLeft(double x, double y) {
		return this.objects.stream().filter(o -> betweenY(o, y)).noneMatch(o -> o.getX() == x - Tile.SIZE);
	}
	
	private boolean canMoveUp(double x, double y) {
		return this.objects.stream().filter(o -> betweenX(o, x)).noneMatch(o -> o.getY() == y - Tile.SIZE);  
	}
	
	private boolean canMoveDown(double x, double y) {
		return this.objects.stream().filter(o -> betweenX(o, x)).noneMatch(o -> o.getY() == y + Tile.SIZE);  
	}

	@Override
	public void bombExploded(Bomb bomb) {

		List<Entity> entitiesToRemove = new ArrayList<>();
		entitiesToRemove.add(bomb); // remove current bomb from map
		
		// destroy players in range
		entitiesToRemove.add(getEntityToDestroyAtRight(this.getPlayers(), bomb));
		entitiesToRemove.add(getEntityToDestroyAtLeft(this.getPlayers(), bomb));
		entitiesToRemove.add(getEntityToDestroyAtUp(this.getPlayers(), bomb));
		entitiesToRemove.add(getEntityToDestroyAtBottom(this.getPlayers(), bomb));
		entitiesToRemove.addAll(getPlayersAtSite(this.getPlayers(), bomb));
	
		// destroy destructibles entities in range
		entitiesToRemove.add(getEntityToDestroyAtRight(this.getObjects(), bomb));
		entitiesToRemove.add(getEntityToDestroyAtLeft(this.getObjects(), bomb));
		entitiesToRemove.add(getEntityToDestroyAtUp(this.getObjects(), bomb));
		entitiesToRemove.add(getEntityToDestroyAtBottom(this.getObjects(), bomb));
		
		addExplosionsToMap(bomb);
		
		entitiesToRemove.addAll(this.getObjects().stream().filter(e -> e.isExplosion()).collect(Collectors.toList()));
		
		entitiesToRemove.removeAll(Collections.singleton(null)); // magic
		
		// destroy players in range
		entitiesToRemove.forEach(e -> {
			if(e.isPlayer())
				((Destructible)e).destroy();
		});
		
		List<Entity> list = entitiesToRemove.stream().filter(e -> e.isDestructible() && e instanceof DestructibleTile).collect(Collectors.toList());
 		list.forEach(t -> ((DestructibleTile) t).destroy());
 		
 		// destroy recursive bombs
 		entitiesToRemove.stream().filter(o -> o.isBomb() && !o.equals(bomb)).forEach(b -> {
			Bomb currentBomb = (Bomb) b;
			currentBomb.cancelTimer();
			currentBomb.destroy();
			this.getObjects().remove(currentBomb);
 		});
 		
 		//remove players after animation
 		this.removeEntitiesAfterAnimation(this.getPlayers(), entitiesToRemove.stream().filter(e -> e.isPlayer()).collect(Collectors.toList()));
 		 		
 		//remove entities after animation
 		this.removeEntitiesAfterAnimation(this.getObjects(), entitiesToRemove);
		
	}
	
	private List<Entity> getPlayersAtSite(List<Player> players, Bomb b) {
		return players.stream().filter(p -> b.x == p.x && b.y == p.y).collect(Collectors.toList());
	}
	
	private Entity getEntityToDestroyAtRight(List<? extends Entity> entities, Bomb bomb) {
		Entity entityRange1 = getEntityAtRightInRange(entities, bomb, 1);
		Entity entityRange2 = getEntityAtRightInRange(entities, bomb, 2);
		bomb.addExplotionDirection(entityRange1, entityRange2, ExplosionDirection.RIGHT_MAX, ExplosionDirection.RIGHT); 
		return entityRange1 != null ? entityRange1 : entityRange2;
	}
	
	private Entity getEntityAtRightInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> o.getY() == bomb.getY() && between(o.getX(), bomb.getX() + BOMB_ERROR, 
				bomb.getX() + (Tile.SIZE * range))).findFirst().orElse(null);
	} 
	
	private Entity getEntityToDestroyAtLeft(List<? extends Entity> entities, Bomb bomb) {
		Entity entityRange1 = getEntityAtLeftInRange(entities, bomb, 1);
		Entity entityRange2 = getEntityAtLeftInRange(entities, bomb, 2);
		bomb.addExplotionDirection(entityRange1, entityRange2, ExplosionDirection.LEFT_MAX, ExplosionDirection.LEFT); 
		return entityRange1 != null ? entityRange1 : entityRange2;
	}
	
	private Entity getEntityAtLeftInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> o.getY() == bomb.getY() 
				&& between(o.getX(), bomb.getX() - (Tile.SIZE * range), bomb.getX() - BOMB_ERROR)).findFirst().orElse(null);
	}
	
	private Entity getEntityToDestroyAtUp(List<? extends Entity> entities, Bomb bomb) {
		Entity entityRange1 = getEntityAtUpInRange(entities, bomb, 1);
		Entity entityRange2 = getEntityAtUpInRange(entities, bomb, 2);
		bomb.addExplotionDirection(entityRange1, entityRange2, ExplosionDirection.UP_MAX, ExplosionDirection.UP); 
		return entityRange1 != null ? entityRange1 : entityRange2;
	}
	
	private Entity getEntityAtUpInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() - (Tile.SIZE * range), bomb.getY() - BOMB_ERROR)).findFirst().orElse(null);
	}
	
	private Entity getEntityToDestroyAtBottom(List<? extends Entity> entities, Bomb bomb) {
		Entity entityRange1 = getEntityAtBottomInRange(entities, bomb, 1);
		Entity entityRange2 = getEntityAtBottomInRange(entities, bomb, 2);
		bomb.addExplotionDirection(entityRange1, entityRange2, ExplosionDirection.DOWN_MAX, ExplosionDirection.DOWN); 
		return entityRange1 != null ? entityRange1 : entityRange2;
	}
	
	private Entity getEntityAtBottomInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() + BOMB_ERROR, bomb.getY() + (Tile.SIZE * range))).findFirst().orElse(null);
	}
	
	private void removeEntitiesAfterAnimation(List<? extends Entity> sourceEntities,List<? extends Entity> entitiesToRemove) {
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			@Override
			public void run() {
				sourceEntities.removeAll(entitiesToRemove);			
			}
		}, 300);
	}
	
	private void addExplosionsToMap(Bomb bomb) {
		for(ExplosionDirection direction : bomb.getExplosionDirections()) {
			Explosion.addExplosionToMap(this, bomb, direction);
		}
	}
		
}
