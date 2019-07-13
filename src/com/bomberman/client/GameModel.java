package com.bomberman.client;

public class GameModel {
	private String name;
	private boolean hasPassword;
	private int playersCount;
	
	public GameModel(String name, boolean hasPassword) {
		this.name = name;
		this.hasPassword = hasPassword;
		this.playersCount = 0;
	}
	
	public void sumPlayer() {
		this.playersCount++;
	}
	
	public void substractPlayer() {
		this.playersCount--;
	}
	
	public String getName() {
		return this.name;
	}

	public boolean hasPassword() {
		return this.hasPassword;
	}
	
	public int getPlayerCount() {
		return this.playersCount;
	}
}
