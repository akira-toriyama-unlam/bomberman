package com.bomberman.client;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class GameModelRenderer extends JLabel implements ListCellRenderer<GameModel> {
	
	public GameModelRenderer() {
		setOpaque(true);
	}
	 
    @Override
    public Component getListCellRendererComponent(JList<? extends GameModel> list, GameModel gameModel, int index,
        boolean isSelected, boolean cellHasFocus) {                  
    	ImageIcon imageIcon = new ImageIcon("./resources/key.png"); 
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        setIcon(imageIcon);        
        setText(gameModel.getName());
        
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
         
        return this;
    }
     
}
