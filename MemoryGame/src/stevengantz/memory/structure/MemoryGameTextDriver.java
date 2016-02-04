package stevengantz.memory.structure;

import java.util.ArrayList;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.card.TextCardFace;
import stevengantz.memory.cli.MemoryGameBoardWriter;
import stevengantz.memory.spiegel.SpiegelCode;

/**
 * @author: Steven Gantz
 * @date: 2/3/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This class is tightly coupled with the text based Memory Game
 *       implementation. This class implements the actual game simulation and
 *       has simple calls that display the contents of the game to the console
 *       after each call.
 **/
public class MemoryGameTextDriver {

	/**
	 * Internal representation of the game that will be driven by this class to
	 * play the game in a terminal or command line environment.
	 */
	private MemoryGameBoard board;

	/**
	 * Display values for the game's output
	 */
	int row, column;

	/**
	 * Set when the board game has begun, raises exception if methods are called
	 * out of order.
	 */
	public boolean gameStarted;

	/**
	 * General constructor doesn't do anything useful except say that the game
	 * has not started yet and instantiate a writer object.
	 */
	public MemoryGameTextDriver(int row, int column) {
		this.gameStarted = false;
		this.row = row;
		this.column = column;
	}

	/**
	 * Build the board and initialize the game board.
	 */
	public void beginGame() {
		buildBoard();
		this.gameStarted = true;
	}

	/**
	 * Play a turn in the command line interface.
	 * 
	 * @throws Exception
	 */
	public void playTurn() {
		if (!gameStarted)
			throw new RuntimeException();
		try{
			// Draw the board and ask the user for input
			MemoryGameBoardWriter.drawBoard(this.board, this.row, this.column);
			MemoryCard card1 = getUserChoice();
			if (this.isCardPaired(card1)) {
				this.printCardIsPaired();
			}
			else{
				// Flip the card and draw the board
				card1.flip();
			}
	
			MemoryGameBoardWriter.drawBoard(this.board, this.row, this.column);
	
			// Ask the user for their second choice, flip, and draw the board
			MemoryCard card2 = getUserChoice();
			if (this.isCardPaired(card2)) {
				this.printCardIsPaired();
				return;
			}
			// If it is the same card, don't flip it back
			else if(card2 == card1){
				System.out.println("You just selected the same card...");
				return;
			}
			else{
				// Flip the card and draw the board
				card2.flip();
			}
			
			MemoryGameBoardWriter.drawBoard(this.board, this.row, this.column);
	
			// If cards are a match, print success message and lock as pair linked
			if (card1.equals(card2) && card1 != card2) {
				this.printMatch();
				card1.lockInPair(card2);
				card2.lockInPair(card1);
			}
			// If cards arent match, print failed message and flip back
			else {
				this.printNoMatch();
				card1.flip();
				card2.flip();
			}
			
			// Check for win condition
			// all cards have to have the "paired" attribute set
			if(this.board.checkIfBoardIsAllPairs()){
				System.out.println("You won!");
				System.exit(0);
			}
		} catch(Exception e){
			System.out.println("You caused the program to get angry.");
			System.out.println("Please never do what you just did, EVER again.");
			System.out.println("Since you might be Dr. Spiegel testing this code,");
			System.out.println("I guess I'll just start another turn for you.\n");

		}
	}

	// Internal helper methods ----------------------------
	
	

	private boolean isCardPaired(MemoryCard mem) {
		// Card is not paired
		if (mem.getPairedCard() == null) {
			return false;
		} else {
			return true;
		}
	}

	private void printCardIsPaired() {
		System.out.println("Card is already paired! Try Again.");
	}

	private void printNoMatch() {
		System.out.println("No Match! Try Again.");
	}

	private void printMatch() {
		System.out.println("Successful match! Pair Found.");
	}

	/**
	 * Retrieve the card choice from the user
	 * 
	 * @return the actual card chosen by the user
	 */
	private MemoryCard getUserChoice() throws Exception{
		// Display question to user
		System.out.print("Select a card: ");

		// The 0th card is represented as the 30th
		int choice = SpiegelCode.getIntFromUser();

		// Sneakily check "choice" for the cheat code
		// The game acts like it was never entered as a choice
		// ie. still on your second or first card
		if (choice == -1) {
			try {
				engageSneakyCheat();
				// Display question to user
				System.out.print("Select a card: ");
				choice = SpiegelCode.getIntFromUser();
				while(choice == -1){
					System.out.println("Really? Twice? Nah");
					System.out.print("Select a card: ");
					choice = SpiegelCode.getIntFromUser();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Handle representing 0 as 30
		if (choice == 30)
			choice = 0;

		if(choice > this.board.totalCards() || choice < 0){
			System.out.println("That isn't a valid option");
			System.out.println("Try Again");
			while(choice > this.board.totalCards() || choice < 1){
				System.out.print("Select a card: ");
				choice = SpiegelCode.getIntFromUser();
			}
		}
		return board.getCard(choice);
	}

	private void engageSneakyCheat() throws Exception {
		System.out.println("That's a weird input...");
		MemoryGameBoardWriter.drawCheatBoard(this.board, this.row, this.column);
		System.out.println("Not sure what happened there,");
		System.out.println("lets just get back to the game...");
		MemoryGameBoardWriter.drawBoard(this.board, this.row, this.column);
	}

	/**
	 * Populate the game board
	 */
	private void buildBoard() {
		this.board = new MemoryGameBoard(this.buildCardList());
		this.board.randomizeBoard();
	}

	/**
	 * Build the list of cards for the text game
	 * 
	 * @return the list of possible cards
	 */
	private ArrayList<MemoryCard> buildCardList() {
		ArrayList<MemoryCard> cardList = new ArrayList<MemoryCard>();
		cardList.add(
				new MemoryCard(new TextCardFace("AA"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("BB"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("CC"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("DD"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("EE"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("FF"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("GG"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("HH"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("II"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("JJ"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("KK"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("LL"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("MM"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("NN"), new TextCardFace("--")));
		cardList.add(
				new MemoryCard(new TextCardFace("OO"), new TextCardFace("--")));

		return cardList;
	}

}
