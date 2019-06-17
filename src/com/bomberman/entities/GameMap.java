package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameMap implements InteractionListener {

	protected String name;
	protected List<Entity> objects;
	protected List<Player> players;
	protected int width;
	protected int height;
	protected final static int MOVEMENT_ERROR = 2;

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
		return between(y + MOVEMENT_ERROR, o.getY() + MOVEMENT_ERROR, o.getY() + Tile.TILE_SIZE - MOVEMENT_ERROR) ||
				between(y + Player.HEIGHT - MOVEMENT_ERROR, o.getY() + MOVEMENT_ERROR, o.getY() + Tile.TILE_SIZE - MOVEMENT_ERROR);
	}
	
	private boolean betweenX(Entity o, double x) {
		return between(x + MOVEMENT_ERROR, o.getX() + MOVEMENT_ERROR, o.getX() + Tile.TILE_SIZE - MOVEMENT_ERROR) ||
				between(x + Player.HEIGHT - MOVEMENT_ERROR, o.getX() + MOVEMENT_ERROR, o.getX() + Tile.TILE_SIZE - MOVEMENT_ERROR);
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
		List<Entity> entitiesToRemove = getEntitesToDestroyAtRight(bomb);
		entitiesToRemove.addAll(getEntitesToDestroyAtLeft(bomb));
		entitiesToRemove.addAll(getEntitesToDestroyUp(bomb));
		entitiesToRemove.addAll(getEntitesToDestroyBottom(bomb));
		entitiesToRemove.removeIf(o -> !(o instanceof Destructible));
		this.getObjects().removeAll(entitiesToRemove);
		this.getObjects().remove(bomb);
	}
	
	private List<Entity> getEntitesToDestroyAtRight(Bomb bomb) {
		List<Entity> list = this.getObjects().stream()
				.filter(o -> o.getY() == bomb.getY() && o.getX() == bomb.getX() + Tile.TILE_SIZE).collect(Collectors.toList());
		return list.isEmpty() ? this.getObjects().stream()
				.filter(o -> o.getY() == bomb.getY() && o.getX() == bomb.getX() + Tile.TILE_SIZE * 2).collect(Collectors.toList()): list;
	}
	
	private List<Entity> getEntitesToDestroyAtLeft(Bomb bomb) {
		List<Entity> list = this.getObjects().stream()
				.filter(o -> o.getY() == bomb.getY() && o.getX() == bomb.getX() - Tile.TILE_SIZE).collect(Collectors.toList());
		return list.isEmpty() ? this.getObjects().stream()
				.filter(o -> o.getY() == bomb.getY() && o.getX() == bomb.getX() - Tile.TILE_SIZE * 2).collect(Collectors.toList()) : list;
	}
	
	private List<Entity> getEntitesToDestroyUp(Bomb bomb) {
		List<Entity> list = this.getObjects().stream()
				.filter(o -> o.getX() == bomb.getX() && o.getY() == bomb.getY() - Tile.TILE_SIZE).collect(Collectors.toList());
		return list.isEmpty() ? this.getObjects().stream()
				.filter(o -> o.getX() == bomb.getX() && o.getY() == bomb.getY() - Tile.TILE_SIZE * 2).collect(Collectors.toList()) : list;
	}
	
	private List<Entity> getEntitesToDestroyBottom(Bomb bomb) {
		List<Entity> list = this.getObjects().stream()
				.filter(o -> o.getX() == bomb.getX() && o.getY() == bomb.getY() + Tile.TILE_SIZE).collect(Collectors.toList());
		return list.isEmpty() ? this.getObjects().stream()
				.filter(o -> o.getX() == bomb.getX() && o.getY() == bomb.getY() + Tile.TILE_SIZE * 2).collect(Collectors.toList()) : list;
	}
	
}
