package com.bomberman.dto;

import com.bomberman.entities.Direction;

public class MovementStatusDto {
	
	private Direction direction;
	private boolean moving;
	private int animateCount;
	
	public MovementStatusDto(Direction direction, boolean moving, int animateCount) {
		if(direction != null ) {
			this.direction = direction;	
		} else {
			this.direction = Direction.RIGHT;
		}
		
		this.moving = moving;
		this.animateCount = animateCount;
	}
	
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	public int getAnimateCount() {
		return animateCount;
	}
	public void setAnimateCount(int animateCount) {
		this.animateCount = animateCount;
	}
}
