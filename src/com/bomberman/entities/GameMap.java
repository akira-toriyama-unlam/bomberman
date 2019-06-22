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

	public boolean addObject(Entity obj) {
		Entity entity = getAtPosition(obj.getX(), obj.getY());
 		if(entity == null) {
			this.objects.add(obj);
			return true;
		}
		return false;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public List<Entity> getObjects() {
		return this.objects;
	}
	
	public List<Entity> getObjectsNotDestroyed(){
		return this.objects.stream().filter(e -> !e.isDestroyed()).collect(Collectors.toList());
	}
	
	public List<Player> getPlayersNotDestroyed(){
		return this.players.stream().filter(e -> !e.isDestroyed()).collect(Collectors.toList());
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	 
	public Entity getAtPosition(double x, double y) {
		return this.getObjectsNotDestroyed().stream().filter(o -> o.getX() == x && o.getY() == y).findFirst().orElse(null);
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
	public boolean placeBomb(double x, double y, InteractionListener map, Player player) {
		Entity entity = getAtPosition(x, y);
		
 		if(entity == null) {
			addObject(new Bomb((int)x, (int)y, map, player));
			return true;
		}
		return false;
		
		
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
		return this.objects.stream().filter(o -> !o.isDestroyed() && betweenY(o, y)).noneMatch(o -> o.getX() == x + Tile.SIZE);  
	}
	
	private boolean canMoveLeft(double x, double y) {
		return this.objects.stream().filter(o -> !o.isDestroyed() && betweenY(o, y)).noneMatch(o -> o.getX() == x - Tile.SIZE);
	}
	
	private boolean canMoveUp(double x, double y) {
		return this.objects.stream().filter(o -> !o.isDestroyed() && betweenX(o, x)).noneMatch(o -> o.getY() == y - Tile.SIZE);  
	}
	
	private boolean canMoveDown(double x, double y) {
		return this.objects.stream().filter(o -> !o.isDestroyed() && betweenX(o, x)).noneMatch(o -> o.getY() == y + Tile.SIZE);  
	}

	@Override
	public void bombExploded(Bomb bomb) {

		List<Entity> entitiesToRemove = new ArrayList<>();
		entitiesToRemove.add(bomb); // remove current bomb from map
		
		// get players to destroy in range
		entitiesToRemove.add(getEntityToDestroyAtRight(this.getPlayers(), bomb, true));
		entitiesToRemove.add(getEntityToDestroyAtLeft(this.getPlayers(), bomb, true));
		entitiesToRemove.add(getEntityToDestroyAtUp(this.getPlayers(), bomb, true));
		entitiesToRemove.add(getEntityToDestroyAtBottom(this.getPlayers(), bomb, true));
		entitiesToRemove.addAll(getPlayersAtSite(this.getPlayers(), bomb));
	
		// get entities to destroy in range
		entitiesToRemove.add(getEntityToDestroyAtRight(this.getObjects(), bomb, false));
		entitiesToRemove.add(getEntityToDestroyAtLeft(this.getObjects(), bomb, false));
		entitiesToRemove.add(getEntityToDestroyAtUp(this.getObjects(), bomb, false));
		entitiesToRemove.add(getEntityToDestroyAtBottom(this.getObjects(), bomb, false));
		
		addExplosionsToMap(bomb);
		
		// add explosions to the list to destroy
		entitiesToRemove.addAll(this.getObjects().stream().filter(e -> e.isExplosion()).collect(Collectors.toList()));
		
		entitiesToRemove.removeAll(Collections.singleton(null)); // magic
		
		// destroy players in range
		entitiesToRemove.forEach(e -> {
			if(e.isPlayer())
				((Destructible)e).destroy();
		});
		
		// destroy tiles in range
		entitiesToRemove.stream().filter(e -> e.isDestructibleTile()).forEach(t -> ((DestructibleTile) t).destroy());
 		
 		// destroy recursive bombs
 		entitiesToRemove.stream().filter(o -> !o.isDestroyed() && o.isBomb() && !o.equals(bomb)).forEach(b -> {
			Bomb currentBomb = (Bomb) b;
			if(!currentBomb.destroyed) {
				currentBomb.setPainted(true);
				currentBomb.cancelTimer();
				currentBomb.destroy();
			} 
 		});
 		
 		//remove players after animation
 		this.removeEntitiesAfterAnimation(this.getPlayers(), entitiesToRemove.stream().filter(e -> e.isPlayer()).collect(Collectors.toList()));
 		 		
 		//remove entities after animation
 		this.removeEntitiesAfterAnimation(this.getObjects(), entitiesToRemove.stream().filter(e -> !e.isPlayer()).collect(Collectors.toList()));
		
	}
	
	private List<Entity> getPlayersAtSite(List<Player> players, Bomb b) {
		return players.stream().filter(p -> b.x == p.x && b.y == p.y).collect(Collectors.toList());
	}
	
	private Entity getEntityToDestroy(Entity e1, Entity e2) {
		if(e1 != null) {
			if(e1.isNotDestructible()) {
				return null;
			} else {
				return e1;
			}
		} else if(e2 != null) {
			if(e2.isNotDestructible()) {
				return null;
			} else {
				return e2;
			}
		}
		
		return null;
	}
	
	private Entity getEntityToDestroyAtRight(List<? extends Entity> entities, Bomb bomb, boolean arePlayers) {
		Entity entityRange1 = getEntityAtRightInRange(entities, bomb, 1);
		Entity entityRange2 = getEntityAtRightInRange(entities, bomb, 2);
		if(!arePlayers) {
			bomb.addExplotionDirection(entityRange1, entityRange2, ExplosionDirection.RIGHT_MAX, ExplosionDirection.RIGHT);
		}
		return getEntityToDestroy(entityRange1, entityRange2);
	}
	
	private Entity getEntityAtRightInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> !o.isDestroyed() && o.getY() == bomb.getY() && between(o.getX(), bomb.getX() + BOMB_ERROR, 
				bomb.getX() + (Tile.SIZE * range))).findFirst().orElse(null);
	} 
	
	private Entity getEntityToDestroyAtLeft(List<? extends Entity> entities, Bomb bomb, boolean arePlayers) {
		Entity entityRange1 = getEntityAtLeftInRange(entities, bomb, 1);
		Entity entityRange2 = getEntityAtLeftInRange(entities, bomb, 2);
		if(!arePlayers) {
			bomb.addExplotionDirection(entityRange1, entityRange2, ExplosionDirection.LEFT_MAX, ExplosionDirection.LEFT);
		}
		return getEntityToDestroy(entityRange1, entityRange2);
	}
	
	private Entity getEntityAtLeftInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> !o.isDestroyed() && o.getY() == bomb.getY() 
				&& between(o.getX(), bomb.getX() - (Tile.SIZE * range), bomb.getX() - BOMB_ERROR)).findFirst().orElse(null);
	}
	
	private Entity getEntityToDestroyAtUp(List<? extends Entity> entities, Bomb bomb, boolean arePlayers) {
		Entity entityRange1 = getEntityAtUpInRange(entities, bomb, 1);
		Entity entityRange2 = getEntityAtUpInRange(entities, bomb, 2);
		if(!arePlayers) {
			bomb.addExplotionDirection(entityRange1, entityRange2, ExplosionDirection.UP_MAX, ExplosionDirection.UP);
		}
		return getEntityToDestroy(entityRange1, entityRange2);
	}
	
	private Entity getEntityAtUpInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> !o.isDestroyed() && o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() - (Tile.SIZE * range), bomb.getY() - BOMB_ERROR)).findFirst().orElse(null);
	}
	
	private Entity getEntityToDestroyAtBottom(List<? extends Entity> entities, Bomb bomb, boolean arePlayers) {
		Entity entityRange1 = getEntityAtBottomInRange(entities, bomb, 1);
		Entity entityRange2 = getEntityAtBottomInRange(entities, bomb, 2);
		if(!arePlayers) {
			bomb.addExplotionDirection(entityRange1, entityRange2, ExplosionDirection.DOWN_MAX, ExplosionDirection.DOWN);
		}
		return getEntityToDestroy(entityRange1, entityRange2);
	}
	
	private Entity getEntityAtBottomInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> !o.isDestroyed() && o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() + BOMB_ERROR, bomb.getY() + (Tile.SIZE * range))).findFirst().orElse(null);
	}
	
	private void removeEntitiesAfterAnimation(List<? extends Entity> sourceEntities,List<? extends Entity> entitiesToRemove) {
		   Thread thread = new Thread() {
			   public void run() {
					
			 	entitiesToRemove.stream().forEach(t -> {
			 		if(t.isBomb()) {
			 			((Bomb)t).cancelTimer();
			 		}
			 		t.setPainted(true);
			 		});			
			   }
		   };  
		   thread.start();
	}
	
	private void addExplosionsToMap(Bomb bomb) {
		for(ExplosionDirection direction : bomb.getExplosionDirections()) {
			Explosion.addExplosionToMap(this, bomb, direction);
		}
	}
		
}
