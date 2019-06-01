
package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 1;
	public static final int TIME_TO_EXPLOIT = 2000;

	private ExplosionListener listener;

	public Bomb(double x, double y, GameMap map, ExplosionListener listener) {
		super(x, y, map, true);
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

	@Override
	public void destroy() {
		for(Entity entity : map.getObjects()) {
			
			//explota a la deracha
			if(entity.getX() <= (this.getX()+map.widthBomberman) && 
					(this.getX()+map.widthBomberman) <= (entity.getX() + map.widthTile) && 
					(entity.getY() + map.widthTile) > (this.getY()+map.errorMovimiento) && 
					(this.getY() + map.widthBomberman) > (entity.getY() + map.errorMovimiento) && entity instanceof Destructible) {
				
				entity.setDestroyed(true);
				
			}
			
			//explota a la izquierda
			if(this.getX() <= (entity.getX()+map.widthTile) && (entity.getX()+map.widthTile) <= (this.getX() + map.widthBomberman) && (entity.getY() + map.widthTile) > (this.getY() + map.errorMovimiento) && (this.getY() + map.widthBomberman) > (entity.getY() + map.errorMovimiento)&& entity instanceof Destructible) {
				entity.setDestroyed(true);	
			}
			
			//explota para abajo
			if((entity.getX()<= this.getX() && (this.getX()+map.errorMovimiento) <= (entity.getX() + map.widthTile)||
					this.getX() <= entity.getX() && entity.getX() <= (this.getX() + map.widthBomberman-map.errorMovimiento) || 
						 (this.getX()+map.errorMovimiento) <= (entity.getX() + map.widthTile) && (entity.getX() + map.widthTile) <= (this.getX() + map.widthBomberman)
						 ) && 
					   ((this.getY() + map.widthBomberman) >= (entity.getY()) && (this.getY() + map.widthBomberman) <= (entity.getY() + map.widthTile)) && entity instanceof Destructible) {
				entity.setDestroyed(true);
			}
			
			//explota para arriba
			if((entity.getX()<= this.getX() && (this.getX()+map.errorMovimiento) <= (entity.getX() + map.widthTile)||
					this.getX() <= entity.getX() && entity.getX() <= (this.getX() + map.widthBomberman - map.errorMovimiento) || 
					 (this.getX()+map.errorMovimiento) <= (entity.getX() + map.widthTile) && (entity.getX() + map.widthTile) <= (this.getX() + map.widthBomberman)
					 ) &&  (this.getY() <= (entity.getY() + map.widthTile) && this.getY() >= (entity.getY())) && entity instanceof Destructible) {
					entity.setDestroyed(true);
			}
			
		}
	}
}
