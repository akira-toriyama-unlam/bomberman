package com.bomberman.entities;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import com.bomberman.graphics.Sprite;

public class Player extends Entity implements ExplosionListener, Destructible {

	public static final double MOVEMENT_UNIT = 8;
	public static final int HEIGHT = 40;
	public static final int WIDTH = 28;
	private static final int CONCURRENT_BOMBS = 2;
	private int bombsCount;
	private boolean alive;
	
	public Player(double x, double y, InteractionListener map) {
		super(x, y, map);
		this.alive = true;
		this.bombsCount = Player.CONCURRENT_BOMBS;
		this.sprite = Sprite.player_down; //initial position
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alive ? 1231 : 1237);
		result = prime * result + bombsCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (alive != other.alive)
			return false;
		if (bombsCount != other.bombsCount)
			return false;
		return true;
	}

	private int generateFixedX() {
		return Tile.SIZE * ((int) x / Tile.SIZE);
	}
	
	private int generateFixedY() {
		return Tile.SIZE * ((int) y / Tile.SIZE);
	}

	public void placeBomb(InteractionListener map) {
		if (this.bombsCount > 0) {
			Bomb bomb = new Bomb(generateFixedX(), generateFixedY(), map, this);
			map.bombPlaced(bomb);
			bombsCount--;
		}
	}
	
	public void move(Direction direction) {
		interactionListener.movement(this, direction);
		chooseSprite(direction);		
	}

	@Override
	public void destroy() {
		this.alive = false;
		
			Timer timer = new Timer();
			 timer.schedule(new TimerTask() {
				int counter = 0;
				@Override
				public void run() {
					animate();
					sprite = movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3);
					counter++;
					
			       if (counter == 3){
			         timer.cancel();
			       }
				}
			}, 0, 100);	
		}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public void update() {
		bombsCount++;
	}

	public void chooseSprite(Direction direction) {
		animate();
		switch(direction) {
		case UP:
			sprite = Sprite.player_up;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, 5);
			}
			break;
		case RIGHT:
			sprite = Sprite.player_right;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, 5);
			}
			break;
		case DOWN:
			sprite = Sprite.player_down;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, 5);
			}
			break;
		case LEFT:
			sprite = Sprite.player_left;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, 5);
			}
			break;
		}
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
