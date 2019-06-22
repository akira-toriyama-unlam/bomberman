package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

import com.bomberman.server.GameActionPerformed;

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
		
		this.fillMapWithTiles();
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
	
	
	public void setObjects(List<Entity> objects) {
		this.objects = objects;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	private void addTileToMap(int x, int y, boolean destroy) {
		if (destroy) {
			this.objects.add(new DestructibleTile(x, y));
		} else {
			this.objects.add(new Tile(x, y));
		}
	}

	
	private void fillMapWithTiles() {
		addTileToMap(40,80,true);
		addTileToMap(80,360,true);
		addTileToMap(80,200,true);
		addTileToMap(120,200,true);
		addTileToMap(200,120,true);
		addTileToMap(280,160,true);
		addTileToMap(40,240,true);
		addTileToMap(200,200,true);
		addTileToMap(400,40,true);
		addTileToMap(360,40,true);
		addTileToMap(400,200,true);
		addTileToMap(280,280,true);
		addTileToMap(280,320,true);
		addTileToMap(320,360,true);
		addTileToMap(480,360,true);
		addTileToMap(520,240,true);
		addTileToMap(360,120,true);
		addTileToMap(440,280,true);
		
		// Border limits
		for(int i = 0; i< 800; i+=40) {
			addTileToMap(i,0,false);
			addTileToMap(0,i,false);
			addTileToMap(800,i,false);
			addTileToMap(i,560,false);
		}
		
		// Inner non-breaking tiles
		for(int i = 80; i< 1040; i+=80) {
			addTileToMap(80,i,false);
			addTileToMap(160,i,false);
			addTileToMap(240,i,false);
			addTileToMap(320,i,false);
			addTileToMap(400,i,false);
			addTileToMap(480,i,false);
			addTileToMap(560,i,false);
			addTileToMap(640,i,false);
			addTileToMap(720,i,false);
			addTileToMap(800,i,false);
			addTileToMap(880,i,false);
		}

	}
}
