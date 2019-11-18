package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SouthPanel extends JPanel {
	private JTextField currPlayer;
	private JTextField die;
	public SouthPanel()
	{
		// Create a layout with 2 rows 3 columns
		setLayout(new GridLayout(2,3));
		
		JPanel topRow = new JPanel();
		topRow.setLayout(new GridLayout(1, 2));
		topRow.add(createWhoseTurnPanel());
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		buttons.add(createNextPlayerPanel());
		buttons.add(createAccusationPanel());
		topRow.add(buttons);
		add(topRow);
		
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new GridLayout(1, 3));
		bottomRow.add(createRollPanel());
		bottomRow.add(createGuessPanel());
		bottomRow.add(createGuessResultPanel());
		add(bottomRow);
		
		
	}	
	
	
	
	private JPanel createWhoseTurnPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(2, 1));
		 	JLabel nameLabel = new JLabel("Whose Turn?");
			JTextField name = new JTextField();
			name.setEditable(false);
			currPlayer = name;
			panel.add(nameLabel);
			panel.add(name);
//			panel.setBorder(new TitledBorder (new EtchedBorder(), "Who are you?"));
			return panel;
	}
	private JPanel createNextPlayerPanel() {

		JPanel panel = new JPanel();
		JButton nextPlayerButton = new JButton("Next Player");
		nextPlayerButton.addActionListener(new NextPlayerListener());
		panel.add(nextPlayerButton, BorderLayout.CENTER);
		return panel;
	
	}
	private class NextPlayerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Board.getInstance().nextPlayer();
			
		}
		
	}
	private JPanel createAccusationPanel(){
		JPanel panel = new JPanel();
		JButton accusation = new JButton("Make an accusation");
		panel.add(accusation, BorderLayout.CENTER);
		return panel;
	}

	 private JPanel createRollPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel rollLabel = new JLabel("Roll");
			die = new JTextField(2);
			die.setEditable(false);
			panel.add(rollLabel);
			panel.add(die);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
			return panel;
	}
	 private JPanel createGuessPanel() {
		 JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel nameLabel = new JLabel("Guess");
			JTextField name = new JTextField(2);
			panel.add(nameLabel);
			panel.add(name);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
			return panel;
		 
	 }
	 private JPanel createGuessResultPanel() {
		 JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
		 	JLabel nameLabel = new JLabel("Guess Result");
			JTextField name = new JTextField(2);
			panel.add(nameLabel);
			panel.add(name);
			name.setEditable(false);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Response"));
			return panel;
		 
	}
	 
	public JTextField getDieBox() {
		return die;
	}
	public JTextField getNameBox() {
		return currPlayer;
	}
	//Not main
	/* 
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(1000, 150);	
		// Create the JPanel and add it to the JFrame
		SouthPanel gui = new SouthGUI();
		frame.add(gui, BorderLayout.CENTER);
		
		// Now let's view it
		frame.setVisible(true);
	}
	*/

}
