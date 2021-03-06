package com.bomberman.dto;

import com.bomberman.entities.EntityTypes;
import com.bomberman.entities.Tile;
import com.bomberman.graphics.Sprite;

public class TileDto extends EntityDto {
	
	public TileDto(int x, int y, boolean painted, boolean destroyed) {
		super(x, y, EntityTypes.TILE, painted, destroyed);
		this.sprite = Sprite.wall; 
	}
	
	public Tile toTile() {
		return new Tile(x, y);
	}
	
}
