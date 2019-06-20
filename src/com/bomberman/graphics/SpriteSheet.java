package com.bomberman.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private static final String PATH = "./resources/classic.png";
	public final int SIZE;
	public int[] pixels;
	public BufferedImage image;
	
	public SpriteSheet(int size) {
		this.SIZE = size;
		this.pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() {
		try {
			image = ImageIO.read(new File(PATH));
			int w = image.getWidth();
			int h = image.getHeight();
			//image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
