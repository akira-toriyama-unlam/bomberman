package com.bomberman.entities;

import java.io.Serializable;
import java.util.EventListener;

public interface InteractionListener extends EventListener, Serializable { 
	
	void movement(Player player,Direction direction);
	
	void bombPlaced(Bomb bomb);
	
	void bombExploded(Bomb bomb);

}
