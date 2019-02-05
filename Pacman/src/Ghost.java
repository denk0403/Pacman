import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Ghost {

	String name;
	int x;
	int y;

	public int pathMode;
	public int targetX;
	public int targetY;
	public static Board board;
	public Direction curDir = Direction.NONE;
	public int[][] curPath = new int[31][28];
	private Color origColor;
	public Color color;
	public ArrayList<GridSquare> available;

	static boolean friendsInDanger = false;
	boolean inDanger = false;

	static final int box = 0;
	static final int chase = 1;
	static final int ahead = 2;
	static final int runaway = 3;
	static final int still = 4;

	Ghost(String name, Color color, int x, int y, int pathMode, Direction dir, ArrayList<GridSquare> available) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.pathMode = pathMode;
		this.curDir = dir;
		this.origColor = color;
		this.color = color;
		this.available = available;
	}

	public void setPathMode(int pathMode) {
		this.pathMode = pathMode;
	}

	public int getPathMode() {
		return pathMode;
	}

	public void updatePath(int targetX, int targetY) {
		if (inDanger) {
			GridSquare farthest = available.get(0);
			double dist = Math.sqrt(Math.pow(targetX - farthest.getX(), 2) + Math.pow(targetY - farthest.getY(), 2));
			for (GridSquare square : available) {
				double newDist = Math.sqrt(Math.pow(square.getX() - targetX, 2) + Math.pow(square.getY() - targetY, 2));
				if (newDist >= dist) {
					dist = newDist;
					farthest = square;
				}
			}
			this.targetX = farthest.getX();
			this.targetY = farthest.getY();
		} else {
			GridSquare closest = available.get(0);
			double dist = Math.sqrt(Math.pow(targetX - closest.getX(), 2) + Math.pow(targetY - closest.getY(), 2));
			for (GridSquare square : available) {
				double newDist = Math.sqrt(Math.pow(square.getX() - targetX, 2) + Math.pow(square.getY() - targetY, 2));
				if (newDist < dist) {
					dist = newDist;
					closest = square;
				}
			}
			this.targetX = closest.getX();
			this.targetY = closest.getY();
		}

		if (pathMode == box) {
			// find random box cell
			// runAStarTo(boxCell);
			runAStarTo(this.targetX, this.targetY);
		} else if (pathMode == chase) {
			runAStarTo(this.targetX, this.targetY);
		} else if (pathMode == ahead) {
			// find path to several cells ahead
			runAStarTo(this.targetX, this.targetY);
//		} else if (pathMode == runaway) {
//			// randomly run chase or ahead pathfinding
//			// and runaway when too close to Pacman
//			runAStarTo(targetX, targetY);
		} else if (pathMode == still) {
			// runAStarTo(x, y);
		}
	}

	public void runAStarTo(int x, int y) {
		// System.out.println("A* to x: " + x + " y: " + y);
		// System.out.println("my pos x: " + this.x + " y: " + this.y);
		resetCurPath();
		int dist = 1;
		while (curPath[this.y][this.x] == 0) {
//			System.out.println("cur dist: " + dist);
			for (GridSquare[] row : board.grid) {
				for (GridSquare cell : row) {
					int cx = cell.getX();
					int cy = cell.getY();

					if (curPath[cy][cx] == dist) {
						if (curPath[Math.floorMod((cy - 1), 31)][cx] == 0) {
							curPath[Math.floorMod((cy - 1), 31)][cx] = dist + 1;
						}
						if (curPath[Math.floorMod((cy + 1), 31)][cx] == 0) {
							curPath[Math.floorMod((cy + 1), 31)][cx] = dist + 1;
						}
						if (curPath[cy][Math.floorMod((cx - 1), 28)] == 0) {
							curPath[cy][Math.floorMod((cx - 1), 28)] = dist + 1;
						}
						if (curPath[cy][Math.floorMod((cx + 1), 28)] == 0) {
							curPath[cy][Math.floorMod((cx + 1), 28)] = dist + 1;
						}
					}

//					if (curPath[cy][cx] == dist) {
//						//System.out.println("cell x: " + cx + " y: " + cy + " with " + dist);
//						if (cy - 1 > 0) {
//							if (curPath[cy-1][cx] == 0) {
//								curPath[cy-1][cx] = dist + 1;
//							}
//						}
//						if (cy + 1 < 31) {
//							if (curPath[cy+1][cx] == 0) {
//								curPath[cy+1][cx] = dist + 1;
//							}
//						}
//						if (cx - 1 > 0) {
//							if (curPath[cy][cx-1] == 0) {
//								curPath[cy][cx-1] = dist + 1;
//							}
//						}
//						if (cx + 1 < 28) {
//							if (curPath[cy][cx+1] == 0) {
//								curPath[cy][cx+1] = dist + 1;
//							}
//						}
//
//					}

				}
			}
			dist++;
		}

//		System.out.println();
//		System.out.println(color);
//		for (int[] row : curPath) {
//			System.out.println(Arrays.toString(row));
//		}

	}

	private void resetCurPath() {
		curPath = new int[31][28];

		for (GridSquare[] row : board.grid) {
			for (GridSquare square : row) {
				if (square.isBarrier() && !(square instanceof Gate)) {
					curPath[square.getY()][square.getX()] = Integer.MAX_VALUE;
				}
			}
		}

		curPath[this.targetY][this.targetX] = 1;
	}

	public void move() {
		// TODO Auto-generated method stub
		color = origColor;
		GridSquare curCell = board.grid[this.y][this.x];
		if (curCell.isBarrier() && !(curCell instanceof Gate)) {
			resetGhost();
		}
		if (x == targetX && y == targetY) {
			updatePath(board.player.getX(), board.player.getY());
		} else if (this.inDanger) {
			color = Color.GREEN;
		} else {
			color = origColor;
		}
		int[] neighborCellValues = new int[] { curPath[Math.floorMod((y - 1), 31)][x],
				curPath[y][Math.floorMod((x + 1), 28)], curPath[y][Math.floorMod((x - 1), 28)],
				curPath[Math.floorMod((y + 1), 31)][x] };
		if (curDir != Direction.NONE) {
			curDir = Direction.dirs[findMin(neighborCellValues)];
		}

		curPath[this.y][this.x] = -1;

		if (curDir == Direction.UP) {
			y = Math.floorMod((y - 1), 31);
		} else if (curDir == Direction.DOWN) {
			y = Math.floorMod((y + 1), 31);
		} else if (curDir == Direction.LEFT) {
			x = Math.floorMod((x - 1), 28);
		} else if (curDir == Direction.RIGHT) {
			x = Math.floorMod((x + 1), 28);
		} else if (curDir == Direction.NONE) {

		}
	}

	public void resetGhost() {
		inDanger = false;
		this.x = 13;
		this.y = 14;
		runAStarTo(targetX, targetY);
	}

	private int findMin(int[] cells) {
		int min = Integer.MAX_VALUE;
		int index = 0;
		int ofZero = 0;
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] < min && cells[i] > 0) {
				min = cells[i];
				index = i;
			} else if (cells[i] == 0) {
				ofZero = i;
			}
		}
		if (min == Integer.MAX_VALUE) {
			return ofZero;
		} else {
			return index;
		}
	}

	public void setInDanger() {
		// TODO Auto-generated method stub
		friendsInDanger = true;
		inDanger = true;
	}

}
