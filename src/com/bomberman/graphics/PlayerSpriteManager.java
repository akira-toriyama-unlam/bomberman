package com.bomberman.graphics;

import java.awt.Image;

public class PlayerSpriteManager {

	private int playerId;

	private static final PlayerSpriteManager instance = null;

	private PlayerSpriteManager(int playerId) {
		this.playerId = playerId;
	}

	public static PlayerSpriteManager getInstance(int playerId) {
		if (instance == null) {
			return new PlayerSpriteManager(playerId);
		}
		return instance;
	}

	public Image getPlayerSpriteUp(int animation) {
		switch (this.playerId) {
		case 1:
			return animation == 0 ? Sprite.player_blue_up
					: animation == 1 ? Sprite.player_blue_up_1 : Sprite.player_blue_up_2;
		case 2:
			return animation == 0 ? Sprite.player_green_up
					: animation == 1 ? Sprite.player_green_up_1 : Sprite.player_green_up_2;
		case 3:
			return animation == 0 ? Sprite.player_yellow_up
					: animation == 1 ? Sprite.player_yellow_up_1 : Sprite.player_yellow_up_2;
		case 4:
			return animation == 0 ? Sprite.player_violet_up
					: animation == 1 ? Sprite.player_violet_up_1 : Sprite.player_violet_up_2;
		default:
			return animation == 0 ? Sprite.player_blue_up
					: animation == 1 ? Sprite.player_blue_up_1 : Sprite.player_blue_up_2;
		}
	}

	public Image getPlayerSpriteDown(int animation) {
		switch (this.playerId) {
		case 1:
			return animation == 0 ? Sprite.player_blue_down
					: animation == 1 ? Sprite.player_blue_down_1 : Sprite.player_blue_down_2;
		case 2:
			return animation == 0 ? Sprite.player_green_down
					: animation == 1 ? Sprite.player_green_down_1 : Sprite.player_green_down_2;
		case 3:
			return animation == 0 ? Sprite.player_yellow_down
					: animation == 1 ? Sprite.player_yellow_down_1 : Sprite.player_yellow_down_2;
		case 4:
			return animation == 0 ? Sprite.player_violet_down
					: animation == 1 ? Sprite.player_violet_down_1 : Sprite.player_violet_down_2;
		default:
			return animation == 0 ? Sprite.player_blue_down
					: animation == 1 ? Sprite.player_blue_down_1 : Sprite.player_blue_down_2;
		}
	}

	public Image getPlayerSpriteRight(int animation) {
		switch (this.playerId) {
		case 1:
			return animation == 0 ? Sprite.player_blue_right
					: animation == 1 ? Sprite.player_blue_right_1 : Sprite.player_blue_right_2;
		case 2:
			return animation == 0 ? Sprite.player_green_right
					: animation == 1 ? Sprite.player_green_right_1 : Sprite.player_green_right_2;
		case 3:
			return animation == 0 ? Sprite.player_yellow_right
					: animation == 1 ? Sprite.player_yellow_right_1 : Sprite.player_yellow_right_2;
		case 4:
			return animation == 0 ? Sprite.player_violet_right
					: animation == 1 ? Sprite.player_violet_right_1 : Sprite.player_violet_right_2;
		default:
			return animation == 0 ? Sprite.player_blue_right
					: animation == 1 ? Sprite.player_blue_right_1 : Sprite.player_blue_right_2;
		}
	}

	public Image getPlayerSpriteLeft(int animation) {
		switch (this.playerId) {
		case 1:
			return animation == 0 ? Sprite.player_blue_left
					: animation == 1 ? Sprite.player_blue_left_1 : Sprite.player_blue_left_2;
		case 2:
			return animation == 0 ? Sprite.player_green_left
					: animation == 1 ? Sprite.player_green_left_1 : Sprite.player_green_left_2;
		case 3:
			return animation == 0 ? Sprite.player_yellow_left
					: animation == 1 ? Sprite.player_yellow_left_1 : Sprite.player_yellow_left_2;
		case 4:
			return animation == 0 ? Sprite.player_violet_left
					: animation == 1 ? Sprite.player_violet_left_1 : Sprite.player_violet_left_2;
		default:
			return animation == 0 ? Sprite.player_blue_left
					: animation == 1 ? Sprite.player_blue_left_1 : Sprite.player_blue_left_2;
		}
	}

	public Image getPlayerSpriteDead(int animation) {
		switch (this.playerId) {
		case 1:
			return animation == 0 ? Sprite.player_blue_dead1
					: animation == 1 ? Sprite.player_blue_dead2 : Sprite.player_blue_dead3;
		case 2:
			return animation == 0 ? Sprite.player_green_dead1
					: animation == 1 ? Sprite.player_green_dead2 : Sprite.player_green_dead3;
		case 3:
			return animation == 0 ? Sprite.player_yellow_dead1
					: animation == 1 ? Sprite.player_yellow_dead2 : Sprite.player_yellow_dead3;
		case 4:
			return animation == 0 ? Sprite.player_violet_dead1
					: animation == 1 ? Sprite.player_violet_dead2 : Sprite.player_violet_dead3;
		default:
			return animation == 0 ? Sprite.player_blue_dead1
					: animation == 1 ? Sprite.player_blue_dead2 : Sprite.player_blue_dead3;
		}
	}

}
