package com.bomberman.graphics;

import com.bomberman.services.MapMessage;

public interface SocketActionListener {
	
	void messageReceived(MapMessage message);

}
