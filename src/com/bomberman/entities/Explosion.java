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
				setPainted(true);
			}
		}, 0, 200);	
		 
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
		return new ExplosionDto((int) x, (int) y, direction, animateCount, painted);
	}
	
}
