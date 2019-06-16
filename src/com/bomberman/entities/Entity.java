package com.bomberman.entities;

public abstract class Entity {

	protected double x;
	protected double y;
	protected boolean destroyed;
	protected GameMap map;
	protected boolean canOver = false;

  public Entity(double x, double y, GameMap map, boolean canOver) {
		this.x = x;
		this.y = y;
		this.destroyed = false;
		this.map = map;
		this.canOver = canOver;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}

	public boolean isDestroyed() {
		return this.destroyed;
	}
	
}
