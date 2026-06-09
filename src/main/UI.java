package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B;
	BufferedImage titleBackground;
	public int commandNum = 0;
	
	public UI(GamePanel gp)  {
		this.gp = gp;
		
		// LOADING FONT AND IMAGE INSIDE UI CONSTRACTOR
		try {
		    Font pixellari = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/Pixellari.ttf"));
		    arial_40 = pixellari.deriveFont(Font.PLAIN, 40F);
		    arial_80B = pixellari.deriveFont(Font.BOLD, 80F);

		    titleBackground = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/backgrounds/EcoVale.png"));
		} catch (Exception e) {
		    e.printStackTrace();
		}		
//		arial_40 = new Font("Arial", Font.PLAIN, 40);
//		arial_80B = new Font("Arial", Font.BOLD, 80);	
	}
	
	public void draw(Graphics2D g2)  {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState)  {
			drawTitleScreen();
		}
		
		if(gp.gameState == gp.playState)  {
			
			if (!gp.gameComplete) {
			    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
			    g2.setColor(new Color(255, 220, 120)); // warm task yellow
			    String task = "🧹 Task: Clean up all the trash!";
			    g2.drawString(task, 50, 100); // align left, just below status
			}
			
			// Do playState stuff later
			String status = "Collected: " + gp.trashCollected + "  |  Dumped: " + gp.trashDumped + "/" + gp.totalTrash;
	        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
	        g2.setColor(Color.white);
	        g2.drawString(status, 50, 50);
	        
	        // Show game complete message
	        if (gp.gameComplete) {
	            drawGameCompleteMessage();  // Much cleaner
	        }
		}
		
		if(gp.gameState == gp.pauseState)  {
			drawPauseScreen();
		}
		
	    // Draw quit confirmation screen if active
		if (gp.gameState == gp.quitConfirmState) {
		    drawQuitConfirmation();
		}
		
	}
	
	public void drawTitleScreen()  {
		
//		g2.setColor(new Color(70,120,80));
//		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.drawImage(titleBackground, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,432F)); // tile font size
		String text = "EcoVale";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*4;
		
		// SHADOW
		g2.setColor(Color.black);
		g2.drawString(text, x+5, y+5);
		
		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// TITLE SCREEN IMAGE
//		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
//		y += gp.tileSize*2;
//		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		
		text = "START GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0)  {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
//		text = "LOAD GAME";
//		x = getXforCenteredText(text);
//		y += gp.tileSize;
//		g2.drawString(text, x, y);
//		if(commandNum == 1)  {
//			g2.drawString(">", x-gp.tileSize, y);
//		}	
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1)  {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
	}
	public void drawPauseScreen()  {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawGameCompleteMessage() {
	    String msg = "Congratulations!";
	    String msg2 = "You've cleaned up the entire environment & Saved EcoVale!";
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
	    g2.setColor(Color.green);
	    g2.drawString(msg, getXforCenteredText(msg), gp.screenHeight / 2);
	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
	    g2.drawString(msg2, getXforCenteredText(msg2), gp.screenHeight / 2 + 80);
	}
	
	// Option Menu
	public void drawQuitConfirmation() {
	    g2.setColor(new Color(0, 0, 0, 170)); // Semi-transparent overlay
	    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
	    g2.setColor(Color.white);
	    String text = "Quit game?";
	    int x = getXforCenteredText(text);
	    int y = gp.screenHeight / 2 - 20;
	    g2.drawString(text, x, y);

	    text = "Press ENTER to confirm";
	    x = getXforCenteredText(text);
	    y += 60;
	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36f));
	    g2.drawString(text, x, y);

	    text = "Press ESC to cancel";
	    x = getXforCenteredText(text);
	    y += 40;
	    g2.drawString(text, x, y);
	}

	public int getXforCenteredText(String text)  {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
