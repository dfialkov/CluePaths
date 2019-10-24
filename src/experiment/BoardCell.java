//Authors: Daniel Fialkov and Darian Dickerson
package experiment;

public class BoardCell {
	private int row;
	private int col;
	
	public BoardCell(int newRow, int newCol) {
		row = newRow;
		col = newCol;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
