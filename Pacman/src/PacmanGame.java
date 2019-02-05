import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.util.Arrays;

public class PacmanGame {

	private Board board;
	private Player player;
	private Ghost[] ghosts;
	private boolean isOver;
	
	static AudioClip death = Applet.newAudioClip(GridSquare.class.getResource("/death.wav"));
	
	public PacmanGame(Board board) {
		this.board = board;
		this.player = board.player;
		this.ghosts = new Ghost[] 
				{new Ghost("Blinky", Color.RED, 14, 11, Ghost.chase, Direction.RIGHT, board.redTiles),
				new Ghost("Pinky", Color.PINK, 13, 14, Ghost.chase, Direction.NONE, board.pinkTiles),
				new Ghost("Inky", Color.CYAN, 14, 14, Ghost.chase, Direction.NONE, board.blueTiles)};
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean isOver() {
		return player.points == board.scoreNeeded || isOver;
	}
	
	GridSquare updateGame() {
		int[] nextPos = player.getNextCell();
		GridSquare nextCell = board.getCell(nextPos);
		//System.out.println(Arrays.toString(nextPos));
		int[] queuedPos = player.getQueuedCell();
		Collectable col = Collectable.NONE;
		GridSquare queuedCell = board.getCell(queuedPos);
		if (!queuedCell.isBarrier()) {
			player.facingBarrier = false;
			player.addPoints(queuedCell.getCol().getWorth());
			col = nextCell.getCol();
			queuedCell.collect();
			player.setDirection(player.queuedDir);
			player.queuedDir = null;
			player.movePlayer(queuedPos[0], queuedPos[1]);
		} else if(!nextCell.isBarrier()) {
			player.facingBarrier = false;
			player.addPoints(nextCell.getCol().getWorth());
			col = nextCell.getCol();
			nextCell.collect();
			player.movePlayer(nextPos[0], nextPos[1]);
		} else {
			player.facingBarrier = true;
		}
		
		for (Ghost ghost : ghosts) {
			if (ghost.x == player.getX() && ghost.y == player.getY()) {
				if (ghost.inDanger) {
					ghost.resetGhost();
				} else {
					isOver = true;
				}
			}
		}
		
		updateGhosts(col);
		
		//System.out.println(nextCell.isBarrier());
		return nextCell;
	}

	private void updateGhosts(Collectable col) {
		if (col == Collectable.POWER) {
			for (Ghost ghost : ghosts) {
				ghost.setInDanger();
				Ghost.friendsInDanger = true;
				ghost.updatePath(player.getX(), player.getY());
			}
		}
		for (Ghost ghost : ghosts) {
			ghost.move();
		}
	}

	public void updatePaths() {
		for (Ghost ghost : ghosts) {
			ghost.updatePath(player.getX(), player.getY());
		}
	}
	
	public Ghost[] getGhosts() {
		return ghosts;
	}

}
