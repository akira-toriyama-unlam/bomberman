package com.bomberman.graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.bomberman.entities.Direction;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;

public class JVentanaGrafica extends JFrame {

	private JPanelGrafico contentPane;
	private int cuenta = -1;
	
	public static void main(String[] args) {
		new JVentanaGrafica().setVisible(true);
	}
	
	public JVentanaGrafica() {
		setResizable(false);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 470);
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
			
		});
		setLocationRelativeTo(null);
	}
	
	public void setMovimiento(KeyEvent event) {
		Player bomberman = contentPane.getBomberman();
		GameMap map = contentPane.getMap();
		cuenta+=1;
		switch(event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			bomberman.move(Direction.LEFT);
			contentPane.setImageBomberman("./resources/Izquierda_" + (cuenta % 3) + ".png");
			break;
		case KeyEvent.VK_RIGHT: 
			bomberman.move(Direction.RIGHT);
			contentPane.setImageBomberman("./resources/Derecha_" + (cuenta % 3) + ".png");
			break;
		case KeyEvent.VK_UP:
			bomberman.move(Direction.UP);
			contentPane.setImageBomberman("./resources/Arriba_" + (cuenta % 3) + ".png");
			break;
		case KeyEvent.VK_DOWN:
			bomberman.move(Direction.DOWN);
			contentPane.setImageBomberman("./resources/Abajo_" + (cuenta % 3) + ".png");
			break;
		case KeyEvent.VK_SPACE:
			bomberman.placeBomb(map);
			//contentPane.agregarBomba(bomba);
		default:
			// do nothing
			break;
		}
		
		if(cuenta==2) {
			cuenta=-1;
		}
	}
}
