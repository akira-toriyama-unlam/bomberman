package com.bomberman.client;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bomberman.dto.MapDto;
import com.bomberman.entities.Direction;
import com.bomberman.server.LoginMessage;
import com.bomberman.server.RoomsDto;
import com.bomberman.services.DirectionMessage;
import com.bomberman.services.MapMessage;

public class Window extends JFrame implements SocketActionListener {

	public static final int WIDTH = 840;
	public static final int HEIGHT = 620;
	private JPanel contentPane;
	private boolean stopKeyEvents = false;

	private Client client;
	private Timer timer;
	private MapDto map;
	private boolean repaintOn = false;

	protected List<GameModel> rooms;

	public static void main(String[] args) {
		new Window().setVisible(true);
	}

	public Window() {
		this.client = new Client(this);
		this.initializeGraphicWindow();
		this.intializeKeyboardListeners();
		contentPane = new Login(this);
		setContentPane(contentPane);
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
					if (!stopKeyEvents) {
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
		/*
		 * g.setColor(Color.black); g.fillRect(0, 0, WIDTH, HEIGHT); Font font = new
		 * Font("Arial", Font.PLAIN, 50); g.setFont(font); g.setColor(Color.white);
		 * drawCenteredString("GAME OVER", g); // cancelTimer();
		 * 
		 */
	}

	public void drawCenteredString(String s, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = (WIDTH - fm.stringWidth(s)) / 2;
		int y = (fm.getAscent() + (HEIGHT - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(s, x, y);
	}

	private void setMovimiento(KeyEvent event) throws IOException {
		switch (event.getKeyCode()) {
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
		default:
			break;
		}
	}

	public void sendLoginIntent(LoginMessage loginMessage) {
		client.sendMessage(loginMessage);
	}

	public void sendCreateRoomIntent(GameModel gameModel) {
		client.sendMessage(gameModel);
	}

	private void stopMovimiento(KeyEvent event) {
		client.sendMessage(new DirectionMessage(null));
	}

	public MapDto getMap() {
		return this.map;
	}

	@Override
	public void mapMessageReceived(MapMessage mapMessage) {
		if (this.map == null) {
			this.map = new MapDto();
		}

		this.map.setEntites(mapMessage.getEntities());
		this.map.setPlayers(mapMessage.getPlayers());

		if (!this.repaintOn) {
			// this.initializeRepaint();
			contentPane = new Room(this);
			setContentPane(contentPane);
			this.repaintOn = true;
			revalidate();
		}

		repaint();
	}

	@Override
	public void loginMessageReceived(RoomsDto message) {
		if (message != null) {
			this.rooms = message.getRooms();
			contentPane = new Menu(this);
			setContentPane(contentPane);
			revalidate();
		}
	}
}
