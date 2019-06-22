package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.graphics.Sprite;

public class DestructibleTile extends Tile implements Destructible {

	public DestructibleTile(int x, int y) {
		super(x, y);
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
				sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate);
				counter++;
				
		       if (counter == 3){
		         timer.cancel();
		       }
			}
		}, 0, 100);	
	}

}
