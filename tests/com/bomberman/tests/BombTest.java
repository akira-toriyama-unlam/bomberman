package com.bomberman.tests;

import org.junit.Assert;
import org.junit.Test;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.Entity;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;

public class BombTest {
	
	@Test
	public void shouldExplodeBomb() throws InterruptedException {
		GameMap map = new GameMap("mapTest", 50, 50);
		Bomb bomb = new Bomb(0, 0, map,new Player(0,0,map));
		Thread.sleep(2000);
		Assert.assertEquals(bomb.isDestroyed(), true);				
	}
	
	@Test
	public void shouldExplodeBombAndEverythinInRange() throws InterruptedException {
		GameMap map = new GameMap("mapTest", 50, 50);
		// map.addObject();
		// 4,5 - 4,3 - 3,4 - 5,4
		Bomb bomb = new Bomb(4, 4, map,new Player(4,4,map));
		Thread.sleep(2000);
	}
	

}
