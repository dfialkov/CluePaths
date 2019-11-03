//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList; 

public abstract class Player {
	private String playerName;
	protected int row;
	protected int col;
	Color color;
	ArrayList<Card> cards;
	public Player(String playerName) {
		this.playerName = playerName;
	}
	
	public Color convertColor(String strColor) {
		 Color color;
		 try {
		 // We can use reflection to convert the string to a color
		 Field field = Class.forName("java.awt.Color").getField(strColor.trim());
		 color = (Color)field.get(null);
		 } catch (Exception e) {
		 color = null; // Not defined
		 }
		 return color;
		}
	
	
	public void drawCard(Card card) {
		cards.add(card);
	}
	
	//Setter/Getters for Row/Col position
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getName() {
		return playerName;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
	
	
}
