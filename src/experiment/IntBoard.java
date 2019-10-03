package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;


	public IntBoard() {
		//TODO: implement

	}
	
	public Map<BoardCell, Set<BoardCell>> calcAdjacencies(int row, int col) {
		//TODO: implement
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		//TODO: implement
	}

	public Set<BoardCell> getTargets(){
		//TODO: implement
		return null;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		// TODO Auto-generated method stub
		return null;
	}
	public BoardCell getCell(int toRow, int toCol) {
		// TODO Auto-generated method stub
		return null;
	}

}
