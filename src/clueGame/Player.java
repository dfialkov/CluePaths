//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections; 

public abstract class Player {

	private String playerName;
	protected int row;
	protected int col;
	Color color;
	//Never modify this. I'd make it final but it needs to be procedurally filled.
	private ArrayList<Card> allCards = new ArrayList<Card>();
	private ArrayList<Card> seenCards = new ArrayList<Card>();
	private ArrayList<Card> unseenCards = new ArrayList<Card>();
	ArrayList<Card> hand;
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
		hand.add(card);
		seeCard(card);
	}
	
	public Solution makeSuggestion() {
return null;
	
	}
	//Gets the unseen cards by taking a list with the entire deck and removing the ones that have been seen. Do this before doing anything that requires decisions based on cards.
	public void calcUnseen() {
		for(Card toUnsee : allCards) {
			unseenCards.add(toUnsee);
		}
		for(int i = 0;i<unseenCards.size();i++) {
			Card testCard = unseenCards.get(i);
			for(Card seenCard : seenCards) {
				if(testCard == seenCard) {
					unseenCards.remove(i);
				}
			}
		}
	}
	
	//Setter/Getters 
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
		return hand;
	}
	//The actual contents of unseenCards are constructed elsewhere
	public void unseeCard(Card toUnsee) {
		unseenCards.add(toUnsee);
	}
	//Add to seen cards, happens when a card is added to hand or revealed
	public void seeCard(Card toSee) {
		seenCards.add(toSee);
	}
	//Add to the ideal list of cards that's not to be modified
	public void constructFull(Card toAdd) {
		allCards.add(toAdd);
	}
	
	
	
	
}
