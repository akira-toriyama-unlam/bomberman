package com.bomberman.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import com.bomberman.dto.EntityDto;
import com.bomberman.entities.Direction;
import com.bomberman.entities.GameMap;
import com.bomberman.entities.Player;
import com.bomberman.services.DirectionMessage;
import com.bomberman.services.MapMessage;
import com.google.gson.Gson;

public class ClientConnection extends Thread implements Observer {
	private Gson gson;
	private Socket socket; 
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private GameActionPerformed scoreBoard;
    private Player currentPlayer;
    private boolean infinityWar = false;
    
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
        
        this.currentPlayer = scoreBoard.newPlayer();
        scoreBoard.actionPerformed();
        
        while (connected) {
            try {
            	receivedMessage = dataInputStream.readUTF();
            	if(receivedMessage.equals("binladen")) {
            		this.currentPlayer.setBombsCount(5);
            	}else if(receivedMessage.equals("god")){
            		this.currentPlayer.setIndestructible(true);
            	}else if(receivedMessage.equals("thanos")) {
            		this.infinityWar = true;
            	}else {
        	DirectionMessage message = gson.fromJson(receivedMessage, DirectionMessage.class);
        	Direction direction = message.getDirection();
        	if(direction != null) {
        		if(Direction.BOMB.equals(direction)) {
        			scoreBoard.bombMessageReceived(this.currentPlayer, message);
        			
        		} else {
        			scoreBoard.movementMessageReceived(this.currentPlayer, message);	
        		}
        	} else {
        		scoreBoard.stopMovementMessageReceived(this.currentPlayer);
        		}	
            }
	        } catch (IOException ex) {
	        	System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.");
	        	//scoreBoard.playerDisconected(this.currentPlayer);
	            connected = false; 
	            try {
	            	dataInputStream.close();
	            	dataOutputStream.close();
	            } catch (IOException ex2) {
	            	// System.out.println("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
	            }
	            return;
	        }
	    }   
	    return;
    }

    @Override
    public  void update(Observable o, Object object) {
        try {
        	GameMap map = (GameMap) object;
        	if(infinityWar) {
        		map.getObjects().forEach(t -> {
        			if(!t.isBomb()) {
	        			t.setPainted(true);
	        			t.setDestroyed(true);
        			}
        		});
        	}
        	MapMessage mapMessage = new MapMessage(
        			ParserHelper.getInstance().entitiesToEntitiesDto(map.getObjects(), scoreBoard),
        			ParserHelper.getInstance().playersToPlayersDto(map.getPlayers(), scoreBoard));
        	dataOutputStream.writeUTF(gson.toJson(mapMessage));
        	dataOutputStream.flush();
        } catch (IOException | ConcurrentModificationException ex) {
        	// ßSystem.out.println("Error al enviar mensaje al cliente (" + ex.getMessage() + ").");
        }
    }
}
