package Objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Lava extends GameObject {
    public Lava(int x, int y, int width, int height, BufferedImage sprite) {
        super(x,y+16,width,height/2,sprite);
    }
    
    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(this.sprite, null, x, y-16);
    }
}
