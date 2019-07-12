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
	private Integer id;
	private Direction currentDirection;
	
	public Player(double x, double y, Integer id) {
		super(x, y);
		this.bombsCount = Player.CONCURRENT_BOMBS;
		this.id = id;
		this.currentDirection = Direction.DOWN;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		// this.setDestroyed(true);
		this.setPainted(true);  // TODO: A VER SI ES ENCESARIO EN EL DTO
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			int counter = 0;
			@Override
			public void run() {
				incrementAnimateCount();
				counter++;
				
		       if (counter == 3){
		         timer.cancel();
		       }
			}
		}, 0, 100);
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
	
	public int getId() {
		return this.id;
	}
	
	public PlayerDto toDto() {
		return new PlayerDto(x, y, id, new MovementStatusDto(currentDirection, moving, animateCount), destroyed, painted);
	}
	
	public void animate(Direction direction) {
		this.currentDirection = direction;
		moving = true;
		incrementAnimateCount();
	}

}
