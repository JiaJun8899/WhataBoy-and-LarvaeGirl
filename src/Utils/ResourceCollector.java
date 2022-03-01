package Utils;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics2D;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public final class ResourceCollector {

    public enum SpriteMaps{
        ICON,
        BACKGROUND,
        HARD_WALL,
        LAVA_BLOCK,
        WATER_BLOCK,
        MOVABLE_BLOCK,
        ELEVATOR,
        BLUE_DOOR,
        RED_DOOR,
        GREEN_GEM,
        BLUE_GEM,
        PLAYER_1,
        PLAYER_2,
        NPC,
        GREEN_BUTTON,
        DOOR,
        HEART,
        Leaderboard,
        SoundOn,
        SoundOff,
        EndStateBg;

        private BufferedImage image = null;
        private BufferedImage sprites[][] = null;

        public BufferedImage getImage(){
            return this.image;
        }

        public BufferedImage[][] getSprites(){
            return this.sprites;
        }
    }

    public enum UIMaps{
        PLAYER_1,
        PLAYER_2,
        HEART;

        private BufferedImage image = null;

        public BufferedImage getImage(){
            return this.image;
        }
    }

    public enum Files{
        MAP_LEVEL_1,
        SCORE;

        private InputStreamReader file = null;

        public InputStreamReader getFile(){
            return this.file;
        }
    }

    public static void readFiles(){
        try{
            SpriteMaps.BACKGROUND.image = ImageIO.read(ResourceCollector.class.getResourceAsStream("/Backgrounds/nightcartoon.png"));
            SpriteMaps.EndStateBg.image = ImageIO.read(ResourceCollector.class.getResource("/Backgrounds/Endstate.png"));
            SpriteMaps.Leaderboard.image = ImageIO.read(ResourceCollector.class.getResource("/Icons/leaderboard.png"));
            SpriteMaps.SoundOn.image = ImageIO.read(ResourceCollector.class.getResource("/Icons/soundOn.png"));
            SpriteMaps.SoundOff.image = ImageIO.read(ResourceCollector.class.getResource("/Icons/soundOff.png"));
            SpriteMaps.HARD_WALL.image = ImageIO.read(ResourceCollector.class.getResource("/Tiles/dirtCenter.png"));
            SpriteMaps.LAVA_BLOCK.image = ImageIO.read(ResourceCollector.class.getResource("/Tiles/liquidLavaTop_mid.png"));
            SpriteMaps.WATER_BLOCK.image = ImageIO.read(ResourceCollector.class.getResource("/Tiles/liquidWaterTop_mid.png"));
            SpriteMaps.MOVABLE_BLOCK.image = ImageIO.read(ResourceCollector.class.getResource("/Tiles/boxAlt.png"));
            SpriteMaps.GREEN_BUTTON.image = ImageIO.read(ResourceCollector.class.getResource("/Objects/Green_button.png"));
            SpriteMaps.DOOR.image = ImageIO.read(ResourceCollector.class.getResource("/Objects/laserRedVertical.png"));
            SpriteMaps.GREEN_GEM.image = ImageIO.read(ResourceCollector.class.getResource("/Objects/gemGreen.png"));
            SpriteMaps.BLUE_DOOR.image = ImageIO.read(ResourceCollector.class.getResource("/Objects/door_closed_Blue.png"));
            SpriteMaps.RED_DOOR.image = ImageIO.read(ResourceCollector.class.getResource("/Objects/door_closed_Red.png"));
            SpriteMaps.PLAYER_1.image = ImageIO.read(ResourceCollector.class.getResource("/Player/Player_1.png"));
            SpriteMaps.PLAYER_2.image = ImageIO.read(ResourceCollector.class.getResource("/Player/Player_2.png"));
            SpriteMaps.NPC.image = ImageIO.read(ResourceCollector.class.getResource("/Player/NPC.png"));
            SpriteMaps.ELEVATOR.image = ImageIO.read(ResourceCollector.class.getResource("/Objects/Elevator.png"));

            UIMaps.HEART.image = ImageIO.read(ResourceCollector.class.getResource("/Objects/heart_full.png"));
            UIMaps.PLAYER_1.image = ImageIO.read(ResourceCollector.class.getResource("/Player/player1_ui.png"));
            UIMaps.PLAYER_2.image = ImageIO.read(ResourceCollector.class.getResource("/Player/player2_ui.png"));

            Files.MAP_LEVEL_1.file = new InputStreamReader(ResourceCollector.class.getResourceAsStream("/Maps/Level_1.csv"));
            Files.SCORE.file = new InputStreamReader(ResourceCollector.class.getResourceAsStream("/Scoreboard/Scores.txt"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Function to resize images and sprite to a smaller size
     * @param img - image that needs to be resized
     * @param newW - new height of th image or sprite
     * @param newH - new width of the image or sprite
     * @return - returns the resized image or sprite
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
	
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
	
		return dimg;
	}

    /**
     * Function to spilt sprite map into smaller sprites and puts it in a multi dimensional array
     * @param spriteMap - takes in an image with multiple sprites
     * @return - a multi dimensional array rows are store in the first [ ], image number is stored n the second [ ]
     */
    public static BufferedImage[][] splitPlayerSprites(BufferedImage spriteMap){
        int rows = spriteMap.getHeight() / 44;
        int cols = spriteMap.getWidth() / 32;
        BufferedImage sprites[][] = new BufferedImage[rows][cols];
        for (int x = 0; x < rows; x++){
            for(int y = 0; y < cols; y++){
                sprites[x][y] = spriteMap.getSubimage(y * 32, x*44, 32, 44);
            }
        }
        return sprites;
    }
}
