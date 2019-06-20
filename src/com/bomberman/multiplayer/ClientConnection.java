package com.bomberman.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import com.bomberman.entities.GameMap;
import com.bomberman.services.MapMessage;
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
        
        scoreBoard.actionPerformed();
        
        while (connected) {
            try {
            	receivedMessage = dataInputStream.readUTF();
            	GameMap map = gson.fromJson(receivedMessage, GameMap.class);
                // VALIDO SI ME PUEDO MOVER
                // ACTUALIZO EL OBJETO
                
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
        	GameMap map = (GameMap) object;
        	//MapMessage mapMessage = new MapMessage(map.getObjects(), map.generatePlayersModelList());
        	MapMessage mapMessage = new MapMessage(map.getObjects(), null);

        	dataOutputStream.writeUTF(gson.toJson(mapMessage));
        } catch (IOException ex) {
        	System.out.println("Error al enviar mensaje al cliente (" + ex.getMessage() + ").");
        }
    }
}
