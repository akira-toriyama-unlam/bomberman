package com.bomberman.entities;

public abstract class Entity {

	protected int x;
	protected int y;
	protected boolean destroyed;
	protected boolean canBeDestroyed;
	protected GameMap map;
	protected boolean canOver = false;
	
	public Entity(int x, int y, boolean canBeDestroyed, GameMap map, boolean canOver) {
		this.x = x;
		this.y = y;
		this.destroyed = false;
		this.canBeDestroyed = canBeDestroyed;
		this.map = map; 
		this.canOver = canOver;
		
		this.map.addObject(this);
	}

	public void destroy() {
		this.destroyed = true;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getX() {
		return this.x;
	}

}
