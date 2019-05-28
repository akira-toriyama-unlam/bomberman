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
	private ArrayList<Bomb> listaBombas;
	private GameMap map;
	private Player player;
	
	
	private Image background;
	 

	
	public JPanelGrafico() {
		this.map = new GameMap("prueba de graficos y funcionamiento", 600, 460);
		this.player = new Player(40, 40, this.map);
		listaBombas = new ArrayList<Bomb>();
		ladrillo = new ImageIcon("./src/imagenes/images.png");
		bombIcon = new ImageIcon("./src/imagenes/bomba.png");
		bomberman = new ImageIcon("./src/imagenes/Abajo_0.png");
		this.background = new ImageIcon("./src/imagenes/fondo.png").getImage();
		
		Entity e1 = new Tile(40, 80, true, this.map, false);
		this.map.addObject(e1);
		Entity e2 = new Tile(80, 360, true, this.map, false);
		this.map.addObject(e2);
		Entity e3 = new Tile(80, 200, true, this.map, false);
		this.map.addObject(e3);
		
		Entity e4 = new Tile(120, 200, true, this.map, false);
		this.map.addObject(e4);
		
		Entity e5 = new Tile(200, 120, true, this.map, false);
		this.map.addObject(e5);
		
		Entity e6 = new Tile(280, 160, true, this.map, false);
		this.map.addObject(e6);
		
		Entity e7 = new Tile(40, 240, true, this.map, false);
		this.map.addObject(e7);
		
	
	}

	public void paintComponent(Graphics g) {
		
//		super.paintComponent(g);
		/* Obtenemos el tamaño del panel para hacer que se ajuste a este
		cada vez que redimensionemos la ventana y se lo pasamos al drawImage */
		int width = this.getSize().width;
		int height = this.getSize().height;
 
		// Mandamos que pinte la imagen en el panel
		if (this.background != null) {
			g.drawImage(this.background, 0, 0, width, height, null);
		}
		
		//Dibujamos el Bomberman
//		g.setColor(Color.BLUE);
		//g.fillOval((int) circulo.getCentro().getX(), (int) circulo.getCentro().getY(), (int) circulo.getRadio(),
			//	(int) circulo.getRadio());
		
		// Vamos a diobujar las paredes
		g.setColor(new Color(204,204,204));
		for(int i = 0; i< 560; i+=40) {
			g.fillRect(i,0, 35, 35);
			g.fillRect(0,i, 35, 35);
			g.fillRect(560,i, 35, 35);
			g.fillRect(i,400, 35, 35);
		}
		
		for(int i = 80; i< 360; i+=80) {
			g.fillRect(80,i, 35, 35);	
			g.fillRect(160,i, 35, 35);
			g.fillRect(240,i, 35, 35);
			g.fillRect(320,i, 35, 35);
			g.fillRect(400,i, 35, 35);
			g.fillRect(480,i, 35, 35);
		}

		
		//Agregar Imagenes
//		g.drawImage(ladrillo.getImage(), 160, 160, 40, 40, null);
		
//		this.x = x;
//		this.y = y;
//		this.destroyed = false;
//		this.canBeDestroyed = canBeDestroyed;
//		this.map = map;
//		this.canOver = canOver;
		
		
		g.drawImage(ladrillo.getImage(), 40, 80, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 80, 360, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 80, 200, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 120, 200, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 200, 120, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 280, 160, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 40, 240, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 200, 200, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 400, 40, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 400, 200, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 280, 280, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 280, 320, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 320, 360, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 480, 360, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 520, 240, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 360, 120, 35, 35, null);
		g.drawImage(ladrillo.getImage(), 440, 280, 35, 35, null);
		
		//AgregarBomba
		
		//for (Bomb bomba : listaBombas) {
			//g.drawImage(this.bombIcon.getImage(), (int)bomba.getX(), (int)bomba.getY(), 50, 50, null);			
		//}
		for(Entity entity : map.getObjects()) {
			if(entity instanceof Bomb)
				g.drawImage(this.bombIcon.getImage(), (int)entity.getX(), (int)entity.getY(), 30, 30, null);
		}
		g.drawImage(bomberman.getImage(), (int) player.getX(), (int) player.getY(), 35, 35, null);
		

	
	
 
//		super.paintComponent(g);

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
