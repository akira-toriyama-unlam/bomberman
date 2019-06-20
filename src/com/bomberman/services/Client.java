package com.bomberman.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.bomberman.graphics.SocketActionListener;
import com.google.gson.Gson;


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
			public void run(){
		    	try {
		    		while(conected) {
		    			System.out.println("Thread Running");
		    			String message = dataInputStream.readUTF();
						reciveMessage(message);
		    		}
				} catch (IOException e) {
					conected = false;
					e.printStackTrace();
				}
		    }
		};
		thread.start();
    }
    
	    
	public void sendMessage(Message message) {
		try {
			dataOutputStream.writeUTF(gson.toJson(message));
		} catch (IOException e) {
			System.out.println("Error al intentar enviar un mensaje: " + e.getMessage());
		}
	}
	
	private void reciveMessage(String message) {
		System.out.println("Mensage recibido en el cliente");
		Message messageObject = gson.fromJson(message, Message.class);
		System.out.println(messageObject.getMessage());
		//TODO: Do something with the recieved message
	}

}
