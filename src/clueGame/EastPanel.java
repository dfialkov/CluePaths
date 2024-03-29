package clueGame;


//It's not immediately clear from the logs but this is part of Darian's contribution to C22A. Feel free to check the last commit to verify that this code was here before I started working on it, if you want.
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class EastPanel extends JPanel{
	private ArrayList<Card> hand;
	Board board;
	
	public EastPanel() {
		
		board = Board.getInstance();
		hand = board.getPlayers().get(0).getCards();
		
		// Create a layout with 3 rows 1 column 
		setLayout(new GridLayout(3,1));
		
		//First Row (people)
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(1, 1));
		people.add(createPeoplePanel());
		add(people);
		
		//Second Row (rooms)
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(1, 1));
		rooms.add(createRoomsPanel());
		add(rooms);
		
		//Third Row (weapons)
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(1, 1));
		weapons.add(createWeaponsPanel());
		add(weapons);
		
		//Cards Title
		setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		
	}


	private Component createPeoplePanel() {
		JPanel panel = new JPanel();
	 	
		panel.setLayout(new GridLayout(0,1));
	 	
		for(Card card: hand) {
			if(card.getType() == CardType.PERSON) {
				JTextField cardField = new JTextField(card.getCardName());
				cardField.setEditable(false);
				panel.add(cardField);
			}
		}
		
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		return panel;
	}

	private Component createRoomsPanel() {
		JPanel panel = new JPanel();
	 	
		panel.setLayout(new GridLayout(0,1));
	 	
		for(Card card: hand) {
			if(card.getType() == CardType.ROOM) {
				JTextField cardField = new JTextField(card.getCardName());
				cardField.setEditable(false);
				panel.add(cardField);
			}
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		return panel;
	}

	private Component createWeaponsPanel() {
		JPanel panel = new JPanel();
	 	
		panel.setLayout(new GridLayout(0,1));
	 	
		for(Card card: hand) {
			if(card.getType() == CardType.WEAPON) {
				JTextField cardField = new JTextField(card.getCardName());
				cardField.setEditable(false);
				panel.add(cardField);
			}
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		return panel;
	}
}
