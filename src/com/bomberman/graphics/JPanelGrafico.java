package com.bomberman.graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.Destructible;
import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.Entity;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class JPanelGrafico extends JPanel {

	private ImageIcon brickImage;
	private ImageIcon bombImage;
	private ImageIcon bombermanImage;
	private ImageIcon enemyImage;
	private ArrayList<Bomb> bombList;
	private GameMap map;
	private Player player;
	private Player playerEnemy;
	
	private Image background;
	 
	public JPanelGrafico() {
		this.map = new GameMap("Bomberman", 600, 470);
		this.player = new Player(40, 40, this.map);
		this.playerEnemy = new Player(280, 40, this.map);
		
		bombList = new ArrayList<Bomb>();
		brickImage = new ImageIcon("./resources/images.png");
		bombImage = new ImageIcon("./resources/bomba.png");
		bombermanImage = new ImageIcon("./resources/Abajo_0.png");
		enemyImage = new ImageIcon("./resources/enemy.png");
		
		this.background = new ImageIcon("./resources/fondo.png").getImage();
		
		this.map.addPlayer(player);
		this.map.addPlayer(playerEnemy);
		
		fillMapWithTiles();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int width = this.getSize().width;
		int height = this.getSize().height;
 

		if (this.background != null) {
			g.drawImage(this.background, 0, 0, width, height, null);
		}
		
		g.setColor(new Color(204,204,204));
		
		for(Entity entity : map.getObjects()) {
			if(entity instanceof Bomb) {
				g.drawImage(this.bombImage.getImage(), (int)entity.getX(), (int)entity.getY(), 30, 30, null);
			} else if (entity instanceof Tile && entity instanceof Destructible) {
				g.drawImage(this.brickImage.getImage(), (int)entity.getX(), (int)entity.getY(), 35, 35, null);
			} else if(entity instanceof Tile && !(entity instanceof Destructible)) {
				g.fillRect((int) entity.getX(),(int) entity.getY(), 35, 35);
			}
		}
		
		g.drawImage(bombermanImage.getImage(), (int) player.getX(), (int) player.getY(), 30, 30, null);	
		g.drawImage(enemyImage.getImage(), (int)playerEnemy.getX(), (int)playerEnemy.getY(), 30, 30, null);	
	}
	
	public void setBomberman(Player player) {
		this.player = player;
	}
	
	public Player getBomberman() {
		return this.player;
	}
	
	public void addBomb(Bomb bomb) {
		bombList.add(bomb);
	}
	
	public GameMap getMap() {
		return this.map;
	}
	
	public void setImageBomberman(String image) {
		bombermanImage = new ImageIcon(image);
	}

	public ImageIcon getBombermanImage() {
		return this.bombermanImage;
	}

	private void addTileToMap(int x, int y, GameMap m, boolean destroy) {
		if (destroy) {
			this.map.addObject(new DestructibleTile(x, y, m));
		} else {
			this.map.addObject(new Tile(x, y, m));
		}
	}
	
	private void fillMapWithTiles() {
		addTileToMap(40,80,this.map,true);
		//addTileToMap(80,40,this.map,true);
		addTileToMap(80,360,this.map,true);
		addTileToMap(80,200,this.map,true);
		addTileToMap(120,200,this.map,true);
		addTileToMap(200,120,this.map,true);
		addTileToMap(280,160,this.map,true);
		addTileToMap(40,240,this.map,true);
		addTileToMap(200,200,this.map,true);
		addTileToMap(400,40,this.map,true);
		addTileToMap(360,40,this.map,true);
		addTileToMap(400,200,this.map,true);
		addTileToMap(280,280,this.map,true);
		addTileToMap(280,320,this.map,true);
		addTileToMap(320,360,this.map,true);
		addTileToMap(480,360,this.map,true);
		addTileToMap(520,240,this.map,true);
		addTileToMap(360,120,this.map,true);
		addTileToMap(440,280,this.map,true);
		
		for(int i = 0; i< 560; i+=40) {
			addTileToMap(i,0,this.map,false);
			addTileToMap(0,i,this.map,false);
			addTileToMap(560,i,this.map,false);
			addTileToMap(i,400,this.map,false);
		}
		
		for(int i = 80; i< 360; i+=80) {
			addTileToMap(80,i,this.map,false);
			addTileToMap(160,i,this.map,false);
			addTileToMap(240,i,this.map,false);
			addTileToMap(320,i,this.map,false);
			addTileToMap(400,i,this.map,false);
			addTileToMap(480,i,this.map,false);
		}

	}
	
}
