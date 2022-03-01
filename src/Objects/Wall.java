package Objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {
    
    public Wall(int x, int y, int width, int height, BufferedImage sprite) {
        super(x,y,width,height,sprite);
    }

    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(this.sprite, null, x, y);
    }
}
