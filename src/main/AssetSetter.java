package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import objects.SuperObject;
import objects.Trash;
import objects.TrashBin;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp)  {
		this.gp = gp;
	}
	
	public void setObject()  {
		setTrashObjects();
        setTrashBins(); // optional: still place bins statically
	}
	
	  private void setTrashObjects() {
	        // Step 1: Create list of valid tile positions for trash
	        ArrayList<Point> validTrashSpots = new ArrayList<>();

	        for (int x = 0; x < gp.maxWorldCol; x++) {
	            for (int y = 0; y < gp.maxWorldRow; y++) {
	                int tileNum = gp.tileM.mapTileNum[x][y];
	                boolean isSolid = gp.tileM.tile[tileNum].collision;

	                if (!isSolid) {
	                    validTrashSpots.add(new Point(x, y));
	                }
	            }
	        }
	        
	        ArrayList<Point> safeSpots = new ArrayList<>();

	        for (Point p : validTrashSpots) {
	            boolean overlapsBin = false;

	            for (SuperObject obj : gp.objectManager.objectList) {
	                if (obj instanceof TrashBin) {
	                    int binTileX = obj.worldX / gp.tileSize;
	                    int binTileY = obj.worldY / gp.tileSize;

	                    if (p.equals(new Point(binTileX, binTileY))) {
	                        overlapsBin = true;
	                        break;
	                    }
	                }
	            }

	            if (!overlapsBin) {
	                safeSpots.add(p);
	            }
	        }
	        
//	        Collections.shuffle(validTrashSpots);
	        Collections.shuffle(safeSpots);
	        gp.objectManager.objectList.clear();
	        
	        gp.objectManager.objectList.removeIf(o -> o instanceof Trash); // optional safety
//	        for (int i = 0; i < gp.totalTrash && i < validTrashSpots.size(); i++) {
	        for (int i = 0; i < gp.totalTrash && i < safeSpots.size(); i++) {	
//	            Point p = validTrashSpots.get(i);
	            Point p = safeSpots.get(i);
	            Trash trash = new Trash(gp);
	            trash.worldX = gp.tileSize * p.x;
	            trash.worldY = gp.tileSize * p.y;
	            gp.objectManager.objectList.add(trash);
	        }

	    }

	    private void setTrashBins() {
	        ArrayList<Point> binPositions = new ArrayList<>();
	        // Place trash bins statically (example)
	    	TrashBin bin1 = new TrashBin(gp);
	    	bin1.worldX = 15 * gp.tileSize;
	    	bin1.worldY = 14 * gp.tileSize;
	    	gp.objectManager.objectList.add(bin1);
	    	binPositions.add(new Point(15, 14));

	    	TrashBin bin2 = new TrashBin(gp);
	    	bin2.worldX = 19 * gp.tileSize;
	    	bin2.worldY = 11 * gp.tileSize;
	    	gp.objectManager.objectList.add(bin2);
	    	binPositions.add(new Point(19, 11));

	    	TrashBin bin3 = new TrashBin(gp);
	    	bin3.worldX = 40 * gp.tileSize;
	    	bin3.worldY = 31 * gp.tileSize;
	    	gp.objectManager.objectList.add(bin3);
	    	binPositions.add(new Point(40, 31));

	    }
	    
}
