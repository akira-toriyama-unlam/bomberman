package com.bomberman.services;

import com.bomberman.entities.InteractionListener;

public class PlayerModel {
	private double x;
	private double y;
	InteractionListener map;
	PlayerTypes playerType;

	public PlayerModel(double x, double y, PlayerTypes playerType) {
		this.x = x;
		this.y = y;
		this.playerType = playerType;
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
}
