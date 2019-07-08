package com.bomberman.entities;

import java.util.EventListener;

public interface InteractionListener extends EventListener { 
	
	void movement(Player player,Direction direction);
	
	boolean placeBomb(double x, double y, InteractionListener map, Player player);
	
	void bombExploded(Bomb bomb);

}
