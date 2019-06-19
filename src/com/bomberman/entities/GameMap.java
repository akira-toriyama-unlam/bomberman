package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameMap implements InteractionListener {

	protected String name;
	protected List<Entity> objects;
	protected List<Player> players;
	protected int width;
	protected int height;	protected final static int MOVEMENT_ERROR = 2; // This constant is used for fixing the movement into the Jpanel.
	protected final static double BOMB_ERROR = 0.01; // This constant is used for fixing bomb range error.

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
	
	public List<Player> getPlayers() {
		return this.players;
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
	
	private boolean between(double i,  double minValueInclusive, double maxValueInclusive) {
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

		List<Entity> entitiesToRemove = new ArrayList<>();
		entitiesToRemove.add(bomb); // remove current bomb from map
		
		// destroy players in range
		entitiesToRemove.addAll(getEntitesToDestroyAtRight(this.getPlayers(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyAtLeft(this.getPlayers(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyUp(this.getPlayers(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyBottom(this.getPlayers(), bomb));
	
		// destroy destructibles entities in range
		entitiesToRemove.addAll(getEntitesToDestroyAtRight(this.getObjects(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyAtLeft(this.getObjects(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyUp(this.getObjects(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyBottom(this.getObjects(), bomb));
		
 		this.getPlayers().removeAll(entitiesToRemove.stream().filter(e -> e.isPlayer()).collect(Collectors.toList()));
		this.getObjects().removeAll(entitiesToRemove);
	
		// destroy recursive bombs
		entitiesToRemove.stream().filter(o -> o.isBomb() && !o.equals(bomb)).forEach(b -> {
			Bomb currentBomb = (Bomb) b;
			currentBomb.cancelTimer();
			currentBomb.destroy();
		});
		
	}
	
	private List<? extends Entity> getEntitesToDestroyAtRight(List<? extends Entity> entities, Bomb bomb) {
		List<? extends Entity> listRange1 = getEntitesAtRightInRange(entities, bomb, 1);
		return isBombOnlyEntityInList(listRange1, bomb) ? getEntitesAtRightInRange(entities, bomb, 2) : listRange1;
	}
	
	private List<? extends Entity> getEntitesAtRightInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> o.isDestructible() && o.getY() == bomb.getY()
				&& between(o.getX(), bomb.getX() + BOMB_ERROR, bomb.getX() + (Tile.TILE_SIZE * range))).collect(Collectors.toList());
	} 
	
	private List<? extends Entity> getEntitesToDestroyAtLeft(List<? extends Entity> entities, Bomb bomb) {
		List<? extends Entity> listRange1 = getEntitesAtLeftInRange(entities, bomb, 1);
		return isBombOnlyEntityInList(listRange1, bomb) ? getEntitesAtLeftInRange(entities, bomb, 2) : listRange1;
	}
	
	private List<? extends Entity> getEntitesAtLeftInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> o.isDestructible() &&o.getY() == bomb.getY() 
				&& between(o.getX(), bomb.getX() - (Tile.TILE_SIZE * range), bomb.getX() - BOMB_ERROR)).collect(Collectors.toList());
	}
	
	private List<? extends Entity> getEntitesToDestroyUp(List<? extends Entity> entities, Bomb bomb) {
		List<? extends Entity> listRange1 = getEntitesAtUpInRange(entities, bomb, 1);
		return isBombOnlyEntityInList(listRange1, bomb) ? getEntitesAtUpInRange(entities, bomb, 2) : listRange1;
	}
	
	private List<? extends Entity> getEntitesAtUpInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> o.isDestructible() &&o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() - (Tile.TILE_SIZE * range), bomb.getY() - BOMB_ERROR)).collect(Collectors.toList());
	}
	
	private List<? extends Entity> getEntitesToDestroyBottom(List<? extends Entity> entities, Bomb bomb) {
		List<? extends Entity> listRange1 = getEntitesAtBottomInRange(entities, bomb, 1);
		return isBombOnlyEntityInList(listRange1, bomb) ? getEntitesAtBottomInRange(entities, bomb, 2) : listRange1;
	}
	
	private List<? extends Entity> getEntitesAtBottomInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> o.isDestructible() &&o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() + BOMB_ERROR, bomb.getY() + (Tile.TILE_SIZE * range))).collect(Collectors.toList());
	}
	
	private boolean isBombOnlyEntityInList(List<? extends Entity> entities, Bomb bomb) {
		return entities.isEmpty() || entities.size() == 1 && entities.get(0).equals(bomb);
	}
}
