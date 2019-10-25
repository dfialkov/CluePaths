//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;



public class BoardCell {
	private int row;
	private int col;
	private String initial;
	
	public BoardCell(int newRow, int newCol, String newInitial) {
		row = newRow;
		col = newCol;
		initial = newInitial;
	}

	public boolean isWalkway() {
			return initial.charAt(0) == 'W';

	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

	public boolean isDoorway() {
		if(initial.length() == 2 && initial.charAt(1) != 'N') {
			return true;
		}
		return false;
	}

	public DoorDirection getDoorDirection() {
		DoorDirection direction = DoorDirection.NONE;
		if(initial.length() < 2) {
			return direction;
		}
		
		char dir = initial.charAt(1);
		
		switch(dir) {
			case 'R':
				direction = DoorDirection.RIGHT;
				break;
			case 'L':
				direction = DoorDirection.LEFT;
				break;
			case 'U':
				direction = DoorDirection.UP;
				break;
			case 'D':
				direction = DoorDirection.DOWN;
				break;
			default:
				break;
		}
		
		return direction;
	}

	public char getInitial() {
		return initial.charAt(0);
	}
}
