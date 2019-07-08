package com.bomberman.client;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.bomberman.dto.BombDto;
import com.bomberman.dto.DestructibleTileDto;
import com.bomberman.dto.EntityDto;
import com.bomberman.dto.ExplosionDto;
import com.bomberman.dto.MapDto;
import com.bomberman.dto.PlayerDto;
import com.bomberman.dto.TileDto;

public class JGraphicPanel extends JPanel {

	private JGraphicWindow frame;
	private Image background;
	
	public JGraphicPanel(JGraphicWindow frame) {
		this.frame = frame;
		this.background = new ImageIcon("./resources/fondo.png").getImage();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		MapDto map = this.frame.getMap();
		int width = this.getSize().width;
		int height = this.getSize().height;
		
		if(map.getPlayers().isEmpty()) {
			frame.setStopKeyEvents(true);
			frame.cancelTimer();
			frame.drawEndGame(g);
		} else {
			if (this.background != null) {
				g.drawImage(this.background, 0, 0, width, height, null);
			}
			g.setColor(new Color(204,204,204));
			
			Iterator<EntityDto> itEntity = map.getEntities().iterator();
			while (itEntity.hasNext()) {				
			    EntityDto entity = itEntity.next();
			    g.drawImage(getEntityByType(entity).getSprite(), entity.getX(), entity.getY(), 40, 40, null);
			    
			    Thread thread = new Thread() {
		    		public void run() {
		    			try {
							Thread.sleep(300);
							entity.setDestroyed(true);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
		    	};
		    	thread.start();
			}
			
			Iterator<PlayerDto> itPlayer = map.getPlayersNotDestroyed().iterator();
			while(itPlayer.hasNext()) {
				PlayerDto player = itPlayer.next();
				//player.chooseSprite();
				g.drawImage(player.chooseSprite(), (int) player.getX(), (int) player.getY(), 40, 40, null);
				if(player.isPainted()) {
					Thread thread = new Thread() {
			    		public void run() {
			    			try {
								Thread.sleep(300);
			    				// player.setDestroyed(true);  // TODO : MARCE DIJO QUE ES NECESARIO
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		
			    		}
			    	};
			    	thread.start();
				}
			}
		}
	}
	
	private EntityDto getEntityByType(EntityDto entity) {
		switch (entity.getEntityType()) {
		case BOMB:
			return new BombDto(entity.getX(), entity.getY(), 1, entity.getAnimateCount(), entity.isDestroyed(), entity.isPainted());
		case DESTRUCTIBLE_TILE:
			return new DestructibleTileDto(entity.getX(), entity.getY(), entity.getAnimateCount(), entity.isPainted());
		case TILE:
			return new TileDto(entity.getX(), entity.getY(), entity.isPainted());
		case EXPLOSION:
			return new ExplosionDto(entity.getX(), entity.getY(), entity.getExplosionDirection(), entity.getAnimateCount(), entity.isPainted());
		default:
			return entity;
		}
	}
}
