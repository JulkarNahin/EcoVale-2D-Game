package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Player;
import objects.ObjectManager;
import objects.SuperObject;
import objects.TrashBin;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int orginalTileSize = 32; // 32x32 tile
	final int scale = 3;
	
	public final int tileSize = orginalTileSize * scale; // 96x96 tile
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels ,1536 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels ,1152 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// FULL SCREEN
//	int screenWidth2 = 1920; // or whatever desired width is
//	int screenHeight2 = 1080; // and desired height	
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	
	// FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler KeyH = new KeyHandler(this);
//	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter eSetter = new AssetSetter(this);
//	public Player player = new Player(this,KeyH);
	public UI ui = new UI(this);
	public ObjectManager objectManager = new ObjectManager(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this,KeyH);
//	public SuperObject obj[] = new SuperObject[70]; // enough space!
	public int totalTrash = 20; // number of randomized trash objects
	
	// GAME STATE
//	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public int gameState = playState;
	public final int characterState = 4;
	public final int optionState = 5;
	public final int quitConfirmState = 6;
	
	// TRASH COLLECTION STATE
	public int trashCollected = 0;      // how many have been picked up
	public int trashDumped = 0;         // how many have been deposited
//	public final int totalTrash = 20;   //HIDE THIS STATEMENT FOR NOW
	public boolean gameComplete = false;
	
	
	// set player's default position
//	int playerX = 100;
//	int playerY = 100;
//	int playerSpeed = 4;

	
	public GamePanel () {
		
		this.setPreferredSize (new Dimension (screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);
		
		setupGame();  // <-- Call your setup here
	}
	
	public void setupGame()  {
		
//		gameState = playState;
		gameState = titleState; // Bypass everything to the titlescreen
		
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB); // FULL SCREEN METHOD
		g2 = (Graphics2D)tempScreen.getGraphics();
		
//		objectManager = new ObjectManager(this);
//		objectManager.addObject(new TrashBin());
		// Add more objects as needed
	    // Initialize object manager first
	    objectManager = new ObjectManager(this);

	    // Initialize totalTrash BEFORE calling assetSetter
	    totalTrash = 20; // or whatever value is appropriate

	    // Now call assetSetter to populate objects
	    eSetter = new AssetSetter(this);
	    eSetter.setObject();

	    // Optionally add individual hardcoded objects
	    objectManager.addObject(new TrashBin(this));

	    // add more setup logic when needed
	}
	
	public void setFullScreen(Window window) {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    gd.setFullScreenWindow(window);

	    screenWidth2 = window.getWidth();
	    screenHeight2 = window.getHeight();
	}		
		// GET LOCAL SCREEN DEVICE
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice ge = ge.getDefaultScreenDevice();
//		gd.setFullScreenWindow();
//	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0; // Display FPS
		long drawCount = 0; // Display FPS
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime); // Display FPS
			lastTime = currentTime; 
			
			if(delta >= 1) {
				update();
//				repaint();
				drawToTempScreen(); // draw everything to the buffered image
				drawToScreen(); // draw the buffered image to the screen
				delta--;
				drawCount++; // FPS drawCount
				
			}
			
			if(timer >= 1000000000) { //adding logic for FPS
				System.out.println("FPS:" + drawCount); // Displaying FPS
				drawCount = 0; // FPS drawCount
				timer = 0; // FPS timer
			}
//			long currentTime1 = System.nanoTime();
//			System.out.println("current Time:"+currentTime1);
//			System.out.println("The game is loop is running");
			
			// 1 UPDATE: update information such as character positions
			
			// 2 DRAW: draw the screen with the updated information
			
		}
	}
	public void update() {
		
		if(gameState == playState)  {
			player.update();
		}
		if(gameState == pauseState)  {
			// nothing
		}

/*	    if(KeyH.upPressed) {
	        playerY -= playerSpeed; // Move up
	    }
	    else if (KeyH.downPressed) {
	        playerY += playerSpeed; // Move down
	    }
	    else if (KeyH.leftPressed) {
	        playerX -= playerSpeed; // Move left (FIXED)
	    }
	    else if (KeyH.rightPressed) {
	        playerX += playerSpeed; // Move right (FIXED)
	    } 
	    
		if(KeyH.upPressed == true) {
			playerY -= playerSpeed;
			playerY = playerY - playerSpeed;
		}
		else if (KeyH.downPressed == true) {
			playerY += playerSpeed;
		}
		else if (KeyH.leftPressed == true) {
			player -= playerSpeed;
		}
		else if (KeyH.rightPressed == true) {
			playerX += playerSpeed;
		}	
*/		
	}
	
	// FULL SCREEN DRAWING
	public void drawToTempScreen()  {
		
		// TITLE SCREEN
		if(gameState == titleState)  {
			ui.draw(g2);
		}
		
		// OTHERS (Set everything here including NPCs, Objects)
		else  {
			// TILE
			tileM.draw(g2);
			

			// Draw objects method 1
			for (SuperObject obj : objectManager.objectList) {
			    if (obj != null) {
			        obj.draw(g2, this); // Or however you render it
			    }
			}
        
			// PLAYER
			player.draw(g2);
			
			// UI
			ui.draw(g2);
		}	

	}
	
	public void drawToScreen()  {
		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
//		g.drawImage(tempScreen, 0, 0, null); // alternative
		g.dispose();
	}
/*	public void paintComponent(Graphics g) {		
		super.paintComponent(g);	
		Graphics2D g2 = (Graphics2D)g;
		
		// TILE
		tileM.draw(g2);
		
		// PLAYER
		player.draw(g2);
		
		// UI
		ui.draw(g2);
//		g2.setColor(Color.white);
		
//		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
	}
*/			
	public void startNewGame() {
	    // Clear previous objects
	    objectManager.objectList.clear();

	    // Shuffle and place trash and bins
	    eSetter.setObject();

	    // Reset player position and values
	    player.setDefaultValues();

	    // Reset trash counters and game status
	    trashCollected = 0;
	    trashDumped = 0;
	    gameComplete = false;

	    // Set game to play mode
	    gameState = playState;

	    System.out.println("🌿 New game started — trash has been reshuffled!");
	}
}
