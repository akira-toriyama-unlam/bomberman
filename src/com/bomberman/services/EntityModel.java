package com.bomberman.services;

public class EntityModel {
	
	private double x;
	private double y;
	private EntityTypes entityType;
	
	public EntityModel(double x, double y, EntityTypes entityType) {
		this.x = x;
		this.y = y;
		this.entityType = entityType;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public EntityTypes getEntityType() {
		return entityType;
	}

	public void setY(EntityTypes entityType) {
		this.entityType = entityType;
	}

}
