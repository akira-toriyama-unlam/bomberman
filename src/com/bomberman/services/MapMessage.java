package com.bomberman.services;

import java.io.Serializable;
import java.util.List;

import com.bomberman.entities.Entity;

public class MapMessage implements Serializable {
	
	private List<Entity> objects;
	private List<PlayerModel> players;
	
	public MapMessage(List<Entity> objects, List<PlayerModel> players) {
		this.objects = objects;
		this.players = players;
	}
	
	public List<Entity> getObjects() {
		return objects;
	}

	public void setObjects(List<Entity> objects) {
		this.objects = objects;
	}

	public List<PlayerModel> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerModel> players) {
		this.players = players;
	}

}
