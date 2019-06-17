
package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;


public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 2;
	public static final int TIME_TO_EXPLOIT = 2000;

	private ExplosionListener listener;

	public Bomb(int x, int y, InteractionListener map, ExplosionListener listener) {
		super(x, y, map);
		this.listener = listener;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				destroy();
			}
		}, 3000);
	}

	@Override
	public void destroy() {
		interactionListener.bombExploded(this);
		listener.update(); // Notify player.
		
	}
}
