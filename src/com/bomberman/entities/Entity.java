package com.bomberman.entities;

public abstract class Entity {

	protected double x;
	protected double y;
	protected boolean destroyed;
	protected boolean canBeDestroyed; // TODO: Remove this attribute and create 2 new entities.
	protected GameMap map;
	protected boolean canOver = false;

  public Entity(double x, double y, boolean canBeDestroyed, GameMap map, boolean canOver) {
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

	public void destroy() {
		this.destroyed = true;
		if (this instanceof Bomb) {
			for(Entity entity : map.getObjects()) {
				
				//explota a la deracha
				if(entity.getX() <= (this.getX()+map.widthBomberman) && 
						(this.getX()+map.widthBomberman) <= (entity.getX() + map.widthTile) && 
						(entity.getY() + map.widthTile) > (this.getY()+map.errorMovimiento) && 
						(this.getY() + map.widthBomberman) > (entity.getY() + map.errorMovimiento) && entity.canBeDestroyed) {
					
					entity.setDestroyed(true);
					
				}
				
				//explota a la izquierda
				if(this.getX() <= (entity.getX()+map.widthTile) && (entity.getX()+map.widthTile) <= (this.getX() + map.widthBomberman) && (entity.getY() + map.widthTile) > (this.getY() + map.errorMovimiento) && (this.getY() + map.widthBomberman) > (entity.getY() + map.errorMovimiento)&& entity.canBeDestroyed) {
					entity.setDestroyed(true);	
				}
				
				//explota para abajo
				if((entity.getX()<= this.getX() && (this.getX()+map.errorMovimiento) <= (entity.getX() + map.widthTile)||
						this.getX() <= entity.getX() && entity.getX() <= (this.getX() + map.widthBomberman-map.errorMovimiento) || 
							 (this.getX()+map.errorMovimiento) <= (entity.getX() + map.widthTile) && (entity.getX() + map.widthTile) <= (this.getX() + map.widthBomberman)
							 ) && 
						   ((this.getY() + map.widthBomberman) >= (entity.getY()) && (this.getY() + map.widthBomberman) <= (entity.getY() + map.widthTile))&& entity.canBeDestroyed) {
					entity.setDestroyed(true);
				}
				
				//explota para arriba
				if((entity.getX()<= this.getX() && (this.getX()+map.errorMovimiento) <= (entity.getX() + map.widthTile)||
						this.getX() <= entity.getX() && entity.getX() <= (this.getX() + map.widthBomberman - map.errorMovimiento) || 
						 (this.getX()+map.errorMovimiento) <= (entity.getX() + map.widthTile) && (entity.getX() + map.widthTile) <= (this.getX() + map.widthBomberman)
						 ) &&  (this.getY() <= (entity.getY() + map.widthTile) && this.getY() >= (entity.getY()))&& entity.canBeDestroyed) {
						entity.setDestroyed(true);
				}
				
			}
			
			
		}
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
	
	public boolean canBeDestroy() {
		return this.canBeDestroyed;
	}
}
