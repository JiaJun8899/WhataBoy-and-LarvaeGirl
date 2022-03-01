package State;

import java.awt.Graphics;

import Main.GamePanel;

public interface GameState {
    public static final int TILE_SIZE = GamePanel.TILE_SIZE;

    public void init();
    public void update();
    public void draw(Graphics g);
    public void keyPressed(int k);
    public void keyReleased(int k);
}
