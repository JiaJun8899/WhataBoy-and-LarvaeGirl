package Main;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Main function is in App.
 * It will generate a new GUI and puts a gamepanel onto the window
 * First state it will call will be the menu state
 */
public class App {
    private final static JFrame window = new JFrame();
    private final static ImageIcon img = new ImageIcon("Resources/Icons/SIT_logo.png");
    private final static GamePanel gamePanel = new GamePanel();
    public static void main(String s[]) {
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("WhatABoy and LarvaeGirl");
        window.setSize(new Dimension(GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT));
        window.setIconImage(img.getImage());

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();
    }
}
