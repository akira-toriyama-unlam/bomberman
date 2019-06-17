
package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;


public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 1;
	public static final int TIME_TO_EXPLOIT = 2000;

	private ExplosionListener listener;

	public Bomb(int x, int y, InteractionListener map, ExplosionListener listener) {
		super(x, y, map);
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
		interactionListener.bombExploded(this);
		listener.update(); // Notify player.
	}
	
	@Override
	public void destroy() {
		for(Entity entity : map.getObjects()) {
			
			//explota a la deracha
			if(entity.getX() <= (this.getX() + Player.HEIGHT) && 
					(this.getX() + Player.HEIGHT) <= (entity.getX() + Tile.TILE_SIZE) && 
					(entity.getY() + Tile.TILE_SIZE) > (this.getY()+map.errorMovimiento) && 
					(this.getY() + Player.HEIGHT) > (entity.getY() + map.errorMovimiento) && entity instanceof Destructible) {
				
				map.getObjects().remove(entity);
			}
			
			//explota a la izquierda
			if(this.getX() <= (entity.getX()+Tile.TILE_SIZE) && (entity.getX()+Tile.TILE_SIZE) <= (this.getX() + Player.HEIGHT) && (entity.getY() + Tile.TILE_SIZE) > (this.getY() + map.errorMovimiento) && (this.getY() + Player.HEIGHT) > (entity.getY() + map.errorMovimiento)&& entity instanceof Destructible) {
				map.getObjects().remove(entity);	
			}
			
			//explota para abajo
			if((entity.getX()<= this.getX() && (this.getX()+map.errorMovimiento) <= (entity.getX() + Tile.TILE_SIZE)||
					this.getX() <= entity.getX() && entity.getX() <= (this.getX() + Player.HEIGHT-map.errorMovimiento) || 
						 (this.getX()+map.errorMovimiento) <= (entity.getX() + Tile.TILE_SIZE) && (entity.getX() + Tile.TILE_SIZE) <= (this.getX() + Player.HEIGHT)
						 ) && 
					   ((this.getY() + Player.HEIGHT) >= (entity.getY()) && (this.getY() + Player.HEIGHT) <= (entity.getY() + Tile.TILE_SIZE)) && entity instanceof Destructible) {
				map.getObjects().remove(entity);
			}
			
			//explota para arriba
			if((entity.getX()<= this.getX() && (this.getX()+map.errorMovimiento) <= (entity.getX() + Tile.TILE_SIZE)||
					this.getX() <= entity.getX() && entity.getX() <= (this.getX() + Player.HEIGHT - map.errorMovimiento) || 
					 (this.getX()+map.errorMovimiento) <= (entity.getX() + Tile.TILE_SIZE) && (entity.getX() + Tile.TILE_SIZE) <= (this.getX() + Player.HEIGHT)
					 ) &&  (this.getY() <= (entity.getY() + Tile.TILE_SIZE) && this.getY() >= (entity.getY())) && entity instanceof Destructible) {
				map.getObjects().remove(entity);
			}
			
		}
	}
}
