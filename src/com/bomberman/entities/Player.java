package com.bomberman.entities;

public class Player extends Entity implements ExplosionListener, Destructible {

	public static final double MOVEMENT_UNIT = 5;
	public static final double HEIGHT = 40;
	public static final double WIDTH = 28;
	private static final int CONCURRENT_BOMBS = 2;
	private int bombsCount;
	private boolean alive;
	
	public Player(double x, double y, InteractionListener map) {
		super(x, y, map);
		this.alive = true;
		this.bombsCount = Player.CONCURRENT_BOMBS;
	}
	
	private int generateFixedX() {
		if (x != 0) {
			return (int) (40 / x);
		}
		return 40;
	}

	public void placeBomb(InteractionListener gameMap) {
		if (this.bombsCount > 0) {
			int fixedX = generateFixedX();
			Bomb bomb = new Bomb(fixedX, (int) y, gameMap, this);
			gameMap.bombPlaced(bomb);
			bombsCount--;
		}
	}
	
	public void move(Direction direction) {
		interactionListener.movement(this, direction);
	}

	@Override
	public void destroy() {
		this.alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public void update() {
		bombsCount++;
	}

}
