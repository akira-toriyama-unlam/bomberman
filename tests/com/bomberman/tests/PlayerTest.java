package com.bomberman.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bomberman.entities.Direction;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class PlayerTest {

	GameMap m;
	Player p;

	@Before
	public void beforeFunc() {
		m = new GameMap("mapTest", 50, 50);
	}

	@Test
	public void moveUpWithOutObj() {
		p = new Player(0, 0, m);

		p.move(Direction.UP);

		Assert.assertEquals(p.getY(), 1);
		Assert.assertEquals(p.getX(), 0);

		p.move(Direction.UP);

		Assert.assertEquals(p.getY(), 2);
		Assert.assertEquals(p.getX(), 0);
	}

	@Test
	public void moveLeftWithOutObj() {
		p = new Player(1, 0, m);

		p.move(Direction.LEFT);
		Assert.assertEquals(p.getX(), 0);
		Assert.assertEquals(p.getY(), 0);

	}

	@Test
	public void moveDownWithOutObj() {
		p = new Player(0, 1, m);

		p.move(Direction.DOWN);
		Assert.assertEquals(p.getY(), 0);
		Assert.assertEquals(p.getX(), 0);

	}

	@Test
	public void moveRightWithOutObj() {
		p = new Player(0, 0, m);

		p.move(Direction.RIGHT);
		Assert.assertEquals(p.getY(), 0);
		Assert.assertEquals(p.getX(), 1);
		p.move(Direction.RIGHT);
		Assert.assertEquals(p.getY(), 0);
		Assert.assertEquals(p.getX(), 2);
	}

	@Test
	public void moveUpWithObj() {
		p = new Player(0, 0, m);
		Tile t = new Tile(0, 1, false, m, false);
		m.addObject(t);
		p.move(Direction.UP);

		Assert.assertEquals(p.getY(), 0);
		Assert.assertEquals(p.getX(), 0);

	}

	@Test
	public void moveDownWithObjOver() {
		p = new Player(0, 1, m);
		Tile t = new Tile(0, -1, false, m, true);
		m.addObject(t);

		p.move(Direction.DOWN);

		Assert.assertEquals(p.getY(), 0);
		Assert.assertEquals(p.getX(), 0);

	}

	@Test
	public void placeBombAndWalkOver() {
		p = new Player(1, 1, m);
		p.placeBomb(m);

		p.move(Direction.DOWN);
		Assert.assertEquals(p.getY(), 0);

		p.move(Direction.UP);
		Assert.assertEquals(p.getY(), 1);

		p.move(Direction.UP);
		Assert.assertEquals(p.getY(), 2);

		p.move(Direction.DOWN);
		Assert.assertEquals(p.getY(), 1);

		p.move(Direction.RIGHT);
		Assert.assertEquals(p.getX(), 2);

		p.move(Direction.LEFT);
		Assert.assertEquals(p.getX(), 1);

		p.move(Direction.LEFT);
		Assert.assertEquals(p.getX(), 0);
	}

	@Test
	public void cantMoveLeftOutOfMap() {
		p = new Player(0, 0, m);

		p.move(Direction.LEFT);
		Assert.assertEquals(p.getX(), 0);
		Assert.assertEquals(p.getY(), 0);
	}

	@Test
	public void cantMoveDownOutOfMap() {
		p = new Player(0, 0, m);

		p.move(Direction.DOWN);
		Assert.assertEquals(p.getX(), 0);
		Assert.assertEquals(p.getY(), 0);
	}

	@Test
	public void cantMoveRightOutOfMap() {
		p = new Player(50, 50, m);

		p.move(Direction.RIGHT);
		Assert.assertEquals(p.getX(), 50);
		Assert.assertEquals(p.getY(), 50);
	}

	@Test
	public void cantMoveUptOutOfMap() {
		p = new Player(50, 50, m);

		p.move(Direction.UP);
		Assert.assertEquals(p.getX(), 50);
		Assert.assertEquals(p.getY(), 50);
	}

	@Test
	public void shouldCanPlaceOneBombAtTheTime() {
		p = new Player(50, 50, m);
		p.placeBomb(m);
		p.placeBomb(m);
		Assert.assertEquals(1, m.getObjects().size());
	}

	@Test
	public void shouldCanPlaceAnotherBombAfterWaitForExplosion() throws InterruptedException {
		p = new Player(50, 50, m);
		p.placeBomb(m);
		// Thread.sleep(2200);
		p.placeBomb(m);
		Assert.assertEquals(1, m.getObjects().size());
	}
}
