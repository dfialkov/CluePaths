//Authors:
//Daniel Fialkov
//Darian Dickerson

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Queue;
import java.util.Random;




public class Board {
	// instance variables
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private int numRows;
	private int numColumns;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private ArrayList<Card> deck;
	private ArrayList<String[]> names;
	private ArrayList<Player> players;
	private String boardConfigFile;
	private String roomConfigFile;
	private String cardConfigFile;
	private HashSet visited;
	Solution answer;


	// variable used for singleton pattern
	private static Board theInstance = new Board();


	// constructor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		deck = new ArrayList<Card>();
		names = new ArrayList<String[]>();
		players = new ArrayList<Player>();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() throws  BadConfigFormatException {
		loadRoomConfig();
		loadBoardConfig();
		calcAdjacencies();
		loadCardConfig();
		createPlayers();
		answer = new Solution();
		deal();
	}

	public void deal() {
		for(Player currPlayer : players) {
			for (Card currCard : deck) {
				currPlayer.constructFull(currCard);
			}
		}		
		Random num = new Random();
		int count = 0;

		while(deck.size() > 0) {
			//Random card from remaining # of cards in deck
			int index = num.nextInt(deck.size());

			//Draw Card & add to player's hand
			players.get(count % 6).drawCard(deck.get(index));
			//Remove card from deck
			deck.remove(index);
			count++;
		}
	}

	public void createPlayers() {
		//Needed to make some tests work
		players.clear();
		//Random index between 0 & size of the deck
		Random num = new Random();
		int index = num.nextInt(names.size());

		//Create Players & assign values: name,color,row,col
		//namesArray:name0/type1/color2/row3/col4
		//Create Human
		Player human = new HumanPlayer(names.get(index)[0],names.get(index)[2],names.get(index)[3],names.get(index)[4]);
		//Delete name at Index
		names.remove(index);
		//Add Player to players list
		players.add(human);

		//Create Computers
		for(int i = 0; i<5; i++ ) {
			index = num.nextInt(names.size());
			Player comp = new ComputerPlayer(names.get(index)[0],names.get(index)[2], Integer.valueOf(names.get(index)[3]),Integer.valueOf(names.get(index)[4]));
			names.remove(index);
			players.add(comp);
		}

	}

	public void loadCardConfig() {
		FileReader roomReader;

		try {
			roomReader = new FileReader(cardConfigFile);
			Scanner roomScan = new Scanner(roomReader);

			while(roomScan.hasNextLine()) {
				String[] lineSplit= roomScan.nextLine().split(", ");

				//Create Card
				Card card = new Card(lineSplit[0], lineSplit[1]);
				//Create list of 6 names
				if(lineSplit[1].contentEquals("person") && names.size()<6) {
					names.add(lineSplit);
				}

				//Add to deck
				deck.add(card);

			}

			roomScan.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	//Load in the room file
	public void loadRoomConfig() throws BadConfigFormatException {
		FileReader roomReader;

		try {
			roomReader = new FileReader(roomConfigFile);
			Scanner roomScan = new Scanner(roomReader);

			while(roomScan.hasNextLine()) {
				String[] lineSplit= roomScan.nextLine().split(", ");
				legend.put(lineSplit[0].charAt(0),lineSplit[1]);

				//Invalid Room Type
				if(!lineSplit[2].contentEquals("Card") && !lineSplit[2].contentEquals("Other")) {
					throw new BadConfigFormatException("Invalid Room Type: " + lineSplit[2]);
				}

				//Create Room card
				if(lineSplit[2].contentEquals("Card")) {
					Card card = new Card(lineSplit[1]);
					deck.add(card);
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

		try {
			boardReader = new FileReader(boardConfigFile);
			Scanner boardScan = new Scanner(boardReader);

			//find numColumns numRows
			numRows = 0;
			while(boardScan.hasNextLine()) {
				String[] array= boardScan.nextLine().split(",");
				numColumns = array.length;
				numRows++;
			}
			boardScan.close();

			//create board
			board = new BoardCell[numRows][numColumns];

			//Populate Board
			boardReader = new FileReader(boardConfigFile);
			boardScan = new Scanner(boardReader);

			int row = 0;
			while(boardScan.hasNextLine()) {
				String[] rowContents= boardScan.nextLine().split(",");

				// Test Same # of Columns in each Row
				int actualColCount = rowContents.length;
				if(actualColCount != numColumns) {
					throw new BadConfigFormatException("Invalid Board Configuration: Number of columns vary");
				}

				for(int i = 0; i < numColumns; i++) {
					// Create BoardCell
					if (rowContents[i].charAt(0) > 172){

						board[row][i] = new BoardCell(row, i, "C");

					}else {	
						board[row][i] = new BoardCell(row, i, rowContents[i]);

						// Room not in Legend
						if(!legend.containsKey(rowContents[i].charAt(0))) {
							throw new BadConfigFormatException("Invalid Board Configuration: Room not in Legend");
						}
					}
				}
				row++;
			}

			boardScan.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	} 


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
					case NONE: 
						break;
					default:
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

						//If the adj cell is a walkway, add it
						if(currCell.getInitial() == 'W') {
							adjCells.add(currCell);
						}
						//If the adj cell is a door, check testCell's relative position and add it if the door direction is correct
						else if(currCell.getInitial() != 'W' && currCell.isDoorway()) {
							if(currCell.getDoorDirection() == DoorDirection.UP && currCell.getRow() == testCell.getRow() +1 ) {
								adjCells.add(currCell);
							}
							else if(currCell.getDoorDirection() == DoorDirection.DOWN && currCell.getRow() == testCell.getRow() - 1) {
								adjCells.add(currCell);
							}
							else if(currCell.getDoorDirection() == DoorDirection.RIGHT && currCell.getCol() == testCell.getCol() - 1) {
								adjCells.add(currCell);
							}
							else if(currCell.getDoorDirection() == DoorDirection.LEFT && currCell.getCol() == testCell.getCol() + 1) {
								adjCells.add(currCell);
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
	
	public Card handleSuggestion(Solution suggestion) {
		return null;
	}

	public boolean handleAccusation(Solution accusation) {
		if(accusation.equals(answer)) {
			return true;
		}
		else {
			return false;
		}

	}


	//Setters and getters. Self-explanatory
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

	public void setCardConfigFile(String cardConfigFile) {
		this.cardConfigFile = cardConfigFile;
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

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	//Testing purposes only
	public void setSolution(String setPerson, String setWeapon, String setRoom) {
		answer = new Solution(setPerson, setWeapon, setRoom);
	}
	





}
