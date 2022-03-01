package Objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Utils.ResourceCollector;

public class Box extends GameObject {
    private ArrayList<Wall> wallArray;

    public Box(int x, int y, int width, int height, BufferedImage sprite, GameObjectManager gameObjectManager) {
        super(x, y, width, height, sprite, gameObjectManager);
        this.wallArray = gameObjectManager.getWallArray();

        hitBox = new Rectangle(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(ResourceCollector.SpriteMaps.MOVABLE_BLOCK.getImage(), null, x, y);
    }

    public void addX(double x) {
        this.x += x;
    }

    public boolean collision() {
        for (Wall wall : wallArray) {
            if (hitBox.intersects(wall.getHitBox())) {
                return true;
            }
        }
        for (Box box: gameObjectManager.getBoxArray()){
            if(hitBox.intersects(box.getHitBox())){
                if(!(box == this))
                return true;
            }
        }
        return false;
    }
}
