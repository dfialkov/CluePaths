//Authors:
//Daniel Fialkov
//Darian Dickerson

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Queue;




public class Board {
	// instance variables
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private int numRows;
	private int numColumns;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private HashSet visited;

	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() throws FileNotFoundException, BadConfigFormatException {
		loadRoomConfig();
		loadBoardConfig();
		calcAdjacencies();
	}
	
	//Load in the room file
	public void loadRoomConfig() throws BadConfigFormatException {
		FileReader roomReader;
		
		try {
			roomReader = new FileReader(roomConfigFile);
			Scanner roomScan = new Scanner(roomReader);
		
			while(roomScan.hasNextLine()) {
				String array1[]= roomScan.nextLine().split(",");
				legend.put(array1[0].charAt(0),array1[1].substring(1));
				
				//Invalid Room Type
				if(!array1[2].substring(1).contentEquals("Card") && !array1[2].substring(1).contentEquals("Other")) {
					throw new BadConfigFormatException("Invalid Room Type: " + array1[2].substring(1));
				}
				
			}
			
			roomScan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	} 
		
	
	
	//Load in the board legend file
	public void loadBoardConfig() throws BadConfigFormatException {
		FileReader boardReader;
		FileReader boardReader1;
		
		try {
			boardReader = new FileReader(boardConfigFile);
			Scanner boardScan = new Scanner(boardReader);

			//find numColumns numRows
			numRows = 0;
			while(boardScan.hasNextLine()) {
				String array[]= boardScan.nextLine().split(",");
				numColumns = array.length;
				numRows++;
			}
			boardScan.close();

			//create board
			board = new BoardCell[numRows][numColumns];

			//Populate Board
			boardReader1 = new FileReader(boardConfigFile);
			Scanner boardScan1 = new Scanner(boardReader1);

			int row = 0;
			while(boardScan1.hasNextLine()) {
				String array1[]= boardScan1.nextLine().split(",");

				// Test Same # of Columns in each Row
				int temp = array1.length;
				if(temp != numColumns) {
					throw new BadConfigFormatException("Invalid Board Configuration: Number of columns vary");
				}

				for(int i = 0; i < numColumns; i++) {
					// Create BoardCell
					if (array1[i].charAt(0) > 172){

						board[row][i] = new BoardCell(row, i, "C");

					}else {	
						board[row][i] = new BoardCell(row, i, array1[i]);

						// Room not in Legend
						if(!legend.containsKey(array1[i].charAt(0))) {
							throw new BadConfigFormatException("Invalid Board Configuration: Room not in Legend");
						}
					}
				}
				row++;
			}
			
		
		if(boardScan!=null)
			boardScan.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
} 

	
	//Implemented in IntBoard. Take a look at C12A2 for more info
	public void calcAdjacencies() {
		adjMatrix = new HashMap();
		//Code to populate adj map, checks for valid coordinates before adding cell.
		for(int row = 0;row<getNumRows();row++) {
			for(int col = 0;col<getNumColumns();col++) {
				Set<BoardCell> adjCells = new HashSet();
				BoardCell testCell = getCellAt(row, col);
				
				//Handle door cells within rooms
				if(testCell.getInitial() != 'W' && testCell.getDoorDirection() != DoorDirection.NONE) {
					switch(testCell.getDoorDirection()) {
					case UP: 
						adjCells.add(board[row-1][col]);
						break;
					case DOWN:
						adjCells.add(board[row+1][col]);
						break;
					case RIGHT:
						adjCells.add(board[row][col+1]);
						break;
					case LEFT:
						adjCells.add(board[row][col-1]);
						break;
					}
				}
				//Handle walkways
				else if(testCell.getInitial() == 'W') {
					//Get every possible adjacency into a queue for more compact code
					Queue adjQueue = new LinkedList();
					//Simple bounds check
					if(row - 1 >= 0 && row - 1 < numRows  ) {
						adjQueue.add(board[row-1][col]);
					}
					if(row + 1 >= 0 && row + 1 < numRows  ) {
						adjQueue.add(board[row+1][col]);
					}
					if(col - 1 >= 0  && col - 1 < numColumns) {
						adjQueue.add(board[row][col-1]);
					}
					if(col + 1 >= 0  && col + 1 < numColumns) {
						adjQueue.add(board[row][col+1]);
					}
					//For every valid adj cell:
					while(!adjQueue.isEmpty()){
						BoardCell currCell = (BoardCell) adjQueue.element();
						adjQueue.remove();
						//if the adj cell is a doorless room, ignore the cell
						if(currCell.getInitial() != 'W' && currCell.getDoorDirection() == DoorDirection.NONE) {
							
						}
						//If the adj cell is a walkway, add it
						else if(currCell.getInitial() == 'W') {
							adjCells.add(currCell);
						}
						//If the adj cell is a door, check testCell's relative position and add it if the door direction is correct
						else if(currCell.getInitial() != 'W' && currCell.isDoorway()) {
							
							if(currCell.getDoorDirection() == DoorDirection.UP) {
								if(currCell.getRow() == testCell.getRow() +1 ) {
									adjCells.add(currCell);
								}}
								else if(currCell.getDoorDirection() == DoorDirection.DOWN) {
									if(currCell.getRow() == testCell.getRow() - 1) {
										adjCells.add(currCell);
									}
								}
								else if(currCell.getDoorDirection() == DoorDirection.RIGHT) {
									if(currCell.getCol() == testCell.getCol() - 1) {
										adjCells.add(currCell);
									}
								}
								else if(currCell.getDoorDirection() == DoorDirection.LEFT) {
									if(currCell.getCol() == testCell.getCol() + 1) {
										adjCells.add(currCell);
									}
								}
							}
						}
					}
				
				adjMatrix.put(testCell, adjCells);
			}
		}
		
	}
	
	
	public void calcTargets(int row, int col, int pathLength) {
		//set up for recursive call
				visited = new HashSet();
				targets = new HashSet();
				visited.add(board[row][col]);
				BoardCell startCell = board[row][col];
				findAllTargets(startCell, pathLength);
		
	}
	//recursive pathfinding algorithm
	//Do not modify without running all test cases to make sure you didn't break anything. 
	public void findAllTargets(BoardCell startCell, int pathLength) {
		int numSteps = pathLength;
		for(BoardCell adjCell : adjMatrix.get(startCell)) {
			
			if(visited.contains(adjCell)) {
				continue;
			}
			if(adjCell.isDoorway() && !targets.contains(adjCell)) {
				targets.add(adjCell);
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
	
	
	//Getters. Self-explanatory
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public void setConfigFiles(String boardConfig, String roomConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;
	}
	
	public Map<Character, String> getLegend() {
		return legend;
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	

	

}
