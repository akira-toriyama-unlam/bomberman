package com.bomberman.multiplayer;

import java.util.Observer;

import com.bomberman.services.Message;

public interface GameActionPerformed {
	
	void actionPerformed(Message message);
	void addObserver(Observer observer);

}
