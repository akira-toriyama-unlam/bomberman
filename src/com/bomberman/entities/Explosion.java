package com.bomberman.entities;

import java.awt.Image;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.graphics.Sprite;

public class Explosion extends Entity {

	private ExplosionDirection direction;
	private Date bombTimeCreated;
	private Thread spriteThread;
	
	public Explosion(double x, double y, InteractionListener map, ExplosionDirection direction, Date bombTimeCreated) {
		super(x, y, map);
		this.direction = direction;
		this.bombTimeCreated = bombTimeCreated;
		chooseSprite();
	}
	
	public void chooseSprite() {
		spriteThread = new Thread() {
		    @Override
			public void run(){
		    	while(true) {
		    		animate();
		    		if((new Date().getTime() - bombTimeCreated.getTime()) > Bomb.TIME_TO_EXPLOIT){
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
				}
		    }
		};
		spriteThread.start();
	}
	
	private Image movingSprite(Image normal, Image x1, Image x2) {
		int calc = animate % 3;
		
		if(calc == 1) {
			return normal;
		}
			
		if(calc == 2) {
			return x1;
		}
	
		return x2;
	}
	
	static void addExplosionToMap(GameMap map,Bomb bomb, ExplosionDirection direction) {
		double explosionX = bomb.getX();
		double explosionY = bomb.getY();
		switch(direction) {
			case RIGHT:
				explosionX += Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, direction, bomb.getTimeCreated()));
				break;
			case RIGHT_MAX:	
				explosionX += Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, ExplosionDirection.RIGHT, bomb.getTimeCreated()));
				explosionX += Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, direction, bomb.getTimeCreated()));
				break;
			case LEFT:
				explosionX -= Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, direction, bomb.getTimeCreated()));
				break;
			case LEFT_MAX:	
				explosionX -= Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, ExplosionDirection.LEFT, bomb.getTimeCreated()));
				explosionX -= Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, direction, bomb.getTimeCreated()));
				break;
			case UP:
				explosionY -= Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, direction, bomb.getTimeCreated()));
				break;
			case UP_MAX:	
				explosionY -= Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, ExplosionDirection.UP, bomb.getTimeCreated()));
				explosionY -= Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, direction, bomb.getTimeCreated()));
				break;
			case DOWN:
				explosionY += Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, direction, bomb.getTimeCreated()));
				break;
			case DOWN_MAX:	
				explosionY += Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, ExplosionDirection.DOWN, bomb.getTimeCreated()));
				explosionY += Tile.SIZE;
				map.addObject(new Explosion(explosionX, explosionY, map, direction, bomb.getTimeCreated()));
				break;
		default:
			break;
		}
	}
	
	
	
}
