package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

	protected String name;
	protected List<Entity> objects;
	protected int width;
	protected int height;

	public GameMap(String name, int width, int height) {
		this.name = name;
		this.objects = new ArrayList<>();
		this.width = width;
		this.height = height;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean canMove(double x, double y, Direction direction) {
		switch (direction) {
		case RIGHT:
			return !((x + 0.1) > (width - 80))
					&& !this.objects.stream().anyMatch
					(o -> (o.x <= (x+40) && x < o.x && (o.y+40) > y && (y + 40) > o.y )
							);
		case LEFT:
			return !((x - 0.1 - 40) < 0)
			&& !this.objects.stream().anyMatch
			(o -> ((o.x+40) >= x && x > o.x && (o.y+40) > y && (y + 40) > (o.y) )
					);
		case UP:
			return !((y - 0.1 - 40) < 0)
					&& !this.objects.stream().anyMatch(o -> (o.x <= x && (o.x + 40) >= x && y >= (o.y) && y <= (o.y + 40)));
		case DOWN:
			return !((y + 0.1) > (height-100))
					&& !this.objects.stream().anyMatch(o -> (o.x <= x && (o.x + 40) >= x && (y+40) >= (o.y) && (y + 40) <= (o.y + 40)));
		default:
			return false;
		}
	}

	public void addObject(Entity obj) {
		this.objects.add(obj);
	}

	public List<Entity> getObjects() {
		return this.objects;
	}

	public Entity getAtPosition(double x, double y) {
		return this.objects.stream().filter(o -> o.x == x && o.y == y).findFirst().orElse(null);
	}

	public void exploitEntitesInBombRange(Bomb bomb) {

		destroyEntity(bomb.x - Bomb.BOMB_RANGE, bomb.y); // destroy entity at left if it is possible
		destroyEntity(bomb.x + Bomb.BOMB_RANGE, bomb.y); // destroy entity at right if it is possible
		destroyEntity(bomb.x, bomb.y - Bomb.BOMB_RANGE); // destroy entity below if it is possible
		destroyEntity(bomb.x, bomb.y + Bomb.BOMB_RANGE); // destroy entity above if it is possible
		this.objects.remove(bomb);
	}

	private void destroyEntity(double x, double y) {
		Entity entity = getAtPosition(x, y);
		if (entity != null && entity.canBeDestroyed) {
			if (entity instanceof Bomb && !entity.isDestroyed()) {
				entity.destroy();
				exploitEntitesInBombRange((Bomb) entity);
			} else {
				entity.destroy();
				objects.remove(entity);
			}
		}
	}
	
}
