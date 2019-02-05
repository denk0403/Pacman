import javax.sound.sampled.*;
import java.io.*;

public class Player {
	private int x, y;
	Direction curDir;
	Direction queuedDir;
	int points;
	public boolean facingBarrier;
	
	Player(int x, int y, Direction dir) {
		this.setX(x);
		this.setY(y);
		this.curDir = dir;
	}
	
	void movePlayer(int newX, int newY) {
		setX(newX); setY(newY);
	}

	public int[] getNextCell() {
		// TODO Auto-generated method stub
		int nextX = 0, nextY = 0;
		if (curDir == Direction.UP) {
			nextX = getX();
			nextY = Math.floorMod((getY()-1), 31);
		} else if (curDir == Direction.DOWN) {
			nextX = getX();
			nextY = Math.floorMod((getY()+1), 31);
		} else if (curDir == Direction.LEFT) {
			nextX = Math.floorMod((getX()-1), 28);
			nextY = y;
		} else if (curDir == Direction.RIGHT) {
			nextX = Math.floorMod((getX()+1), 28);
			nextY = y;
		}
		return new int[] {nextX, nextY};
	}
	
	public int[] getQueuedCell() {
		// TODO Auto-generated method stub
		int nextX = 0, nextY = 0;
		if (queuedDir == Direction.UP) {
			nextX = getX();
			nextY = Math.floorMod((getY()-1), 31);
		} else if (queuedDir == Direction.DOWN) {
			nextX = getX();
			nextY = Math.floorMod((getY()+1), 31);
		} else if (queuedDir == Direction.LEFT) {
			nextX = Math.floorMod((getX()-1), 28);
			nextY = y;
		} else if (queuedDir == Direction.RIGHT) {
			nextX = Math.floorMod((getX()+1), 28);
			nextY = y;
		}
		return new int[] {nextX, nextY};
	}
	
	public void addPoints(int points) {
		this.points += points;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setDirection(Direction dir) {
		// TODO Auto-generated method stub
		this.curDir = dir;
	}
	
	public void setQueued(Direction dir) {
		// TODO Auto-generated method stub
		this.queuedDir = dir;
	}
	
	
}
