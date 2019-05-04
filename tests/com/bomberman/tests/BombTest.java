package com.bomberman.tests;

import org.junit.Assert;
import org.junit.Test;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class BombTest {

	@Test
	public void shouldExplodeBomb() throws InterruptedException {
		GameMap map = new GameMap("mapTest", 50, 50);
		Bomb bomb = new Bomb(0, 0, map, new Player(0, 0, map));
		Thread.sleep(2200);
		Assert.assertEquals(bomb.isDestroyed(), true);
	}

	@Test
	public void shouldExplodeBombAndEverythinInRange() throws InterruptedException {
		GameMap map = new GameMap("mapTest", 50, 50);
		map.addObject(new Tile(4, 5, true, map, false));
		map.addObject(new Tile(4, 3, true, map, false));
		map.addObject(new Tile(3, 4, true, map, false));
		map.addObject(new Tile(5, 4, true, map, false));
		new Bomb(4, 4, map, new Player(4, 4, map));
		Thread.sleep(2200);
		Assert.assertEquals(map.getAtPosition(4, 5), null);
		Assert.assertEquals(map.getAtPosition(4, 3), null);
		Assert.assertEquals(map.getAtPosition(3, 4), null);
		Assert.assertEquals(map.getAtPosition(5, 4), null);
	}

}
