package com.bomberman.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import com.bomberman.client.GameModel;
import com.bomberman.entities.Direction;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.services.DirectionMessage;
import com.bomberman.services.LoginService;
import com.bomberman.services.MapMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ClientConnection extends Thread implements Observer {
	private Gson gson;
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private GameActionPerformed scoreBoard;
	private Player currentPlayer;

	public ClientConnection(Socket socket) {
		this.gson = new Gson();
		this.socket = socket;

		try {
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException ex) {
			System.out.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		String receivedMessage;
		boolean connected = true;

		// this.currentPlayer = scoreBoard.newPlayer();
		// scoreBoard.actionPerformed();

		while (connected) {
			try {
				receivedMessage = dataInputStream.readUTF();
				this.extractAndPeformActionFromJson(receivedMessage);

			} catch (IOException ex) {
				System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.");
				// scoreBoard.playerDisconected(this.currentPlayer);
				connected = false;
				try {
					dataInputStream.close();
					dataOutputStream.close();
				} catch (IOException ex2) {
					System.out.println("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
				}
				return;
			}
		}
		return;
	}

	public void setScoreboard(GameActionPerformed scoreBoard) {
		this.scoreBoard = scoreBoard;
		this.scoreBoard.addObserver(this);
	}

	private void extractAndPeformActionFromJson(String json) {
		Gson gson = new Gson();
		JsonObject e = new JsonParser().parse(json).getAsJsonObject();
		if (e.get("direction") != null) {
			this.directionMessageReceive(gson.fromJson(json, DirectionMessage.class));
		} else if (e.get("name") != null) {
			this.gameModelMessageReceive(gson.fromJson(json, GameModel.class));
		} else if (e.get("authType") != null) {
			this.loginMessageReceive(gson.fromJson(json, LoginMessage.class));
		}

	}

	private void gameModelMessageReceive(GameModel gameModelMessage) {
		System.out.println("Creando partidita en clientConection");
		Optional<ScoreBoard> room = MainServer.rooms.stream()
				.filter(r -> r.getName().equals(gameModelMessage.getName())).findFirst();
		if (room.isPresent()) {
			setScoreboard(room.get());
		} else {
			ScoreBoard scoreBoard = new ScoreBoard(gameModelMessage.getName());
			setScoreboard(scoreBoard);
		}
		this.scoreBoard.setPlayerInitialPosition(currentPlayer);
	}

	private void directionMessageReceive(DirectionMessage directionMessage) {
		Direction direction = directionMessage.getDirection();
		if (direction != null) {
			if (Direction.BOMB.equals(direction)) {
				scoreBoard.bombMessageReceived(this.currentPlayer, directionMessage);
			} else {
				scoreBoard.movementMessageReceived(this.currentPlayer, directionMessage);
			}
		} else {
			scoreBoard.stopMovementMessageReceived(this.currentPlayer);
		}
	}

	private void loginMessageReceive(LoginMessage loginMessage) {
		LoginService loginService = new LoginService();
		Optional<Player> player;
		if (AuthTypes.LOGIN == loginMessage.getAuthType()) {
			player = loginService.existsPlayer(loginMessage.getUser(), loginMessage.getPassword());
		} else {
			player = loginService.createPlayer(loginMessage.getUser(), loginMessage.getPassword());
		}

		if (player.isPresent()) {
			System.out.println("Player presente en el login");
			currentPlayer = player.get();
		}
		System.out.println("POST player presente");
		sendLoginMessage();
	}

	private void sendLoginMessage() {
		List<ScoreBoard> scoreBoards = MainServer.rooms;
		List<GameModel> list = new ArrayList<>();

		if (scoreBoards.isEmpty()) {
			scoreBoards.stream().forEach(s -> {
				list.add(new GameModel(s.getName()));
			});
		}

		try {
			dataOutputStream.writeUTF(gson.toJson(new RoomsDto(list)));
			dataOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object object) {
		try {
			GameMap map = (GameMap) object;
			MapMessage mapMessage = new MapMessage(
					ParserHelper.getInstance().entitiesToEntitiesDto(map.getObjects(), scoreBoard),
					ParserHelper.getInstance().playersToPlayersDto(map.getPlayers(), scoreBoard));
			dataOutputStream.writeUTF(gson.toJson(mapMessage));
			dataOutputStream.flush();
		} catch (IOException ex) {
			System.out.println("Error al enviar mensaje al cliente (" + ex.getMessage() + ").");
		}
	}
}

class User {
	int id;
	String name;
	String password;

	public User(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.getPassword();
	}
}
