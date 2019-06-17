package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

public class GameMap implements InteractionListener {

	protected String name;
	protected List<Entity> objects;
	protected List<Player> players;
	protected int width;
	protected int height;
	protected double errorMovimiento = 2; // es un entero para que da un error de margen para que el bomberman no se tilde justo en los l�mites de cada ladrillo

	public GameMap(String name, int width, int height) {
		this.name = name;
		this.objects = new ArrayList<>();
		this.players = new ArrayList<>();
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
					return canMoveLeft(x,y); 
				case UP:
					return canMoveUp(x,y);
				case DOWN:
					return canMoveDown(x,y); 
				default:
					return false;
			}
		}
		return false;
	}

	public void addObject(Entity obj) {
		this.objects.add(obj);
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public List<Entity> getObjects() {
		return this.objects;
	}
	 
	public Entity getAtPosition(double x, double y) {
		return this.objects.stream().filter(o -> o.x == x && o.y == y).findFirst().orElse(null);
	}
	
	public void exploitEntitesInBombRange(Bomb bomb) {

		destroyEntity(bomb.x - Bomb.BOMB_RANGE, bomb.y); // destroy entity at left if it is possible
		destroyEntity(bomb.x + Bomb.BOMB_RANGE, bomb.y); // destroy entity at right if it is possible
		destroyEntity(bomb.x, bomb.y - Bomb.BOMB_RANGE); // destroy entity below if it is possible
		destroyEntity(bomb.x, bomb.y + Bomb.BOMB_RANGE); // destroy entity above if it is possible
		this.objects.remove(bomb);
	}
	
	private void destroyEntity(double x, double y) {
		Entity entity = getAtPosition(x, y);
		if (entity instanceof Bomb) {
			((Bomb) entity).destroy();
			exploitEntitesInBombRange((Bomb) entity);
		} else {
			((Bomb) entity).destroy();
			objects.remove(entity);
		}
	}

	@Override
	public void movement(Player player, Direction direction) {
		if (this.canMove(player.x, player.y, direction)) {
			switch (direction) {
			case UP:
				player.y -= Player.MOVEMENT_UNIT;
				break;
			case DOWN:
				player.y += Player.MOVEMENT_UNIT;
				break;
			case LEFT:
				player.x -= Player.MOVEMENT_UNIT;
				break;
			case RIGHT:
				player.x += Player.MOVEMENT_UNIT;
				break;
			}
		}
	}
	
	@Override
	public void bombPlaced(Bomb bomb) {
		objects.add(bomb);
	}

	private boolean crashWithLimits(double x, double y, Direction direction) {
		switch (direction) {
			case RIGHT:
				return (x + Player.MOVEMENT_UNIT) > (width - Player.HEIGHT - Tile.TILE_SIZE);
			case LEFT:
				return (x - Player.MOVEMENT_UNIT - Tile.TILE_SIZE) < 0;
			case UP:
				return (y - Player.MOVEMENT_UNIT - Tile.TILE_SIZE) < 0;
			case DOWN:
				return (y + Player.MOVEMENT_UNIT) > (height - Player.HEIGHT - Tile.TILE_SIZE);
			default:
				return false;
		}
	}
	
	public static boolean between(double i,  double minValueInclusive, double maxValueInclusive) {
	    return (i >= minValueInclusive && i <= maxValueInclusive);
	}
	
	private boolean betweenY(Entity o, double y) {
		return between(y + errorMovimiento, o.getY() + errorMovimiento, o.getY() + Tile.TILE_SIZE - errorMovimiento) ||
				between(y + Player.HEIGHT - errorMovimiento, o.getY() + errorMovimiento, o.getY() + Tile.TILE_SIZE - errorMovimiento);
	}
	
	private boolean betweenX(Entity o, double x) {
		return between(x + errorMovimiento, o.getX() + errorMovimiento, o.getX() + Tile.TILE_SIZE - errorMovimiento) ||
				between(x + Player.HEIGHT - errorMovimiento, o.getX() + errorMovimiento, o.getX() + Tile.TILE_SIZE - errorMovimiento);
	}
	
	private boolean canMoveRight(double x, double y) {
		return this.objects.stream().filter(o -> betweenY(o, y)).noneMatch(o -> o.getX() == x + Tile.TILE_SIZE);  
	}
	
	private boolean canMoveLeft(double x, double y) {
		return this.objects.stream().filter(o -> betweenY(o, y)).noneMatch(o -> o.getX() == x - Tile.TILE_SIZE);
	}
	
	private boolean canMoveUp(double x, double y) {
		return this.objects.stream().filter(o -> betweenX(o, x)).noneMatch(o -> o.getY() == y - Tile.TILE_SIZE);  
	}
	
	private boolean canMoveDown(double x, double y) {
		return this.objects.stream().filter(o -> betweenX(o, x)).noneMatch(o -> o.getY() == y + Tile.TILE_SIZE);  
	}

	@Override
	public void bombExploded(Bomb bomb) {
		// TODO Auto-generated method stub
		
	}
	
}
