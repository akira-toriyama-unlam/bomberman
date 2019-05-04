package com.bomberman.tests;

import org.junit.Assert;
import org.junit.Test;

import com.bomberman.entities.Direction;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class PlayerTest {

	@Test
	public void moveUpWithOutObj() {
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(0, 0, m);
		
		p.move(Direction.UP);
		
		Assert.assertEquals(p.getY(),1);
		Assert.assertEquals(p.getX(),0);
		
		p.move(Direction.UP);
		
		Assert.assertEquals(p.getY(),2);
		Assert.assertEquals(p.getX(),0);
	}
	
	@Test
	public void moveLeftWithOutObj() {
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(1, 0, m);
		
		p.move(Direction.LEFT);
		Assert.assertEquals(p.getX(),0);
		Assert.assertEquals(p.getY(),0);
		
	}
	
	@Test
	public void moveDownWithOutObj() {
		
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(0, 1, m);
		
		p.move(Direction.DOWN);
		Assert.assertEquals(p.getY(),0);
		Assert.assertEquals(p.getX(),0);
		
	}
	
	@Test
	public void moveRightWithOutObj() {
		
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(0, 0, m);
		
		p.move(Direction.RIGHT);
		Assert.assertEquals(p.getY(),0);
		Assert.assertEquals(p.getX(),1);
		p.move(Direction.RIGHT);
		Assert.assertEquals(p.getY(),0);
		Assert.assertEquals(p.getX(),2);
	}
	
	@Test
	public void moveUpWithObj() {
			
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(0, 0, m);
		Tile t = new Tile(0,1,false,m,false);
		
		p.move(Direction.UP);
		
		Assert.assertEquals(p.getY(),0);
		Assert.assertEquals(p.getX(),0);
		
	}
	
	@Test
	public void moveDownWithObjOver() {
			
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(0, 1, m);
		Tile t = new Tile(0,-1,false,m, true);
		
		p.move(Direction.DOWN);
		
		Assert.assertEquals(p.getY(),0);
		Assert.assertEquals(p.getX(),0);
		
	}
	
	@Test
	public void placeBombAndWalkOver(){
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(1, 1, m);
		p.placeBomb(m);
		
		p.move(Direction.DOWN);
		Assert.assertEquals(p.getY(),0);
		
		p.move(Direction.UP);
		Assert.assertEquals(p.getY(),1);
		
		p.move(Direction.UP);
		Assert.assertEquals(p.getY(),2);
		
		p.move(Direction.DOWN);
		Assert.assertEquals(p.getY(),1);
		
		p.move(Direction.RIGHT);
		Assert.assertEquals(p.getX(),2);
		
		p.move(Direction.LEFT);
		Assert.assertEquals(p.getX(),1);
		
		p.move(Direction.LEFT);
		Assert.assertEquals(p.getX(),0);
	}
	
	@Test
	public void cantMoveLeftOutOfMap() {
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(0, 0, m);
		
		p.move(Direction.LEFT);
		Assert.assertEquals(p.getX(),0);
		Assert.assertEquals(p.getY(),0);
	}
	
	@Test
	public void cantMoveDownOutOfMap() {
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(0, 0, m);
		
		p.move(Direction.DOWN);
		Assert.assertEquals(p.getX(),0);
		Assert.assertEquals(p.getY(),0);
	}
	
	@Test
	public void cantMoveRightOutOfMap() {
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(50, 50, m);
		
		p.move(Direction.RIGHT);
		Assert.assertEquals(p.getX(),50);
		Assert.assertEquals(p.getY(),50);
	}
	
	@Test
	public void cantMoveUptOutOfMap() {
		GameMap m = new GameMap("mapTest", 50, 50);
		Player p = new Player(50, 50, m);
		
		p.move(Direction.UP);
		Assert.assertEquals(p.getX(),50);
		Assert.assertEquals(p.getY(),50);
	}
	
}
