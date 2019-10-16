package clueGame;

import java.util.Map;
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
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		
	}
	//Load in the room file
	public void loadRoomConfig() {
		
	}
	//Load in the board legend file
	public void loadBoardConfig() {
		
	}
	//Implemented in IntBoard. Take a look at C12A2 for more info
	public void calcAdjacencies() {
		
	}
	//Implemented in IntBoard. Take a look at C12A2 for for more info
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	//Getters. Self-explanatory
	public BoardCell getCellAt(int row, int col) {
		return null;
	}
	public int getNumRows() {
		return 0;
	}
	public int getNumColumns() {
		return 0;
	}
	public void setConfigFiles(String boardConfig, String roomConfig) {
		
	}
	public Map<Character, String> getLegend() {
		return null;
	}
}
