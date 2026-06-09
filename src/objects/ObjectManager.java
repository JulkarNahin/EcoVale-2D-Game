package objects;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.GamePanel;

public class ObjectManager {
	GamePanel gp;
    public ArrayList<SuperObject> objectList = new ArrayList<>();

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
    }

    public void addObject(SuperObject obj) {
        objectList.add(obj);
    }

    public void draw(Graphics2D g2) {
        for (SuperObject obj : objectList) {
            if (obj != null) {
                obj.draw(g2, gp);
            }
        }
    }

}
