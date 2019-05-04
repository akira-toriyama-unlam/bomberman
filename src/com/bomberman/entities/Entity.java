package com.bomberman.entities;

public abstract class Entity {

	protected int x;
	protected int y;
	protected boolean destroyed;
	protected boolean canBeDestroyed;
	protected GameMap map;

	public Entity(int x, int y, boolean canBeDestroyed, GameMap map) {
		this.x = x;
		this.y = y;
		this.destroyed = false;
		this.canBeDestroyed = canBeDestroyed;
		this.map = map;
	}
	
	

	public boolean isDestroyed() {
		return destroyed;
	}



	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}



	public void destroy() {
		this.destroyed = true;
	}

}
