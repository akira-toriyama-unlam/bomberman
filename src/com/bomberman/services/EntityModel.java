package com.bomberman.services;

public class EntityModel {
	
	private double x;
	private double y;
	private EntityTypes entityType;
	private int idPlayer;
	
	public EntityModel(double x, double y, EntityTypes entityType, int idPlayer) {
		this.x = x;
		this.y = y;
		this.entityType = entityType;
		this.idPlayer = idPlayer;
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
	
	public int getIdPlayer() {
		return this.idPlayer;
	}
	
	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

}
