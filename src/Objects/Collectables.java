package Objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Collectables extends GameObject {

    public Collectables(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width / 4, height / 4, sprite);
    }

    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(this.sprite, null, x, y - 16);
    }
}