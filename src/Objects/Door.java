package Objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Utils.ResourceCollector;

public class Door extends GameObject implements ObjectMovement {
	private int originHeight;
	private int doorId;

	/**
	 * Constructor for end game doors
	 * 
	 * @param x      - door's x postion on the map
	 * @param y      - door's y postion on the map
	 * @param width  - width of the door
	 * @param height - height of the door
	 * @param doorId - door id for end game door which will be tagged to the player
	 * @param sprite - image for the door
	 */
	public Door(int x, int y, int width, int height, int doorId, BufferedImage sprite) {
		super(x, y, width, 2 * height, sprite);
		this.doorId = doorId;
	}

	/***
	 * Constructor for movable doors
	 * 
	 * @param x      - movable door's x position
	 * @param y      - movable door's y position
	 * @param width  - width of the door
	 * @param height - height of the door
	 * @param sprite - image for the door
	 */
	public Door(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, 2 * height, sprite);
		this.originHeight = height;
		this.doorId = 0;
	}

	@Override
	public void draw(Graphics2D gtd) {
		switch (doorId) {
			case 0:
				gtd.drawImage(this.sprite, null, x, y);
				break;

			case 1:
				gtd.drawImage(ResourceCollector.SpriteMaps.BLUE_DOOR.getImage(), null, x, y);
				break;

			case 2:
				gtd.drawImage(ResourceCollector.SpriteMaps.RED_DOOR.getImage(), null, x, y);
				break;

			default:
				gtd.setColor(Color.green);
				break;
		}
		//gtd.drawString(Integer.toString(doorId), x, y);
	}

	@Override
	public void moveUp() {
		if (this.height != 0) {
			this.y--;
			hitBox.y = y;
			height--;
		}
	}

	@Override
	public void moveDown() {
		if (this.height != originHeight) {
			this.y++;
			hitBox.y = y;
			height++;
		}
	}
	// space here for getter and setter functions

	public int getDoorId() {
		return this.doorId;
	}
}
