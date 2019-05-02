package com.bomberman.tests;

import org.junit.Assert;
import org.junit.Test;

import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;

public class PlayerTest {

	@Test
	public void aRandomTest() {
		Player p = new Player(0, 0, new GameMap("mapTest", 50, 50));
		Assert.assertEquals(p.isAlive(), true);
	}

}
