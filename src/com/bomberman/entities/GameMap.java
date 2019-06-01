package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

	protected String name;
	protected List<Entity> objects;
	protected int width;
	protected int height;
	protected int widthBomberman = 28;
	protected int widthTile = 40;
	protected int errorMovimiento = 2; // es un entero para que da un error de margen para que el bomberman no se tilde justo en los l�mites de cada ladrillo

	public GameMap(String name, int width, int height) {
		this.name = name;
		this.objects = new ArrayList<>();
		this.width = width;
		this.height = height;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean canMove(double x, double y, Direction direction) {
		switch (direction) {
		case RIGHT:
			return !((x + 0.1) > (width - 80))
					&& !this.objects.stream().anyMatch 
					(o -> (o.x <= (x+widthBomberman) && (x+widthBomberman) <= (o.x + widthTile) && (o.y + widthTile) > (y+errorMovimiento) && (y + widthBomberman) > (o.y + errorMovimiento) && !o.isDestroyed() && !o.canOver)
							);
		case LEFT:
			return !((x - 0.1 - 40) < 0) 
					&& !this.objects.stream().anyMatch
					(o -> (x <= (o.x+widthTile) && (o.x+widthTile) <= (x + widthBomberman) && (o.y + widthTile) > (y + errorMovimiento) && (y + widthBomberman) > (o.y + errorMovimiento) && !o.isDestroyed() && !o.canOver)
							);
		case UP:
			return !((y - 0.1 - 40) < 0)
					&& !this.objects.stream().anyMatch(o -> (
							(o.x<= x && (x+errorMovimiento) <= (o.x + widthTile)||
							 x <= o.x && o.x <= (x + widthBomberman - errorMovimiento) || 
							 (x+errorMovimiento) <= (o.x + widthTile) && (o.x + widthTile) <= (x + widthBomberman)
							 ) &&  (y <= (o.y + widthTile) && y >= (o.y)) && !o.isDestroyed() && !o.canOver ));
		case DOWN:
			return !((y + 0.1) > (height-100)) 
					&& !this.objects.stream().anyMatch(o -> (
							(o.x<= x && (x+errorMovimiento) <= (o.x + widthTile)||
							 x <= o.x && o.x <= (x + widthBomberman-errorMovimiento) || 
							 (x+errorMovimiento) <= (o.x + widthTile) && (o.x + widthTile) <= (x + widthBomberman)
							 ) && 
						   ((y + widthBomberman) >= (o.y) && (y + widthBomberman) <= (o.y + widthTile))&& !o.isDestroyed() && !o.canOver ));
		default:
			return false;
		}
	}

	public void addObject(Entity obj) {
		this.objects.add(obj);
	}

	public List<Entity> getObjects() {
		return this.objects;
	}

	public Entity getAtPosition(double x, double y) {
		return this.objects.stream().filter(o -> o.x == x && o.y == y).findFirst().orElse(null);
	}
	
}
