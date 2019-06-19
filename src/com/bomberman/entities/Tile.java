package com.bomberman.entities;

public class Tile extends Entity {
	public static final int TILE_SIZE = 40;
	
	public Tile (int x, int y, InteractionListener map) {
		super(x, y, map);
	}
	
}
