package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Trash extends SuperObject {
	
	GamePanel gp;
	
	public Trash(GamePanel gp) {
		this.gp = gp;
        name = "Trash";
        image = setup("/objects/trash.png");
        image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        collision = false;
    }  
}
