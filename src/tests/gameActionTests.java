package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class gameActionTests {

	private static Board board;
	@Before
	public void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("./CTestFiles/ourBoard.csv", "./CTestFiles/ourBoardLegend.txt");		
		//Sets the file name for the Cards
		board.setCardConfigFile("./CTestFiles/cardsLegend.txt");
		// Initialize will load BOTH config files 
		board.initialize();
	}
	//Test destination selection behavior when only walkways are options. Should select at random
	@Test
	public void testRandomSelectionWalkways() {
		ComputerPlayer player = new ComputerPlayer("Test", "blue", 12, 6);
		board.calcTargets(12, 6, 1);
		assertTrue(board.getTargets().size() == 4);
		boolean loc_13_6 = false;
		boolean loc_11_6 = false;
		boolean loc_12_5 = false;
		boolean loc_12_7 = false;

		for(int i = 0;i<100;i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if(selected == board.getCellAt(13, 6)) {
				loc_13_6 = true;
			}
			else if(selected == board.getCellAt(11, 6)) {
				loc_11_6 = true;
			}
			else if(selected == board.getCellAt(12, 5)) {
				loc_12_5 = true;
			}
			else if(selected == board.getCellAt(12, 7)) {
				loc_12_7 = true;
			}
			else {
				fail("Invalid target selected");
			}
			
		}
		assertTrue(loc_13_6);
		assertTrue(loc_12_5);
		assertTrue(loc_12_7);
		assertTrue(loc_11_6);
	}
	//Test destination behavior when an unvisited room is available
	@Test
	public void testRandomSelection1Room() {
		ComputerPlayer player = new ComputerPlayer("Test", "blue", 19, 3);
		board.calcTargets(19, 3, 2);
		boolean always_18_2 = true;
		for(int i = 0;i<100;i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if(selected != board.getCellAt(18, 2)) {
				always_18_2 = false;
			}
			
		}
		assertFalse(always_18_2);
	}
	//Test destination behavior when a visited room is an option
	@Test
	public void testRandomSelectionVisited() {
		ComputerPlayer player = new ComputerPlayer("Test", "blue", 1, 14);
		player.visit('L');
		board.calcTargets(1, 14, 1);
		assertTrue(board.getTargets().size() == 4);
		boolean loc_1_13 = false;
		boolean loc_0_14 = false;
		boolean loc_2_14 = false;
		boolean loc_1_15 = false;

		for(int i = 0;i<100;i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if(selected == board.getCellAt(1, 13)) {
				loc_1_13 = true;
			}
			else if(selected == board.getCellAt(0, 14)) {
				loc_0_14 = true;
			}
			else if(selected == board.getCellAt(2, 14)) {
				loc_2_14 = true;
			}
			else if(selected == board.getCellAt(1, 15)) {
				loc_1_15 = true;
			}
			else {
				fail("Invalid target selected");
			}
			
		}
		assertTrue(loc_1_13);
		assertTrue(loc_0_14);
		assertTrue(loc_2_14);
		assertTrue(loc_1_15);
	}
	//Test correct accusation
	@Test
	public void testAccusation() {
		board.setSolution("Item 1", "Item 2", "Item 3");
		assertTrue(board.handleAccusation(new Solution("Item 1", "Item 2", "Item 3")));
	}
	//Test wrong person
	@Test
	public void testAccusationWrongPerson() {
		board.setSolution("Item 1", "Item 2", "Item 3");
		assertFalse(board.handleAccusation(new Solution("Not Item 1", "Item 2", "Item 3")));
	}
	//Test wrong room
	@Test
	public void testAccusationWrongRoom() {
		board.setSolution("Item 1", "Item 2", "Item 3");
		assertFalse(board.handleAccusation(new Solution("Item 1", "Not Item 2", "Item 3")));
	}
	//Test wrong weapon
	@Test
	public void testAccusationWrongWeapon() {
		board.setSolution("Item 1", "Item 2", "Item 3");
		assertFalse(board.handleAccusation(new Solution("Item 1", "Item 2", "Not Item 3")));
	}
	
	//Test suggestions
	//Ensure weapon  and person is chosen randomly from those not seen
	//Worth noting that the return value of makeSuggestion isn't actually used for anything except testing.
	@Test
	public void ensureRandomWeapon() {
		//Place player in room
		Player testPlayer = new ComputerPlayer("Test", "Blue", 18, 2);
		//Make a right and wrong weapon, show wrong to player.
		Card wrongWeapon = new Card("umbrella", "weapon");
		testPlayer.drawCard(wrongWeapon);
		Card correctWeapon = new Card("zweihander", "weapon");
		testPlayer.unseeCard(correctWeapon);
		testPlayer.constructFull(wrongWeapon);
		testPlayer.constructFull(correctWeapon);
		testPlayer.constructFull(new Card("me", "person"));
		for(int i = 0;i<100;i++) {
			Solution mySuggestion = testPlayer.makeSuggestion();
			boolean is_correct_weapon = mySuggestion.getWeapon().equals("zweihander");
			boolean is_correct_room = mySuggestion.getRoom().equals("Man Cave");
			boolean is_correct_person = mySuggestion.getPerson().equals("me");
			assertTrue(is_correct_weapon);
			assertTrue(is_correct_room);
			assertTrue(is_correct_person);
			
		}
	}
	



}
