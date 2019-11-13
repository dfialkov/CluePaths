//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

public class Card {
	private String cardName;
	CardType type;
	
	//Constructor for loading in rooms 
	public Card(String name) {
		cardName = name;
		type = CardType.ROOM;
	}
	
	//Constructor for loading in Weapons/People
	public Card(String name, String cardType) {
		cardName = name;
		
		switch(cardType) {
		case "weapon":
			type = CardType.WEAPON;
			break;
		case "person":
			type = CardType.PERSON;
			break;
		case "room":
			type = CardType.ROOM;
			break;
		}
	}

	public Card(String name, CardType cardType) {
		cardName = name;
		type = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}


	
	
}
