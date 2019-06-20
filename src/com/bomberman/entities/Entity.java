package com.bomberman.entities;

import java.awt.Image;

import com.bomberman.graphics.Sprite;

public abstract class Entity {

	protected double x;
	protected double y;
	protected boolean moving = false; 
	protected InteractionListener interactionListener;
	public Image sprite;
	public boolean destroyed;
	protected int animate = 0;
	protected final int MAX_ANIMATE = 7500; //save the animation status and dont let this get too big
	
	public void animate() {
		if(animate < MAX_ANIMATE) animate++; else animate = 0; //reset animation
	}
	

	public Entity(double x, double y, InteractionListener map) {
		this.x = x;
		this.y = y;
		this.interactionListener = map;
		this.destroyed = false;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}
	
	public boolean canBeOverpassed() {
		return this instanceof Player;
	}
	
	public boolean isDestructible() {
		return this instanceof Destructible;
	}
	
	public boolean isBomb() {
		return this instanceof Bomb;
	}
	
	public boolean isPlayer() {
		return this instanceof Player;
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
	
	
	
}
