package com.bomberman.entities;

import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.dto.MovementStatusDto;
import com.bomberman.dto.PlayerDto;
import com.bomberman.extras.Sound;
import com.bomberman.server.GameActionPerformed;

public class Player extends Entity implements ExplosionListener, Destructible {

	public static double MOVEMENT_UNIT = 8;
	public static final int HEIGHT = 40;
	public static final int WIDTH = 28;
	private static final int CONCURRENT_BOMBS = 2;
	private  boolean moving = false;

	private int bombsCount;
	private int id;
	private String name;
	private String password;
	private Direction currentDirection;

private boolean indestructible = false;
	public Player() {
		super(0, 0);
	}

	public Player(String name, String password) {
		super(0, 0);
		this.name = name;
		this.password = password;
		this.bombsCount = Player.CONCURRENT_BOMBS;
		this.currentDirection = Direction.DOWN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bombsCount;
		result = prime * result + ((currentDirection == null) ? 0 : currentDirection.hashCode());
		result = prime * result + id;
		result = prime * result + (moving ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	public void setBombsCount(int bombsCount) {
		this.bombsCount = bombsCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Player other = (Player) obj;
		if (bombsCount != other.bombsCount) {
			return false;
		}
		if (currentDirection != other.currentDirection) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (moving != other.moving) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
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
			bomb.setPlayer(this);
			boolean placed = actionPerformedListener.placeBomb(bomb);
			if (placed) {
				bombsCount--;
			}
		}
	}

	@Override
	public synchronized void destroy() {
		if(!this.indestructible) {
		// this.setDestroyed(true);
		this.setPainted(true);  // TODO: A VER SI ES ENCESARIO EN EL DTO
        new Sound("music/enemydown.wav", false).play();
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
	}
	

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void update() {
		bombsCount++;
	}

	public int getId() {
		return this.id;
	}

	public PlayerDto toDto() {
		return new PlayerDto(x, y, id, new MovementStatusDto(currentDirection, moving, animateCount), destroyed,
				painted);
	}

	public void animate(Direction direction) {
		this.currentDirection = direction;
		moving = true;
		incrementAnimateCount();
	}

	public synchronized void setIndestructible(boolean godMode) {
		this.indestructible = godMode;
		
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			@Override
			public void run() {
				indestructible = false;
			}
		}, 10000);
	}
	
	public boolean getIndestructible() {
		return this.indestructible;
	}
}
