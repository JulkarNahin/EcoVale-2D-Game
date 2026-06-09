package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import objects.SuperObject;
import objects.TrashBin;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler KeyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler KeyH) {
		
		this.gp = gp;
		this.KeyH = KeyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
//		solidArea = new Rectangle();
//		solidArea.x = 32;
//		solidArea.y = 64;
//		solidArea.width = 8;
//		solidArea.height = 8;
		
		// cleaner player hitbox checker
		solidArea = new Rectangle(32, 64, 8, 8);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 20; // player hitbox
		worldY = gp.tileSize * 8;  // player hitbox
		speed = 6; // player speed
		direction = "down";
	}
	public void getPlayerImage() {
		
/*		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/ben_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/ben_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/ben_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/ben_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/ben_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/ben_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/ben_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/ben_right_2.png"));
			
		}catch(IOException e) {
				e.printStackTrace();
		}
*/		
		up1 = setup("ben_up_1");
		up2 = setup("ben_up_2");
		down1 = setup("ben_down_1");
		down2 = setup("ben_down_2");
		left1 = setup("ben_left_1");
		left2 = setup("ben_left_2");
		right1 = setup("ben_right_1");
		right2 = setup("ben_right_2");
		
	}
	
	public BufferedImage setup(String imageName)  {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
		    image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
		    image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return image;
	}
	public void update() {
		
		if(KeyH.upPressed == true || KeyH.downPressed == true || 
				KeyH.leftPressed == true || KeyH.rightPressed == true) {
			
		    if(KeyH.upPressed == true) {
		    	direction = "up";		        
		    }
		    else if (KeyH.downPressed == true) {
		    	direction = "down";		        
		    }
		    else if (KeyH.leftPressed == true) {
		    	direction = "left";		        
		    }
		    else if (KeyH.rightPressed == true) {
		    	direction = "right";	        
		    }
		    
		    // CHECK TILE COLLISION
		    collisionOn = false;
		    gp.cChecker.checkTile(this);
		 // Prevent player from leaving the world boundaries
		 // Boundary clamp — prevent walking out of world
		    if (direction.equals("up") && worldY - speed < 0) {
		        collisionOn = true;
		    }
		    if (direction.equals("down") && worldY + gp.tileSize + speed > gp.worldHeight) {
		        collisionOn = true;
		    }
		    if (direction.equals("left") && worldX - speed < 0) {
		        collisionOn = true;
		    }
		    if (direction.equals("right") && worldX + gp.tileSize + speed > gp.worldWidth) {
		        collisionOn = true;
		    }
		    
		    // IF COLLISION IS FALSE, PLAYER CAN MOVE
		    if(collisionOn == false)  {
		    	
		    	switch(direction)  {
		    	case "up":
		    		worldY -= speed; // Move up
		    		break;
		    	case "down":
		    		worldY += speed; // Move down
		    		break;
		    	case "left":
		    		worldX -= speed; // Move left (FIXED)
		    		break;
		    	case "right":
		    		worldX += speed; // Move right (FIXED)
		    		break;
		    	}
		    }
		    
		    int objIndex = gp.cChecker.checkObject(this, true);
		    if (objIndex != 999) {
		        pickUpObject(objIndex);
		    }
		    
/*		    // 🧤 Check for object (trash) pickup
	        int objIndex = gp.cChecker.checkObject(this, true);
	        if (objIndex != -1 && gp.obj[objIndex] != null) {
	            String objName = gp.obj[objIndex].name;
	            if ("Trash".equals(objName)) {
	                gp.trashCollected++; // Incremaent collected count
	                gp.obj[objIndex] = null; // Remove trash from game
	            }
	        }
*/		    
		    // UPDATE ANIMATION
		    spriteCounter++;
		    if(spriteCounter > 10) {
		    	if(spriteNum == 1) {
		    		spriteNum = 2;
		    	}
		    	else if(spriteNum == 2) {
		    		spriteNum = 1;
		    	}
		    	spriteCounter = 0;
		    }
		}
		// Step 2: Activate bin glow only if player has trash
		for (int i = 0; i < gp.objectManager.objectList.size(); i++) {
		    SuperObject tempObj = gp.objectManager.objectList.get(i);
		    if (tempObj instanceof TrashBin) {
		        ((TrashBin) tempObj).active = (gp.trashCollected > 0);
		    }
		}
	}
	
	public void pickUpObject(int index) {
	    SuperObject obj = gp.objectManager.objectList.get(index);

	    if (obj != null) {
	        String objName = obj.name;

	        if ("Trash".equals(objName)) {
	            gp.trashCollected++;
	            gp.objectManager.objectList.set(index, null);
	            System.out.println("Picked up trash. Total collected: " + gp.trashCollected);
	        }

	        if ("TrashBin".equals(objName) && gp.trashCollected > 0) {
	            gp.trashDumped += gp.trashCollected;
	            gp.trashCollected = 0;
	            System.out.println("Dumped trash! Total dumped: " + gp.trashDumped);

	            if (gp.trashDumped >= gp.totalTrash) {
	                gp.gameComplete = true;
	                System.out.println("🎉 All trash dumped! Game complete.");

	                // Optional: Add delay before restarting to show the win message
	                new java.util.Timer().schedule(
	                    new java.util.TimerTask() {
	                        @Override
	                        public void run() {
	                            gp.startNewGame();
	                        }
	                    },
	                    10000 // delay in milliseconds (3 seconds)
	                );
	            }
	        }
	    }
	}
	
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);	
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, null);
	}
}
