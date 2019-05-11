package com.bomberman.entities;

public abstract class Entity {

	protected int x;
	protected int y;
	protected boolean destroyed;
	protected boolean canBeDestroyed; // TODO: Remove this attribute and create 2 new entities.
	protected GameMap map;
	protected boolean canOver = false;

	public Entity(int x, int y, boolean canBeDestroyed, GameMap map, boolean canOver) {
		this.x = x;
		this.y = y;
		this.destroyed = false;
		this.canBeDestroyed = canBeDestroyed;
		this.map = map;
		this.canOver = canOver;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
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

	public boolean isDestroyed() {
		return this.destroyed;
	}

}
