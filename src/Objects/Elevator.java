package Objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Elevator extends GameObject implements ObjectMovement {
	private int maxMove = 0;
	private int elaId;

	public Elevator(int x, int y, int width, int height, int id, BufferedImage sprite) {
		super(x, y, 3 * width, height, sprite);
		this.elaId = id;
	}

	@Override
	public void draw(Graphics2D gtd) {
		gtd.drawImage(this.sprite, null, x, y);
		// Debug tools
		// gtd.drawString(Integer.toString(elaId), x, y);
		// gtd.drawRect(x, y, width, height);
	}

	@Override
	public void moveUp() {
		if (maxMove != 128) {
			maxMove++;
			this.y--;
			hitBox.y = y;
		}
	}

	@Override
	public void moveDown() {
		if (maxMove != 0) {
			maxMove--;
			this.y++;
			hitBox.y = y;
		}
	}

	public int getelaId() {
		return this.elaId;
	}
}
