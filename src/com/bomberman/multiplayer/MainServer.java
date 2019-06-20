package com.bomberman.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	
	private static final int maxConcurrentConnecetions = 4;
	private static final int port = 1236;
	
	private static ServerSocket server;
	private static Socket socket;
	private static ScoreBoard scoreBoard;
	
	public static void main(String[] args) throws Exception {
        server = null; 
        socket = null;
        scoreBoard = new ScoreBoard();
	        
		 try {
			server = new ServerSocket(port, maxConcurrentConnecetions);
			
            while (true) {
            	System.out.println("Servidor a la espera de conexiones.");
                socket = server.accept(); 
                
                System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");
                ClientConnection cc = new ClientConnection(socket, scoreBoard);
                cc.start();
                
            }
        } catch (IOException ex) {
        	System.out.println("Error: " + ex.getMessage());
        } finally {
        	server.close();
		}
	}

}
