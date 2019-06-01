
package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {

	public static final int BOMB_RANGE = 1;
	public static final int TIME_TO_EXPLOIT = 2000;

	private ExplosionListener listener;

	public Bomb(double x, double y, GameMap map, ExplosionListener listener) {
		super(x, y, true, map, true);
		this.listener = listener;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				exploit();
			}
		}, 3000);
	}

	public void exploit() {
		destroy();
		listener.update(); // Notify player.
	}

}
