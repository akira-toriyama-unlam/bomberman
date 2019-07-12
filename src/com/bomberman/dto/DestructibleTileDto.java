package com.bomberman.dto;

import java.awt.Image;

import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.EntityTypes;
import com.bomberman.graphics.Sprite;

public class DestructibleTileDto extends EntityDto {
	
	public DestructibleTileDto(int x, int y, int animateCount, boolean painted, boolean destroyed) {
		super(x, y, EntityTypes.DESTRUCTIBLE_TILE, painted, destroyed);
		this.sprite = Sprite.brick;
		this.animateCount = animateCount;
	}
	
	public DestructibleTile toDestructibleTile() {
		return new DestructibleTile(x, y);
	}

	public void chooseSprite() {
		if(animateCount != 0) {
			sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animateCount);	
		}
	}
	
	@Override
	public Image getSprite() {
		chooseSprite();
		return this.sprite;
	}
	
}
