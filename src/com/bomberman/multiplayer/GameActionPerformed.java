package com.bomberman.multiplayer;

import java.util.Observer;

public interface GameActionPerformed {
	
	void actionPerformed();
	void addObserver(Observer observer);

}
