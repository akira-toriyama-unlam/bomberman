package com.bomberman.services;

public class PlayerModel {
	private double x;
	private double y;
	private PlayerTypes playerType;
	private int id;

	public PlayerModel(double x, double y, PlayerTypes playerType, int id) {
		this.x = x;
		this.y = y;
		this.playerType = playerType;
		this.id = id;
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
	
	public PlayerTypes getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerTypes playerType) {
		this.playerType = playerType;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}
