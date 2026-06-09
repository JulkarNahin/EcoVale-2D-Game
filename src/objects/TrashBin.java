package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TrashBin extends SuperObject {
	public boolean active = false;
	
	GamePanel gp;

    public TrashBin(GamePanel gp) {
    	this.gp = gp;
        name = "TrashBin";
        image = setup("/objects/trashbin.png");
        image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        collision = true;
        
        // ✅ This is where you define the solid (collision) area
 //       solidArea = new Rectangle(0, 0, 48, 48); // use your actual tile size
//        solidAreaDefaultX = solidArea.x;
//       solidAreaDefaultY = solidArea.y;
//        collision = false;
    }
	
    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw if object is within visible screen area
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // ✨ Glow effect for active TrashBin
            if (active) {
                g2.setColor(new Color(255, 255, 100, 128)); // soft yellow glow
                g2.fillOval(screenX - 8, screenY - 8, gp.tileSize + 16, gp.tileSize + 16);
            }

            // 🗑 Draw the bin image
            g2.drawImage(image, screenX, screenY, null);
        }
    }   
}
