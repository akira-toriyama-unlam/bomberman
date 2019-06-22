package com.bomberman.dto;

import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.EntityTypes;
import com.bomberman.graphics.Sprite;

public class DestructibleTileDto extends EntityDto {
	
	public DestructibleTileDto(int x, int y) {
		super(x, y, EntityTypes.DESTRUCTIBLE_TILE);
		this.sprite = Sprite.brick;
	}
	
	public DestructibleTile toDestructibleTile() {
		return new DestructibleTile(x, y);
	}

}
