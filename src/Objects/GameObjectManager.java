package Objects;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.awt.Graphics2D;

import Characters.NPC;
import Characters.Player;
import Main.GamePanel;
import State.PlayingState;
import Utils.ResourceCollector;

public class GameObjectManager {
    // ArrayList initalize
    private ArrayList<Wall> wallArray = new ArrayList<>();
    private ArrayList<Lava> lavaArray = new ArrayList<>();
    private ArrayList<Box> boxArray = new ArrayList<>();
    private ArrayList<Water> waterArray = new ArrayList<>();
    private ArrayList<Door> doorArray = new ArrayList<>();
    private ArrayList<Elevator> elevatorArray = new ArrayList<>();
    private ArrayList<Button> btnArray = new ArrayList<>();
    private ArrayList<Collectables> collectablesArray = new ArrayList<>();
    // Player objects
    private Player player1;
    private Player player2;
    private NPC NPC;
    private PlayingState state;
    private int btnId = 1;
    private int elevatorId = 1;
    private int doorId = 1;

    public GameObjectManager(PlayingState state) {
        this.state = state;
    }

    /**
     * Functions that needs to updated by the game panel every frame
     */
    public void update() {
        player1.update();
        player2.update();
        NPC.update();
    }

    public void draw(Graphics2D gtd){
        player1.draw(gtd);
        player2.draw(gtd);
        NPC.draw(gtd);
        //Game might some times face issue rendering the blocks as such a try catch here
        try {
            for (Button btn : btnArray)
                btn.draw(gtd);
            for (Door door : doorArray)
                door.draw(gtd);
            for (Elevator elevator : elevatorArray)
                elevator.draw(gtd);
            for (Wall wall : wallArray)
                wall.draw(gtd);
            for (Box box : boxArray)
                box.draw(gtd);
            for (Lava lava : lavaArray)
                lava.draw(gtd);
            for (Water water : waterArray)
                water.draw(gtd);
            for (Collectables collectables : collectablesArray)
                collectables.draw(gtd);
        } catch (ConcurrentModificationException e) {
            System.out.println(e);
            System.out.println("error in drawing objects");
            hardreset();
        }
    }

    /**
     * Function that clears all the objects array so that the game can reset and objects are at their original position
     */
    public void reset() {
        wallArray.clear();
        lavaArray.clear();
        boxArray.clear();
        waterArray.clear();
        doorArray.clear();
        btnArray.clear();
        elevatorArray.clear();
        collectablesArray.clear();
        btnId = 1;
        elevatorId = 1;
        doorId = 1;
    }

    /**
     * Function to hard reset the map the game and re generate objects
     */
    public void hardreset(){
        state.reset();
    }

    /**
     * Function that creates game objects and puts it in an array
     * @param x - location of the item in the csv row
     * @param y - location of the item in the csv col
     * @param code - the data that was read, W for wall, L for lava etc
     */
    public void spawn(int x, int y, String code) {
        switch (code) {
            case ("W"):
                wallArray.add(new Wall(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                        GamePanel.TILE_SIZE, ResourceCollector.SpriteMaps.HARD_WALL.getImage()));
                break;

            case ("DE"):
                doorArray.add(new Door(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                        GamePanel.TILE_SIZE, doorId, ResourceCollector.SpriteMaps.DOOR.getImage()));
                doorId++;
                break;

            case ("DM"):
                doorArray.add(new Door(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                        GamePanel.TILE_SIZE, ResourceCollector.SpriteMaps.DOOR.getImage()));
                break;

            case ("E"):
                elevatorArray
                        .add(new Elevator(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                                GamePanel.TILE_SIZE, elevatorId,
                                ResourceCollector.SpriteMaps.ELEVATOR.getImage()));
                elevatorId++;
                break;

            case ("L"):
                lavaArray.add(new Lava(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                        GamePanel.TILE_SIZE, ResourceCollector.SpriteMaps.LAVA_BLOCK.getImage()));
                break;

            case ("M"):
                boxArray.add(new Box(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE,
                        GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                        ResourceCollector.SpriteMaps.MOVABLE_BLOCK.getImage(), this));
                break;

            case ("WA"):
                waterArray.add(
                        new Water(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                                GamePanel.TILE_SIZE, ResourceCollector.SpriteMaps.WATER_BLOCK.getImage()));
                break;

            case ("B"):
                btnArray.add(new Button(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                        GamePanel.TILE_SIZE, btnId, ResourceCollector.SpriteMaps.GREEN_BUTTON.getImage(),
                        this));
                btnId++;
                break;

            case ("C"):
                collectablesArray.add(new Collectables(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE,
                        GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,
                        ResourceCollector.SpriteMaps.GREEN_GEM.getImage()));
                break;

            case ("P1"):
                player1 = new Player(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, 1,
                        ResourceCollector.splitPlayerSprites(ResourceCollector.SpriteMaps.PLAYER_1.getImage()),
                        this);
                break;

            case ("P2"):
                player2 = new Player(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, 2,
                        ResourceCollector.splitPlayerSprites(ResourceCollector.SpriteMaps.PLAYER_2.getImage()),
                        this);
                break;
            case ("NPC"):
                NPC = new NPC(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE,
                        ResourceCollector.splitPlayerSprites(ResourceCollector.SpriteMaps.NPC.getImage()),
                        this);
                break;

            default:
                break;
        }
    }

    //Space for getter and setter functions
    public ArrayList<Wall> getWallArray() {
        return wallArray;
    }

    public ArrayList<Box> getBoxArray() {
        return boxArray;
    }

    public ArrayList<Door> getDoorArray() {
        return doorArray;
    }

    public ArrayList<Elevator> getElevatorArray() {
        return elevatorArray;
    }

    public ArrayList<Lava> getLavaArray() {
        return lavaArray;
    }

    public ArrayList<Water> getWaterArray() {
        return waterArray;
    }

    public ArrayList<Button> getBtnArray() {
        return btnArray;
    }

    public ArrayList<Collectables> getCollectablesArray() {
        return collectablesArray;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

}
