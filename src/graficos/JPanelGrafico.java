package graficos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.Entity;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class JPanelGrafico extends JPanel {

	private ImageIcon ladrillo;
	private ImageIcon bombIcon;
	private ImageIcon bomberman;
	private ImageIcon enemy;
	private ArrayList<Bomb> listaBombas;
	private GameMap map;
	private Player player;
	private Player playerEnemy;
	
	private Image background;
	 

	
	public JPanelGrafico() {
		this.map = new GameMap("Bomberman - Programación Avanzada Unlam 2019", 600, 460);
		this.player = new Player(40, 40, this.map);
		this.playerEnemy = new Player(520,360,this.map);
		
		listaBombas = new ArrayList<Bomb>();
		ladrillo = new ImageIcon("./src/imagenes/images.png");
		bombIcon = new ImageIcon("./src/imagenes/bomba.png");
		bomberman = new ImageIcon("./src/imagenes/Abajo_0.png");
		enemy = new ImageIcon("./src/imagenes/enemy.png");
		this.background = new ImageIcon("./src/imagenes/fondo.png").getImage();
		this.map.addObject(player);
		this.map.addObject(playerEnemy);
		crear_objeto(40,80,this.map,true);
		crear_objeto(80,360,this.map,true);
		crear_objeto(80,200,this.map,true);
		crear_objeto(120,200,this.map,true);
		crear_objeto(200,120,this.map,true);
		crear_objeto(280,160,this.map,true);
		crear_objeto(40,240,this.map,true);
		crear_objeto(200,200,this.map,true);
		crear_objeto(400,40,this.map,true);
		crear_objeto(400,200,this.map,true);
		crear_objeto(280,280,this.map,true);
		crear_objeto(280,320,this.map,true);
		crear_objeto(320,360,this.map,true);
		crear_objeto(480,360,this.map,true);
		crear_objeto(520,240,this.map,true);
		crear_objeto(360,120,this.map,true);
		crear_objeto(440,280,this.map,true);
		
		for(int i = 0; i< 560; i+=40) {
			crear_objeto(i,0,this.map,false);
			crear_objeto(0,i,this.map,false);
			crear_objeto(560,i,this.map,false);
			crear_objeto(i,400,this.map,false);
		}
		
		for(int i = 80; i< 360; i+=80) {
			crear_objeto(80,i,this.map,false);
			crear_objeto(160,i,this.map,false);
			crear_objeto(240,i,this.map,false);
			crear_objeto(320,i,this.map,false);
			crear_objeto(400,i,this.map,false);
			crear_objeto(480,i,this.map,false);
		}

		
	}
	
	public void crear_objeto(int x, int y, GameMap m, boolean destroy) {
		this.map.addObject(new Tile(x, y, destroy, m, false));
	}
	
	public void paintComponent(Graphics g) {
		int width = this.getSize().width;
		int height = this.getSize().height;
 

		if (this.background != null) {
			g.drawImage(this.background, 0, 0, width, height, null);
		}
		
		g.setColor(new Color(204,204,204));
		
		for(Entity entity : map.getObjects()) {
			if(entity instanceof Bomb && !entity.isDestroyed())
				g.drawImage(this.bombIcon.getImage(), (int)entity.getX(), (int)entity.getY(), 30, 30, null);
			else if (entity instanceof Tile && entity.canBeDestroy() && !entity.isDestroyed())
				g.drawImage(this.ladrillo.getImage(), (int)entity.getX(), (int)entity.getY(), 35, 35, null);
			else if(entity instanceof Tile && !entity.canBeDestroy())
				g.fillRect((int) entity.getX(),(int) entity.getY(), 35, 35);
		}
		
		if(!player.isDestroyed()) {
			g.drawImage(bomberman.getImage(), (int) player.getX(), (int) player.getY(), 30, 30, null);	
		}
		
		if(!playerEnemy.isDestroyed()) {
			g.drawImage(enemy.getImage(), (int)playerEnemy.getX(), (int)playerEnemy.getY(), 30, 30, null);	
		}

	}
	
	public Player getBomberman() {
		return this.player;
	}
	
	public ImageIcon getBombermanImage() {
		return this.bomberman;
	}
	public void setBomberman(Player player) {
		this.player = player;
	}
	
	public void agregarBomba(Bomb bomba) {
		listaBombas.add(bomba);
	}
	
	public GameMap getMap() {
		return this.map;
	}
	
	public void setImageBomberman(String image) {
		bomberman = new ImageIcon(image);
	}

}
