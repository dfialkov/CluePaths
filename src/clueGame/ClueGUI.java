package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGUI extends JFrame{
	private static Board board;
	NotesDialog notes;
	
	public ClueGUI() throws BadConfigFormatException {
		
		// Create a JFrame with all the normal functionality
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(650, 650);	
		
		//Add Board
		board = Board.getInstance();
		board.setConfigFiles("./CTestFiles/ourBoard.csv", "./CTestFiles/ourBoardLegend.txt");
		board.setCardConfigFile("./CTestFiles/cardsLegend.txt");
		board.initialize();
		add(board,BorderLayout.CENTER);
		
		//Add South Panel
		SouthPanel south = new SouthPanel();
		add(south,BorderLayout.SOUTH);
		
		//Add East Panel
		EastPanel east = new EastPanel();
		add(east,BorderLayout.EAST);
		
		//Add Menu Bar (Exit & Show notes)
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		//Create Detective Notes 
		notes = new NotesDialog();
	
	}	
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		//Add Notes
		menu.add(createFileNotesItem());
		
		//Add Exit
		menu.add(createFileExitItem());
		
		return menu;
	}
	
	private JMenuItem createFileNotesItem() {
		JMenuItem item = new JMenuItem("Notes");
		
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		
		return item;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	public static void main(String[] args) throws BadConfigFormatException {
		// Create the GUI
		ClueGUI gui = new ClueGUI();
		// Now let's view it
		gui.setVisible(true);
	}
}
