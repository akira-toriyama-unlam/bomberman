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
			}
			
			Iterator<PlayerDto> itPlayer = map.getPlayers().iterator();
			while(itPlayer.hasNext()) {
				PlayerDto player = itPlayer.next();
				player.chooseSprite();
				g.drawImage(player.chooseSprite(), (int) player.getX(), (int) player.getY(), 40, 40, null);
			}
		}
	}
	
	private EntityDto getEntityByType(EntityDto entity) {
		switch (entity.getEntityType()) {
		case BOMB:
			return new BombDto(entity.getX(), entity.getY(), 1, entity.getAnimateCount(), entity.isDestroyed());
		case DESTRUCTIBLE_TILE:
			return new DestructibleTileDto(entity.getX(), entity.getY(), entity.getAnimateCount());
		case TILE:
			return new TileDto(entity.getX(), entity.getY());
		case EXPLOSION:
			return new ExplosionDto(entity.getX(), entity.getY(), entity.getExplosionDirection(), entity.getAnimateCount());
		default:
			return entity;
		}
	}
}
