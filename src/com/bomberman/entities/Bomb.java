package com.bomberman.entities;

import java.awt.Image;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.graphics.Sprite;

public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 2;
	public static final int TIME_TO_EXPLOIT = 3000;
	
	private Set<ExplosionDirection> explosionDirections;
	private Date timeCreated;
	private Thread thread;
	private Thread spriteThread;
	private Timer spriteTimer;

	private ExplosionListener listener;

	public Bomb(int x, int y, InteractionListener map, ExplosionListener listener) {
		super(x, y, map);
		this.listener = listener;
		this.explosionDirections = new HashSet<>();
		this.timeCreated = new Date();
		this.sprite = Sprite.bomb;
		this.chooseSprite();
		this.destroy();
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
		thread = new Thread() {
		    @Override
			public void run(){
		    	while(true) {
		    		if((new Date().getTime() - timeCreated.getTime()) > TIME_TO_EXPLOIT){
		    			explode();
						break;
					}
				}
		    }
		};
		thread.start();
	}
	
	public void explode() {
		destroyed = true;
		interactionListener.bombExploded(this);
		listener.update();
	}
	
	public void interruptThread() {
		thread.interrupt();
	}
	
	public void cancelThread() {
		this.spriteTimer.cancel();
	}
	
	public Date getTimeCreated() {
		return this.timeCreated;
	}
	
	public void chooseSprite() {
		spriteThread = new Thread() {
		    @Override
			public void run(){
		    	while(true) {
		    		animate();
		    		if((new Date().getTime() - timeCreated.getTime()) < TIME_TO_EXPLOIT){
		    			sprite = movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2);
					} else {
						sprite = movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2);
					}
				}
		    }
		};
		spriteThread.start();
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
