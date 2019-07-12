package com.bomberman.entities;

import com.bomberman.dto.MovementStatusDto;

public class MovementStatus {
	private Direction direction;
	private boolean moving;
	private int animateCount;
	
	public MovementStatus(Direction direction, boolean moving, int animateCount) {
		this.direction = direction;
		this.moving = moving;
		this.animateCount = animateCount;
	}
	
	public MovementStatus(MovementStatusDto movementStatusDto) {
		this.direction = movementStatusDto.getDirection();
		this.moving = movementStatusDto.isMoving();
		this.animateCount = movementStatusDto.getAnimateCount();
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
