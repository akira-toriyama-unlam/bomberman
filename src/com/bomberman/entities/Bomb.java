package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {

	public static final int BOMB_RANGE = 1;

	public static final int TIME_TO_EXPLOIT = 2000;

	private ExplosionListener listener;

	public Bomb(int x, int y, GameMap map, ExplosionListener listener) {
		super(x, y, true, map);
		this.listener = listener;
		this.exploit();
	}
	
	

	public void exploit() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(getTimerTask(this,map,listener), 0, TIME_TO_EXPLOIT);
		
	}

	private TimerTask getTimerTask(Bomb bomb, GameMap map,ExplosionListener listener) {
		return new TimerTask() {
			@Override
			public void run() {
				destroy();
				map.exploitEntitesInBombRange(bomb);
				listener.update(); // Notify player.
			}
		};
	}
}
