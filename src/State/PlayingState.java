package State;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

import Main.UI;
import Objects.Background;
import Objects.GameObjectManager;
import Utils.ResourceCollector;

public class PlayingState implements GameState {

    private ArrayList<ArrayList<String>> mapGrid = new ArrayList<>();
    private BufferedReader bufferedReader;
    private Background bg;
    private UI ui;
    private GameStateManager gsm;
    private GameObjectManager gom;

    // Constructor
    public PlayingState(GameStateManager gsm) {
        this.gsm = gsm;
        this.gom = new GameObjectManager(this);
        init();
    }

    /**
     * Initialises the map 
     */
    @Override
    public void init() {
        try {
            if (mapGrid.size() == 0) {
                loadMapFile();
            }
            reset();
            this.ui = new UI(ResourceCollector.UIMaps.HEART.getImage(),
                    ResourceCollector.UIMaps.PLAYER_1.getImage(),
                    ResourceCollector.UIMaps.PLAYER_2.getImage(), gom.getPlayer1(), gom.getPlayer2());
            this.bg = new Background(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in initializing game");
        }
    }

    /**
     * Contains all the functions that needs to be run every second for the game
     */
    @Override
    public void update() {
        endState();
        gom.update();
        if (ui.checkZeroLives()) {
            gsm.setState(GameStateManager.ENDSTATE);
        }
        if (gom.getPlayer1().death()) {
            ui.player_death(1);
            //gom.getPlayer1().respawn();
            reset();
        }
        if (gom.getPlayer2().death()) {
            ui.player_death(2);
            //gom.getPlayer2().respawn();
            reset();
        }
    }

    /**
     * Function to change to end state
     */
    public void endState() {
        try {
            if (gom.getPlayer1().endCondition() && gom.getPlayer2().endCondition()) {
                ui.readWriteFile();
                gsm.setState(GameStateManager.ENDSTATE);
            }
        } catch (Exception e) {
            System.out.println("Here at 97");
        }
    }

    /**
     * Function that enables the drawing of objects and for displaying the objects onto the panel
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D gtd = (Graphics2D) g;
        bg.draw(gtd);
        ui.draw(gtd);
        gom.draw(gtd);
    }

    /** 
     * Key inputs for players 1 and 2 
     * @param k - int value for the key on the keyboard
     * */
    @Override
    public void keyPressed(int k) {
        gom.getPlayer1().setMovement(k);
        gom.getPlayer2().setMovement(k);
        // Debug tool
        if (k == KeyEvent.VK_R)
            reset();
        if (k == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    @Override
    public void keyReleased(int k) {
        gom.getPlayer1().unsetMovement(k);
        gom.getPlayer2().unsetMovement(k);
    }

    public void reset() {
        gom.reset();
        try {
            generateMap();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Caught in reset");
        }
    }

    /**
     * Private function to read the map level csv file and store it into map gird
     * Works with generateMap() to generate map
     */
    private void loadMapFile() {
        try {
            this.bufferedReader = new BufferedReader(ResourceCollector.Files.MAP_LEVEL_1.getFile());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting Map_Level_1 file");
        }

        try {
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                if (row.isEmpty()) {
                    continue;
                }
                // Split line by "," to get each value individually
                mapGrid.add(new ArrayList<>(Arrays.asList(row.split(","))));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting reading map file");
        }
    }

    /**
     * Private function that calls the spawn methods from GOM to generate object from the arraylist map grid
     */
    private void generateMap() {
        for (int y = 0; y < mapGrid.size(); y++) {
            for (int x = 0; x < mapGrid.get(y).size(); x++) {
                gom.spawn(x, y, mapGrid.get(y).get(x));
            }
        }
    }
}
