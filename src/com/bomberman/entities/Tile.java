package com.bomberman.entities;

import com.bomberman.graphics.Sprite;

public class Tile extends Entity {
	public static final int SIZE = 40;
	
	public Tile (int x, int y) {
		super(x, y, null);
		this.sprite = Sprite.wall; 
	}
	
}
