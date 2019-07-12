package com.bomberman.client;

import com.bomberman.services.MapMessage;

public interface SocketActionListener {
	
	void messageReceived(MapMessage message);

}
