package com.bomberman.multiplayer;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.Direction;
import com.bomberman.entities.Entity;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;
import com.bomberman.graphics.JGraphicWindow;
import com.bomberman.services.DirectionMessage;


public class ScoreBoard extends Observable implements GameActionPerformed {
	
	private GameMap map;
	private Timer timer;
	private final static int MOVEMENT_ERROR = 2;
	
	public ScoreBoard() {
		this.map = new GameMap();
		this.generateBaseMap();
		//this.notifyAllObservers(this.map);
		
		//initializeReSend();
	}
	
	private void initializeReSend() {
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {	
				actionPerformed();
			}
		}, 200, 1);
	}

	@Override
	public void actionPerformed() {
		// Update status the notify
		System.out.println("Trying to notify all observers from EMPTY");
		this.setChanged();
        //this.notifyObservers(this.gameMap);
		this.notifyObservers(map); // just for test
	}
	
	@Override
	public Player newPlayer() {
		int playersCount = this.map.getPlayers().size(); 
		Player player = null;
		switch(playersCount) {
		case 0:
			player = new Player(40, 40, this.map, new ImageIcon("./resources/Abajo_0.png"), 1);
			this.map.addPlayer(player);
			break;
		case 1:
			player = new Player(760, 40, this.map, new ImageIcon("./resources/Abajo_0.png"), 2);
			this.map.addPlayer(player);
			break;
		case 2:
			player = new Player(40, 520, this.map, new ImageIcon("./resources/Abajo_0.png"), 3);
			this.map.addPlayer(player);
			break;
		case 3:
			player = new Player(760, 520, this.map, new ImageIcon("./resources/Abajo_0.png"), 4);
			this.map.addPlayer(player);
			break;
			
		}
		
		return player;
	}
		
	private void addTileToMap(int x, int y, GameMap m, boolean destroy) {
		if (destroy) {
			this.map.addObject(new DestructibleTile(x, y, m));
		} else {
			this.map.addObject(new Tile(x, y, m));
		}
	}
	
	private void generateBaseMap() {
		addTileToMap(40,80,this.map,true);
		//addTileToMap(80,40,this.map,true);
		addTileToMap(80,360,this.map,true);
		addTileToMap(80,200,this.map,true);
		addTileToMap(120,200,this.map,true);
		addTileToMap(200,120,this.map,true);
		addTileToMap(280,160,this.map,true);
		addTileToMap(40,240,this.map,true);
		addTileToMap(200,200,this.map,true);
		addTileToMap(400,40,this.map,true);
		addTileToMap(360,40,this.map,true);
		addTileToMap(400,200,this.map,true);
		addTileToMap(280,280,this.map,true);
		addTileToMap(280,320,this.map,true);
		addTileToMap(320,360,this.map,true);
		addTileToMap(480,360,this.map,true);
		addTileToMap(520,240,this.map,true);
		addTileToMap(360,120,this.map,true);
		addTileToMap(440,280,this.map,true);
		
		// Border limits
		for(int i = 0; i< 800; i+=40) {
			addTileToMap(i,0,this.map,false);
			addTileToMap(0,i,this.map,false);
			addTileToMap(800,i,this.map,false);
			addTileToMap(i,560,this.map,false);
		}
		
		// Inner non-breaking tiles
		for(int i = 80; i< 1040; i+=80) {
			addTileToMap(80,i,this.map,false);
			addTileToMap(160,i,this.map,false);
			addTileToMap(240,i,this.map,false);
			addTileToMap(320,i,this.map,false);
			addTileToMap(400,i,this.map,false);
			addTileToMap(480,i,this.map,false);
			addTileToMap(560,i,this.map,false);
			addTileToMap(640,i,this.map,false);
			addTileToMap(720,i,this.map,false);
			addTileToMap(800,i,this.map,false);
			addTileToMap(880,i,this.map,false);
		}

	}
	
	private boolean crashWithLimits(double x, double y, Direction direction) {
		switch (direction) {
			case RIGHT:
				return (x + Player.MOVEMENT_UNIT) > (JGraphicWindow.WIDTH - Player.HEIGHT - Tile.TILE_SIZE);
			case LEFT:
				return (x - Player.MOVEMENT_UNIT - Tile.TILE_SIZE) < 0;
			case UP:
				return (y - Player.MOVEMENT_UNIT - Tile.TILE_SIZE) < 0;
			case DOWN:
				return (y + Player.MOVEMENT_UNIT) > (JGraphicWindow.HEIGHT - Player.HEIGHT - Tile.TILE_SIZE);
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
		return this.map.getObjects().stream().filter(o -> betweenY(o, y)).noneMatch(o -> o.getX() == x + Tile.TILE_SIZE);  
	}
	
	private boolean canMoveLeft(double x, double y) {
		return this.map.getObjects().stream().filter(o -> betweenY(o, y)).noneMatch(o -> o.getX() == x - Tile.TILE_SIZE);
	}
	
	private boolean canMoveUp(double x, double y) {
		return this.map.getObjects().stream().filter(o -> betweenX(o, x)).noneMatch(o -> o.getY() == y - Tile.TILE_SIZE);  
	}
	
	private boolean canMoveDown(double x, double y) {
		return this.map.getObjects().stream().filter(o -> betweenX(o, x)).noneMatch(o -> o.getY() == y + Tile.TILE_SIZE);  
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

	@Override
	public void movementMessageReceived(Player player, DirectionMessage message) {
		Direction direction = message.getDirection();
		if (this.canMove(player.getX(), player.getY(), direction)) {
			Player current = this.map.getPlayers().stream().filter(p -> p.equals(player)).findFirst().orElse(null);
			switch (direction) {
			case UP:
				current.setY(current.getY() - Player.MOVEMENT_UNIT);
				break;
			case DOWN:
				current.setY(current.getY() + Player.MOVEMENT_UNIT);
				break;
			case LEFT:
				current.setX(current.getX() - Player.MOVEMENT_UNIT);
				break;
			case RIGHT:
				current.setX(current.getX() + Player.MOVEMENT_UNIT);
				break;
			}
			this.actionPerformed();
		}
	}
	
	@Override
	public void bombMessageReceived(Player player, DirectionMessage message) {
		Player current = this.map.getPlayers().stream().filter(p -> p.equals(player)).findFirst().orElse(null);
		current.placeBomb(this.map);
		this.actionPerformed();
	}

	@Override
	public void playerDisconected(Player player) {
		this.map.getPlayers().remove(player);
		this.actionPerformed();
	}

}
