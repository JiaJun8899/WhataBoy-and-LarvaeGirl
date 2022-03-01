package Main;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;

import State.GameStateManager;

public class GamePanel extends JPanel implements Runnable {
    // Tile Size and scale of sizes
    public static final int TILE_SIZE = 32; // Tiles are 32x32

    //Screen Dimensions
    public static final int SCREEN_COL = 32;
    public static final int SCREEN_ROW = 20;
    public static final int SCREEN_WIDTH = SCREEN_COL * TILE_SIZE;
    public static final int SCREEN_HEIGHT = SCREEN_ROW * TILE_SIZE;

    // Variables for Thread and for game loop
    private Thread gameThread;
    private final int FPS = 60;
    private double drawInterval;
    private double delta;
    private long lastTime;
    private long currentTime;
    private long timer;
    private int drawCount;

    //Variables for background image
    //private BufferedImage image;

    //Object initalization
    private GameStateManager gsm;
    private KeyHandler keyH;

    /**
     * GamePanel contructor
     */
    public GamePanel() {
        // Setting the size of the window to be 1024 x 640
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.init();
    }

    /**
     * Starts a thread for the current process
     */
    public void startGameThread() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    /**
     * Initialises GamePanel
     */
    private void init() {
        //image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        gsm = new GameStateManager();
        keyH = new KeyHandler(gsm);
        this.addKeyListener(keyH);
    }

    /**
     * Game Loop
     */
    @Override
    public void run() {
        //Variables for delta time game loop
        drawInterval = 1000000000 / FPS;
        delta = 0;
        lastTime = System.nanoTime();
        timer = 0;
        drawCount = 0;

        //While there is a game process
        while (gameThread != null) {
            //Logic to calculate delta time with 60 frames per sec
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            //prints and reset the timer after 1 sec which is 1000000000 nano secs
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
            
        }
    }

    /**
     * Updates the GameStateManager
     */
    private void update() {
        gsm.update();
    }

    /**
     * Calls GameStateManager to draw objects
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gtd = (Graphics2D) g;
        gsm.draw(gtd);
    }
}
