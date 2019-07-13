package com.bomberman.client;

import com.bomberman.server.RoomsDto;
import com.bomberman.services.MapMessage;

public interface SocketActionListener {
	
	void mapMessageReceived(MapMessage message);
	void loginMessageReceived(RoomsDto message);

}
