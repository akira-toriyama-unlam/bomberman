package com.bomberman.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.graphics.Sprite;
import com.bomberman.multiplayer.GameActionPerformed;

public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 2;
	public static final int TIME_TO_EXPLOIT = 3000;
	
	private Set<ExplosionDirection> explosionDirections;
	private Timer timerInstace;
	private int id;
	private ExplosionListener listener;

	public Bomb(int x, int y, GameActionPerformed gameActionPerformedListener, ExplosionListener listener, int id) {
		super(x, y, gameActionPerformedListener);
		this.listener = listener;
		this.id = id;
		this.explosionDirections = new HashSet<>();
		this.sprite = Sprite.bomb;
		this.chooseSprite();
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
	
	public void addExplotionDirection(Entity range1, Entity range2, ExplosionDirection max, ExplosionDirection min) {
		if(range1 == null) {
			if(range2 == null) {
				this.explosionDirections.add(max);
			} else {
				this.explosionDirections.add(min);
			}
		} 
	}
	
	public Set<ExplosionDirection> getExplosionDirections() {
		return explosionDirections;
	}
	
	@Override
	public void destroy() {
		destroyed = true;
		this.gameActionPerformedListener.explodeBomb(this);
		listener.update();		
	}
	
	public void cancelTimer() {
		this.timerInstace.cancel();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public void chooseSprite() {
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			@Override
			public void run() {
				animate();
				if(destroyed) {
					sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate);
				} else {
					sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate);
				}
			}
		}, 0, 200);	
	}
	
	
}
