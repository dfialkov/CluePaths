//Authors: Daniel Fialkov and Darian Dickerson
package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class AdjTargetTests {
	
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;

	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		
		// set the file names to use my config files
		board.setConfigFiles("./CTestFiles/ourBoard.csv", "./CTestFiles/ourBoardLegend.txt");
		//board.setConfigFiles("ourBoard.csv", "ourBoardLegend.txt");
		
		//Sets the file name for the Cards
		board.setCardConfigFile("./CTestFiles/cardsLegend.txt");
		//board.setCardConfigFile("cardsLegend.txt");
		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	// Ensure that player does not move around within room
	// These cells are YELLOW on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(4, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(17, 3);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(1, 10);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(10, 15);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(3, 16);
		assertEquals(0, testList.size());
	}
	
	// Ensure that the adjacency list from a doorway is only the
	// walkway.  
	// These tests are PINK on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(9, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 6)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(15, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 10)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(11, 15);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 15)));
		//TEST DOORWAY UP
		testList = board.getAdjList(15, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 17)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(9, 6);
		assertTrue(testList.contains(board.getCellAt(9, 7)));
		assertTrue(testList.contains(board.getCellAt(8, 6)));
		assertTrue(testList.contains(board.getCellAt(10, 6)));
		assertTrue(testList.contains(board.getCellAt(9, 5)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(12, 15);
		assertTrue(testList.contains(board.getCellAt(12, 16)));
		assertTrue(testList.contains(board.getCellAt(12, 14)));
		assertTrue(testList.contains(board.getCellAt(13, 15)));
		assertTrue(testList.contains(board.getCellAt(11, 15)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(15, 10);
		assertTrue(testList.contains(board.getCellAt(15, 11)));
		assertTrue(testList.contains(board.getCellAt(15, 9)));
		assertTrue(testList.contains(board.getCellAt(14, 10)));
		assertTrue(testList.contains(board.getCellAt(16, 10)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(14,17);
		assertTrue(testList.contains(board.getCellAt(13, 17)));
		assertTrue(testList.contains(board.getCellAt(15, 17)));
		assertTrue(testList.contains(board.getCellAt(14, 16)));
		assertEquals(3, testList.size());
	}
	
	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, two walkway pieces
		Set<BoardCell> testList = board.getAdjList(0, 14);
		assertTrue(testList.contains(board.getCellAt(0, 15)));
		assertTrue(testList.contains(board.getCellAt(1, 14)));
		assertEquals(2, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(13, 0);
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		assertTrue(testList.contains(board.getCellAt(13, 1)));
		assertTrue(testList.contains(board.getCellAt(14, 0)));
		assertEquals(3, testList.size());
		
		// Test on bottom edge of board, 1 walkway piece
		testList = board.getAdjList(19, 15);
		assertTrue(testList.contains(board.getCellAt(18,15)));
		assertEquals(1, testList.size());
		
		// Test on right edge of board, 2 walkway pieces
		testList = board.getAdjList(7, 19);
		assertTrue(testList.contains(board.getCellAt(8, 19)));
		assertTrue(testList.contains(board.getCellAt(7, 18)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(4,9);
		assertTrue(testList.contains(board.getCellAt(4, 8)));
		assertTrue(testList.contains(board.getCellAt(4, 10)));
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		assertTrue(testList.contains(board.getCellAt(3, 9)));
		assertEquals(4, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(14, 8);
		assertTrue(testList.contains(board.getCellAt(13, 8)));
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		assertTrue(testList.contains(board.getCellAt(14, 9)));
		assertEquals(3, testList.size());
	}
	
	// Test getting into a room
	// These are GRAY on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// Two rooms exactly 2 away
		board.calcTargets(1, 4, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		// Wine Cellar Doorway
		assertTrue(targets.contains(board.getCellAt(0, 3)));
		// Lounge Doorway
		assertTrue(targets.contains(board.getCellAt(1, 6)));
		// Walkway spaces
		assertTrue(targets.contains(board.getCellAt(0, 5)));
		assertTrue(targets.contains(board.getCellAt(2, 5)));
		assertTrue(targets.contains(board.getCellAt(3, 4)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are GRAY on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{	
		//Three Steps
		board.calcTargets(18, 4, 3);
		Set<BoardCell> targets= board.getTargets();
		System.out.println(targets.size());
		assertEquals(4, targets.size());
		//2 Steps into room
		assertTrue(targets.contains(board.getCellAt(18, 2)));
		//Walkway Paths
		assertTrue(targets.contains(board.getCellAt(18, 3)));
		assertTrue(targets.contains(board.getCellAt(19, 4)));
		assertTrue(targets.contains(board.getCellAt(15, 4)));		
		
	}
	
	// Test getting out of a room
	// These are GRAY on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(4, 18, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 17)));
		// Take two steps
		board.calcTargets(4, 18, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 16)));
		assertTrue(targets.contains(board.getCellAt(5, 17)));
	}
	
	
	// Tests of just walkways, 1 step
	// These are GRAY on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(11, 12, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 12)));
		assertTrue(targets.contains(board.getCellAt(12, 12)));
		assertTrue(targets.contains(board.getCellAt(11, 13)));
					
	}
	
	// Tests of just walkways, 2 steps
	// These are GRAY on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(5, 10, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 10)));
		assertTrue(targets.contains(board.getCellAt(4, 11)));
		assertTrue(targets.contains(board.getCellAt(5, 12)));
		assertTrue(targets.contains(board.getCellAt(6, 11)));
		assertTrue(targets.contains(board.getCellAt(7, 10)));
		assertTrue(targets.contains(board.getCellAt(6, 9)));
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(4, 9)));
	}
	
	// Tests of just walkways, 4 steps
	// These are GRAY on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(19, 9, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 10)));
		assertTrue(targets.contains(board.getCellAt(17, 9)));
		assertTrue(targets.contains(board.getCellAt(16, 10)));
		assertTrue(targets.contains(board.getCellAt(15, 9)));	
	}	
	
	// Tests of just walkways, 3 steps
	// These are GRAY on the planning spreadsheet
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(6, 0, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 0)));
		assertTrue(targets.contains(board.getCellAt(6, 1)));	
		assertTrue(targets.contains(board.getCellAt(5, 2)));
		assertTrue(targets.contains(board.getCellAt(6, 3)));
	}	
}
