package clueGame;



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

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getInitial() {
		// TODO Auto-generated method stub
		return null;
	}
}
