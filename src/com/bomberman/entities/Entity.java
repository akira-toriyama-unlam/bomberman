package com.bomberman.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable {

	protected double x;
	protected double y;
	protected InteractionListener interactionListener;

	public Entity(double x, double y, InteractionListener map) {
		this.x = x;
		this.y = y;
		this.interactionListener = map;
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}

}
