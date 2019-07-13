package com.bomberman.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.bomberman.database.HibernateConfiguration;

public class MainServer {

	private static final int MAX_CONCURRENT_CONNECTIONS = 4;
	private static final int PORT = 1236;

	private static ServerSocket server;
	private static Socket socket;
	// private static ScoreBoard scoreBoard;

	public static List<ScoreBoard> rooms;

	public static void main(String[] args) throws Exception {
		server = null;
		socket = null;
		// scoreBoard = new ScoreBoard();
		rooms = new ArrayList<>();
		HibernateConfiguration.createSessionFactory();

		try {
			server = new ServerSocket(PORT, MAX_CONCURRENT_CONNECTIONS);
			while (true) {
				System.out.println("Servidor a la espera de conexiones.");
				socket = server.accept();

				System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");
				ClientConnection cc = new ClientConnection(socket);
				cc.start();

			}
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			server.close();
		}
	}

}
