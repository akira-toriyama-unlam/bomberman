package com.bomberman.entities;

import java.util.EventListener;

public interface InteractionListener extends EventListener { 
	
	void movement(Player player,Direction direction);
	
	void bombPlaced(Bomb bomb);
	
	void bombExploded(Bomb bomb);

}
