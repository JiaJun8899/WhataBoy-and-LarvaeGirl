package Characters;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import Objects.*;

public class Player extends Character {
    // Booleans for player movement
    private boolean keyLeft;
    private boolean keyRight;
    private boolean keyUp;
    private boolean keyDown;
    //Variables for players such as ID and original X and Y position
    private int playerID;
    private int spawnX;
    private int spawnY;
    private int points;
    //Variables for the animation of player's sprite when walking
    private BufferedImage[][] sprites;
    private BufferedImage sprite;
    private int spriteTimer = 0;
    private int spriteIndex = 0;
    private int spriteDirection = 0;
    // All the arrays to assist with collision
    private ArrayList<Wall> wallArray;
    private ArrayList<Box> boxArray;
    private ArrayList<Door> doorArray;
    private ArrayList<Elevator> elevatorArray;
    private ArrayList<Water> waterArray;
    private ArrayList<Button> btnArray;
    private ArrayList<Lava> lavaArray;
    private ArrayList<Collectables> collectablesArray;
    // Variable for different functions
    private Rectangle playerHitBox;
    private boolean boxTop = false;
    private boolean dead = false;

    /**
     * Player constructor to create a player class
     * @param x - player's x position
     * @param y - player's y position
     * @param playerID - playerID, 1 for player 1 and so on
     * @param spriteMap - spriteMap needed for the animation of the character
     * @param gameObjectManager - GOM for the collision logic
     */
    public Player(int x, int y, int playerID, BufferedImage[][] spriteMap, GameObjectManager gameObjectManager) {
        super(x, y, spriteMap, gameObjectManager);
        this.spawnX = x;
        this.spawnY = y;
        this.playerID = playerID;
        this.playerHitBox = new Rectangle(x, y, width, height);
        this.wallArray = gameObjectManager.getWallArray();
        this.boxArray = gameObjectManager.getBoxArray();
        this.doorArray = gameObjectManager.getDoorArray();
        this.elevatorArray = gameObjectManager.getElevatorArray();
        this.waterArray = gameObjectManager.getWaterArray();
        this.btnArray = gameObjectManager.getBtnArray();
        this.lavaArray = gameObjectManager.getLavaArray();
        this.collectablesArray = gameObjectManager.getCollectablesArray();
        this.sprites = spriteMap;
        this.sprite = spriteMap[0][0];
    }

    /**
     * Function that handles what should be updated every second
     */
    @Override
    public void update() {
        movement();
    }

    @Override
    public void draw(Graphics2D gtd) {
        if (keyLeft) {
            spriteDirection = 1;
            if ((spriteTimer += Math.abs(xSpeed)) >= 5) {
                spriteIndex++;
                spriteTimer = 0;
            }
        }
        if (keyRight) {
            spriteDirection = 0;
            if ((spriteTimer += Math.abs(xSpeed)) >= 5) {
                spriteIndex++;
                spriteTimer = 0;
            }
        }
        if (!keyLeft && !keyRight) {
            spriteIndex = 0;
        }
        if (keyUp) {
            spriteIndex = 3;
        }
        if ((this.spriteIndex >= this.sprites[0].length)) {
            spriteIndex = 0;
        }
        this.sprite = sprites[spriteDirection][spriteIndex];
        gtd.drawImage(sprite, null, x, y);
        // Debug tool
        // gtd.drawRect(x, y, width, height);
        // gtd.drawString(Double.toString(playerHitBox.getY()), x, y);
    }

    /**
     * Function that handles all collision logic for the player
     */
    @Override
    public void collision() {
        // playerHitBox x gets updated with accordance to player
        playerHitBox.x += xSpeed;

        // Wall Objects collision logic
        try {
            for (Wall wall : wallArray) {
                if (playerHitBox.intersects(wall.getHitBox())) {
                    playerHitBox.x -= xSpeed;
                    while (!wall.getHitBox().intersects(playerHitBox)) {
                        playerHitBox.x += Math.signum(xSpeed);
                    }
                    playerHitBox.x -= Math.signum(xSpeed);
                    xSpeed = 0;
                    x = playerHitBox.x;
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Player wall array exception");
            gameObjectManager.hardreset();
        }

        // Elevator collision logic
        for (Elevator elevator : elevatorArray) {
            if (playerHitBox.intersects(elevator.getHitBox())) {
                playerHitBox.x -= xSpeed;
                while (!elevator.getHitBox().intersects(playerHitBox)) {
                    playerHitBox.x -= Math.signum(xSpeed);
                    break;
                }
                playerHitBox.x += Math.signum(xSpeed);
                xSpeed = 0;
                x = playerHitBox.x;
            }
        }

        for (Door door : doorArray) {
            if (door.getDoorId() == 0) {
                if (playerHitBox.intersects(door.getHitBox())) {
                    playerHitBox.x -= xSpeed;
                    while (!door.getHitBox().intersects(playerHitBox)) {
                        playerHitBox.x -= Math.signum(xSpeed);
                        break;
                    }
                    playerHitBox.x += Math.signum(xSpeed);
                    xSpeed = 0;
                    x = playerHitBox.x;
                }
            }
        }

        // Movable object collision and movement logic
        for (Box box : boxArray) {
            if (playerHitBox.intersects(box.getHitBox())) {
                playerHitBox.x -= xSpeed;
                while (!box.getHitBox().intersects(playerHitBox)) {
                    playerHitBox.x += Math.signum(xSpeed);
                    break;
                }
                playerHitBox.x -= Math.signum(xSpeed);
                if (!boxTop && !box.collision()) {
                    box.addX(xSpeed);
                    box.getHitBox().x = box.getX();
                }
                xSpeed = 0;
                x = playerHitBox.x;
            }
        }

        // Vertical collision of objects
        playerHitBox.y += ySpeed;

        // Wall Y axis collision
        try {
            for (Wall wall : wallArray) {
                if (playerHitBox.intersects(wall.getHitBox())) {
                    playerHitBox.y -= ySpeed;
                    while (!wall.getHitBox().intersects(playerHitBox)) {
                        playerHitBox.y += Math.signum(ySpeed);
                    }
                    playerHitBox.y -= Math.signum(ySpeed);
                    ySpeed = 0;
                    y = playerHitBox.y;
                }
            }
        } catch (ConcurrentModificationException e) {
            gameObjectManager.hardreset();
        }

        // Elevator Y axis collision
        for (Elevator elevator : elevatorArray) {
            if (playerHitBox.intersects(elevator.getHitBox())) {
                playerHitBox.y -= ySpeed;
                while (!elevator.getHitBox().intersects(playerHitBox)) {
                    playerHitBox.y += Math.signum(ySpeed);
                }
                playerHitBox.y -= Math.signum(ySpeed);
                ySpeed = 0;
                y = playerHitBox.y;
            }
        }

        // Movable box Y axis collision
        for (Box box : boxArray) {
            if (playerHitBox.intersects(box.getHitBox())) {
                playerHitBox.y -= ySpeed;
                while (!box.getHitBox().intersects(playerHitBox)) {
                    playerHitBox.y += Math.signum(ySpeed);
                }
                boxTop = true;
                playerHitBox.y -= Math.signum(ySpeed);
                ySpeed = 0;
                y = playerHitBox.y;
            } else {
                boxTop = false;
            }
        }

        /**
         * Area for special collision such as buttons
         * Buttons have their own interactions with elevators
         * Collectables will disappear and distribute points to the player and game
         * after picking it up
         */
        for (Button btn : btnArray) {
            btn.collision();
        }

        for (Collectables collectables : collectablesArray) {
            if (playerHitBox.intersects(collectables.getHitBox())) {
                collectablesArray.remove(collectables);
                // Increase points
                points += 100;
                break;
            }
        }
    }

    /**
    * Function that handles all movement of player.
    * 
    * @param xSpeed is the movement speed of the x-axis
    * @param ySpeed is the gravity of the player, higher = more gravity
    * 
    */
    @Override
    public void movement() {
        // Acceleration of the player depending on the direction
        if (keyLeft && keyRight || !keyLeft && !keyRight) {
            xSpeed *= 0.018;
        } else if (keyLeft && !keyRight) {
            xSpeed--;
        } else if (keyRight && !keyLeft) {
            xSpeed++;
        }

        // Conditions to stop the players when speed reaches a certain
        if (xSpeed > 0 && xSpeed < 0.2) {
            xSpeed = 0;
        }
        if (xSpeed < 0 && xSpeed > -0.2) {
            xSpeed = 0;
        }
        // Limit's player xSpeed so that it won't be infinity
        if (xSpeed > 2) {
            xSpeed = 2;
        }
        if (xSpeed < -2) {
            xSpeed = -2;
        }

        /**
         * Jump conditions
         * Each for loops is checking if the player is on top of the object
         * If the player is, ySpeed is set to -4 to allow the player to jump
         * 
         * @param playerHitBox.y is constantly updated whenever the player's y
         *                       coordinate is changed
         *                       Likewise, playerHitBox.x will constantly be updated to
         *                       the player's x position
         */
        if (keyUp) {
            playerHitBox.y++;
            for (Wall wall : wallArray) {
                if (wall.getHitBox().intersects(playerHitBox))
                    ySpeed = -4;
            }
            for (GameObject box : boxArray) {
                if (box.getHitBox().intersects(playerHitBox)) {
                    ySpeed = -4;
                }
            }
            for (Elevator elevator : elevatorArray) {
                if (elevator.getHitBox().intersects(playerHitBox)) {
                    ySpeed = -4;
                }
            }
            playerHitBox.y--;
        }
        ySpeed += 0.2;

        collision();

        x += xSpeed;
        y += ySpeed;

        playerHitBox.x = x;
        playerHitBox.y = y;
    }

    /**
     * Function to check death of the player, works with other codes to respwan the character and reset the map
     * @return - dead = true when player 1 touches lava or player 2 touches water
     */
    public boolean death() {
        // Death code
        for (Lava lava : lavaArray) {
            if (playerID == 1 && playerHitBox.intersects(lava.getHitBox())) {
                this.dead = true;
                break;
            }
        }
        for (Water water : waterArray) {
            if (playerID == 2 && playerHitBox.intersects(water.getHitBox())) {
                this.dead = true;
                break;
            }
        }
        return this.dead;
    }

    /**
     * Sets the player back to it's starting position after it has died
     */
    public void respawn() {
        this.setX(this.spawnX);
        this.setY(this.spawnY);
        this.dead = false;
    }

    /**
     * Function to check if the player has touched the correct door.
     * Player 1 to touch the blue door and player 2 to the red door
     * @return true if player is touching the correct dooe
     */
    public boolean endCondition() {
        // Condition to end game
        for (Door door : doorArray) {
            if (playerID == door.getDoorId() && playerHitBox.intersects(door.getHitBox())) {
                return true;
            }
        }
        return false;
    }

    //Space for all additional getter and setter functions

    public int getPoints() {
        return points;
    }

    public Rectangle getHitBox() {
        return this.playerHitBox;
    }

    public void setHitBox(Rectangle playerHitBox) {
        this.playerHitBox = playerHitBox;
    }

    public void setMovement(int keyPress) {
        if (playerID == 1) {
            if (keyPress == KeyEvent.VK_A)
                this.keyLeft = true;
            if (keyPress == KeyEvent.VK_D)
                this.keyRight = true;
            if (keyPress == KeyEvent.VK_W)
                this.keyUp = true;
            if (keyPress == KeyEvent.VK_S)
                this.keyDown = true;
        }
        if (playerID == 2) {
            // Player 2 key controls
            if (keyPress == KeyEvent.VK_UP)
                this.keyUp = true;
            if (keyPress == KeyEvent.VK_LEFT)
                this.keyLeft = true;
            if (keyPress == KeyEvent.VK_DOWN)
                this.keyDown = true;
            if (keyPress == KeyEvent.VK_RIGHT)
                this.keyRight = true;
        }
    }

    public void unsetMovement(int k) {
        if (playerID == 1) {
            if (k == KeyEvent.VK_A)
                this.keyLeft = false;
            if (k == KeyEvent.VK_D)
                this.keyRight = false;
            if (k == KeyEvent.VK_W)
                this.keyUp = false;
            if (k == KeyEvent.VK_S)
                this.keyDown = false;
        }
        if (playerID == 2) {
            // Player 2 key controls
            if (k == KeyEvent.VK_UP)
                this.keyUp = false;
            if (k == KeyEvent.VK_LEFT)
                this.keyLeft = false;
            if (k == KeyEvent.VK_DOWN)
                this.keyDown = false;
            if (k == KeyEvent.VK_RIGHT)
                this.keyRight = false;
        }
    }

}
