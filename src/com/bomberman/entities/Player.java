package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

import graficos.EstadoMovimiento;

public class Player extends Entity implements ExplosionListener {

	private static final int CONCURRENT_BOMBS = 2;
	private int bombsCount;
	private boolean alive;
	private double DesplazamientoX;
	private double DesplazamientoY;
	
	public Player(int x, int y, GameMap map) {
		super(x, y, true, map, true);
		this.alive = true;
		this.bombsCount = Player.CONCURRENT_BOMBS;
		this.DesplazamientoX = 0;
		this.DesplazamientoY = 0;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				desplazarHorizontalmente(DesplazamientoX);
				desplazarVerticalmente(DesplazamientoY);
			}
		}, 1, 1);
	}

	public void placeBomb(GameMap gameMap) {
		if (this.bombsCount > 0) {
			Bomb bomb = new Bomb(x, y, gameMap, this);
			gameMap.addObject(bomb);
			bombsCount--;
		}
	}
	
	public void move(Direction direction) {
		if (this.map.canMove(this.x, this.y, direction)) {
			switch (direction) {
			case UP:
				this.DesplazamientoY = -0.1;
				break;
			case DOWN:
				this.DesplazamientoY = 0.1;
				break;
			case LEFT:
				this.DesplazamientoX = -0.1;
				break;
			case RIGHT:
				this.DesplazamientoX = 0.1;
				break;
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		this.alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public void update() {
		bombsCount++;
	}
	public void setDesplazamientoX(double desplazamientoX) {
		this.DesplazamientoX = desplazamientoX;
	}
	public void setDesplazamientoY(double desplazamientoY) {
		this.DesplazamientoY = desplazamientoY;
	}
	public void desplazarHorizontalmente(double delta_x) {
		
		if ((this.map.canMove(this.x, this.y, Direction.LEFT) && delta_x < 0 )|| (this.map.canMove(this.x, this.y, Direction.RIGHT) && delta_x > 0)) {
			this.x += delta_x;	
		}else {
			this.x += 0;	
		}
		
	}
	
	public void desplazarVerticalmente(double delta_y) {
		if ((this.map.canMove(this.x, this.y, Direction.UP) && delta_y < 0) || (this.map.canMove(this.x, this.y, Direction.DOWN) && delta_y > 0)) {
			this.y += delta_y;	
		}else {
			this.y += 0;
		}
			
		
	}
}
