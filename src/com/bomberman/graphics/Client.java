package com.bomberman.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextField;

public class Client implements ActionListener{
	  	private String tfMensaje;
	    private String usuario;
	    private DataOutputStream salidaDatos;
	    private Socket socket; 
	    
    public void enviar(Socket socket, String tfMensaje, String usuario) {
	  this.socket = socket;
      this.tfMensaje = tfMensaje;
      this.usuario = usuario;
      try {
          this.salidaDatos = new DataOutputStream(socket.getOutputStream());
      } catch (IOException ex) {
      	System.out.println("Error al crear el stream de salida : " + ex.getMessage());
      } catch (NullPointerException ex) {
      	System.out.println("El socket no se creo correctamente. ");
      }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
      try {
          salidaDatos.writeUTF(usuario + ": " + tfMensaje );
          tfMensaje = "";
      } catch (IOException ex) {
      	System.out.println("Error al intentar enviar un mensaje: " + ex.getMessage());
      }
  }
}
