package com.bomberman.multiplayer;

import java.util.Observable;

public class Messages extends Observable{
private String mensaje;
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
        this.setChanged();
        this.notifyObservers(this.getMensaje());
    }
}
