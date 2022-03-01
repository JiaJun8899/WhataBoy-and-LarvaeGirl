package Objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Characters.Player;

public class Button extends GameObject implements ObjectMovement {
    private Player player1;
    private Player player2;
    private int originHeight;
    private Elevator elevator;
    private Elevator sharedElevator;
    private int buttonID;
    private Button secondButton;
    private boolean btnPress = false;

    public Button(int x, int y, int width, int height, int id, BufferedImage sprite, GameObjectManager gameObjectManager) {
        super(x, y + height / 2, width / 2, height / 2, sprite, gameObjectManager);
        this.originHeight = height / 2;
        this.buttonID = id;

        hitBox = new Rectangle(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D gtd) {
        gtd.drawImage(this.sprite, null, x, y - 16);
        // Debug tools
        //gtd.drawString(Integer.toString(buttonID), x, y);
    }

    /**
     * Collision logic for buttons
     * getCorrectElevator will get the elevator which is tagged to the btn via bId
     * and eId
     * Algo for shared elevator is as such
     * Even bId will control the shared elevator
     */
    public boolean collision() {
        // gets the elevator that correspond to this button
        if(buttonID == 5){
            elevator = getCorrectElevator(2);
        }
        // get the player object from state
        this.player1 = gameObjectManager.getPlayer1();
        this.player2 = gameObjectManager.getPlayer2();
        // if box or either player is pressing the btn, set btnpress to true
        if (player1.getHitBox().intersects(this.hitBox) || player2.getHitBox().intersects(this.hitBox) || getBoxPressBtn()) {
            btnPress = true;
        } else {
            btnPress = false;
        }

        if(buttonID == 2){
            if(getButton(1).getPressed() && this.btnPress){
                getMoveDoor().moveUp();
            }
        }

        /**
         * Even numbered buttons handles all the shared elevator's movement
         * The second btn is always the odd number before
         * The shared elevator is the elevator with id of even btnId + 1
         * Example: for elevator 3 to work (2 + 1 = 3 for the elevator id)
         * The primary button is button id 2
         * The secondary button is button id 1 (2 - 1 = 1 for the secondary btn id)
         * Shared elevator movement have to be hard coded, so need additional
         * if-else statment to control how the elevator functions
         */
        if (buttonID == 4) {
            secondButton = getButton(3);
            sharedElevator = getCorrectElevator(1);
            if (sharedElevator != null && secondButton != null) {
                // statment for if btn 1 and btn are pressed to activate the elevator
                // if (secondButton.getPressed() && this.btnPress) {
                // sharedElevator.moveUp();
                // } else {
                // sharedElevator.moveDown();
                // }

                // statments for if btn 1 or btn is pressed can activate the elevator
                if (secondButton.getPressed() || this.btnPress) {
                    sharedElevator.moveUp();
                } else {
                    sharedElevator.moveDown();
                }
            }
        }

        //btn is pressed and there is an elevator tagged to it, the btn will move down
        if (btnPress) {
            if(elevator != null){
                elevator.moveUp();
            }
            moveDown();
        }
        //btn is not pressed and there is an elevator tagged to it, the btn will move back up
        if (!btnPress){
            if(elevator != null){
                elevator.moveDown();
            }
            moveUp();
        }
        return btnPress;
    }

    // function that animates the button
    @Override
    public void moveUp() {
        if (this.height != originHeight) {
            this.height++;
            this.y--;
        }
    }

    //function that animates the button
    @Override
    public void moveDown() {
        if (this.height != 0) {
            this.height -= 1;
            this.y++;
        }
    }

    //Getter functions
    public int getBtnId() {
        return buttonID;
    }

    //returns a button with a specific btnId
    public Button getButton(int btnId) {
        for (Button btn : gameObjectManager.getBtnArray()) {
            if (btn.getBtnId() == btnId) {
                return btn;
            }
        }
        return null;
    }

    //returns an elevator with a specific elevatorId
    private Elevator getCorrectElevator(int eid) {
        for (Elevator elevator : gameObjectManager.getElevatorArray()) {
            if (eid == elevator.getelaId()) {
                return elevator;
            }
        }
        return null;
    }

    private Door getMoveDoor(){
        for(Door door: gameObjectManager.getDoorArray()){
            if(door.getDoorId() == 0){
                return door;
            }
        }
        return null;
    }

    //Checks if any movable boxs is pressing the button
    public boolean getBoxPressBtn() {
        for (Box box : gameObjectManager.getBoxArray()) {
            if (hitBox.intersects(box.hitBox)) {
                return true;
            }
        }
        return false;
    }

    //returns btnPress
    public boolean getPressed() {
        return btnPress;
    }
}