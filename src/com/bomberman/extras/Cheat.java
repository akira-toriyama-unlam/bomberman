package com.bomberman.extras;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

import com.bomberman.client.Window;

import java.util.HashMap;

public class Cheat {
	

	public ArrayList<String> cheatsList = new ArrayList<String>();
    Map<String, Runnable> commands = new HashMap<>();

	
	private String cheatSelected = "";
	
	public Cheat(Window window) {
		cheatsList.add("villero");
		cheatsList.add("lukitas");
		cheatsList.add("forest");
		cheatsList.add("binladen");
		cheatsList.add("god");
		cheatsList.add("thanos");
		cheatsList.add("stopit");
		commands.put( "villero", () -> window.changeSound());
		commands.put( "lukitas", () -> window.changeImage());
		commands.put( "stopit", () -> window.stopMusic());
		commands.put( "binladen", () -> window.addBombs());
		commands.put( "god", () -> window.modeGod());
		commands.put( "thanos", () -> window.infinityWar());
	}
	
	public void cheat(KeyEvent k){
		cheatSelected = cheatSelected + k.getKeyChar();

		for (String cheat : cheatsList) {
			if(cheatSelected.contains(cheat)) {
				doCheat(cheat);
				cheatSelected = "";
			}
		}
		
	}
		
	public void doCheat(String cheat) {
        commands.get(cheat).run(); 
	}
}
