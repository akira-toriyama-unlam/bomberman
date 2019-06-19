package com.bomberman.entities;

public class DestructibleTile extends Tile implements Destructible {

	public DestructibleTile(int x, int y, GameMap map) {
		super(x, y, map);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void destroy() {}

}
