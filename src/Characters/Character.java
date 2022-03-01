package Characters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Objects.GameObjectManager;
import State.GameState;

public abstract class Character {
    //Variables taken from constructor
    protected int x;
    protected int y;
    protected int width, height;
    protected double xSpeed;
    protected double ySpeed;
    protected GameObjectManager gameObjectManager;
    protected BufferedImage[][] spriteMap;

    /**
     * Super constructor for all chaacter objects such as NPC and players
     * @param x - character's x position on the map
     * @param y - character's y position on the map
     * @param spriteMap - spriteMap of the character
     * @param gameObjectManager - takes in the game object manager to manipulate the blocks and collision
     */
    public Character(int x, int y, BufferedImage[][] spriteMap, GameObjectManager gameObjectManager) {
        this.x = x;
        this.y = y;
        this.spriteMap = spriteMap;
        this.gameObjectManager = gameObjectManager;

        this.width = GameState.TILE_SIZE;
        this.height = 48; //1.5 * TILE_SIZE
    }

    //Function to handle the display of player sprite to the panel
    public void draw(Graphics2D gtd) {
        gtd.drawImage(spriteMap[0][0], null, x, y);
        gtd.drawRect(x, y, width, height);
    }

    public abstract void update();

    public abstract void collision();

    public abstract void movement();

    //Get and setter methods to be declared in this space
    public void setCharacter(int x, int y){
        this.setX(x);
        this.setY(y);
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

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getXSpeed() {
        return this.xSpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getYSpeed() {
        return this.ySpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }
}