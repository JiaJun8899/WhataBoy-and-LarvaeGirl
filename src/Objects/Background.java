package Objects;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import Main.GamePanel;
import Utils.ResourceCollector;

/**
 * Background is responsible for generating all the background
 * elements onto the screen
 */
public class Background {
	private static final int WIDTH = GamePanel.SCREEN_WIDTH;
	private static final int HEIGHT = GamePanel.SCREEN_HEIGHT;
    private BufferedImage image;
	private int backgroundOffset;

	/**
     * Background Constructor
     */
    public Background(double ms){
        try{
            //Load images from resources package
			image = ResourceCollector.SpriteMaps.BACKGROUND.getImage();
			image = ResourceCollector.resize(image, WIDTH, HEIGHT);
        }
        catch(Exception e){
            e.printStackTrace();
			System.out.println("Error in Background class, unable to get images");
        }
    }

	/**
     * Draw background on screen
     */
    public void draw(Graphics2D g) {
		backgroundOffset++;
		if(backgroundOffset % WIDTH == 0)
		{
			backgroundOffset = 0;
		}
		g.drawImage(image, -backgroundOffset, 0, null);
		g.drawImage(image, -backgroundOffset+WIDTH, 0, null);
	}
}
