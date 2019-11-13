//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String playerName, String color, int row, int col) {
		super(playerName);
		this.row = Integer.valueOf(row);
		this.col = Integer.valueOf(col);
		
		hand = new ArrayList<Card>();
		convertColor(color);
	}
	@Override
	public String getHumanName() {
		return this.getName();
	}
}
