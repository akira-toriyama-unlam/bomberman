package com.bomberman.entities;

import javax.swing.ImageIcon;

public class Player extends Entity implements ExplosionListener, Destructible {

	public static final double MOVEMENT_UNIT = 8;
	public static final int HEIGHT = 40;
	public static final int WIDTH = 28;
	private static final int CONCURRENT_BOMBS = 2;
	private int bombsCount;
	private boolean alive;
	
	private ImageIcon imageIcon;
	
	public Player(double x, double y, InteractionListener map, ImageIcon imageIcon) {
		super(x, y, map);
		this.alive = true;
		this.bombsCount = Player.CONCURRENT_BOMBS;
		this.imageIcon = imageIcon;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alive ? 1231 : 1237);
		result = prime * result + bombsCount;
		result = prime * result + ((imageIcon == null) ? 0 : imageIcon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (alive != other.alive)
			return false;
		if (bombsCount != other.bombsCount)
			return false;
		if (imageIcon == null) {
			if (other.imageIcon != null)
				return false;
		} else if (!imageIcon.equals(other.imageIcon))
			return false;
		return true;
	}

	private int generateFixedX() {
		return Tile.TILE_SIZE * ((int) x / Tile.TILE_SIZE);
	}
	
	private int generateFixedY() {
		return Tile.TILE_SIZE * ((int) y / Tile.TILE_SIZE);
	}

	public void placeBomb(InteractionListener gameMap) {
		if (this.bombsCount > 0) {
			Bomb bomb = new Bomb(generateFixedX(), generateFixedY(), gameMap, this);
			gameMap.bombPlaced(bomb);
			bombsCount--;
		}
	}
	
	public void move(Direction direction) {
		interactionListener.movement(this, direction);
	}

	@Override
	public void destroy() {
		this.alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public void update() {
		bombsCount++;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}
	
	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

}
