
package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 2;
	public static final int TIME_TO_EXPLOIT = 3000;
	
	private Timer timerInstace;
	private int id;
	private ExplosionListener listener;

	public Bomb(int x, int y, InteractionListener map, ExplosionListener listener, int id) {
		super(x, y, map);
		this.listener = listener;
		this.id = id;
		this.timerInstace = new Timer();
		this.timerInstace.schedule(new TimerTask() {
			@Override
			public void run() {
				destroy();
			}
		}, TIME_TO_EXPLOIT);
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
		else if (this.x != other.x || this.y != other.y)
			return false;
		return true;
	}

	@Override
	public void destroy() {
		interactionListener.bombExploded(this);
		listener.update();		
	}
	
	public void cancelTimer() {
		this.timerInstace.cancel();
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}
