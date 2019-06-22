package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.dto.DestructibleTileDto;

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
				counter++;
				
		       if (counter == 3){
		         timer.cancel();
		       }
			}
		}, 0, 100);	
	}
	
	public DestructibleTileDto toDestructibleTileDto() {
		return new DestructibleTileDto((int) x, (int) y, this.animateCount);
	}

}
