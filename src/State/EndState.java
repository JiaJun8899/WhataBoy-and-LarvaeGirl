package State;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Utils.ResourceCollector;

//suppose to act as a transition phase, meaning not a full state wipe?
public class EndState implements GameState{
    private GameStateManager gsm;
    private static int currentChoice = 0;
    private int Points = 0;
    private int P1Hearts = 0;
    private int P2Hearts = 0;
    private double Time = 0;
    private ArrayList<String> buttonsList = new ArrayList<String>(); //default menu and retry

    public EndState(GameStateManager gsm){
        this.gsm = gsm;
    }

    @Override
    public void init() {
        try {
            File file = new File("Resources/Scoreboard/Scores.txt");
            BufferedReader input;
            input = new BufferedReader(new FileReader(file));
            String inputString = input.readLine();
            input.close();

            List<String> inputList = Arrays.asList(inputString.split(","));

            Time = Double.parseDouble(inputList.get(0));
            Points = Integer.parseInt(inputList.get(1));
            P1Hearts = Integer.parseInt(inputList.get(2));
            P2Hearts = Integer.parseInt(inputList.get(3)); 

        } catch (IOException ex1) {
            System.out.printf("ERROR writing score to file: %s\n", ex1);
        }
        
        buttonsList.add("Menu");
        buttonsList.add("Retry");
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g) {
        Graphics2D gtd = (Graphics2D) g;
        int x = 0;
        int y = 0; 
        String text = "";        
        int centerOfPage = TILE_SIZE * 12;//use for text alignment
        BufferedImage bg = ResourceCollector.SpriteMaps.EndStateBg.getImage();

        bg = ResourceCollector.resize(bg, TILE_SIZE*32, TILE_SIZE*20);
        gtd.drawImage(bg, 0, 0,null);

        //set font for following section
        gtd.setColor(Color.white);
        gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,36F));

        //hearts area
        text = "Player 1 ";

        //add hearts 
        for(int i = 0; i < P1Hearts; i++){
            //print hearts
            gtd.drawImage(ResourceCollector.UIMaps.HEART.getImage(),centerOfPage - 4 * TILE_SIZE + (i*TILE_SIZE), TILE_SIZE * 8 ,null);
        }

        gtd.drawString(text, centerOfPage - 4 * TILE_SIZE, TILE_SIZE * 6);
        text = "Player 2";

        //add hearts
        for(int i = 0; i < P2Hearts; i++){
            //print hearts
            gtd.drawImage(ResourceCollector.UIMaps.HEART.getImage(),centerOfPage + 7 * TILE_SIZE + (i *TILE_SIZE), TILE_SIZE * 8 ,null);

        }
        gtd.drawString(text, centerOfPage + 7 * TILE_SIZE, TILE_SIZE * 6);

        //timer calculation
        text = "Time Taken: " + Time;
        gtd.drawString(text, centerOfPage, TILE_SIZE*12);

        //pts system
        text = "Points: " + Points;
        gtd.drawString(text, centerOfPage, TILE_SIZE*14);
        for (int i = 0; i < buttonsList.size() ;i ++){
           
            x = TILE_SIZE * 10 + i * 8 * TILE_SIZE; 
            y = TILE_SIZE * 16;
            gtd.drawString(buttonsList.get(i), x , y);

            if(i == currentChoice){
                gtd.drawString(">", x-TILE_SIZE, y);
            }
        }
        
    }

    @Override
    public void keyPressed(int k) {
        switch(k){
            //Select menu item on ENTER key
            case(KeyEvent.VK_ENTER):
                select();
                break;
            //Highlight top menu item on UP arrow key
            case(KeyEvent.VK_LEFT):
                currentChoice--;
                if(currentChoice == -1){
                    currentChoice = buttonsList.size() - 1;
                }
                break;
            //Highlight bottom menu item on DOWN arrow key
            case(KeyEvent.VK_RIGHT):
                currentChoice++;
                if(currentChoice == buttonsList.size()){
                    currentChoice = 0;
                }
                break;
        }
    }
    
    /**
     * Executes the option selected by the user on the Menu Screen
     */
    public void select(){
        buttonsList.clear();
        //pointsArray.clear();
        switch(currentChoice){
            case(0):
                //MENU
                gsm.setState(GameStateManager.MENUSTATE);
                break;

            case(1):
                //LEVEL1
                gsm.setState(GameStateManager.PLAYSTATE);
                break;
        }
    }

    @Override
    public void keyReleased(int k) {

    }
    
}
