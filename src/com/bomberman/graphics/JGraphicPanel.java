package com.bomberman.graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.Destructible;
import com.bomberman.entities.Entity;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class JGraphicPanel extends JPanel {

	private JGraphicWindow frame;
	private ImageIcon brickImage;
	private ImageIcon bombImage;
	private ImageIcon bombermanImage;
	private ArrayList<Bomb> bombList;

	private Image background;
	 
	public JGraphicPanel(JGraphicWindow frame) {
		this.frame = frame;
		this.brickImage = new ImageIcon("./resources/images.png");
		this.bombImage = new ImageIcon("./resources/bomba.png");
		this.background = new ImageIcon("./resources/fondo.png").getImage();
		
		// Move to server
		//this.map = new GameMap("Bomberman", JGraphicWindow.WIDTH, JGraphicWindow.HEIGHT); // server
		//bombList = new ArrayList<Bomb>(); // server
		
		
		
		
		// Server
		//this.map.addPlayer(new Player(40, 40, this.map, new ImageIcon("./resources/Abajo_0.png"))); 
		//fillMapWithTiles(); // server
	}
	
	@Override
	public void paintComponent(Graphics g) {
		GameMap map = this.frame.getMap();
		int width = this.getSize().width;
		int height = this.getSize().height;
		
		if(map.getPlayers().size() == 0) {
			frame.setStopKeyEvents(true);
			frame.cancelTimer();
			frame.drawEndGame(g);
		} else {
			if (this.background != null) {
				g.drawImage(this.background, 0, 0, width, height, null);
			}
			g.setColor(new Color(204,204,204));
			
			// Se dibuja usando el mapa del frame (que viene del server)
			for(Entity entity : map.getObjects()) {
				if(entity instanceof Bomb) {
					g.drawImage(this.bombImage.getImage(), (int)entity.getX(), (int)entity.getY(), 40, 40, null);
				} else if (entity instanceof Tile && entity instanceof Destructible) {
					g.drawImage(this.brickImage.getImage(), (int)entity.getX(), (int)entity.getY(), 40, 40, null);
				} else if(entity instanceof Tile && !(entity instanceof Destructible)) {
					g.fillRect((int) entity.getX(),(int) entity.getY(), 40, 40);
				}
			}
			
			for(Player player : map.getPlayers() ) {
				g.drawImage(player.getImageIcon().getImage(), (int) player.getX(), (int) player.getY(), 40, 40, null);	
			}
		}
	}
	
	public void addBomb(Bomb bomb) {
		bombList.add(bomb);
	}
	
	public void setImageBomberman(String image) {
		bombermanImage = new ImageIcon(image);
	}

	public ImageIcon getBombermanImage() {
		return this.bombermanImage;
	}
}
