package com.bomberman.multiplayer;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.CollisionManager;
import com.bomberman.entities.Destructible;
import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.Direction;
import com.bomberman.entities.Entity;
import com.bomberman.entities.Explosion;
import com.bomberman.entities.ExplosionDirection;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;
import com.bomberman.graphics.JGraphicWindow;
import com.bomberman.services.DirectionMessage;


public class ScoreBoard extends Observable implements GameActionPerformed {
	
	private GameMap map;
	private final static int MOVEMENT_ERROR = 2;
	
	public ScoreBoard() {
		this.map = new GameMap(this);
		this.generateBaseMap();
		//this.notifyAllObservers(this.map);
		
		//initializeReSend();
	}
	
	@Override
	public void actionPerformed() {
		// Update status the notify
		System.out.println("Trying to notify all observers from EMPTY");
		this.setChanged();
        //this.notifyObservers(this.gameMap);
		this.notifyObservers(map); // just for test
	}
	
	@Override
	public Player newPlayer() {
		int playersCount = this.map.getPlayers().size(); 
		Player player = null;
		switch(playersCount) {
		case 0:
			player = new Player(40, 40, this, 1);
			this.map.addPlayer(player);
			break;
		case 1:
			player = new Player(760, 40, this, 2);
			this.map.addPlayer(player);
			break;
		case 2:
			player = new Player(40, 520, this, 3);
			this.map.addPlayer(player);
			break;
		case 3:
			player = new Player(760, 520, this, 4);
			this.map.addPlayer(player);
			break;
			
		}
		
		return player;
	}
		
	@Override
	public void movementMessageReceived(Player player, DirectionMessage message) {
		Direction direction = message.getDirection();
		Player current = this.map.getPlayers().stream().filter(p -> p.equals(player)).findFirst().orElse(null);
		if (this.canMove(player.getX(), player.getY(), direction)) {
			switch (direction) {
			case UP:
				current.setY(current.getY() - Player.MOVEMENT_UNIT);
				break;
			case DOWN:
				current.setY(current.getY() + Player.MOVEMENT_UNIT);
				break;
			case LEFT:
				current.setX(current.getX() - Player.MOVEMENT_UNIT);
				break;
			case RIGHT:
				current.setX(current.getX() + Player.MOVEMENT_UNIT);
				break;
			}
			this.actionPerformed();
		}
	}
	
	@Override
	public void bombMessageReceived(Player player, DirectionMessage message) {
		Player current = this.map.getPlayers().stream().filter(p -> p.equals(player)).findFirst().orElse(null);
		current.placeBomb(this);
		
		
		this.actionPerformed();
	}

	@Override
	public void playerDisconected(Player player) {
		this.map.getPlayers().remove(player);
		this.actionPerformed();
	}

	@Override
	public boolean placeBomb(Bomb bomb) {
		return map.addObject(bomb);
	}
	
	@Override
	public void explodeBomb(Bomb bomb) {
		CollisionManager manager = new CollisionManager();
		List<Entity> entitiesToRemove = manager.getEntitiesToRemove(this.map.getPlayers(), this.map.getObjects(), bomb);
		
		addExplosionsToMap(bomb);
		
		// destroy players in range
		entitiesToRemove.forEach(e -> {
			if(e.isPlayer())
				((Destructible)e).destroy();
		});
		
		// destroy tiles in range
		entitiesToRemove.stream().filter(e -> e.isDestructibleTile()).forEach(t -> ((DestructibleTile) t).destroy());
 		
 		// destroy recursive bombs
 		entitiesToRemove.stream().filter(o -> o.isBomb() && !o.equals(bomb)).forEach(b -> {
			Bomb currentBomb = (Bomb) b;
			currentBomb.cancelTimer();
			currentBomb.destroy();
 		});
 		
 		//remove players after animation
 		this.removeEntitiesAfterAnimation(this.map.getPlayers(), entitiesToRemove.stream().filter(e -> e.isPlayer()).collect(Collectors.toList()));
 		 		
 		//remove entities after animation
 		this.removeEntitiesAfterAnimation(this.map.getObjects(), entitiesToRemove);
	}
	

	@Override
	public void addObjectToMap(Entity entity) {
		this.map.addObject(entity);
	}
	
	private void addExplosionsToMap(Bomb bomb) {
		for(ExplosionDirection direction : bomb.getExplosionDirections()) {
			Explosion.addExplosionToMap(this, bomb, direction);
		}
	}
	
	private boolean crashWithLimits(double x, double y, Direction direction) {
		switch (direction) {
			case RIGHT:
				return (x + Player.MOVEMENT_UNIT) > (JGraphicWindow.WIDTH - Player.HEIGHT - Tile.SIZE);
			case LEFT:
				return (x - Player.MOVEMENT_UNIT - Tile.SIZE) < 0;
			case UP:
				return (y - Player.MOVEMENT_UNIT - Tile.SIZE) < 0;
			case DOWN:
				return (y + Player.MOVEMENT_UNIT) > (JGraphicWindow.HEIGHT - Player.HEIGHT - Tile.SIZE);
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
	
	private void removeEntitiesAfterAnimation(List<? extends Entity> sourceEntities,List<? extends Entity> entitiesToRemove) {
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			@Override
			public void run() {
				sourceEntities.removeAll(entitiesToRemove);			
			}
		}, 300);
	}
	
	private void addTileToMap(int x, int y, GameMap m, boolean destroy) {
		if (destroy) {
			this.map.addObject(new DestructibleTile(x, y));
		} else {
			this.map.addObject(new Tile(x, y));
		}
	}
	
	private void generateBaseMap() {
		addTileToMap(40,80,this.map,true);
		//addTileToMap(80,40,this.map,true);
		addTileToMap(80,360,this.map,true);
		addTileToMap(80,200,this.map,true);
		addTileToMap(120,200,this.map,true);
		addTileToMap(200,120,this.map,true);
		addTileToMap(280,160,this.map,true);
		addTileToMap(40,240,this.map,true);
		addTileToMap(200,200,this.map,true);
		addTileToMap(400,40,this.map,true);
		addTileToMap(360,40,this.map,true);
		addTileToMap(400,200,this.map,true);
		addTileToMap(280,280,this.map,true);
		addTileToMap(280,320,this.map,true);
		addTileToMap(320,360,this.map,true);
		addTileToMap(480,360,this.map,true);
		addTileToMap(520,240,this.map,true);
		addTileToMap(360,120,this.map,true);
		addTileToMap(440,280,this.map,true);
		
		// Border limits
		for(int i = 0; i< 800; i+=40) {
			addTileToMap(i,0,this.map,false);
			addTileToMap(0,i,this.map,false);
			addTileToMap(800,i,this.map,false);
			addTileToMap(i,560,this.map,false);
		}
		
		// Inner non-breaking tiles
		for(int i = 80; i< 1040; i+=80) {
			addTileToMap(80,i,this.map,false);
			addTileToMap(160,i,this.map,false);
			addTileToMap(240,i,this.map,false);
			addTileToMap(320,i,this.map,false);
			addTileToMap(400,i,this.map,false);
			addTileToMap(480,i,this.map,false);
			addTileToMap(560,i,this.map,false);
			addTileToMap(640,i,this.map,false);
			addTileToMap(720,i,this.map,false);
			addTileToMap(800,i,this.map,false);
			addTileToMap(880,i,this.map,false);
		}

	}

}
