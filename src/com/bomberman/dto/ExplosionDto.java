package com.bomberman.dto;

import java.awt.Image;

import com.bomberman.entities.EntityTypes;
import com.bomberman.entities.ExplosionDirection;
import com.bomberman.graphics.Sprite;

public class ExplosionDto extends EntityDto {

	public ExplosionDto(int x, int y, ExplosionDirection explosionDirection, int animateCount, boolean painted, boolean destroyed) {
		super(x, y, EntityTypes.EXPLOSION, painted, destroyed);
		this.explosionDirection = explosionDirection;
		this.animateCount = animateCount;
		chooseSprite();
	}
	
	public void chooseSprite() {
		switch(explosionDirection) {
			case RIGHT:
				sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animateCount);
				break;
			case RIGHT_MAX:	
				sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, animateCount);
				break;
			case LEFT:
				sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animateCount);
				break;
			case LEFT_MAX:	
				sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, animateCount);
				break;
			case UP:
				sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, animateCount);
				break;
			case UP_MAX:	
				sprite = Sprite.movingSprite(Sprite.explosion_vertical_up_last, Sprite.explosion_vertical_up_last1, Sprite.explosion_vertical_up_last2, animateCount);
				break;
			case DOWN:
				sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, animateCount);
				break;
			case DOWN_MAX:	
				sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, animateCount);
				break;
		default:
			break;
		}
	}
}
