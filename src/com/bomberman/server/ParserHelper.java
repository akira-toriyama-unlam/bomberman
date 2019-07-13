package com.bomberman.server;

import java.util.ArrayList;
import java.util.List;

import com.bomberman.dto.BombDto;
import com.bomberman.dto.EntityDto;
import com.bomberman.dto.ExplosionDto;
import com.bomberman.dto.PlayerDto;
import com.bomberman.entities.Bomb;
import com.bomberman.entities.DestructibleTile;
import com.bomberman.entities.Entity;
import com.bomberman.entities.Explosion;
import com.bomberman.entities.Player;
import com.bomberman.entities.Tile;

public class ParserHelper {
	
	private static final ParserHelper instance = new ParserHelper();
	
	private ParserHelper() {}
	
	public static ParserHelper getInstance() {
		return instance;
	}

	public synchronized List<EntityDto> entitiesToEntitiesDto(List<Entity> entities, GameActionPerformed scoreBoard) {
		
		List<EntityDto> listDto = new ArrayList<>();
		for(Entity e : entities) {
			if(e.isBomb()) {
				listDto.add(((Bomb) e).toDto());	
			} else if (e.isDestructibleTile()) {
				listDto.add(((DestructibleTile) e).toDestructibleTileDto());
			} else if (e.isTile()) {
				// Just a tile
				listDto.add(((Tile) e).toDto());
			} else if (e.isExplosion()) {
				// Just a tile
				listDto.add(((Explosion) e).toDto());
			}
		}
		return listDto;
	}
	
	public List<PlayerDto> playersToPlayersDto(List<Player> players, GameActionPerformed scoreBoard) {
		
		List<PlayerDto>  listDto = new ArrayList<>();
		for(Player player : players) {
			listDto.add(player.toDto());
		}
		
		return listDto;
	}
	
	public List<Player> dtosToPlayers(List<PlayerDto> dtos, GameActionPerformed scoreBoard) {
		List<Player> players = new ArrayList<>(); 
		for(PlayerDto p : dtos) {
			players.add(new Player(p.getX(), p.getY(), p.getId()));
		}
		return players;
	}
	
	public List<Entity> dtosToEntities(List<EntityDto> dtos, GameActionPerformed scoreBoard) {
		List<Entity> entities = new ArrayList<>();
		for(EntityDto e : dtos) {
			if(e.isBombDto()) {
				entities.add(new Bomb(e.getX(), e.getY(), scoreBoard, ((BombDto)e).getPlayerId()));
			} else if(e.isDestructibleTileDto()) {
				entities.add(new DestructibleTile(e.getX(), e.getY()));
			} else if(e.isTileDto()) {
				entities.add(new Tile(e.getX(), e.getY()));
			}
		}
		
		return entities;
	}
	
}
