package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

	protected String name;
	protected List<Entity> objects;
	protected int width;
	protected int height;
	protected int errorMovimiento = 2; // es un entero para que da un error de margen para que el bomberman no se tilde justo en los l�mites de cada ladrillo

	public GameMap(String name, int width, int height) {
		this.name = name;
		this.objects = new ArrayList<>();
		this.width = width;
		this.height = height;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean canMove(double x, double y, Direction direction) {
		if(!crashWithLimits(x, y, direction)) {
			switch (direction) {
				case RIGHT:
						return canMoveRight(x, y);
				case LEFT:
					return canMoveLeft(x, y); 
				case UP:
					return canMoveUp(x, y);
				case DOWN:
					return canMoveDown(x, y); 
				default:
					return false;
			}
			
		}
		return false;
	}

	public void addObject(Entity obj) {
		this.objects.add(obj);
	}

	public List<Entity> getObjects() {
		return this.objects;
	}

	public Entity getAtPosition(double x, double y) {
		return this.objects.stream().filter(o -> o.x == x && o.y == y).findFirst().orElse(null);
	}

	private boolean crashWithLimits(double x, double y, Direction direction) {
		switch (direction) {
			case RIGHT:
				return (x + Player.PLAYER_MOVEMENT) > (width - Player.PLAYER_SIZE - Tile.TILE_SIZE);
			case LEFT:
				return (x - Player.PLAYER_MOVEMENT - Tile.TILE_SIZE) < 0;
			case UP:
				return (y - Player.PLAYER_MOVEMENT - Tile.TILE_SIZE) < 0;
			case DOWN:
				return (y + Player.PLAYER_MOVEMENT) > (height - Player.PLAYER_SIZE - Tile.TILE_SIZE);
			default:
				return false;
		}
	}
	
	private boolean canMoveRight(double x, double y) {
		return !this.objects.stream().anyMatch(o -> 
		(o.x <= (x + Player.PLAYER_WIDTH) // compare if player exceeds map width
				&& (x + Player.PLAYER_WIDTH) <= (o.x + Tile.TILE_SIZE) // compare if player crash with tile
				&& (o.y + Tile.TILE_SIZE) > (y + errorMovimiento) 
				&& (y + Player.PLAYER_WIDTH) > (o.y + errorMovimiento) 
				&& !o.isDestroyed() // compare if crash with destroyed object
				&& !o.canOver)); // compare if crash with an object that can be overpassed (another player) 
	}
	
	private boolean canMoveLeft(double x, double y) {
		return !this.objects.stream().anyMatch(o -> 
		(x <= (o.x + Tile.TILE_SIZE) // compare if player exceeds map width
				&& (o.x + Tile.TILE_SIZE) <= (x + Player.PLAYER_WIDTH) 
				&& (o.y + Tile.TILE_SIZE) > (y + errorMovimiento) 
				&& (y + Player.PLAYER_WIDTH) > (o.y + errorMovimiento) 
				&& !o.isDestroyed() // compare if crash with destroyed object
				&& !o.canOver)); // compare if crash with an object that can be overpassed (another player)
	}
	
	private boolean canMoveUp(double x, double y) {
		return !this.objects.stream().anyMatch(o -> (
		(o.x <= x && (x + errorMovimiento) <= (o.x + Tile.TILE_SIZE) // compare if player exceeds map width
				|| x <= o.x && o.x <= (x + Player.PLAYER_WIDTH - errorMovimiento) 
				|| (x+errorMovimiento) <= (o.x + Tile.TILE_SIZE) 
				&& (o.x + Tile.TILE_SIZE) <= (x + Player.PLAYER_WIDTH)) 
				&&  (y <= (o.y + Tile.TILE_SIZE) && y >= (o.y))
				&& !o.isDestroyed() // compare if crash with destroyed object
				&& !o.canOver )); // compare if crash with an object that can be overpassed (another player)
	}
	
	private boolean canMoveDown(double x, double y) {
		return !this.objects.stream().anyMatch(o -> (
		(o.x<= x && (x+errorMovimiento) <= (o.x + Tile.TILE_SIZE) // compare if player exceeds map width
				|| x <= o.x && o.x <= (x + Player.PLAYER_WIDTH - errorMovimiento)
				|| (x+errorMovimiento) <= (o.x + Tile.TILE_SIZE) 
					&& (o.x + Tile.TILE_SIZE) <= (x + Player.PLAYER_WIDTH))
				&& ((y + Player.PLAYER_WIDTH) >= (o.y) 
					&& (y + Player.PLAYER_WIDTH) <= (o.y + Tile.TILE_SIZE)) 
				&& !o.isDestroyed() // compare if crash with destroyed object
				&& !o.canOver )); // compare if crash with an object that can be overpassed (another player)
	}
	
}
