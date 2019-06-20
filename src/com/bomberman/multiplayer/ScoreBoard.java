package com.bomberman.multiplayer;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Tile;


public class ScoreBoard extends Observable implements GameActionPerformed {
	
	private GameMap map;
	private Timer timer;
	
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
		System.out.println("Trying to notify all observers");
		this.setChanged();
        //this.notifyObservers(this.gameMap);
		this.notifyObservers(map); // just for test
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

}
