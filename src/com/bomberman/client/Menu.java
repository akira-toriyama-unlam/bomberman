package com.bomberman.client;

import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JPanel {
	
	private Window frame;
	private JList currentGames;
		
	public Menu(Window frame) {
		this.frame = frame;
		setLayout(null);
		setBounds(100, 100, Window.WIDTH, Window.HEIGHT);
		
		JButton btnNewButton = new JButton("Crear partida");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCreateNewGameClick();
			}
		});
		btnNewButton.setBounds(73, 213, 117, 29);
		add(btnNewButton);
		
		currentGames = new JList(frame.rooms.toArray());
		currentGames.setCellRenderer(new GameModelRenderer());
		currentGames.setBounds(347, 213, 432, 125);
		currentGames.setBorder(new EmptyBorder(5,5, 5, 5));
		currentGames.setFixedCellHeight(28);
		add(currentGames);
		
		JButton btnNewButton_1 = new JButton("Configuración");
		btnNewButton_1.setBounds(73, 266, 117, 29);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Ver Ranking");
		btnNewButton_2.setBounds(73, 329, 117, 29);
		add(btnNewButton_2);
		
		JButton btnNewButton_4 = new JButton("Unirse");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onJoinClick();
			}
		});
		btnNewButton_4.setBounds(662, 350, 117, 29);
		add(btnNewButton_4);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		this.setBackground(g);
	}
	
	private void setBackground(Graphics g) {
		int width = this.getSize().width;
		int height = this.getSize().height;
		g.drawImage(new ImageIcon("./resources/fondo-menu.jpg").getImage(), 0, 0, width, height, null);
	}
	
	private void onCreateNewGameClick() {
		JTextField name = new JTextField(5);
		JTextField password = new JTextField(5);
		
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Room's name:"));
		myPanel.add(name);
	/*
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel("Password:"));
		myPanel.add(password)
		*/;
		
		int result = JOptionPane.showOptionDialog(
				null,
				myPanel, 
		        "Create a new room", 
		        JOptionPane.OK_CANCEL_OPTION, 
		        JOptionPane.INFORMATION_MESSAGE, 
		        null, 
		        new String[]{"Go for it", "Cancel"}, // this is the array
		        "default");
		
		
		if (result == JOptionPane.OK_OPTION) {
			frame.sendCreateRoomIntent(new GameModel(name.getText()));;
		}

	}
	
	private void onJoinClick() {
		GameModel g = (GameModel) currentGames.getSelectedValue();
		frame.sendJoinRoomIntent(g);
	}
}
