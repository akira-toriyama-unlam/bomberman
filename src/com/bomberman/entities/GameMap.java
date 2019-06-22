package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

import com.bomberman.multiplayer.GameActionPerformed;
import com.bomberman.services.EntityModel;
import com.bomberman.services.EntityTypes;
import com.bomberman.services.PlayerModel;
import com.bomberman.services.PlayerTypes;

public class GameMap {
	
	public final static int MOVEMENT_ERROR = 2; // This constant is used for fixing the movement into the Jpanel.
	public final static double BOMB_ERROR = 0.01; // This constant is used for fixing bomb range error.

	protected List<Entity> objects;
	protected List<Player> players;
	private GameActionPerformed gameActionPerformedListener;

	public GameMap(GameActionPerformed gameActionPerformedListener) {
		this.gameActionPerformedListener = gameActionPerformedListener;
		this.objects = new ArrayList<>();
		this.players = new ArrayList<>();
	}

	public boolean addObject(Entity obj) {
		Entity entity = getAtPosition(obj.getX(), obj.getY());
		if(entity == null) {
			this.objects.add(obj);
			return true;
		}
		return false;
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
					this.gameActionPerformedListener,
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
				objectsList.add(new Bomb((int) e.getX(), (int) e.getY(), this.gameActionPerformedListener, currentPlayer, e.getIdPlayer()));
				break;
			case DESTRUCTIBLE_TILE:
				objectsList.add(new DestructibleTile((int) e.getX(), (int) e.getY()));
				break;
			case TILE:
				objectsList.add(new Tile((int) e.getX(), (int) e.getY()));
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
