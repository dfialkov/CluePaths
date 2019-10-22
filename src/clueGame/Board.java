package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;



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
		
	}
	
	//Implemented in IntBoard. Take a look at C12A2 for for more info
	public void calcTargets(BoardCell cell, int pathLength) {
		
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
}
