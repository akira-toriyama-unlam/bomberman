package com.bomberman.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.Entity;
import com.bomberman.entities.ExplosionDirection;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class CollisionManager {
	
	public List<Entity> getEntitiesToRemove(List<Player> players, List<Entity> objects, Bomb bomb) {
		List<Entity> entitiesToRemove = new ArrayList<>();
		entitiesToRemove.add(bomb); // remove current bomb from map
		
		// get players to destroy in range
		entitiesToRemove.add(getEntityToDestroyAtRight(players, bomb, true));
		entitiesToRemove.add(getEntityToDestroyAtLeft(players, bomb, true));
		entitiesToRemove.add(getEntityToDestroyAtUp(players, bomb, true));
		entitiesToRemove.add(getEntityToDestroyAtBottom(players, bomb, true));
		entitiesToRemove.addAll(getPlayersAtSite(players, bomb));
	
		// get entities to destroy in range
		entitiesToRemove.add(getEntityToDestroyAtRight(objects, bomb, false));
		entitiesToRemove.add(getEntityToDestroyAtLeft(objects, bomb, false));
		entitiesToRemove.add(getEntityToDestroyAtUp(objects, bomb, false));
		entitiesToRemove.add(getEntityToDestroyAtBottom(objects, bomb, false));
		
		// add explosions to the list to destroy
		entitiesToRemove.addAll(objects.stream().filter(e -> e.isExplosion()).collect(Collectors.toList()));
		
		entitiesToRemove.removeAll(Collections.singleton(null)); // magic
		
		return entitiesToRemove;
	}
	
	private List<Entity> getPlayersAtSite(List<Player> players, Bomb b) {
		return players.stream().filter(p -> b.getX() == p.getX() && b.getY() == p.getY()).collect(Collectors.toList());
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
		return entities.stream().filter(o -> o.getY() == bomb.getY() && between(o.getX(), bomb.getX() + GameMap.BOMB_ERROR, 
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
		return entities.stream().filter(o -> o.getY() == bomb.getY() 
				&& between(o.getX(), bomb.getX() - (Tile.SIZE * range), bomb.getX() - GameMap.BOMB_ERROR)).findFirst().orElse(null);
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
		return entities.stream().filter(o -> o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() - (Tile.SIZE * range), bomb.getY() - GameMap.BOMB_ERROR)).findFirst().orElse(null);
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
		return entities.stream().filter(o -> o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() + GameMap.BOMB_ERROR, bomb.getY() + (Tile.SIZE * range))).findFirst().orElse(null);
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
	
	private boolean between(double i,  double minValueInclusive, double maxValueInclusive) {
	    return (i >= minValueInclusive && i <= maxValueInclusive);
	}
	
}
