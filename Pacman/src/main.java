import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class main implements Runnable {
	
	static AudioClip intro = Applet.newAudioClip(GridSquare.class.getResource("/Intro.wav"));
	static AudioClip siren = Applet.newAudioClip(GridSquare.class.getResource("/siren.wav"));
	static AudioClip power = Applet.newAudioClip(GridSquare.class.getResource("/power.wav"));
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new main());
	}

	public void run() {
		Board board = null;
		try {
			board = new Board("/default.txt");
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
//		System.out.println(board);
//		for (GridSquare[] row : board.grid) {
//			for (GridSquare square : row) {
//				System.out.println(square.col);
//			}
//		}

		Ghost.board = board;
		PacmanGame game = new PacmanGame(board);
		PacmanGraphics gr = new PacmanGraphics(game);
		gr.window.setVisible(true);
		
		Thread count = new Thread( () -> {
			while(true) {
				if (Ghost.friendsInDanger == true) {
					power.loop();
					try {
						Thread.sleep(8500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Ghost.friendsInDanger = false;
					for (Ghost ghost : game.getGhosts()) {
						ghost.inDanger = false;
					}
					power.stop();
					game.updatePaths();
				}
			}
		}); //doesn't work properly when player eats another orb while in the middle of the countdown

		new Thread(() -> {
			intro.play();
			count.start();
			try {
				Thread.sleep(4525);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			siren.loop();
			while (!game.isOver()) {
				try {
					Thread.sleep(275);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				game.updateGame(); 
				
//				try {
//					Thread.sleep(0);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				//System.out.println(game.getPlayer().points);
				//System.out.println(game.getBoard().scoreNeeded);
				//System.out.println(game.getPlayer().facingBarrier);
			}
			count.stop();
			siren.stop();
			PacmanGame.death.play();
		}).start();
		
		new Thread(() -> {
			while (!game.isOver()) {
				gr.redrawBoard();
				try {
					Thread.sleep(175);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gr.redrawBoard();
		}).start();
		
		new Thread(() -> {
			game.updatePaths();
			try {
				Thread.sleep(9500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			game.getGhosts()[1].pathMode = 1;
			game.getGhosts()[1].curDir = Direction.UP;
			game.updatePaths();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			game.getGhosts()[2].pathMode = 1;
			game.getGhosts()[2].curDir = Direction.UP;
			game.updatePaths();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (!game.isOver()) {
//				game.updatePaths();
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!Ghost.friendsInDanger)
					game.getGhosts()[0].updatePath(game.getPlayer().getX(), game.getPlayer().getY());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!Ghost.friendsInDanger)
					game.getGhosts()[1].updatePath(game.getPlayer().getX(), game.getPlayer().getY());
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!Ghost.friendsInDanger)
					game.getGhosts()[2].updatePath(game.getPlayer().getX(), game.getPlayer().getY());
			}
		}).start();
	}
}
