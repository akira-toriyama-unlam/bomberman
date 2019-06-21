package com.bomberman.multiplayer;

import java.util.Observer;

import com.bomberman.entities.Player;
import com.bomberman.services.DirectionMessage;

public interface GameActionPerformed {
	
	void actionPerformed();
	Player newPlayer();
	void movementMessageReceived(Player player, DirectionMessage message);
	void bombMessageReceived(Player player, DirectionMessage message);
	void playerDisconected(Player player);
	void addObserver(Observer observer);

}
