//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;



public class ComputerPlayer extends Player {
	char lastVisited;
	
	public ComputerPlayer(String playerName, String color, int row, int col) {
		super(playerName);
		this.row = row;
		this.col = col;
		
		cards = new ArrayList<Card>();
		convertColor(color);
	}
	


public BoardCell pickLocation(Set<BoardCell> targets) {


	return null;
	
}
public void visit(char roomInitial) {
	this.lastVisited = roomInitial;
}

}
