package tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntBoardTests {

	@Before
    public void beforeAll() {
       board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
    }	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
