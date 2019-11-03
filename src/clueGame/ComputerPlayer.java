//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

import java.util.ArrayList;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String playerName, String color, String row, String col) {
		super(playerName);
		this.row = Integer.valueOf(row);
		this.col = Integer.valueOf(col);
		
		cards = new ArrayList<Card>();
		convertColor(color);
	}

}
