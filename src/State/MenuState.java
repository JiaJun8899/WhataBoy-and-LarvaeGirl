package State;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Main.Sound;
import Objects.Background;
import Utils.ResourceCollector;

/**
 * MenuState is responsible for the generation of the
 * menu screen in the game. This GameState will be called on
 * game launch. This GameState allows the selection of the different
 * GameStates eg. Game, Settings, Leaderboard
 */
public class MenuState implements GameState {

    private Background bg;
    private int currentChoice = 0;
    private Color titleColour;
    private Font titleFont;
    private Font font;
    private GameStateManager gsm;
    private Sound sound = new Sound();
    private boolean soundOn = true;

    private String[] options = {
            "Start Game",
            "Leaderboard",
            "Sound",
            "Quit"
    };

    /**
     * MenuState Constructor
     */
    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            ResourceCollector.readFiles();
            bg = new Background(1);
            titleColour = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            sound.playMusic(0);

            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    /**
     * Draws the graphics for the Menu screen in the game panel
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D gtd = (Graphics2D) g;
        // draw bg
        bg.draw(gtd);

        gtd.setFont(gtd.getFont().deriveFont(Font.BOLD, 64));
        String text = "WhataBoy and LarvaeGirl";
        // get centered position for x, originally a method
        int length = (int) gtd.getFontMetrics().getStringBounds(text, gtd).getWidth();
        int x = TILE_SIZE * 16 - length / 2; // 1024 as screen width, set as stat
        int y = TILE_SIZE * 8;

        // SHADOW
        gtd.setColor(Color.gray);
        gtd.drawString(text, x + 5, y + 5);

        // MAIN COLOR
        gtd.setColor(Color.white);
        gtd.drawString(text, x, y);

        // draw menu options
        gtd.setColor(Color.white);
        gtd.setFont(gtd.getFont().deriveFont(Font.BOLD, 48F));
        for (int i = 0; i < options.length; i++) {

            // gtd.drawString(options[i], 512, 340 + i * 48);
            if (options[i] == "Leaderboard") {

                // BufferedImage LBicon =
                // ImageIO.read(ResourceCollector.class.getResource("/icons/leaderboard.png"));
                BufferedImage LBicon = ResourceCollector.SpriteMaps.Leaderboard.getImage();
                LBicon = ResourceCollector.resize(LBicon, TILE_SIZE * 2, TILE_SIZE * 2);
                x = TILE_SIZE * 25;
                y = TILE_SIZE * 2;
                gtd.drawImage(LBicon, x, y, null);
                y += TILE_SIZE * 1.5; // for cursor postiton

            } else if (options[i] == "Sound") {
                BufferedImage soundIcon = ResourceCollector.SpriteMaps.SoundOn.getImage();
                if (!soundOn) {
                    soundIcon = ResourceCollector.SpriteMaps.SoundOff.getImage();
                }
                soundIcon = ResourceCollector.resize(soundIcon, TILE_SIZE * 2, TILE_SIZE * 2);
                x = TILE_SIZE * 29;
                y = TILE_SIZE * 2;
                gtd.drawImage(soundIcon, x, y, null);
                y += TILE_SIZE * 1.5; // for cursor position

            } else {
                length = (int) gtd.getFontMetrics().getStringBounds(text, gtd).getWidth();
                x = TILE_SIZE * 16 - length / 2; // 1024 as screen width, set as stat
                y = TILE_SIZE * 12 + (int) (1.5 * i * TILE_SIZE);
                gtd.drawString(options[i], x, y);
            }

            // cursor position
            if (i == currentChoice) {
                gtd.drawString(">", x - TILE_SIZE, y);
            }
        }
    }

    /**
     * Executes the option selected by the user on the Menu Screen
     */
    public void select() {
        switch (currentChoice) {
            case (0):
                // Play Game
                gsm.setState(GameStateManager.PLAYSTATE);
                break;

            case (1):
                // Leaderboard
                gsm.setState(GameStateManager.LEADERBOARDSTATE);
                break;

            case (2):
                // Sounds
                if (soundOn) {
                    soundOn = false;
                    sound.stopMusic();
                } else {
                    soundOn = true;
                    sound.playMusic(0);
                }
                break;

            case (3):
                // Quit
                System.exit(0);
                break;
        }
    }

    /**
     * Checks when arrow keys are pressed on keyboard
     */
    @Override
    public void keyPressed(int k) {
        switch (k) {
            // Select menu item on ENTER key
            case (KeyEvent.VK_ENTER):
                select();
                break;

            // Highlight top menu item on UP arrow key
            case (KeyEvent.VK_DOWN):
                currentChoice--;
                if (currentChoice == -1) {
                    currentChoice = options.length - 1;
                }
                break;

            // Highlight bottom menu item on DOWN arrow key
            case (KeyEvent.VK_UP):
                currentChoice++;
                if (currentChoice == options.length) {
                    currentChoice = 0;
                }
                break;
        }
    }

    /**
     * Checks when arrow keys are released on keyboard
     */
    @Override
    public void keyReleased(int k) {

    }
}
