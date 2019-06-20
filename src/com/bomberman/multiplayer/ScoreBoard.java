package com.bomberman.multiplayer;

import java.util.Observable;

import com.bomberman.entities.GameMap;
import com.bomberman.services.Message;


public class ScoreBoard extends Observable implements GameActionPerformed {
	
	private GameMap gameMap;

	@Override
	public void actionPerformed(Message message) {
		// Update status the notify
		
		this.notifyAllObservers(message);
	}
	
	private void notifyAllObservers(Message message) {
		System.out.println("Trying to notify all observers");
		this.setChanged();
        //this.notifyObservers(this.gameMap);
		this.notifyObservers(message); // just for test
	}

}
