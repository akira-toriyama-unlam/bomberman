package com.bomberman.graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.Entity;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class JGraphicPanel extends JPanel {

	private JGraphicWindow frame;
	private Image background;
	private GameMap map;
	
	public JGraphicPanel(JGraphicWindow frame) {
		this.frame = frame;
		this.map = new GameMap(null);
		this.background = new ImageIcon("./resources/fondo.png").getImage();
		
		fillMapWithTiles();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		GameMap map = this.frame.getMap();
		int width = this.getSize().width;
		int height = this.getSize().height;
		
		if(map.getPlayers().isEmpty()) {
			frame.setStopKeyEvents(true);
			frame.cancelTimer();
			frame.drawEndGame(g);
		} else {
			if (this.background != null) {
				g.drawImage(this.background, 0, 0, width, height, null);
			}
			g.setColor(new Color(204,204,204));
			
			Iterator<Entity> itEntity = map.getObjects().iterator();
			while (itEntity.hasNext()) {
			    Entity entity = itEntity.next();
			    g.drawImage(entity.getSprite(), (int) entity.getX(), (int) entity.getY(), 40, 40, null);
			}
			
			Iterator<Player> itPlayer = map.getPlayers().iterator();
			while(itPlayer.hasNext()) {
				Player player = itPlayer.next();
				g.drawImage(player.getSprite(), (int) player.getX(), (int) player.getY(), 40, 40, null);
			}
		}
	}

	public GameMap getMap() {
		return this.map;
	}

	private void addTileToMap(int x, int y, GameMap m, boolean destroy) {
		if (destroy) {
			this.map.addObject(new DestructibleTile(x, y));
		} else {
			this.map.addObject(new Tile(x, y));
		}
	}
	
	private void fillMapWithTiles() {
		addTileToMap(40,80,this.map,true);
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
		
		// Border limits
		for(int i = 0; i< 800; i+=40) {
			addTileToMap(i,0,this.map,false);
			addTileToMap(0,i,this.map,false);
			addTileToMap(800,i,this.map,false);
			addTileToMap(i,560,this.map,false);
		}
		
		// Inner non-breaking tiles
		for(int i = 80; i< 1040; i+=80) {
			addTileToMap(80,i,this.map,false);
			addTileToMap(160,i,this.map,false);
			addTileToMap(240,i,this.map,false);
			addTileToMap(320,i,this.map,false);
			addTileToMap(400,i,this.map,false);
			addTileToMap(480,i,this.map,false);
			addTileToMap(560,i,this.map,false);
			addTileToMap(640,i,this.map,false);
			addTileToMap(720,i,this.map,false);
			addTileToMap(800,i,this.map,false);
			addTileToMap(880,i,this.map,false);
		}

	}
	
}
