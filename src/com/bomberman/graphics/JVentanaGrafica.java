package com.bomberman.graphics;
import java.awt.Color;

import javax.swing.JFrame;

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
	
	public static void main(String[] args) {
		new JVentanaGrafica().setVisible(true);
	}
	
	public JVentanaGrafica() {
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
	
	public void setMovimiento(KeyEvent event){
		Player bombermam = contentPane.getBomberman();
		GameMap map = contentPane.getMap();
		cuenta+=1;
		if(event.getKeyCode() == KeyEvent.VK_LEFT) {
			bombermam.move(Direction.LEFT);
			bombermam.setDesplazamientoY(0);
			contentPane.setImageBomberman("./resources/Izquierda_" + (cuenta % 3) + ".png");
			
		}
		if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
			bombermam.move(Direction.RIGHT);
			bombermam.setDesplazamientoY(0);
			contentPane.setImageBomberman("./resources/Derecha_" + (cuenta % 3) + ".png");
		}
		if(event.getKeyCode() == KeyEvent.VK_UP) {
			bombermam.move(Direction.UP);
			
			bombermam.setDesplazamientoX(0);
			contentPane.setImageBomberman("./resources/Arriba_" + (cuenta % 3) + ".png");
			
		}
		if(event.getKeyCode() == KeyEvent.VK_DOWN) {
			bombermam.move(Direction.DOWN);
			bombermam.setDesplazamientoX(0);
			contentPane.setImageBomberman("./resources/Abajo_" + (cuenta % 3) + ".png");
			
		}
		
		if(cuenta==2) {
			cuenta=-1;
		}
		if(event.getKeyCode() == KeyEvent.VK_SPACE) {
			bombermam.placeBomb(map);
			//contentPane.agregarBomba(bomba);
		}
	}
	
	public void stopMovimiento(KeyEvent event) {
		Player bomberman = contentPane.getBomberman();
		
		if(event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_RIGHT) {
			bomberman.setDesplazamientoX(0);
			
		}

		if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
			bomberman.setDesplazamientoY(0);
		}
	}
}
