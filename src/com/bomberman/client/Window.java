package com.bomberman.client;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.bomberman.dto.MapDto;
import com.bomberman.entities.Direction;
import com.bomberman.extras.Cheat;
import com.bomberman.extras.Sound;
import com.bomberman.extras.Toast;
import com.bomberman.services.DirectionMessage;
import com.bomberman.services.MapMessage;

public class Window extends JFrame implements SocketActionListener {

	public static final int WIDTH = 840;
	public static final int HEIGHT = 620;
	private Room contentPane;
	private boolean stopKeyEvents = false;

    private Client client;
    private Timer timer;
    private MapDto map;
    private boolean repaintOn = false;
    private Sound playSound = new Sound("music/play.wav",true);
    private Cheat cheat;
    private Toast toast  = new Toast(); 
;

	public static void main(String[] args) {
		new Window().setVisible(true);
	}
	
	public Window() {
		this.cheat = new Cheat(this);
		
		this.client = new Client(this);
		this.initializeGraphicWindow();
		this.intializeKeyboardListeners();
        this.playSound.play();

	}
	
	public void changeSound() {
		this.playSound.stop();
		this.playSound = new Sound("music/play_villero.wav",false);
		this.playSound.play();
		this.playSound = new Sound("music/play.wav",true);
		this.playSound.play();
	}
	
	public void stopMusic() {
		this.playSound.stop();
	}
	
	public void addBombs() {
		client.sendMessage("binladen");
	}
	
	public synchronized void changeImage() {
		Room.changeBackground = true;
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Room.changeBackground = false;
			}
		}, 5000);	
	}
	
	public void modeGod() {
		client.sendMessage("god");
	}
	
	public void infinityWar() {
		client.sendMessage("thanos");
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
	

	private void intializeKeyboardListeners() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					cheat.cheat(arg0);				
					if(!stopKeyEvents) {
						setMovimiento(arg0);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				stopMovimiento(e);
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
		
		// cancelTimer();
	}
	
	public void drawCenteredString(String s, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (WIDTH - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (HEIGHT - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	 }
	
	private void setMovimiento(KeyEvent event) throws IOException {
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
			client.sendMessage(new DirectionMessage(Direction.BOMB));
			break;
		case KeyEvent.VK_1:	
			client.sendMessage("1");
			break;
		case KeyEvent.VK_2:	
			client.sendMessage("2");
			break;
		case KeyEvent.VK_3:	
			client.sendMessage("3");
			break;
		case KeyEvent.VK_4:	
			client.sendMessage("4");
			break;
		default:
			break;
		}
	}
	
	private void stopMovimiento(KeyEvent event) {
		client.sendMessage(new DirectionMessage(null));
	}
	
	public MapDto getMap() {
		return this.map;
	}


	
	@Override
	public void messageReceived(MapMessage mapMessage) {
		if(this.map == null) {
			this.map = new MapDto();
		}
		
		
		this.map.setEntites(mapMessage.getEntities());
		this.map.setPlayers(mapMessage.getPlayers());
		
		if(mapMessage.getMessageNumber() != null) {
//		System.out.println(mapMessage.getMessageNumber());
		if(this.toast instanceof Toast && this.toast.isFinish()) {
	        this.toast = new Toast(mapMessage.getMessageNumber(), WIDTH/2, HEIGHT/2, this,client); 
	        toast.showtoast(); 
	        
		}
	}
		
		if (!this.repaintOn) {
			//this.initializeRepaint();
			contentPane = new Room(this);
			setContentPane(contentPane);
			this.repaintOn = true;
			revalidate();
		}
		
		

		
		repaint();
	}
}
