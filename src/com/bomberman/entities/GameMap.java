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
				return !this.objects.stream().anyMatch(o -> o.x == x+1 && o.y == y);
			case LEFT:
				return !this.objects.stream().anyMatch(o -> o.x == x-1 && o.y == y);
			case UP:
				return !this.objects.stream().anyMatch(o -> o.x == x && o.y == y+1);
			case DOWN:
				return !this.objects.stream().anyMatch(o -> o.x == x && o.y == y-1);
			default:
				return false;
		}
	}

	public void addObject(Entity obj) {
		this.objects.add(obj);
	}

	public Entity getAtPosition(int x, int y) {
		return this.objects.stream().filter(o -> o.x == x && o.y == y).findFirst().orElse(null);
	}

}
