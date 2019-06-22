package com.bomberman.entities;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.dto.BombDto;
import com.bomberman.dto.ExplosionDto;
import com.bomberman.graphics.Sprite;
import com.bomberman.server.GameActionPerformed;

public class Explosion extends Entity {

	private ExplosionDirection direction;
	
	public Explosion(double x, double y, ExplosionDirection direction) {
		super(x, y);
		this.direction = direction;
		chooseSprite();
	}
	
	public void chooseSprite() {
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			@Override
			public void run() {
				incrementAnimateCount();
				switch(direction) {
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
		}, 0, 200);	
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
	
	public static void addExplosionToMap(GameActionPerformed gameActionPerformedListener,Bomb bomb, ExplosionDirection direction) {
		double explosionX = bomb.getX();
		double explosionY = bomb.getY();
		switch(direction) {
			case RIGHT:
				explosionX += Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, direction));
				break;
			case RIGHT_MAX:
				explosionX += Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, ExplosionDirection.RIGHT));
				explosionX += Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, direction));
				break;
			case LEFT:
				explosionX -= Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, direction));
				break;
			case LEFT_MAX:	
				explosionX -= Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, ExplosionDirection.LEFT));
				explosionX -= Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, direction));
				break;
			case UP:
				explosionY -= Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, direction));
				break;
			case UP_MAX:	
				explosionY -= Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, ExplosionDirection.UP));
				explosionY -= Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, direction));
				break; 
			case DOWN:
				explosionY += Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, direction));
				break;
			case DOWN_MAX:	
				explosionY += Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, ExplosionDirection.DOWN));
				explosionY += Tile.SIZE;
				gameActionPerformedListener.addObjectToMap(new Explosion(explosionX, explosionY, direction));
				break;
		default:
			break;
		}
	}
	
	public ExplosionDto toDto() {
		return new ExplosionDto((int) x, (int) y, direction, animateCount);
	}
	
}
