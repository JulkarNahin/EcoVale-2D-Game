package main;

import javax.swing.JFrame;

public class main {
	
	public static JFrame window;

	public static void main(String[] args) {
		
//		JFrame window = new JFrame();
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("EcoVale");
		window.setUndecorated(true);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		gamePanel.setFullScreen(window); // Fullscreen before visible
		window.setVisible(true); // Now safe
		
		gamePanel.setupGame(); // Prepare tiles, UI, etc
		gamePanel.startGameThread(); // Start rendering loop

	}

}
