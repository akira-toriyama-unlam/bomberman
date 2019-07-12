package com.bomberman.services;

import com.bomberman.entities.Direction;

public class DirectionMessage {
	
	private Direction direction;
	
	public DirectionMessage(Direction directionMessage) {
		this.direction = directionMessage;
	}
	
	public Direction getDirection() {
		return this.direction;
	}

}
