package com.bomberman.entities;

import com.bomberman.dto.TileDto;

public class Tile extends Entity {
	
	public static final int SIZE = 40;
	
	public Tile (int x, int y) {
		super(x, y);
	}
	
	public TileDto toDto() {
		return new TileDto((int) x, (int) y, painted, destroyed);
	}
	
}
