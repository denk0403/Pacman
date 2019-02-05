import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	
	File originalFileCopy;
	
	GridSquare[][] grid = new GridSquare[31][28];
	Player player = new Player(1, 1, Direction.RIGHT);
	int scoreNeeded;
	
	ArrayList<GridSquare> redTiles = new ArrayList<>();
	ArrayList<GridSquare> pinkTiles = new ArrayList<>();
	ArrayList<GridSquare> blueTiles = new ArrayList<>();
	
	Board(String fileName) throws IOException, URISyntaxException {
		File file = new File(getClass().getResource(fileName).toURI());
		originalFileCopy = file;
		makeBoard(new Scanner(file));
	}

	private void makeBoard(Scanner file) throws IOException {
		scoreNeeded = 0;
		file.useDelimiter("");
		int i = 0;
		int j = 0;
		while (file.hasNext()) {
			//System.out.print(j);
			//System.out.print(" ");
			//System.out.println(i);
			String str = file.next();
			if (str.equals(System.lineSeparator())) {
				i++;
				j = -1;
			} else if (str.equals("B")) {
				grid[i][j] = new Barrier(j,i,Collectable.NONE,true);
			} else if (str.equals("E")) {
				grid[i][j] = new Cell(j,i,Collectable.NONE,false);
				addToGroup(grid[i][j]);
			} else if (str.equals("P")) {
				grid[i][j] = new Cell(j,i,Collectable.POINT,false);
				addToGroup(grid[i][j]);
				scoreNeeded += Collectable.POINT.getWorth();
			} else if (str.equals("O")) {
				grid[i][j] = new Cell(j,i,Collectable.POWER,false);
				addToGroup(grid[i][j]);
				scoreNeeded += Collectable.POWER.getWorth();
			} else if (str.equals("F")) {
				grid[i][j] = new Cell(j,i,Collectable.FRUIT,false);
				addToGroup(grid[i][j]);
			} else if (str.equals("G")) {
				grid[i][j] = new Gate(j,i,Collectable.NONE,true);
			} else if (str.equals("L")) {
				grid[i][j] = new Cell(j,i,Collectable.NONE,false);
				addToGroup(grid[i][j]);
				player = new Player(j,i,Direction.LEFT);
			} else if (str.equals("R")) {
				grid[i][j] = new Cell(j,i,Collectable.NONE,false);
				addToGroup(grid[i][j]);
				player = new Player(j,i,Direction.RIGHT);
			} 
			
			j++;
		}
	}
	
	private void addToGroup(GridSquare square) {
		if(square instanceof Cell) {
			double randomNum = Math.random();
			if (randomNum > 3/4.0) {
				redTiles.add(square);
				//blueTiles.add(square);
			} else if (randomNum > 1/2.0) {
				blueTiles.add(square);
				//pinkTiles.add(square);
			} else if (randomNum > 1/4.0){
				//redTiles.add(square);
				pinkTiles.add(square);
			} else {
				
			}
		}
	}
	
	public GridSquare getCell(int[] pos) {
//		System.out.print(pos[0]);
//		System.out.print(" ");
//		System.out.println(pos[1]);
		return grid[pos[1]][pos[0]];
	}
}
