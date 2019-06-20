package com.bomberman.entities;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.graphics.Sprite;

public class DestructibleTile extends Tile implements Destructible {

	public DestructibleTile(int x, int y, GameMap map) {
		super(x, y, map);
		sprite = Sprite.brick;
	}

	@Override
	public void destroy() {
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			int counter = 0;
			@Override
			public void run() {
				animate();
				sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
				counter++;
				
		       if (counter == 3){
		    	 // setDestroyed(true);
		         timer.cancel();
		       }
			}
		}, 0, 100);	
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

}
