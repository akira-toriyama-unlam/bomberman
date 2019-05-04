package com.bomberman.entities;

public class Player extends Entity implements ExplosionListener {

	private static final int CONCURRENT_BOMBS = 1;
	private int bombsCount;
	private boolean alive;

	public Player(int x, int y, GameMap map) {
		super(x, y, true, map, true);
		this.alive = true;
		this.bombsCount = Player.CONCURRENT_BOMBS;
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
				this.y++;
				break;
			case DOWN:
				this.y--;
				break;
			case LEFT:
				this.x--;
				break;
			case RIGHT:
				this.x++;
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

}
