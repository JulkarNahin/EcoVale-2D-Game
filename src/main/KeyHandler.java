package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	
	
	public KeyHandler(GamePanel gp)  {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		// TITLE SCREEM STATE
		if(gp.gameState == gp.titleState)  {
			
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0)  {
					gp.ui.commandNum = 1;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 1)  {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER)  {
				if(gp.ui.commandNum == 0)  {
					gp.gameState = gp.playState;
				}
			    // new game start below title screen menu
			    if (code == KeyEvent.VK_N) {
			        gp.startNewGame();
			    }
//				if(gp.ui.commandNum == 1)  {
					//add later
//				}
				if(gp.ui.commandNum == 1)  {
					System.exit(0);
				}
			}
		}
		
		// PLAY STATE
		if(code == KeyEvent.VK_W) {
			upPressed = true;	
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
	    // ESCAPE key handling for quit confirmation
		if(code == KeyEvent.VK_ESCAPE) {
	        if(gp.gameState == gp.playState) {
	            gp.gameState = gp.quitConfirmState;
	        } else if(gp.gameState == gp.quitConfirmState) {
	            gp.gameState = gp.playState;  // cancel quit
	        }
	    }
		
	    // QUIT CONFIRMATION state - ENTER to quit
	    if(gp.gameState == gp.quitConfirmState) {
	        if(code == KeyEvent.VK_ENTER) {
	            System.exit(0);  // quit game when confirmed
	        }
	    }
		
		if(code == KeyEvent.VK_P) {
			if(gp.gameState == gp.playState)  {
				gp.gameState = gp.pauseState;
			}
			else if(gp.gameState == gp.pauseState)  {
				gp.gameState = gp.playState;
			}
		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;	
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		
	}

}
