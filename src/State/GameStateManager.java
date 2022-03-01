package State;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * GameStateManager handles all the game window states
 * with each window having a uniques state. This allows the game
 * to cycle through the different menus seamlessly
 */
public class GameStateManager implements GameState{

    private ArrayList<GameState> gameStates;
    private int currentState;
    public static final int MENUSTATE = 0;
    public static final int PLAYSTATE = 1;
    public static final int LEADERBOARDSTATE = 2;
    public static final int ENDSTATE = 3;

    /**
     * GameStateManager Constructor
     */
    public GameStateManager(){
        init();
    }

    @Override
    public void init() {
        gameStates = new ArrayList<GameState>();

        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new PlayingState(this));
        gameStates.add(new LeaderboardState(this));
        gameStates.add(new EndState(this));
    }

    /**
     * Set the state of the game and initialise it
     */
    public void setState(int state){
        currentState = state;
        gameStates.get(currentState).init();
    }

    /**
     * Update the state of the game
     */
    @Override
    public void update(){
        gameStates.get(currentState).update();
    }

    /**
     * Draw the current state of the game
     */
    @Override
    public void draw(Graphics g){
        g = (Graphics2D) g;
        gameStates.get(currentState).draw(g);
    }

    /**
     * Checks when arrow keys are pressed on keyboard of current state
     */
    public void keyPressed(int k){
        gameStates.get(currentState).keyPressed(k);
    }

    /**
     * Checks when arrow keys are released on keyboard of current state
     */
    public void keyReleased(int k){
        gameStates.get(currentState).keyReleased(k);
    }
}
