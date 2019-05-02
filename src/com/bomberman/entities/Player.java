package com.bomberman.entities;

public class Player extends Entity {

	private boolean alive;

	public Player(int x, int y, GameMap map) {
		super(x, y, true, map);
		this.alive = true;
	}

	public void placeBomb(GameMap gameMap) {
		Bomb bomb = new Bomb(x, y, gameMap);
		gameMap.addObject(bomb);
		bomb.exploit();
	}

	public void move(Direction direction) {
		if (this.map.canMove(x, y, direction)) {
			// TODO add logic to move
		}
	}

	public boolean isAlive() {
		return alive;
	}

}
