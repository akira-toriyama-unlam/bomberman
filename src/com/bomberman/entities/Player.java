package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.graphics.Sprite;
import com.bomberman.multiplayer.GameActionPerformed;
import com.bomberman.services.PlayerTypes;

public class Player extends Entity implements ExplosionListener, Destructible {

	public static final double MOVEMENT_UNIT = 8;
	public static final int HEIGHT = 40;
	public static final int WIDTH = 28;
	private static final int CONCURRENT_BOMBS = 2;
	
	private int bombsCount;
	private boolean alive;
	private PlayerTypes playerType;
	private int id;
	
	public Player(double x, double y, GameActionPerformed gameActionPerformedListener, int id) {
		super(x, y, gameActionPerformedListener);
		this.alive = true;
		this.bombsCount = Player.CONCURRENT_BOMBS;
		this.id = id;
		this.sprite = Sprite.player_blue_down;
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

	public void placeBomb(GameActionPerformed actionPerformedListener) {
		if (this.bombsCount > 0) {
			Bomb bomb = new Bomb(generateFixedX(), generateFixedY(), actionPerformedListener, this, this.getId());
			boolean placed = actionPerformedListener.placeBomb(bomb);
			if(placed) {
				bombsCount--;
			}
		}
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
					sprite = Sprite.movingSprite(Sprite.player_blue_dead1, Sprite.player_blue_dead2, Sprite.player_blue_dead3, animate);
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
			sprite = Sprite.player_blue_up;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_blue_up, Sprite.player_blue_up_1, Sprite.player_blue_up_2, animate, 5);
			}
			break;
		case RIGHT:
			sprite = Sprite.player_blue_right;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_blue_right, Sprite.player_blue_right_1, Sprite.player_blue_right_2, animate, 5);
			}
			break;
		case DOWN:
			sprite = Sprite.player_blue_down;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_blue_down, Sprite.player_blue_down_1, Sprite.player_blue_down_2, animate, 5);
			}
			break;
		case LEFT:
			sprite = Sprite.player_blue_left;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_blue_left, Sprite.player_blue_left_1, Sprite.player_blue_left_2, animate, 5);
			}
			break;
		}
	}
	
	public PlayerTypes getPlayerType() {
		return this.playerType;
	}
	
	public void setPlayerType(PlayerTypes playerType) {
		this.playerType = playerType;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

}
