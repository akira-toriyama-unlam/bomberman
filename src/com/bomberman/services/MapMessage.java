package com.bomberman.services;

import java.io.Serializable;
import java.util.List;

import com.bomberman.dto.EntityDto;
import com.bomberman.dto.PlayerDto;

public class MapMessage implements Serializable {
	
	private List<EntityDto> entities;
	private List<PlayerDto> players;
	private String messageNumber;
	public MapMessage(List<EntityDto> entities, List<PlayerDto> players, String messageNumber) {
		this.entities = entities;
		this.players = players;
		this.messageNumber = messageNumber;
	}
	
	public List<EntityDto> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityDto> entities) {
		this.entities = entities;
	}

	public List<PlayerDto> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerDto> players) {
		this.players = players;
	}
	
	public String getMessageNumber() {
		return this.messageNumber;
	}
	
	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
	}

}
