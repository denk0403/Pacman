import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PacmanGraphics{
	Board board;
	Player player;
	Ghost[] ghosts;
	
	JFrame window = new JFrame("Pac-Man");
	JPanel boardGraphics;
	
	PacmanGraphics(PacmanGame game) {
		this.board = game.getBoard();
		this.player = game.getPlayer();
		this.ghosts = game.getGhosts();
		initComponents();
	}
	
	public void initComponents() {
		// creates main window for game
		window.setSize(new Dimension(560, 620));
        window.setResizable(false);

        // makes the "close" button simply exit out of the application
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // centers the frame on screen
        window.setLocationRelativeTo(null);
           
        initBoard();
        window.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					if (player.queuedDir != Direction.LEFT && player.queuedDir != Direction.RIGHT) {
						player.setQueued(Direction.UP);
					}
//					if (board.getCell(player.getNextCell()).isBarrier()) {
//						player.setDirection(curDir);
//					}
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					if (player.queuedDir != Direction.LEFT && player.queuedDir != Direction.RIGHT) {
						player.setQueued(Direction.DOWN);
					}
//					if (board.getCell(player.getNextCell()).isBarrier()) {
//						player.setDirection(curDir);
//					}
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					if (player.queuedDir != Direction.UP && player.queuedDir != Direction.DOWN) {
						player.setQueued(Direction.LEFT);
					}
//					if (board.getCell(player.getNextCell()).isBarrier()) {
//						player.setDirection(curDir);
//					}
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					if (player.queuedDir != Direction.UP && player.queuedDir != Direction.DOWN) {
						player.setQueued(Direction.RIGHT);
					}
//					if (board.getCell(player.getNextCell()).isBarrier()) {
//						player.setDirection(curDir);
//					}
				}
				
			}
		});
        
        window.add(boardGraphics);
        redrawBoard();
        window.pack();
        
	}
	
	public void initBoard() {
		boardGraphics = new JPanel(new GridLayout(31, 28));
        for (GridSquare[] row : board.grid) {
			for (GridSquare square : row) {
				//JPanel tile = square;
//				Collectable col = square.getCol();
//				if (square instanceof Gate) {
//					tile.setBackground(Color.PINK);
//				} else if (square instanceof Barrier) {
//					tile.setBackground(Color.BLUE);
//				} else if (player.getX() == square.getX() && player.getY() == square.getY()) {
//					tile.setBackground(Color.YELLOW);
//				} else if (col == Collectable.NONE) {
//					tile.setBackground(Color.BLACK);
//				} else if (col == Collectable.POINT) {
//					tile = new JPanelOrb(col);
//				} else if (col == Collectable.POWER) {
//					tile = new JPanelOrb(col);
//				} else if (col == Collectable.FRUIT) {
//					tile.setBackground(Color.GREEN);
//				}
//				boardGraphics.add(tile);
				boardGraphics.add(square);
			}
		}
	}
	
	public void redrawBoard() {
		for (Component square : boardGraphics.getComponents()) {
			if (square instanceof Cell) {
				((Cell)square).color = Color.BLACK;
			}
			for (Ghost ghost : ghosts) {
				if (ghost.x == square.getX() && ghost.y == square.getY()) {
					if (square instanceof Cell) {
						((Cell)square).color = ghost.color;
					}
				}
			}
			if (player.getX() == square.getX() && player.getY() == square.getY()) {
				if (square instanceof Cell) {
					((Cell)square).color = Color.YELLOW;
				}
			}
			square.repaint();
			//Thread.sleep(0);
		}
	}
	
}
