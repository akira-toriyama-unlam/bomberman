
package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;


public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 2;
	public static final int TIME_TO_EXPLOIT = 2000;
	private Timer timerInstace;
	private boolean flagPrendido = true;

	private ExplosionListener listener;

	public Bomb(int x, int y, InteractionListener map, ExplosionListener listener) {
		super(x, y, map);
		this.listener = listener;
		 this.timerInstace = new Timer();
		 this.timerInstace.schedule(new TimerTask() {
			@Override
			public void run() {
				if(flagPrendido) {
					destroy();
				}
			}
		}, 3000);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bomb other = (Bomb) obj;
		if (listener == null) {
			if (other.listener != null)
				return false;
		} else if (!listener.equals(other.listener))
			return false;
		return true;
	}

	@Override
	public void destroy() {
		interactionListener.bombExploded(this);
		listener.update();		
	}
	
	public void cancelTimer() {
		this.flagPrendido = false;
		this.timerInstace.cancel();
		this.timerInstace.purge();
	}
	
}
