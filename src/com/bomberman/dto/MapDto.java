package com.bomberman.dto;

import java.util.List;
import java.util.stream.Collectors;

public class MapDto {
	
	private List<EntityDto> entities;
	private List<PlayerDto> players;
	
	public List<PlayerDto> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<PlayerDto> players) {
		this.players = players;
	}
	
	public List<EntityDto> getEntities() {
		return entities;
	}
	public void setEntites(List<EntityDto> entities) {
		this.entities = entities;
	}
	
	public boolean addObject(EntityDto obj) {
		EntityDto entity = getAtPosition(obj.getX(), obj.getY());
		if(entity == null) {
			this.entities.add(obj);
			return true;
		}
		return false;
	}
	
	public void addPlayer(PlayerDto player) {
		this.players.add(player);
	}

	public EntityDto getAtPosition(double x, double y) {
		return this.entities.stream().filter(e -> e.getX() == x && e.getY() == y).findFirst().orElse(null);
	}
	
	public List<EntityDto> getObjectsNotDestroyed(){
		return this.entities.stream().filter(e -> !e.isDestroyed()).collect(Collectors.toList());
	}
	
	public List<PlayerDto> getPlayersNotDestroyed(){
		return this.players.stream().filter(e -> !e.isDestroyed()).collect(Collectors.toList());
	}

}
