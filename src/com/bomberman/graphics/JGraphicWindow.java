package com.bomberman.graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.bomberman.entities.Direction;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;

public class JGraphicWindow extends JFrame {

	public static final int WIDTH = 840;
	public static final int HEIGHT = 620;
	private JGraphicPanel contentPane;
	private boolean stopKeyEvents = false;
    private Socket socket;
    private DataOutputStream salidaDatos;
    private int puerto = 1236;
    private String host = "127.0.0.1";
    private Player currentPlayer;
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
		
		ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	repaint();
            }
        };
		this.timer = new Timer(50, taskPerformer);
		this.timer.start();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					if(!stopKeyEvents) 
						setMovimiento(arg0);		
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(!stopKeyEvents) {
					currentPlayer.setMoving(false);
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
		this.timer.stop();
	}
	
	public void drawEndGame(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);	
		Font font = new Font("Arial", Font.PLAIN, 50);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("GAME OVER", g);
		cancelTimer();
	}
	
	public void drawCenteredString(String s, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (WIDTH - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (HEIGHT - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	 }
	
	public void setMovimiento(KeyEvent event) throws IOException {
		Player bomberman = contentPane.getBomberman();
		if(bomberman != null) {
			this.setCurrentPlayer(bomberman);
			bomberman.setMoving(true);
			GameMap map = contentPane.getMap();
			switch(event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				bomberman.move(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D: 
				bomberman.move(Direction.RIGHT);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				bomberman.move(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				bomberman.move(Direction.DOWN);
				break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_X:	
				bomberman.placeBomb(map);
				break;
			default:
				break;
			}
		}
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
