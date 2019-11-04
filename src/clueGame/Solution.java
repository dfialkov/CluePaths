package clueGame;

import java.util.ArrayList;
import java.util.Random;

public class Solution {
	private static Board board;
	ArrayList<Card> deck;
	Random num;
	private String person = "null";
	private String room = "null";
	private String weapon = "null";
	
	//Select Solution Randomly
	public Solution() {
		super();
		board = Board.getInstance();
		deck = board.getDeck();
		num = new Random();
		
		//Chooses Random card until weapon/person/room are filled 
		while(person.contentEquals("null") || room.contentEquals("null") || weapon.contentEquals("null")) {
			
			//Random index between 0 & size of the deck
			int randNum = num.nextInt(deck.size());
			
			//Places card at that index into the solution
			switch(deck.get(randNum).getType()) {
				case WEAPON:
					if(weapon == "null") {
						weapon = deck.get(randNum).getCardName();
					}
					break;
				case PERSON:
					if(person == "null") {
						person = deck.get(randNum).getCardName();
					}
					break;
				case ROOM:
					if(room == "null") {
						room = deck.get(randNum).getCardName();
					}
					break;
				default:
					break;
			}
		}
	}
	//This is for accusations/suggestions
		public Solution(String testPerson, String testRoom, String testWeapon) {
			this.person = testPerson;
			this.room =testRoom;
			this.weapon = testWeapon;
		}
	//
	@Override
	public boolean equals(Object S) {
		if(((Solution) S).getPerson().equals(this.getPerson()) && ((Solution) S).getRoom().equals(this.getRoom()) &&  ((Solution) S).getWeapon().equals(this.getWeapon())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getPerson() {
		return person;
	}

	public String getRoom() {
		return room;
	}

	public String getWeapon() {
		return weapon;
	}
	
	
	
	

	
	
}
