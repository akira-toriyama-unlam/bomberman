
package com.bomberman.entities;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.graphics.Sprite;

public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 2;
	public static final int TIME_TO_EXPLOIT = 3000;
	
	private Timer timerInstace;

	private ExplosionListener listener;

	public Bomb(int x, int y, InteractionListener map, ExplosionListener listener) {
		super(x, y, map);
		this.listener = listener;
		 this.sprite = Sprite.bomb;
		 this.changeSprite();
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
	
	public void changeSprite() {
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			@Override
			public void run() {
				animate();
				sprite = movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2);
			}
		}, 0, 200);	
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
