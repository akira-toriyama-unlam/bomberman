package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.dto.DestructibleTileDto;
import com.bomberman.graphics.Sprite;

public class DestructibleTile extends Tile implements Destructible {

	public DestructibleTile(int x, int y) {
		super(x, y);
	}

	@Override
	public void destroy() {
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			int counter = 0;
			@Override
			public void run() {
				incrementAnimateCount();
				sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animateCount);
				counter++;
				
		       if (counter == 3){
		         timer.cancel();
		       }
			}
		}, 0, 100);	
	}
	
	public DestructibleTileDto toDestructibleTileDto() {
		return new DestructibleTileDto((int) x, (int) y);
	}

}
