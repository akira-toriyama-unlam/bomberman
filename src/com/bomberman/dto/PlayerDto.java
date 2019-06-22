package com.bomberman.dto;

import java.awt.Image;

import com.bomberman.entities.Player;
import com.bomberman.graphics.Sprite;

public class PlayerDto {
	
	private double x;
	private double y;
	private Integer id;
	private MovementStatusDto movementStatusDto;

	public PlayerDto(double x, double y, Integer id, MovementStatusDto movementStatusDto) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.movementStatusDto = movementStatusDto;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public MovementStatusDto getMovementStatusDto() {
		return movementStatusDto;
	}

	public void setMovementStatus(MovementStatusDto movementStatusDto) {
		this.movementStatusDto = movementStatusDto;
	}

	public Player toPlayer() {
		return new Player(x, y, id);
	}
	
	public Image chooseSprite() {
		switch(this.movementStatusDto.getDirection()) {
		case UP:
			if(this.movementStatusDto.isMoving()) {
				return Sprite.movingSprite(Sprite.player_blue_up, Sprite.player_blue_up_1, Sprite.player_blue_up_2, 
						this.movementStatusDto.getAnimateCount(), 5);
			}
			return Sprite.player_blue_up;
		case RIGHT:
			if(this.movementStatusDto.isMoving()) {
				return Sprite.movingSprite(Sprite.player_blue_right, Sprite.player_blue_right_1, Sprite.player_blue_right_2, 
						this.movementStatusDto.getAnimateCount(), 5);
			}
			return Sprite.player_blue_right;
		case DOWN:
			if(this.movementStatusDto.isMoving()) {
				return Sprite.movingSprite(Sprite.player_blue_down, Sprite.player_blue_down_1, Sprite.player_blue_down_2, 
						this.movementStatusDto.getAnimateCount(), 5);
			}
			return Sprite.player_blue_down;
		case LEFT:
			if(this.movementStatusDto.isMoving()) {
				return Sprite.movingSprite(Sprite.player_blue_left, Sprite.player_blue_left_1, Sprite.player_blue_left_2, 
						this.movementStatusDto.getAnimateCount(), 5);
			}
			return Sprite.player_blue_left;
		default:
			return null;
		}
	}
}