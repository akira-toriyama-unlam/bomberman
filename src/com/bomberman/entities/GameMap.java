package com.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

import com.bomberman.server.GameActionPerformed;

public class GameMap {

	public final static int MOVEMENT_ERROR = 2; // This constant is used for fixing the movement into the Jpanel.
	public final static double BOMB_ERROR = 0.01; // This constant is used for fixing bomb range error.

	protected List<Entity> objects;
	protected List<Player> players;
	private GameActionPerformed gameActionPerformedListener;

	public GameMap(GameActionPerformed gameActionPerformedListener) {
		this.gameActionPerformedListener = gameActionPerformedListener;
		this.objects = new ArrayList<>();
		this.players = new ArrayList<>();
	}

	public boolean addObject(Entity obj) {
		Entity entity = getAtPosition(obj.getX(), obj.getY());
		if (entity == null) {
			this.objects.add(obj);
			return true;
		}
		return false;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public List<Entity> getObjects() {
		return this.objects;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public Entity getAtPosition(double x, double y) {
		return this.getObjects().stream().filter(o -> o.getX() == x && o.getY() == y).findFirst().orElse(null);
	}

	public void setObjects(List<Entity> objects) {
		this.objects = objects;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
