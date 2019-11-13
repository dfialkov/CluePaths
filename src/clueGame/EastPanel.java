package clueGame;

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
				panel.add(new JTextField(card.getCardName()));
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
				panel.add(new JTextField(card.getCardName()));
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
				panel.add(new JTextField(card.getCardName()));
			}
		}
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		return panel;
	}
}
