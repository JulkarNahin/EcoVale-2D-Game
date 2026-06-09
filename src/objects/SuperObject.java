package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	
    // Add these:
    public Rectangle solidArea = new Rectangle(0, 0, 96, 96); // or whatever your object size is
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();
	
	public BufferedImage setup(String imagePath) {
	    try {
	        return javax.imageio.ImageIO.read(getClass().getResourceAsStream(imagePath));
	    } catch (java.io.IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
