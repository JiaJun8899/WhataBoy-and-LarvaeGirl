package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Characters.Player;

public class UI {
	// private GamePanel gp;
	private double playTime = 0;
    private Font arial_40;
	private DecimalFormat dFormat = new DecimalFormat("#0.0");
    private BufferedImage heartImg, player1Img, player2Img;
    private int player1Lives = 3;
    private int player2Lives = 3;
    private int count = 0;
    private int score = 0;
    private Sound sound = new Sound();
    private Player player1collected;
    private Player player2collected;

    /**
     * Constructor for our HUD in game
     * @param heartImg - Image of the hearts to be displayed
     * @param player1Img - Static image of player 1 for the HUD
     * @param player2Img - static image for player for the HUD
     * @param player1 - player 1
     * @param player2 - player 2
     */
    public UI(BufferedImage heartImg, BufferedImage player1Img, BufferedImage player2Img, Player player1, Player player2){
        arial_40 = new Font("Arial", Font.PLAIN, 30);
        this.heartImg = heartImg;
        this.player1Img = player1Img;
        this.player2Img = player2Img;
        this.player1collected = player1;
        this.player2collected = player2;
    }

    /**
     * Function thnat draws and write the neccessary items onto the scree
     */
	public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
		g2.setFont(arial_40);
		g2.setColor(Color.WHITE);
        g2.drawImage(player1Img, null, 40,75);
        g2.drawImage(player2Img, null, 40,115);
		g2.drawString(":" ,64 + 8,105);
        g2.drawString(":" ,64 + 8,150);
        for(count = 0; count< player1Lives; count++){
		    g2.drawImage(heartImg, null, 88  + (20 * count), 90);
        }
        for(count = 0; count< player2Lives; count++){
		    g2.drawImage(heartImg, null, 88 + (20 * count), 135);
        }
		//TIME
        //draw will get called 60 times per second. thats why we div.
		playTime += (double)1/60;
		g2.drawString("Time: " + dFormat.format(playTime) +"s", 40,65);
    }

    /**
     * Decreases the player's life when a player dies and plays the death audio
     * @param playerId - takes in the playerID, player 1 = playerID etc.
     */
    public void player_death(int playerId){
        sound.setFile(1);
        if(playerId == 1){
            player1Lives --;
            sound.playDeathSound();
        }
        if (playerId == 2){
            player2Lives --;
            sound.playDeathSound();
        }
    }

    /**
     * Function to check if either player's life is 0, it will return a true for playing state to end the game
     * @return - true when either player's life is 0
     */
    public boolean checkZeroLives(){
        if( player1Lives == 0 || player2Lives == 0){
            return true;
        }
        return false;
    }

    /**
     * Function to write into the scores.txt which the leaderboard will then read and parse the data to be displayed
     */
    public void readWriteFile(){
        try {

            score = (int) (player1collected.getPoints() + player2collected.getPoints() * (player1Lives + player2Lives) / playTime);
            if(checkZeroLives() == false){
                score += 1000;
            }
            //Debug
            // System.out.println(player1collected.getCollectables());
            // System.out.println(player2collected.getCollectables());


            File file = new File("Resources/Scoreboard/Scores.txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            String inputString = input.lines().collect(Collectors.joining(System.lineSeparator()));
            input.close();

            BufferedWriter output = new BufferedWriter(new FileWriter(file, false));
            output.write(dFormat.format(playTime) + "," + score + "," + player1Lives + "," + player2Lives);
            output.newLine();
            output.append(inputString);
            output.close();
            inputString = "";
            
        } catch (IOException ex1) {
            System.out.printf("ERROR writing score to file: %s\n", ex1);
        }
    }
}
