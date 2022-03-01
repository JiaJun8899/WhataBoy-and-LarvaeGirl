package Main;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import State.GameStateManager;

/**
 * Class that reads and registers the different key events pressed on the keyboard
 * It then passes to the game state manager for future processing of the key event
 */
public class KeyHandler implements KeyListener {
    private GameStateManager gsm;

    public KeyHandler(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
