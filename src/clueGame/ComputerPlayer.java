//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;



public class ComputerPlayer extends Player {
	char lastVisited;

	
	public ComputerPlayer(String playerName, String color, int row, int col) {
		super(playerName);
		this.row = row;
		this.col = col;
		
		hand = new ArrayList<Card>();
		convertColor(color);
	}
	


public BoardCell pickLocation(Set<BoardCell> targets) {
	Random rand = new Random();
	int item = rand.nextInt(targets.size());
	int i = 0;
	for(BoardCell currCell : targets) {
		//Scan for unvisited doors before making random selection
		if(currCell.isDoorway() && currCell.getInitial() != lastVisited) {
			this.row = currCell.getRow();
			this.col = currCell.getCol();
			return currCell;
		}
	}
		for(BoardCell currCell : targets) {
		//Treat all others equally
		if(i == item) {
			this.row = currCell.getRow();
			this.col = currCell.getCol();
			return currCell;
		}
		i++;
	}
		
	return null;
}
public void visit(char roomInitial) {
	this.lastVisited = roomInitial;
}

public void accuse() {
	//TODO: Create code for the program to determine what to use when accusing.
}

}
