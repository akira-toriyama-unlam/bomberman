package com.bomberman.entities;

import java.util.EventListener;

public interface InteractionListener extends EventListener { 
	
	void bombPlaced(Bomb bomb);
	
	void bombExploded(Bomb bomb);

}
