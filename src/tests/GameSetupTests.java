package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

public class GameSetupTests {

	// We make the Board static because we can load it one time and
	// then do all the tests.
	private static Board board;

	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("./CTestFiles/ourBoard.csv", "./CTestFiles/ourBoardLegend.txt");
		// Sets the file name for the Cards
		board.setCardConfigFile("./CTestFiles/cardsLegend.txt");
		// Initialize will load BOTH config files
		// board.initialize();
		board.loadRoomConfig();
		board.loadBoardConfig();
		board.loadCardConfig();
	}

	// Create Room Cards
	@Test
	public void roomCardsTest() {

		ArrayList<Card> testDeck = board.getDeck();

		// Number of rooms
		// TODO change after implementing other cards
		// Cards - #Weapons - #People = #Rooms
		assertEquals(9, testDeck.size() - 15);

		// Test if specific room names are found
		// First
		assertEquals("Bedroom", testDeck.get(0).getCardName());
		// Last
		assertEquals("Library", testDeck.get(8).getCardName());
		// Extra
		assertEquals("Wine Cellar", testDeck.get(1).getCardName());
		assertEquals("Man Cave", testDeck.get(6).getCardName());
		assertEquals("Kitchen", testDeck.get(4).getCardName());

		// Test Card Types of rooms
		// First
		assertEquals(CardType.ROOM, testDeck.get(0).getType());
		// Last
		assertEquals(CardType.ROOM, testDeck.get(8).getType());
		// Extra in between
		assertEquals(CardType.ROOM, testDeck.get(2).getType());
		assertEquals(CardType.ROOM, testDeck.get(4).getType());
		assertEquals(CardType.ROOM, testDeck.get(7).getType());

	}

	// Tests For the Weapons/People Cards
	@Test
	public void cardsTest() {

		ArrayList<Card> testDeck = board.getDeck();

		// Number of Cards total (including rooms)
		assertEquals(24, testDeck.size());

		// Test if specific room names are found
		// First after rooms
		assertEquals("Lightsaber", testDeck.get(9).getCardName());
		// Last
		assertEquals("Chandler", testDeck.get(23).getCardName());
		// Cards in between
		assertEquals("Kindness", testDeck.get(13).getCardName());
		assertEquals("Pillow", testDeck.get(11).getCardName());
		assertEquals("Pheobe", testDeck.get(20).getCardName());
		assertEquals("Joey", testDeck.get(22).getCardName());

		// Test Card Types of rooms
		// First after rooms
		assertEquals(CardType.WEAPON, testDeck.get(9).getType());
		// Last
		assertEquals(CardType.PERSON, testDeck.get(23).getType());
		// Cards in between
		assertEquals(CardType.WEAPON, testDeck.get(13).getType());
		assertEquals(CardType.WEAPON, testDeck.get(15).getType());
		assertEquals(CardType.PERSON, testDeck.get(21).getType());
		assertEquals(CardType.PERSON, testDeck.get(19).getType());

	}

	// Tests Solution Randomness
	// TODO: Next time, not needed for this part
	// @Test
	public void soltuionTest() {

		// Create 4 separate possible Solutions
		Solution sol1 = new Solution();
		Solution sol2 = new Solution();
		Solution sol3 = new Solution();
		Solution sol4 = new Solution();

		// Combine their three values into a single string combination
		String answer1 = sol1.getPerson() + sol1.getRoom() + sol1.getWeapon();
		String answer2 = sol2.getPerson() + sol2.getRoom() + sol2.getWeapon();
		String answer3 = sol3.getPerson() + sol3.getRoom() + sol3.getWeapon();
		String answer4 = sol4.getPerson() + sol4.getRoom() + sol4.getWeapon();

		// Add the strings to array
		ArrayList<String> sols = new ArrayList<String>();
		sols.add(answer1);
		sols.add(answer2);
		sols.add(answer3);
		sols.add(answer4);

		// Test all combinations against each other
		// Can fail if randomly picks the same sequence but not as likely
		for (int i = 0; i < 4; i++) {
			for (int j = i + 1; j < 4; j++) {
				assertNotSame(sols.get(i), sols.get(j));
			}
		}

	}

	// Tests Player attributes
	@Test
	public void playersTest() {
		// Create players
		board.createPlayers();

		ArrayList<Player> testPlayers = board.getPlayers();

		// Test Number of Players
		assertEquals(6, testPlayers.size());

		// Test Player Names/Positions correlate & are registered to a player
		for (int i = 0; i < 6; i++) {
			switch (testPlayers.get(i).getName()) {
			case "Ross":
				// row
				assertEquals(6, testPlayers.get(i).getRow());
				// col
				assertEquals(0, testPlayers.get(i).getCol());
				break;
			case "Pheobe":
				// row
				assertEquals(19, testPlayers.get(i).getRow());
				// col
				assertEquals(15, testPlayers.get(i).getCol());
				break;
			case "Joey":
				// row
				assertEquals(19, testPlayers.get(i).getRow());
				// col
				assertEquals(9, testPlayers.get(i).getCol());
				break;
			case "Rachel":
				// row
				assertEquals(13, testPlayers.get(i).getRow());
				// col
				assertEquals(0, testPlayers.get(i).getCol());
				break;
			case "Chandler":
				// row
				assertEquals(0, testPlayers.get(i).getRow());
				// col
				assertEquals(15, testPlayers.get(i).getCol());
				break;
			case "Monica":
				// row
				assertEquals(9, testPlayers.get(i).getRow());
				// col
				assertEquals(19, testPlayers.get(i).getCol());
				break;
			default:
				break;
			}
		}

		// Deal Cards
		board.deal();

		// Test number of cards in hand
		// All players should have 4
		for (int i = 0; i < 6; i++) {
			assertEquals(4, testPlayers.get(i).getCards().size());
		}

		// Test all cards dealt
		// No cards left in deck
		assertEquals(0, board.getDeck().size());

		// Test no duplicate cards
		// Test the Player's card against all the cards in the computers' hands
		for (int k = 0; k < 4; k++) {
			for (int i = 1; i < 6; i++) {
				for (int j = 0; j < 4; j++) {
					// Player Card vs Computer Card
					assertNotSame(testPlayers.get(0).getCards().get(k).getCardName(),
							testPlayers.get(i).getCards().get(j).getCardName());
				}
			}
		}

	}
}
