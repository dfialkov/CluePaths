//Authors:
//Daniel Fialkov
//Darian Dickerson

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.Queue;
import java.util.Random;




public class Board extends JPanel implements MouseListener{
	// instance variables
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private int numRows;
	private int numColumns;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private ArrayList<Card> deck;
	private ArrayList<Card> copy;
	private ArrayList<String[]> names;
	private ArrayList<Player> players;
	private String boardConfigFile;
	private String roomConfigFile;
	private String cardConfigFile;
	private HashSet visited;
	Solution answer;
	int nextPlayerIndex = 0;
	private JTextField currPlayerField;
	private JTextField rollField;
	private boolean waitingForHuman = false;
	private HumanPlayer currHuman;
	// variable used for singleton pattern
	private static Board theInstance = new Board();


	// constructor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		deck = new ArrayList<Card>();
		copy = new ArrayList<Card>();
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
		addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(BoardCell[] cellRow: board ) {
			for(BoardCell cell: cellRow) {
				cell.draw(g);
			}
		}

		for(Player person: players) {
			person.draw(g);
		}

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
			copy.add(new Card(deck.get(index).getCardName(),deck.get(index).getType()));
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
		Player human = new HumanPlayer(names.get(index)[0],names.get(index)[2],Integer.valueOf(names.get(index)[3]),Integer.valueOf(names.get(index)[4]));
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

	public Card handleSuggestion(Solution suggestion, String accuserName) {
		for(Player currPlayer : players) {
			if(currPlayer.getName() != accuserName) {
				Card disproveCard = currPlayer.disproveSuggestion(suggestion);
				if(disproveCard != null) {
					for(Player seeingPlayer : players) {
						seeingPlayer.seeCard(disproveCard);
					}
					return disproveCard;
				}
			}
		}
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
	public void attemptHumanMove(int mouseX, int mouseY) {
		if(waitingForHuman == false) {
			JOptionPane.showMessageDialog(Board.getInstance(), "You may not move outside of your turn", "Rule warning", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//Go through all the cells in hopes of finding the cell the click landed in
		for(BoardCell[] row : board) {
			for(BoardCell candidate : row) {
				BoardCell cellTest = candidate.containsClick(mouseX, mouseY);
				//Verify target validify within rules
				if(cellTest != null  && targets.contains(candidate)) {
					//Move player
					currHuman.setCol(cellTest.getCol());
					currHuman.setRow(cellTest.getRow());
					//Hand game state to computers
					waitingForHuman = false;
					currHuman = null;
					for(BoardCell[] currRow : board) {
						for(BoardCell currCell : currRow) {
							currCell.resetColor();
						}
					}
					repaint();
					return;
				}
			}
		}
		JOptionPane.showMessageDialog(Board.getInstance(), "Invalid movement location", "Rule warning", JOptionPane.INFORMATION_MESSAGE);
	}

	//This is what the Next Player button calls.
	//Does a number of things depending on whose turn it is.
	public void nextPlayer() {
		//Don't proceed without human input on their turn. 
		if(waitingForHuman) {
			JOptionPane.showMessageDialog(Board.getInstance(), "You must make your move before passing your turn", "Rule warning", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		for(BoardCell[] row : board) {
			for(BoardCell candidate : row) {
				candidate.resetColor();
			}
		}
		Player player = getNextPlayer();
		//Name the next player
		currPlayerField.setText(player.getName());
		//Roll the dice for movement
		Random rand = new Random();
		//Add 2 to add the bounds that dice naturally have. 
		int diceResult = rand.nextInt(6) + rand.nextInt(6) + 2;
		//Display roll
		rollField.setText(Integer.toString(diceResult));
		//Humans and computers both need targets
		calcTargets(player.getRow(), player.getCol(), diceResult);
		
		//Player is a human
		if(player.getHumanName() != null) {
			currHuman = (HumanPlayer) player;
			waitingForHuman = true;
			highlightTargets();

		}
		
		//Player is a computer
		else{
			//Automatically take turn. This should be updating as soon as the turn starts, in human terms.
			((ComputerPlayer) player).pickLocation(targets);
		}
		repaint();
	}
	//show the player their options for movement.
	//Changes the board's cell info without changing the actual GUI info. Repaint elsewhere.
	public void highlightTargets(){
		for(BoardCell target : targets) {
			for(BoardCell[] row : board) {
				for(BoardCell candidate : row) {
					if(candidate == target) {
						candidate.highlight();
					}
				}
			}
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
	//This is how humans are identified.
	public String getHumanPlayerName() {
		for(Player player : players) {
			if(player.getHumanName() != null) {
				return player.getHumanName();
			}
		}
		return null;
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

	public ArrayList<Card> getCopy() {
		return  copy;
	}
	public void setCurrPlayerBox(JTextField box) {
		currPlayerField = box;
	}
	public void setRollBox(JTextField die) {
		rollField = die;
	}
	//For testing handleSuggestion()
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void clearPlayers() {
		players.clear();
	}
	public void addPlayer(Player toAdd) {
		players.add(toAdd);
	}

	//Testing purposes only
	public void setSolution(String setPerson, String setWeapon, String setRoom) {
		answer = new Solution(setPerson, setWeapon, setRoom);
	}
	public Player getNextPlayer() {
		Player toReturn = players.get(nextPlayerIndex);
		nextPlayerIndex++;
		if(nextPlayerIndex == 6) {
			nextPlayerIndex = 0;
		}
		return toReturn;
	}
	//Mouse listener methods. Deliberately left unimplemented. 
	@Override
	public void mouseClicked(MouseEvent arg0) {
		//Unneeded
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//Unneeded
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		//Unneeded
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		attemptHumanMove(arg0.getX(), arg0.getY());
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//Unneeded
		
	}






}
