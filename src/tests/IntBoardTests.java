package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.*;

public class IntBoardTests {

	IntBoard board;
	@Before
    public void beforeAll() {
       board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
    }	
	
	/*
	 * Test adjacencies for all corners
	 */
	@Test
	public void testAdjacency00()
	{
		//All adjacency tests are structured as follows:
		//get cell
		BoardCell cell = board.getCell(0,0);
		//calculate adjacents
		Set<BoardCell> testList = board.getAdjList(cell);
		//test exact results
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		//test for no extra results
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency33()
	{
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency13()
	{
		BoardCell cell = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	@Test
	public void testAdjacency30()
	{
		BoardCell cell = board.getCell(3,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency11()
	{

		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(4, testList.size());
	}
	//testpush
	@Test
	public void testAdjacency22()
	{
		BoardCell cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testTargets00_1()
	{
		//All target tests are structured as follows:
		//get cell
		BoardCell cell = board.getCell(0, 0);
		//calculate results
		board.calcTargets(cell, 1);
		//fetch results
		Set targets = board.getTargets();
		//check for no extra results
		assertEquals(2, targets.size());
		//check for correct results
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		
	}
	@Test
	public void testTargets33_2()
	{
		BoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));

	}
	@Test
	public void testTargets13_3()
	{
		BoardCell cell = board.getCell(1, 3);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	@Test
	public void testTargets30_4()
	{
		BoardCell cell = board.getCell(3, 0);
		board.calcTargets(cell, 4);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	@SuppressWarnings("rawtypes")
	@Test
	public void testTargets11_5()
	{
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 5);
		Set targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(3, 2)));

	}
	@Test
	public void testTargets22_1()
	{
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		
	}

}
