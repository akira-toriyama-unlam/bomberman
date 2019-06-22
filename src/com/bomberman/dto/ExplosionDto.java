package com.bomberman.dto;

import java.awt.Image;

import com.bomberman.entities.EntityTypes;
import com.bomberman.entities.ExplosionDirection;
import com.bomberman.graphics.Sprite;

public class ExplosionDto extends EntityDto {

	public ExplosionDto(int x, int y, ExplosionDirection explosionDirection, int animateCount) {
		super(x, y, EntityTypes.EXPLOSION);
		this.explosionDirection = explosionDirection;
		this.animateCount = animateCount;
		chooseSprite();
	}
	
	public void chooseSprite() {
		switch(explosionDirection) {
			case RIGHT:
				sprite = movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2);
				break;
			case RIGHT_MAX:	
				sprite = movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2);
				break;
			case LEFT:
				sprite = movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2);
				break;
			case LEFT_MAX:	
				sprite = movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2);
				break;
			case UP:
				sprite = movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2);
				break;
			case UP_MAX:	
				sprite = movingSprite(Sprite.explosion_vertical_up_last, Sprite.explosion_vertical_up_last1, Sprite.explosion_vertical_up_last2);
				break;
			case DOWN:
				sprite = movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2);
				break;
			case DOWN_MAX:	
				sprite = movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2);
				break;
		default:
			break;
		}
	}
	
	private Image movingSprite(Image normal, Image x1, Image x2) {
		int calc = animateCount % 3;
		
		if(calc == 1) {
			return normal;
		}
			
		if(calc == 2) {
			return x1;
		}
	
		return x2;
	}
	
}
