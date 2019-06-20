package com.bomberman.entities;

import com.bomberman.graphics.Sprite;

public class Tile extends Entity {
	public static final int SIZE = 40;
	
	public Tile (int x, int y, InteractionListener map) {
		super(x, y, map);
		this.sprite = Sprite.wall; 
	}
	
}
