package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

import com.bomberman.services.EntityModel;
import com.bomberman.services.EntityTypes;
import com.bomberman.services.PlayerModel;
import com.bomberman.services.PlayerTypes;

public class GameMap implements InteractionListener {

	protected List<Entity> objects;
	protected List<Player> players;
	public final static int MOVEMENT_ERROR = 2; // This constant is used for fixing the movement into the Jpanel.
	protected final static double BOMB_ERROR = 0.01; // This constant is used for fixing bomb range error.

	public GameMap() {
		this.objects = new ArrayList<>();
		this.players = new ArrayList<>();
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
	public void bombPlaced(Bomb bomb) {
		objects.add(bomb);
	}
	
	private boolean between(double i,  double minValueInclusive, double maxValueInclusive) {
	    return (i >= minValueInclusive && i <= maxValueInclusive);
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
		entitiesToRemove.addAll(getPlayersAtSite(this.getPlayers(), bomb));
	
		// destroy destructibles entities in range
		entitiesToRemove.addAll(getEntitesToDestroyAtRight(this.getObjects(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyAtLeft(this.getObjects(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyUp(this.getObjects(), bomb));
		entitiesToRemove.addAll(getEntitesToDestroyBottom(this.getObjects(), bomb));
		
 		this.getPlayers().removeAll(entitiesToRemove.stream().filter(e -> isPlayer(e)).collect(Collectors.toList()));
		this.getObjects().removeAll(entitiesToRemove);
	
		// destroy recursive bombs
		entitiesToRemove.stream().filter(o -> isBomb(o) && !o.equals(bomb)).forEach(b -> {
			Bomb currentBomb = (Bomb) b;
			currentBomb.cancelTimer();
			currentBomb.destroy();
		});
		
	}
	
	private List<Entity> getPlayersAtSite(List<Player> players, Bomb b) {
		return players.stream().filter(p -> b.x == p.x && b.y == p.y).collect(Collectors.toList());
	}
	
	private List<? extends Entity> getEntitesToDestroyAtRight(List<? extends Entity> entities, Bomb bomb) {
		List<? extends Entity> listRange1 = getEntitesAtRightInRange(entities, bomb, 1);
		return isBombOnlyEntityInList(listRange1, bomb) ? getEntitesAtRightInRange(entities, bomb, 2) : listRange1;
	}
	
	private List<? extends Entity> getEntitesAtRightInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> isDestructible(o) && o.getY() == bomb.getY()
				&& between(o.getX(), bomb.getX() + BOMB_ERROR, bomb.getX() + (Tile.TILE_SIZE * range))).collect(Collectors.toList());
	} 
	
	private List<? extends Entity> getEntitesToDestroyAtLeft(List<? extends Entity> entities, Bomb bomb) {
		List<? extends Entity> listRange1 = getEntitesAtLeftInRange(entities, bomb, 1);
		return isBombOnlyEntityInList(listRange1, bomb) ? getEntitesAtLeftInRange(entities, bomb, 2) : listRange1;
	}
	
	private List<? extends Entity> getEntitesAtLeftInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> isDestructible(o) &&o.getY() == bomb.getY() 
				&& between(o.getX(), bomb.getX() - (Tile.TILE_SIZE * range), bomb.getX() - BOMB_ERROR)).collect(Collectors.toList());
	}
	
	private List<? extends Entity> getEntitesToDestroyUp(List<? extends Entity> entities, Bomb bomb) {
		List<? extends Entity> listRange1 = getEntitesAtUpInRange(entities, bomb, 1);
		return isBombOnlyEntityInList(listRange1, bomb) ? getEntitesAtUpInRange(entities, bomb, 2) : listRange1;
	}
	
	private List<? extends Entity> getEntitesAtUpInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> isDestructible(o) &&o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() - (Tile.TILE_SIZE * range), bomb.getY() - BOMB_ERROR)).collect(Collectors.toList());
	}
	
	private List<? extends Entity> getEntitesToDestroyBottom(List<? extends Entity> entities, Bomb bomb) {
		List<? extends Entity> listRange1 = getEntitesAtBottomInRange(entities, bomb, 1);
		return isBombOnlyEntityInList(listRange1, bomb) ? getEntitesAtBottomInRange(entities, bomb, 2) : listRange1;
	}
	
	private List<? extends Entity> getEntitesAtBottomInRange(List<? extends Entity> entities, Bomb bomb, int range) {
		return entities.stream().filter(o -> isDestructible(o) &&o.getX() == bomb.getX() 
				&& between(o.getY(), bomb.getY() + BOMB_ERROR, bomb.getY() + (Tile.TILE_SIZE * range))).collect(Collectors.toList());
	}
	
	private boolean isBombOnlyEntityInList(List<? extends Entity> entities, Bomb bomb) {
		return entities.isEmpty() || entities.size() == 1 && entities.get(0).equals(bomb);
	}
	
	private boolean isPlayer(Entity entity) {
		return entity instanceof Player;
	}
	
	private boolean isBomb(Entity entity) {
		return entity instanceof Bomb;
	}
	
	private boolean isDestructible(Entity entity) {
		return entity instanceof Destructible;
	}
	
	public List<PlayerModel> generatePlayersModelList() {
		List<PlayerModel> playerModels = new ArrayList<>();
		for(Player player : this.players) {
			playerModels.add(
					new PlayerModel(
							player.getX(),
							player.getY(),
							PlayerTypes.GREEN,
							player.getId()
							));
		}
		
		return playerModels;
	}
	
	public List<Player> generatePlayerFromModel(List<PlayerModel> playerModels) {
		List<Player> playersList = new ArrayList<>(); 
		for(PlayerModel playerModel : playerModels) {
			// TODO: Select ICON depending on player type
			
			playersList.add(new Player(
					playerModel.getX(),
					playerModel.getY(),
					this,
					new ImageIcon("./resources/Abajo_0.png"),
					playerModel.getId()));
		}
		
		return playersList;
	}
	
	public List<Entity> generateObjectsFromModel(List<EntityModel> entityModels) {
		List<Entity> objectsList = new ArrayList<>();
		for(EntityModel e : entityModels) {
			switch(e.getEntityType()) {
			case BOMB:
				Player currentPlayer = this.getPlayers().stream().filter(p -> p.getId() == e.getIdPlayer()).findFirst().orElse(null);
				objectsList.add(new Bomb((int) e.getX(), (int) e.getY(), this, currentPlayer, e.getIdPlayer()));
				break;
			case DESTRUCTIBLE_TILE:
				objectsList.add(new DestructibleTile((int) e.getX(), (int) e.getY(), this));
				break;
			case TILE:
				objectsList.add(new Tile((int) e.getX(), (int) e.getY(), this));
				break;
			}
		}
		
		return objectsList;
		
	}
	
	public List<EntityModel> generateEntityModelList() {
		List<EntityModel> newList = new ArrayList<>();
		for(Entity e : this.getObjects()) {
			if(e instanceof Bomb) {
				newList.add(new EntityModel(
						e.getX(),
						e.getY(),
						EntityTypes.BOMB,
						((Bomb) e).getId()
						));	
			} else if (e instanceof DestructibleTile) {
				newList.add(new EntityModel(
						e.getX(),
						e.getY(),
						EntityTypes.DESTRUCTIBLE_TILE,
						-1
						));
			} else {
				// Just a tile
				newList.add(new EntityModel(
						e.getX(),
						e.getY(),
						EntityTypes.TILE,
						-1
						));
			}
		}
		return newList;
	}
	
	public void setObjects(List<Entity> objects) {
		this.objects = objects;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
}
