package main;

import entity.Entity;
import objects.SuperObject;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp)  {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity)  {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction)  {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)  {
				entity.collisionOn = true;
			}
			break;
		case "down":		
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)  {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)  {
				entity.collisionOn = true;
			}	
			break;
		case "right":
			entityRightCol = (entityRightWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)  {
				entity.collisionOn = true;
			}	
			break;
		}
	}
	
	// ⬇ Move this method OUTSIDE the checkTile method
	public int checkObject(Entity entity, boolean isPlayer) {
	    // Your object collision logic goes here
	    int index = 999; // Default: no object hit

	    for (int i = 0; i < gp.objectManager.objectList.size(); i++) {
	        SuperObject obj = gp.objectManager.objectList.get(i);
	        if (obj != null) {
	            // Set solidArea bounds and update position
	            entity.solidArea.x = entity.worldX + entity.solidArea.x;
	            entity.solidArea.y = entity.worldY + entity.solidArea.y;

	            obj.solidArea.x = obj.worldX + obj.solidArea.x;
	            obj.solidArea.y = obj.worldY + obj.solidArea.y;

	            if (entity.solidArea.intersects(obj.solidArea)) {
	                if (obj.collision) {
	                    entity.collisionOn = true;
	                }
	                if (isPlayer) {
	                    index = i;
	                }
	            }

	            // Reset positions
	            entity.solidArea.x = entity.solidAreaDefaultX;
	            entity.solidArea.y = entity.solidAreaDefaultY;
	            obj.solidArea.x = obj.solidAreaDefaultX;
	            obj.solidArea.y = obj.solidAreaDefaultY;
	        }
	    }
	    return index;
	}

}
