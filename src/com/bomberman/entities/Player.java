package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.dto.MovementStatusDto;
import com.bomberman.dto.PlayerDto;
import com.bomberman.graphics.Sprite;
import com.bomberman.server.GameActionPerformed;

public class Player extends Entity implements ExplosionListener, Destructible {

	public static final double MOVEMENT_UNIT = 8;
	public static final int HEIGHT = 40;
	public static final int WIDTH = 28;
	
	private static final int CONCURRENT_BOMBS = 200;
	private  boolean moving = false;
	private int bombsCount;
	private boolean alive;
	private Integer id;
	private Direction currentDirection;
	
	public Player(double x, double y, Integer id) {
		super(x, y);
		this.alive = true;
		this.bombsCount = Player.CONCURRENT_BOMBS;
		this.id = id;
		this.currentDirection = Direction.DOWN;
		this.selectInitialSprite(id);
	}
	
	private void selectInitialSprite(Integer id) {
		switch(id) {
		case 1:
			this.sprite = Sprite.player_blue_down; 
			break;
		case 2:
			this.sprite = Sprite.player_green_down; 
			break;
		case 3:
			this.sprite = Sprite.player_blue_down; 
			break;
		case 4:
			this.sprite = Sprite.player_green_down; 
			break;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alive ? 1231 : 1237);
		result = prime * result + bombsCount;
		result = prime * result + ((currentDirection == null) ? 0 : currentDirection.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (moving ? 1231 : 1237);
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
		if (currentDirection != other.currentDirection)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (moving != other.moving)
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
			Bomb bomb = new Bomb(generateFixedX(), generateFixedY(), actionPerformedListener, this.getId());
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
					incrementAnimateCount();
					sprite = Sprite.movingSprite(Sprite.player_blue_dead1, Sprite.player_blue_dead2, Sprite.player_blue_dead3, animateCount);
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
	
	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	@Override
	public void update() {
		bombsCount++;
	}

	public void chooseSprite() {
		System.out.println("Choose sprite executed");
		incrementAnimateCount();
		switch(this.currentDirection) {
		case UP:
			sprite = Sprite.player_blue_up;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_blue_up, Sprite.player_blue_up_1, Sprite.player_blue_up_2, animateCount, 5);
			}
			break;
		case RIGHT:
			sprite = Sprite.player_blue_right;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_blue_right, Sprite.player_blue_right_1, Sprite.player_blue_right_2, animateCount, 5);
			}
			break;
		case DOWN:
			sprite = Sprite.player_blue_down;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_blue_down, Sprite.player_blue_down_1, Sprite.player_blue_down_2, animateCount, 5);
			}
			break;
		case LEFT:
			sprite = Sprite.player_blue_left;
			if(moving) {
				sprite = Sprite.movingSprite(Sprite.player_blue_left, Sprite.player_blue_left_1, Sprite.player_blue_left_2, animateCount, 5);
			}
			break;
		default:
			break;
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public PlayerDto toDto() {
		return new PlayerDto(x, y, id, new MovementStatusDto(currentDirection, moving, animateCount));
	}
	
	public void animate(Direction direction) {
		this.currentDirection = direction;
		moving = true;
		incrementAnimateCount();
	}

}
