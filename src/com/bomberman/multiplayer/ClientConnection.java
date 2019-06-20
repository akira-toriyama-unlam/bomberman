package com.bomberman.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import com.bomberman.services.Message;
import com.google.gson.Gson;

public class ClientConnection extends Thread implements Observer {
	private Gson gson;
	private Socket socket; 
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private GameActionPerformed scoreBoard;
    
    public ClientConnection (Socket socket, GameActionPerformed scoreBoard) {
    	this.gson = new Gson();
        this.socket = socket;
        this.scoreBoard = scoreBoard;
        this.scoreBoard.addObserver(this);
        
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
        
        while (connected) {
            try {
            	receivedMessage = dataInputStream.readUTF();
            	Message message = gson.fromJson(receivedMessage, Message.class);
                System.out.println(message.getMessage());
                
                // VALIDO SI ME PUEDO MOVER
                // ACTUALIZO EL OBJETO
                scoreBoard.actionPerformed(message); // ACA TA EL SEND FORZADO
                
            } catch (IOException ex) {
            	System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.");
                connected = false; 
                try {
                	dataInputStream.close();
                	dataOutputStream.close();
                } catch (IOException ex2) {
                	System.out.println("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
                }
            }
        }   
    }

    @Override
    public void update(Observable o, Object object) {
        try {
            // Envia el mensaje al cliente
        	System.out.println("Observer being updated");
        	Message message = (Message) object;
        	dataOutputStream.writeUTF(gson.toJson(message));
        } catch (IOException ex) {
        	System.out.println("Error al enviar mensaje al cliente (" + ex.getMessage() + ").");
        }
    }
}
