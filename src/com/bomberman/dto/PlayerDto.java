package com.bomberman.dto;

import java.awt.Image;

import com.bomberman.entities.Player;
import com.bomberman.graphics.PlayerSpriteManager;
import com.bomberman.graphics.Sprite;

public class PlayerDto {
	
	private double x;
	private double y;
	private Integer id;
	private MovementStatusDto movementStatusDto;
	private boolean destroyed;
	private boolean painted;

	public PlayerDto(double x, double y, Integer id, MovementStatusDto movementStatusDto, boolean destroyed, boolean painted) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.movementStatusDto = movementStatusDto;
		this.destroyed = destroyed;
		this.painted = painted;
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
	
	public boolean isDestroyed() {
		return this.destroyed;
	}
	
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	
	public boolean isPainted() {
		return this.painted;
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
				return Sprite.movingSprite(PlayerSpriteManager.getInstance(id).getPlayerSpriteUp(0), PlayerSpriteManager.getInstance(id).getPlayerSpriteUp(1),
						PlayerSpriteManager.getInstance(id).getPlayerSpriteUp(2), this.movementStatusDto.getAnimateCount(), 5);
			}
			return PlayerSpriteManager.getInstance(id).getPlayerSpriteUp(0);
		case RIGHT:
			if(this.movementStatusDto.isMoving()) {
				return Sprite.movingSprite(PlayerSpriteManager.getInstance(id).getPlayerSpriteRight(0), PlayerSpriteManager.getInstance(id).getPlayerSpriteRight(1),
						PlayerSpriteManager.getInstance(id).getPlayerSpriteRight(0), this.movementStatusDto.getAnimateCount(), 5);
			}
			return PlayerSpriteManager.getInstance(id).getPlayerSpriteRight(0);
		case DOWN:
			if(this.movementStatusDto.isMoving()) {
				return Sprite.movingSprite(PlayerSpriteManager.getInstance(id).getPlayerSpriteDown(0), PlayerSpriteManager.getInstance(id).getPlayerSpriteDown(1),
						PlayerSpriteManager.getInstance(id).getPlayerSpriteDown(2), this.movementStatusDto.getAnimateCount(), 5);
			}
			return PlayerSpriteManager.getInstance(id).getPlayerSpriteDown(0);
		case LEFT:
			if(this.movementStatusDto.isMoving()) {
				return Sprite.movingSprite(PlayerSpriteManager.getInstance(id).getPlayerSpriteLeft(0), PlayerSpriteManager.getInstance(id).getPlayerSpriteLeft(1),
						PlayerSpriteManager.getInstance(id).getPlayerSpriteLeft(2), this.movementStatusDto.getAnimateCount(), 5);
			}
			return PlayerSpriteManager.getInstance(id).getPlayerSpriteLeft(0);
		default:
			return null;
		}
	}
}