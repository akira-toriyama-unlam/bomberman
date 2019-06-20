package com.bomberman.graphics;

import java.awt.Image;

public class Sprite {
	
	private static final SpriteSheet SPRITE_SHEET = new SpriteSheet(256);
	
	private static final int TILE_PIXELS = 16;
	
	
	public static Image brick = SPRITE_SHEET.image.getSubimage(7 * TILE_PIXELS, 0, 16, 16);
	public static Image wall = SPRITE_SHEET.image.getSubimage(5 * TILE_PIXELS, 0, 16, 16);
	
	
	/*
	|--------------------------------------------------------------------------
	| Player Sprites
	|--------------------------------------------------------------------------
	 */
	public static Image player_up = SPRITE_SHEET.image.getSubimage(0, 0, 12, 16);
	public static Image player_down = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 0, 12, 15);
	public static Image player_left = SPRITE_SHEET.image.getSubimage(3 * TILE_PIXELS, 0, 10, 15);
	public static Image player_right = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 0, 10, 16);
	
	public static Image player_up_1 = SPRITE_SHEET.image.getSubimage(0 * TILE_PIXELS, 1 * TILE_PIXELS, 12, 16);
	public static Image player_up_2 = SPRITE_SHEET.image.getSubimage(0 * TILE_PIXELS, 2 * TILE_PIXELS, 12, 15);
	
	public static Image player_down_1 = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 1 * TILE_PIXELS, 12, 15);
	public static Image player_down_2 = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 2 * TILE_PIXELS, 12, 16);
	
	public static Image player_left_1 = SPRITE_SHEET.image.getSubimage(3 * TILE_PIXELS, 1 * TILE_PIXELS, 11, 16);
	public static Image player_left_2 = SPRITE_SHEET.image.getSubimage(3 * TILE_PIXELS, 2 * TILE_PIXELS, 12 ,16);
	
	public static Image player_right_1 = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 1 * TILE_PIXELS, 11, 16);
	public static Image player_right_2 = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 2 * TILE_PIXELS, 12, 16);
	
	public static Image player_dead1 = SPRITE_SHEET.image.getSubimage(4  * TILE_PIXELS, 2 * TILE_PIXELS, 14, 16);
	public static Image player_dead2 = SPRITE_SHEET.image.getSubimage(5 * TILE_PIXELS, 2 * TILE_PIXELS, 13, 15);
	public static Image player_dead3 = SPRITE_SHEET.image.getSubimage(6 * TILE_PIXELS, 2 * TILE_PIXELS, 16, 16);
	/*
	|--------------------------------------------------------------------------
	| Brick Explosion
	|--------------------------------------------------------------------------
	 */
	public static Image brick_exploded = SPRITE_SHEET.image.getSubimage(7 * TILE_PIXELS, 1 * TILE_PIXELS, 16, 16);
	public static Image brick_exploded1 = SPRITE_SHEET.image.getSubimage(7 * TILE_PIXELS, 2 * TILE_PIXELS, 16, 16);
	public static Image brick_exploded2 = SPRITE_SHEET.image.getSubimage(7 * TILE_PIXELS, 3 * TILE_PIXELS, 16, 16);
	
	/*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */
	public static Image bomb = SPRITE_SHEET.image.getSubimage(0, 3 * TILE_PIXELS, 16, 16);
	public static Image bomb_1 = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 3 * TILE_PIXELS, 16, 16);
	public static Image bomb_2 = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 3 * TILE_PIXELS, 16, 16);
	
	/*
	|--------------------------------------------------------------------------
	| Explosion Sprites
	|--------------------------------------------------------------------------
	 */
	public static Image bomb_exploded = SPRITE_SHEET.image.getSubimage(0, 4 * TILE_PIXELS, 16, 16);
	public static Image bomb_exploded1 = SPRITE_SHEET.image.getSubimage(0, 5 * TILE_PIXELS, 16, 16);
	public static Image bomb_exploded2 = SPRITE_SHEET.image.getSubimage(0, 6 * TILE_PIXELS, 16, 16);
	
	public static Image explosion_vertical = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 5 * TILE_PIXELS, 16, 16);
	public static Image explosion_vertical1 = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 5 * TILE_PIXELS, 16, 16);
	public static Image explosion_vertical2 = SPRITE_SHEET.image.getSubimage(3 * TILE_PIXELS, 5 * TILE_PIXELS, 16, 16);
	
	public static Image explosion_horizontal = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 7 * TILE_PIXELS, 16, 16);
	public static Image explosion_horizontal1 = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 8 * TILE_PIXELS, 16, 16);
	public static Image explosion_horizontal2 = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 9 * TILE_PIXELS, 16, 16);
	
	public static Image explosion_horizontal_left_last = SPRITE_SHEET.image.getSubimage(0, 7 * TILE_PIXELS, 16, 16);
	public static Image explosion_horizontal_left_last1 = SPRITE_SHEET.image.getSubimage(0, 8 * TILE_PIXELS, 16, 16);
	public static Image explosion_horizontal_left_last2 = SPRITE_SHEET.image.getSubimage(0, 9 * TILE_PIXELS, 16, 16);
	
	public static Image explosion_horizontal_right_last = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 7 * TILE_PIXELS, 16, 16);
	public static Image explosion_horizontal_right_last1 = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 8 * TILE_PIXELS, 16, 16);
	public static Image explosion_horizontal_right_last2 = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 9 * TILE_PIXELS, 16, 16);
	
	public static Image explosion_vertical_top_last = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 4 * TILE_PIXELS, 16, 16);
	public static Image explosion_vertical_top_last1 = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 4 * TILE_PIXELS, 16, 16);
	public static Image explosion_vertical_top_last2 = SPRITE_SHEET.image.getSubimage(3 * TILE_PIXELS, 4 * TILE_PIXELS, 16, 16);
	
	public static Image explosion_vertical_down_last = SPRITE_SHEET.image.getSubimage(1 * TILE_PIXELS, 6 * TILE_PIXELS, 16, 16);
	public static Image explosion_vertical_down_last1 = SPRITE_SHEET.image.getSubimage(2 * TILE_PIXELS, 6 * TILE_PIXELS, 16, 16);
	public static Image explosion_vertical_down_last2 = SPRITE_SHEET.image.getSubimage(3 * TILE_PIXELS, 6 * TILE_PIXELS, 16, 16);
	
	
	/*
	|--------------------------------------------------------------------------
	| Moving Sprites
	|--------------------------------------------------------------------------
	 */
	public static Image movingSprite(Image normal, Image x1, Image x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;
		
		if(calc < diff) {
			return normal;
		}
			
		if(calc < diff * 2) {
			return x1;
		}
			
		return x2;
	}
}
