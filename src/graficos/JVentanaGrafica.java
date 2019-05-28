package graficos;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.bomberman.entities.Bomb;
import com.bomberman.entities.Direction;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class JVentanaGrafica extends JFrame {

	private JPanelGrafico contentPane;
	private int cuenta = -1;
	
	public JVentanaGrafica() {
		super("Ejemplo Básico de Graphics");
		setResizable(false);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 460);
		contentPane = new JPanelGrafico();
		setTitle(contentPane.getMap().getName());
		setBackground(Color.WHITE);
		setContentPane(contentPane);
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				repaint();
			}
		}, 33, 1);
		
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				setMovimiento(arg0);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				stopMovimiento(arg0);
			}
			
		});
		setLocationRelativeTo(null);
	}
	
	public void setMovimiento(KeyEvent evento){
		Player bomberbam = contentPane.getBomberman();
		GameMap map = contentPane.getMap();
		cuenta+=1;
		if(evento.getKeyCode() == KeyEvent.VK_LEFT) {
			bomberbam.move(Direction.LEFT);
//			bomberbam.setDesplazamientoX(-0.1);
			bomberbam.setDesplazamientoY(0);
			contentPane.setImageBomberman("./src/imagenes/Izquierda_" + (cuenta % 3) + ".png");
			
		}
		if(evento.getKeyCode() == KeyEvent.VK_RIGHT) {
			bomberbam.move(Direction.RIGHT);
//			bomberbam.setDesplazamientoX(0.1);
			bomberbam.setDesplazamientoY(0);
			contentPane.setImageBomberman("./src/imagenes/Derecha_" + (cuenta % 3) + ".png");
		}
		if(evento.getKeyCode() == KeyEvent.VK_UP) {
			bomberbam.move(Direction.UP);
			
			bomberbam.setDesplazamientoX(0);
//			bomberbam.setDesplazamientoY(-0.1);
			contentPane.setImageBomberman("./src/imagenes/Arriba_" + (cuenta % 3) + ".png");
			
		}
		if(evento.getKeyCode() == KeyEvent.VK_DOWN) {
			bomberbam.move(Direction.DOWN);
			bomberbam.setDesplazamientoX(0);
//			bomberbam.setDesplazamientoY(0.1);
			contentPane.setImageBomberman("./src/imagenes/Abajo_" + (cuenta % 3) + ".png");
			
		}
		
		if(cuenta==2) {
			cuenta=-1;
		}
		if(evento.getKeyCode() == KeyEvent.VK_SPACE) {
			bomberbam.placeBomb(map);
			//contentPane.agregarBomba(bomba);
		}
	}
	public void stopMovimiento(KeyEvent evento) {
		Player bomberbam = contentPane.getBomberman();
		
		if(evento.getKeyCode() == KeyEvent.VK_LEFT) {
			bomberbam.setDesplazamientoX(0);
			
		}
		if(evento.getKeyCode() == KeyEvent.VK_RIGHT) {
			bomberbam.setDesplazamientoX(0);
			
		}
		if(evento.getKeyCode() == KeyEvent.VK_UP) {
		bomberbam.setDesplazamientoY(0);
			
		}
		if(evento.getKeyCode() == KeyEvent.VK_DOWN) {
			bomberbam.setDesplazamientoY(0);
			
		}
	}
	
	public static void main(String[] args) {
		new JVentanaGrafica().setVisible(true);
	}
}
