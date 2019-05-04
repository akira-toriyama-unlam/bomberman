package com.bomberman.entities;

public class Player extends Entity implements ExplosionListener {

	private boolean alive;

	public Player(int x, int y, GameMap map) {
		super(x, y, true, map);
		this.alive = true;
	}

	public void placeBomb(GameMap gameMap) {
		Bomb bomb = new Bomb(x, y, gameMap);
		gameMap.addObject(bomb);
		bomb.addEventListener(this);
	}

	public void move(Direction direction) {
		if (this.map.canMove(x, y, direction)) {
			// TODO add logic to move
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
		// Bomb has no longer exists, this Player can use a new bomb.
	}

}
