package com.bomberman.entities;import java.awt.Image;
import java.io.Serializable;

import com.bomberman.dto.EntityDto;
import com.bomberman.server.GameActionPerformed;

public abstract class Entity implements Serializable {

	protected double x;
	protected double y;
	//protected GameActionPerformed gameActionPerformedListener;
	public Image sprite;
	public boolean destroyed;
	protected int animateCount = 0;
	protected final int MAX_ANIMATE = 7500; //save the animation status and dont let this get too big
	public boolean painted;
	
	public void incrementAnimateCount() {
			if(animateCount < MAX_ANIMATE) animateCount++; else animateCount = 0; //reset animation
	}
	
	public Entity(double x, double y/*, GameActionPerformed gameActionPerformedListener*/) {
		this.x = x;
		this.y = y;
		//this.gameActionPerformedListener = gameActionPerformedListener;
		this.destroyed = false;
		this.painted = false;
	}

	public boolean isPainted() {
		return painted;
	}


	public void setPainted(boolean painted) {
		this.painted = painted;
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}
	
	public void setY(double y) {	
		this.y = y;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}


	public boolean isDestroyed() {
		return destroyed;
	}


	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public boolean canBeOverpassed() {
		return this instanceof Player;
	}
	
	public boolean isDestructible() {
		return this instanceof Destructible;
	}
	
	public boolean isNotDestructible() {
		return !(this instanceof Destructible);
	}
	
	public boolean isBomb() {
		return this instanceof Bomb;
	}
	
	public boolean isExplosion() {
		return this instanceof Explosion;
	}
	
	public boolean isTile() {
		return this instanceof Tile;
	}
	
	public boolean isDestructibleTile() {
		return this instanceof DestructibleTile;
	}
	
	public boolean isPlayer() {
		return this instanceof Player;
	}
	
}
