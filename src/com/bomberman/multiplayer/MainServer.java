package com.bomberman.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServer {
	
	public static void main(String[] args) throws Exception {
	     int puerto = 1236;
	        int maximoConexiones = 4; 
	        ServerSocket servidor = null; 
	        Socket socket = null;
	        Messages mensajes = new Messages();
	        
		 try {
	            servidor = new ServerSocket(puerto, maximoConexiones);
	            
	            while (true) {
	            	System.out.println("Servidor a la espera de conexiones.");
	                socket = servidor.accept(); 
	                
	                System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");
	                ClientConnection cc = new ClientConnection(socket, mensajes);
	                cc.start();
	                
	            }
	        } catch (IOException ex) {
	        	System.out.println("Error: " + ex.getMessage());
	        }finally {
	        	servidor.close();
		}
	}

}
