package com.bomberman.services;

import java.io.Serializable;
import java.util.List;

public class MapMessage implements Serializable {
	
	private List<EntityModel> objects;
	private List<PlayerModel> players;
	
	public MapMessage(List<EntityModel> objects, List<PlayerModel> players) {
		this.objects = objects;
		this.players = players;
	}
	
	public List<EntityModel> getObjects() {
		return objects;
	}

	public void setObjects(List<EntityModel> objects) {
		this.objects = objects;
	}

	public List<PlayerModel> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerModel> players) {
		this.players = players;
	}

}
