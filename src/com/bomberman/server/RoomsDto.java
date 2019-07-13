package com.bomberman.server;

import java.util.List;

import com.bomberman.client.GameModel;

public class RoomsDto {

	private List<GameModel> rooms;

	public RoomsDto(List<GameModel> list) {
		this.rooms = list;
	}

	public RoomsDto() {
		this.rooms = null;
	}

	public List<GameModel> getRooms() {
		return rooms;
	}

	public void setRooms(List<GameModel> rooms) {
		this.rooms = rooms;
	}

}
