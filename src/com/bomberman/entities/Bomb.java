package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {

	public static final int BOMB_RANGE = 1;
	
	public static final int TIME_TO_EXPLOIT = 2000;

	public Bomb(int x, int y, GameMap map) {
		super(x, y, true, map);
	}

	public void exploit() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(getTimerTask(), 0, TIME_TO_EXPLOIT);
		map.exploitEntitesInBombRange(this);
	}

	private TimerTask getTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				destroy();
			}
		};
	}

}
