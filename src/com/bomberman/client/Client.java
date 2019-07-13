package com.bomberman.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.bomberman.server.RoomsDto;
import com.bomberman.services.DirectionMessage;
import com.bomberman.services.MapMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Client {
	private static final int port = 1236;
	private static final String host = "127.0.0.1";
	private Gson gson;
	private Socket socket;
	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;
	private SocketActionListener listener;

	public Client(SocketActionListener listener) {
		this.listener = listener;
		this.gson = new Gson();

		initializeSocket();
	}

	private void initializeSocket() {
		try {
			socket = new Socket(host, port);
			this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
			this.dataInputStream = new DataInputStream(this.socket.getInputStream());
			startReadingThread();
		} catch (IOException e) {
			System.out.println("Error al crear el stream de salida : " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("El socket no se creo correctamente. ");
		}
	}

	private void startReadingThread() {
		Thread thread = new Thread() {
			boolean conected = true;

			@Override
			public void run() {
				try {
					while (conected) {
						String message = dataInputStream.readUTF();
						receiveMessage(message);
					}
				} catch (IOException e) {
					conected = false;
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	public void sendMessage(Object message) {
		try {
			if (message instanceof DirectionMessage) {
				dataOutputStream.writeUTF(gson.toJson(message));
			} else if (message instanceof GameModel) {
				System.out.println("Send GameModel message en Client");
				dataOutputStream.writeUTF(gson.toJson(message));
			} else {
				System.out.println("Send login message en Client");
				dataOutputStream.writeUTF(gson.toJson(message));
			}

		} catch (IOException e) {
			System.out.println("Error al intentar enviar un mensaje: " + e.getMessage());
		}
	}

	private void receiveMessage(String json) {
		Gson gson = new Gson();
		JsonObject e = new JsonParser().parse(json).getAsJsonObject();
		if (e.get("entities") != null) {
			MapMessage mapMessageObject = gson.fromJson(json, MapMessage.class);
			listener.mapMessageReceived(mapMessageObject);
		} else {
			RoomsDto loginMessage = gson.fromJson(json, RoomsDto.class);
			listener.loginMessageReceived(loginMessage);
		}
	}

}
