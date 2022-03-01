package State;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import Objects.Background;
import Utils.ResourceCollector;

public class LeaderboardState implements GameState {

    private Background bg;
    private int currentChoice = 0;
    private Color titleColour;
    private Font titleFont;
    private Font font;
    private static final String SCOREFILE = "Resources/Scoreboard/Scores.txt";
    
    private GameStateManager gsm;

    private String[] options = {
        "Back"
    };

    private String[] num = {
        "No.",
        "1.",
        "2.",
        "3.",
        "4.",
        "5."
    };

    private String[] pointsTitle = {
        "Points"
    };

    /**
     * Leaderboard state constructors
     * @param gsm - takes in the game state manager to enable this state to control and hange states when needed
     */
    public LeaderboardState(GameStateManager gsm){
        this.gsm = gsm;
        
        try{
            ResourceCollector.readFiles();
            bg = new Background(1);
            titleColour = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            
            font = new Font("Arial", Font.PLAIN, 12);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init(){
        
    }

    @Override
    public void update() {

    }

    /**
     * Draws the graphics for the leadrboard screen onto the panel
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D gtd = (Graphics2D) g;
        bg.draw(gtd);
        
        gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,96F));
        String text = "Leaderboard";
        //get centered position for x, originally a method
        int length = (int)gtd.getFontMetrics().getStringBounds(text, gtd).getWidth();
        int x = TILE_SIZE*16 - length/2; //1024 as screen width, set as stat
        int y = TILE_SIZE*8;
        
        //SHADOW
        gtd.setColor(Color.gray);
        gtd.drawString(text, x+5, y+5);
        
        //MAIN COLOR
        gtd.setColor(Color.white);
        gtd.drawString(text, x, y);
        
        //INITIALISE READER TO READ TIMING FROM TEXT FILE
        ArrayList<String> pointsList = new ArrayList<String>();
        try{
            
            BufferedReader bf = new BufferedReader(new FileReader(SCOREFILE));
            String line = bf.readLine();
            while (line != null){
                String[] lineParts = line.split(",");
                pointsList.add(lineParts[1]);
                line = bf.readLine();
            }
            bf.close();

        }catch(IOException ioe){
            System.out.println("File is not found.");
            ioe.printStackTrace();
        }

        String[] array1 = pointsList.toArray(new String[0]);
        Arrays.sort(array1, Collections.reverseOrder(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
            }
        }));
        gtd.setColor(Color.white);
        gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,40F));
        for (int i = 0; i < array1.length; i++){
            length = (int)gtd.getFontMetrics().getStringBounds(text, gtd).getWidth();
            x = TILE_SIZE*16 + length/2; //1024 as screen width, set as stat
            y = TILE_SIZE*10 + (int)(2 * i * TILE_SIZE) ;
            y+= TILE_SIZE * 1.5; // for cursor postiton
            gtd.drawString(array1[i], x, y);
        }

        gtd.setColor(Color.white);
        gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,40F));
        for(int i =0; i < options.length; i++){
           
            length = (int)gtd.getFontMetrics().getStringBounds(text, gtd).getWidth();
            x = TILE_SIZE * 4;
            y= TILE_SIZE * 2;
            y+= TILE_SIZE * 1.5; // for cursor postiton
            gtd.drawString(options[i], x, y);

            //cursor position
            if(i == currentChoice){
                gtd.drawString(">", x-TILE_SIZE, y);
            }
        }

        gtd.setColor(Color.white);
        gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,40F));
        for(int i =0; i < num.length; i++){
            length = (int)gtd.getFontMetrics().getStringBounds(text, gtd).getWidth();
            x = TILE_SIZE*16 - length; //1024 as screen width, set as stat
            y = TILE_SIZE*8 + (int)(2 * i * TILE_SIZE) ;
            y+= TILE_SIZE * 1.5; // for cursor postiton
            gtd.drawString(num[i], x, y);
        }

        gtd.setColor(Color.white);
        gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,40F));
        for(int i =0; i < pointsTitle.length; i++){
            length = (int)gtd.getFontMetrics().getStringBounds(text, gtd).getWidth();
            x = TILE_SIZE*16 + length/2; //1024 as screen width, set as stat
            y = TILE_SIZE*8 + (int)(2 * i * TILE_SIZE) ;
            y+= TILE_SIZE * 1.5; // for cursor postiton
            gtd.drawString(pointsTitle[i], x, y);
        }
        
    }

    /**
     * Executes the option selected by the user on the Menu Screen
     */
    public void select(){
        switch(currentChoice){
            case(0):
                gsm.setState(GameStateManager.MENUSTATE);
                break;
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
            case(KeyEvent.VK_UP):
                currentChoice--;
                if(currentChoice == -1){
                    currentChoice = options.length - 1;
                }
                break;
            
            //Highlight bottom menu item on DOWN arrow key
            case(KeyEvent.VK_DOWN):
                currentChoice++;
                if(currentChoice == options.length){
                    currentChoice = 0;
                }
                break;
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}

