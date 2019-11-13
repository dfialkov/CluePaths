package clueGame;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class NotesDialog extends JDialog{
	private String type; 
	private ArrayList<Card> deck; 
	Board board;
	
	public NotesDialog() {
		
		board = Board.getInstance();
		deck = board.getCopy();
		
		//Set up window
		setTitle("Detective Notes");
		setSize(500, 500);
		
		// Create a layout with 3 rows 2 columns
		setLayout(new GridLayout(3,2));
		
		for(int i=1; i<=3; i++) {
			//Create row layout
			JPanel row = new JPanel();
			row.setLayout(new GridLayout(1, 2));
			
			switch(i) {
			case 1:
				type = "People";
				break;
			case 2:
				type = "Rooms";
				break;
			case 3:
				type = "Weapons";
				break;
			}
			
			row.add(createSeenPanel(type));
			row.add(createGuessPanel(type));
			add(row);
		}
	}

	private Component createGuessPanel(String type) {
		CardType cardType = null;
		JPanel panel = new JPanel();
	 	
		panel.setLayout(new GridLayout(1,1));
		JComboBox<String> combo = new JComboBox<String>();
		
		switch(type) {
			case "People":
				cardType = CardType.PERSON;
				break;
			case "Rooms":
				cardType = CardType.ROOM;
				break;
			case "Weapons":
				cardType = CardType.WEAPON;
				break;
		}
		
		combo.addItem("Unsure");
		for(Card card : deck) {
			if(card.getType() == cardType) {
				combo.addItem(card.getCardName());
			}
		}
		
		panel.add(combo);
		panel.setBorder(new TitledBorder (new EtchedBorder(), type + " Guess"));
		
		return panel;
	}

	private Component createSeenPanel(String type) {
		CardType cardType = null;
		JPanel panel = new JPanel();
	 	int num = 0;
		
		switch(type) {
			case "People":
				cardType = CardType.PERSON;
				break;
			case "Rooms":
				cardType = CardType.ROOM;
				break;
			case "Weapons":
				cardType = CardType.WEAPON;
				break;
		}
		
		for(Card card : deck) {
			if(card.getType() == cardType) {
				num++;
			}
		}
		
		panel.setLayout(new GridLayout(0,2));
		
		for(Card card : deck) {
			if(card.getType() == cardType) {
				panel.add(new JCheckBox(card.getCardName()));
			}
		}
		
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), type + " Seen"));
		
		return panel;
	}
	
}
