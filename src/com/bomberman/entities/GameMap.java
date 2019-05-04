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

	public boolean canMove(int x, int y, Direction direction) {
		switch (direction) {
		case RIGHT:
			return x + 1 < width && !this.objects.stream()
					.anyMatch(o -> (o.x == x + 1 && o.y == y) && (o.x == x + 1 && o.y == y && !o.canOver));
		case LEFT:
			return x - 1 >= 0 && !this.objects.stream()
					.anyMatch(o -> (o.x == x - 1 && o.y == y) && (o.x == x - 1 && o.y == y && !o.canOver));
		case UP:
			return y + 1 < height && !this.objects.stream()
					.anyMatch(o -> (o.x == x && o.y == y + 1) && (o.x == x && o.y == y + 1 && !o.canOver));
		case DOWN:
			return y - 1 >= 0 && !this.objects.stream()
					.anyMatch(o -> (o.x == x && o.y == y - 1) && (o.x == x && o.y == y - 1 && !o.canOver));
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

	public Entity getAtPosition(int x, int y) {
		return this.objects.stream().filter(o -> o.x == x && o.y == y).findFirst().orElse(null);
	}

	public void exploitEntitesInBombRange(Bomb bomb) {

		destroyEntity(bomb.x - Bomb.BOMB_RANGE, bomb.y); // destroy entity at left if it is possible
		destroyEntity(bomb.x + Bomb.BOMB_RANGE, bomb.y); // destroy entity at right if it is possible
		destroyEntity(bomb.x, bomb.y - Bomb.BOMB_RANGE); // destroy entity below if it is possible
		destroyEntity(bomb.x, bomb.y + Bomb.BOMB_RANGE); // destroy entity above if it is possible
		this.objects.remove(bomb);
	}

	private void destroyEntity(int x, int y) {
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
