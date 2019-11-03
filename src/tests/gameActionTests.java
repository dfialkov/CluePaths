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
		ComputerPlayer player = new ComputerPlayer("Test", "blue", 18, 3);
		board.calcTargets(19, 3, 2);
		for(int i = 0;i<100;i++) {
			assertEquals(player.pickLocation(board.getTargets()), board.getCellAt(18, 2));
		}
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



}
