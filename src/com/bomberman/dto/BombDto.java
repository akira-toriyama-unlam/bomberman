package com.bomberman.dto;

import java.awt.Image;

import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.EntityTypes;
import com.bomberman.entities.Tile;
import com.bomberman.graphics.Sprite;

public class BombDto extends EntityDto {
	
	private Integer playerId;
	
	public BombDto(int x,
			int y,
			Integer playerId,
			int animateCount,
			boolean destroyed,
			boolean painted) {
		super(x, y, EntityTypes.BOMB, painted);
		this.x = x;
		this.y = y;
		this.playerId = playerId;
		this.sprite = Sprite.bomb;
		this.animateCount = animateCount;
		this.destroyed = destroyed;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	public Integer getPlayerId() {
		return this.playerId;
	}
	
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	
	public DestructibleTile toDestructibleTile() {
		return new DestructibleTile((int) x, (int) y);
	}
	
	public Tile toTile() {
		return new Tile(x, y);
	}

	@Override
	public Image getSprite() {
		chooseSprite();
		return sprite;
	}
	
	private void chooseSprite() {
		if(painted) {
			sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animateCount);
		} else {
			sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animateCount);
		}	
	}

}
