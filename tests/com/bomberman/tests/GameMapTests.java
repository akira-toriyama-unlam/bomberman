package com.bomberman.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	 */

	@Test
	public void shouldDestroyEntitiesIfTheyExists() {

	}

	/**
	 * End exploitEntitiesInBombRange
	 */

}
