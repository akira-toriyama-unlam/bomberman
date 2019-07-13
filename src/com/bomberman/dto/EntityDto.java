package com.bomberman.dto;

import java.awt.Image;

import com.bomberman.entities.EntityTypes;
import com.bomberman.entities.ExplosionDirection;
import com.bomberman.entities.Player;

public class EntityDto {

	protected int x;
	protected int y;
	protected EntityTypes entityType;
	protected transient Image sprite;
	protected int animateCount;
	protected boolean destroyed;
	protected ExplosionDirection explosionDirection;
	protected boolean painted;

	public EntityDto(int x, int y, EntityTypes entityType, boolean painted, boolean destroyed) {
		this.x = x;
		this.y = y;
		this.entityType = entityType;
		this.painted = painted;
		this.destroyed = destroyed;
	}

	public ExplosionDirection getExplosionDirection() {
		return explosionDirection;
	}

	public void setExplosionDirection(ExplosionDirection explosionDirection) {
		this.explosionDirection = explosionDirection;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getSprite() {
		return sprite;
	}

	public boolean isBombDto() {
		return this instanceof BombDto;
	}

	public boolean isTileDto() {
		return this instanceof TileDto;
	}

	public boolean isDestructibleTileDto() {
		return this instanceof DestructibleTileDto;
	}

	public void setEntityType(EntityTypes entityType) {
		this.entityType = entityType;
	}

	public EntityTypes getEntityType() {
		return this.entityType;
	}

	public int getAnimateCount() {
		return animateCount;
	}

	public void setAnimateCount(int animateCount) {
		this.animateCount = animateCount;
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


	public void setPainted(boolean painted) {
		this.painted = painted;
	}
}