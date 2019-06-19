package com.bomberman.graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.bomberman.entities.Direction;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;

public class JGraphicWindow extends JFrame {

	public static final int WIDTH = 840;
	public static final int HEIGHT = 620;
	private JGraphicPanel contentPane;
	private int cuenta = -1;
	private boolean stopKeyEvents = false;
    private Socket socket;
    private DataOutputStream salidaDatos;
    private int puerto = 1236;
    private String host = "127.0.0.1";
    private Timer timer;
    
	public static void main(String[] args) {
		new JGraphicWindow().setVisible(true);
	}
	
	public JGraphicWindow() {
        try {
            socket = new Socket(host, puerto);
            this.salidaDatos = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception ex) {
        	System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }
        
		setResizable(false);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WIDTH, HEIGHT);
		contentPane = new JGraphicPanel(this);
		setTitle(contentPane.getMap().getName());
		setBackground(Color.WHITE);
		setContentPane(contentPane);
		
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {		
				repaint();
			}
		}, 200, 1);
		
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					if(!stopKeyEvents) {
						setMovimiento(arg0);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public boolean isStopKeyEvents() {
		return stopKeyEvents;
	}

	public void setStopKeyEvents(boolean stopKeyEvents) {
		this.stopKeyEvents = stopKeyEvents;
	}

	public void cancelTimer() {
		this.timer.cancel();
	}
	
	public void drawEndGame(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		Font font = new Font("Arial", Font.PLAIN, 50);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("GAME OVER", g);
	}
	
	public void drawCenteredString(String s, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (WIDTH - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (HEIGHT - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	 }
	
	public void setMovimiento(KeyEvent event) throws IOException {
		Player bomberman = contentPane.getBomberman();
		GameMap map = contentPane.getMap();
		cuenta+=1;
		switch(event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			
			salidaDatos.writeUTF("Left");
			bomberman.move(Direction.LEFT);
			bomberman.setImageIcon(new ImageIcon("./resources/Izquierda_" + (cuenta % 3) + ".png"));
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D: 
			salidaDatos.writeUTF("Right");
			bomberman.move(Direction.RIGHT);
			bomberman.setImageIcon(new ImageIcon("./resources/Derecha_" + (cuenta % 3) + ".png"));
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			salidaDatos.writeUTF("Up");
			bomberman.move(Direction.UP);
			bomberman.setImageIcon(new ImageIcon("./resources/Arriba_" + (cuenta % 3) + ".png"));
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			salidaDatos.writeUTF("Down");
			bomberman.move(Direction.DOWN);
			bomberman.setImageIcon(new ImageIcon("./resources/Abajo_" + (cuenta % 3) + ".png"));
			break;
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_X:	
			salidaDatos.writeUTF("Bomba");
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
