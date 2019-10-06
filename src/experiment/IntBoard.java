package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMap;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;


	public IntBoard() {
		//create board, populate grid
		grid = new BoardCell[4][4];
		for(int i = 0;i < 4;i++) {
			for(int j = 0;j<4;j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		//prepare one-time adj map
		this.calcAdjacencies();
	}


	public void calcAdjacencies() {
		adjMap = new HashMap();
		//Code to populate adj map, checks for valid coordinates before adding cell.
		for(int row = 0;row<4;row++) {
			for(int col = 0;col<4;col++) {
				Set<BoardCell> adjCells = new HashSet();
				if(col - 1 >= 0 && col - 1 <= 3) {
					adjCells.add(grid[row][col - 1]);
				}
				if(col + 1 >= 0 && col + 1 <= 3) {
					adjCells.add(grid[row][col + 1]);
				}
				if(row - 1 >= 0 && row - 1 <= 3) {
					adjCells.add(grid[row - 1][col]);
				}
				if(row + 1 >= 0 && row + 1 <= 3) {
					adjCells.add(grid[row + 1][col]);
				}
				adjMap.put(grid[row][col], adjCells);
			}
		}
	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		//set up for recursive call
		visited = new HashSet();
		targets = new HashSet();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
		
	}
	//recursive pathfinding algorithm
	public void findAllTargets(BoardCell startCell, int pathLength) {
		int numSteps = pathLength;
		for(BoardCell adjCell : adjMap.get(startCell)) {
			if(visited.contains(adjCell)) {
				continue;
			}
			visited.add(adjCell);
			if(numSteps == 1) {
				targets.add(adjCell);
			}
			else {
				findAllTargets(adjCell, numSteps - 1);
			}
			visited.remove(adjCell);
			
		}
	}
	//Some getters
	public Set<BoardCell> getTargets(){
		return targets;
	}

	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMap.get(cell);
	}
	public BoardCell getCell(int toRow, int toCol) {
		return grid[toRow][toCol];
	}

}
