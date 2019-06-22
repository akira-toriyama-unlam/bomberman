package com.bomberman.graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.bomberman.entities.Direction;
import com.bomberman.entities.Entity;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.services.Client;
import com.bomberman.services.DirectionMessage;
import com.bomberman.services.MapMessage;

public class JGraphicWindow extends JFrame implements SocketActionListener {

	public static final int WIDTH = 840;
	public static final int HEIGHT = 620;
	private JGraphicPanel contentPane;
	private boolean stopKeyEvents = false;

    private Client client;
    private Timer timer;
    private GameMap map;
    private boolean repaintOn = false;

	public static void main(String[] args) {
		new JGraphicWindow().setVisible(true);
	}
	
	public JGraphicWindow() {
		this.client = new Client(this);
		this.initializeGraphicWindow();
		//this.initializeRepaint();
		this.intializeKeyboardListeners();
	}
	
	
	private void initializeGraphicWindow() {
		setResizable(false);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WIDTH, HEIGHT);
		setTitle("Bomberman");
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initializeRepaint() {
		contentPane = new JGraphicPanel(this);
		setContentPane(contentPane);
		
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {	
				repaint();
			}
		}, 200, 1);
	}
	
	private void intializeKeyboardListeners() {
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
		cancelTimer();
	}
	
	public void drawCenteredString(String s, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (WIDTH - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (HEIGHT - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	 }
	
	public void setMovimiento(KeyEvent event) throws IOException {
		switch(event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			client.sendMessage(new DirectionMessage(Direction.LEFT));
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			client.sendMessage(new DirectionMessage(Direction.RIGHT));
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			client.sendMessage(new DirectionMessage(Direction.UP));
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			client.sendMessage(new DirectionMessage(Direction.DOWN));
			break;
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_X:	
			client.sendMessage(new DirectionMessage(null));
			break;
		default:
			break;
		}
	}
	
	public GameMap getMap() {
		return this.map;
	}

	@Override
	public void messageReceived(MapMessage mapMessage) {
		if(this.map == null) {
			this.map = new GameMap(null);
		}
		
		List<Entity> objects = this.map.generateObjectsFromModel(mapMessage.getObjects());
		List<Player> players = this.map.generatePlayerFromModel(mapMessage.getPlayers());
		this.map.setObjects(objects);
		this.map.setPlayers(players);
		
		if (!this.repaintOn) {
			this.initializeRepaint();
			this.repaintOn = true;
			revalidate();
		}
		
		//repaint();
	}
}
