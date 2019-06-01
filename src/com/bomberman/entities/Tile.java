package com.bomberman.entities;

public class Tile extends Entity {

	public static final double TILE_SIZE = 40;
	
	public Tile (int x, int y, GameMap map, boolean canOver) {
		super(x, y, map, canOver);
	}
	
}
