package com.bomberman.entities;

public class Player extends Entity{

	private boolean alive;
	
	public Player(int x, int y, boolean destroyed, boolean canBeDestroyed, GameMap map, boolean alive) {
		super(x, y, canBeDestroyed, map);
		this.alive = alive;
	}

	public void placeBomb(GameMap gameMap) {
		Bomb bomb = new Bomb(x, y, gameMap);
		gameMap.addObject(bomb);
		bomb.exploit();
	}
	
	public void move(Direction direction) {
		if(this.map.canMove(x, y, direction)) {
			// TODO add logic to move
		}
	}
	
	public boolean isAlive() {
		return alive;
	}

}
