package Characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Objects.GameObjectManager;

public class NPC extends Character {
	private int spawnX;
	private int spawnY;
	private int levitate = 8;
	private int frameCount = 0;

	static final Color fontColour = new Color(128, 0, 0);
	static final Font font = new Font("Arial", Font.PLAIN, 24);

	public NPC(int x, int y, BufferedImage[][] spriteMap, GameObjectManager gameObjectManager) {
		super(x, y, spriteMap, gameObjectManager);
		this.spawnX = x;
		this.spawnY = y;
	}

	/**
	 * Function that displays the character and the different texts onto the playing state
	 */
	@Override
	public void draw(Graphics2D gtd) {
		if (y >= spawnY + levitate) {
			gtd.drawImage(spriteMap[1][0], null, x, y);
		} else if (y <= spawnY + levitate) {
			gtd.drawImage(spriteMap[1][1], null, x, y);
		}

		gtd.setFont(font);
		int lineHeight = gtd.getFontMetrics().getHeight();
		int textY = spawnY;
		int i = 0;

		String[] textArray = {
				"Welcome WhatABoy \nand LarvaeGirl",
				"Use W,A,D and \n← ↑ → keys to move",
				"Solve this puzzle\n together to win! ",
				"Remember to keep \nwithin the time!" };
		if (frameCount < 60 * 5) {
			i = 0;
		} else if (frameCount < 60 * 10) {
			i = 1;
		} else if (frameCount < 60 * 20) {
			i = 2;
		} else if (frameCount < 60 * 30) {
			i = 3;
		} else {
			i = 0;
		}

		for (String line : textArray[i].split("\n")) {
			gtd.drawString(line, spawnX + 48, textY += lineHeight);
		}

		frameCount++;
	}

	/**
	 * Function containing all the functions that needs to be within the game loop.
	 */
	@Override
	public void update() {
		movement();
	}

	/**
	 * Function that moves and animates the NPC. The NPC only moves 8 units up and down at a speed of 0.2 per frame
	 */
	@Override
	public void movement() {
		if (y > spawnY + levitate) {
			ySpeed -= 0.02;
		} else if (y < spawnY + levitate) {
			ySpeed += 0.02;
		}
		y += ySpeed;
	}

	@Override
	public void collision() {

	}
}