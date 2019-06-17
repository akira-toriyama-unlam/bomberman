package com.bomberman.graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setMovimiento(KeyEvent event) {
		Player bomberman = contentPane.getBomberman();
		GameMap map = contentPane.getMap();
		cuenta+=1;
		switch(event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			bomberman.move(Direction.LEFT);
			bomberman.setImageIcon(new ImageIcon("./resources/Izquierda_" + (cuenta % 3) + ".png"));
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D: 
			bomberman.move(Direction.RIGHT);
			bomberman.setImageIcon(new ImageIcon("./resources/Derecha_" + (cuenta % 3) + ".png"));
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			bomberman.move(Direction.UP);
			bomberman.setImageIcon(new ImageIcon("./resources/Arriba_" + (cuenta % 3) + ".png"));
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			bomberman.move(Direction.DOWN);
			bomberman.setImageIcon(new ImageIcon("./resources/Abajo_" + (cuenta % 3) + ".png"));
			break;
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_X:	
			bomberman.placeBomb(map);
		default:
			// do nothing
			break;
		}
		
		if(cuenta==2) {
			cuenta=-1;
		}
	}
}
