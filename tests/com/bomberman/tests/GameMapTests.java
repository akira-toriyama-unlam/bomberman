package com.bomberman.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.Direction;
import com.bomberman.entities.Entity;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;

public class GameMapTests {

	GameMap map;

	@Before
	public void beforeFunc() {
		map = new GameMap("Map test", 50, 50);
	}

	/**
	 * Start canMove
	 */
	@Test
	public void shouldReturnTrueIfCanMoveRight() {
		Assert.assertEquals(true, map.canMove(0, 0, Direction.RIGHT));
	}

	@Test
	public void shouldReturnTrueIfCanMoveLeft() {
		Assert.assertEquals(true, map.canMove(1, 0, Direction.LEFT));
	}

	@Test
	public void shouldReturnTrueIfCanMoveUp() {
		Assert.assertEquals(true, map.canMove(0, 0, Direction.UP));
	}

	@Test
	public void shouldReturnTrueIfCanMoveDown() {
		Assert.assertEquals(true, map.canMove(0, 1, Direction.DOWN));
	}

	@Test
	public void shouldReturnFalseIfCantMoveRight() {
		Assert.assertEquals(false, map.canMove(49, 0, Direction.RIGHT));
	}

	@Test
	public void shouldReturnFalseIfCantMoveLeft() {
		Assert.assertEquals(false, map.canMove(0, 0, Direction.LEFT));
	}

	@Test
	public void shouldReturnFalseIfCantMoveUp() {
		Assert.assertEquals(false, map.canMove(0, 49, Direction.UP));
	}

	@Test
	public void shouldReturnFalseIfCantMoveDown() {
		Assert.assertEquals(false, map.canMove(0, 0, Direction.DOWN));
	}

	/**
	 * End canMove
	 */

	/**
	 * Start getAtPosition
	 */
	@Test
	public void shouldReturnEntityIfExists() {
		Entity e = new Player(39, 39, map);
		map.addObject(e);
		Assert.assertEquals(e, map.getAtPosition(39, 39));
	}

	@Test
	public void shouldReturnNullIfEntityDoesNotExists() {
		Assert.assertEquals(null, map.getAtPosition(39, 39));
	}

	/**
	 * End getAtPosition
	 */

	/**
	 * Start exploitEntitiesInBombRange
	 * 
	 * @throws InterruptedException
	 */

	@Test
	public void shouldDestroyChainedBombsIfTheyExists() {
		Bomb b1 = new Bomb(0, 0, map, new Player(0, 0, map));
		map.addObject(b1);
		Bomb b2 = new Bomb(1, 0, map, new Player(1, 0, map));
		map.addObject(b2);
		Bomb b3 = new Bomb(2, 0, map, new Player(2, 0, map));
		map.addObject(b3);
		Bomb b4 = new Bomb(3, 0, map, new Player(3, 0, map));
		map.addObject(b4);
		map.exploitEntitesInBombRange(b1);
		Assert.assertEquals(true, b2.isDestroyed());
		Assert.assertEquals(true, b3.isDestroyed());
		Assert.assertEquals(true, b4.isDestroyed());
	}

	@Test
	public void shouldDestroyNeighboorElementsIfTheyExists() {
		Bomb b1 = new Bomb(4, 4, map, new Player(4, 4, map));
		map.addObject(b1);
		Bomb b2 = new Bomb(5, 4, map, new Player(5, 4, map));
		map.addObject(b2);
		Bomb b3 = new Bomb(3, 4, map, new Player(3, 4, map));
		map.addObject(b3);
		Player p1 = new Player(4, 5, map);
		map.addObject(p1);
		Player p2 = new Player(4, 3, map);
		map.addObject(p2);
		map.exploitEntitesInBombRange(b1);
		Assert.assertEquals(true, b2.isDestroyed());
		Assert.assertEquals(true, b3.isDestroyed());
		Assert.assertEquals(true, p1.isDestroyed());
		Assert.assertEquals(true, p2.isDestroyed());
	}

	/**
	 * End exploitEntitiesInBombRange
	 */

}
