package com.bomberman.entities;
import com.bomberman.entities.Direction;

public class Player extends Entity {

	private boolean alive;

	public Player(int x, int y, GameMap map) {
		super(x, y, true, map, true);
		this.alive = true;
	}

	public void placeBomb(GameMap gameMap) {
		Bomb bomb = new Bomb(x, y, gameMap);
		gameMap.addObject(bomb);
		bomb.exploit();
	}

	public void move(Direction direction) {
		
		if (this.map.canMove(this.x, this.y, direction)) {
//			// TODO add logic to move
	
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
//			
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
	


}
