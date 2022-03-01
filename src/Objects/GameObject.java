package Objects;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Utils.ResourceCollector;

public class GameObject {
    protected int x;
    protected int y;
    protected int height;
    protected int width;
    protected BufferedImage sprite;
    protected GameObjectManager gameObjectManager;
    protected Rectangle hitBox;

    /**
     * Super constructors for objects that do not require special collision such as walls, lava and water.
     * @param x - x position on the map
     * @param y - y position on the map
     * @param width - width size for the object
     * @param height - height of the object
     * @param sprite - image for the object
     */
    public GameObject(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

        hitBox = new Rectangle(x, y, this.width, this.height);
    }

    /**
     * Super constructors for objects that requires special collision such as buttons, movable boxes
     * @param x - x position on the map
     * @param y - y position on the map
     * @param width - width size for the object
     * @param height - height of the object
     * @param sprite - image for the object
     * @param gameObjectManager - the game object manager for access to other objects on the map
     */
    public GameObject(int x, int y, int width, int height, BufferedImage sprite, GameObjectManager gameObjectManager) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.gameObjectManager = gameObjectManager;

        hitBox = new Rectangle(x, y, this.width, this.height);
    }

    public void draw(Graphics2D gtd) {
        gtd.drawImage(ResourceCollector.SpriteMaps.MOVABLE_BLOCK.getImage(), null, x, y);
        gtd.drawRect(x, y, width, height);
    }

    //Space for all setters and getter functions needed for game objects to function

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}