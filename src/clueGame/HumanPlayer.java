//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, String color, String row, String col) {
		super(playerName);
		this.row = Integer.valueOf(row);
		this.col = Integer.valueOf(col);
		
		cards = new ArrayList<Card>();
		convertColor(color);
	}

}
