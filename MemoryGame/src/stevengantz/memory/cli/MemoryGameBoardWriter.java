package stevengantz.memory.cli;

import java.util.ArrayList;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.structure.MemoryGameBoard;

/**
 * @author: Steven Gantz
 * @date: 2/2/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This file is a utility class that writes the structure of the game board
 *       that is passed into the object.
 **/
public class MemoryGameBoardWriter {

	/**
	 * This method loads the board into a string with the specified rows and
	 * columns. An exception is raised if row times column is not equal to the
	 * total number of the cards.
	 * 
	 * @param board
	 *            MemoryGameBoard object to be drawn
	 * @param row
	 *            Number of rows to draw
	 * @param column
	 *            Number of columns to draw
	 * @throws Exception
	 *             Raise runtime exception
	 */
	public static void drawBoard(MemoryGameBoard board, int row, int column)
			throws Exception {
		if ((row * column) != board.totalCards())
			throw new RuntimeException();

		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < board.totalCards(); i++) {
			// if the card is face down, put a number for selection
			if (board.getCard(i).isFaceUp())
				builder.append("[" + board.getCard(i).toString() + "]");
			else if (i < 10)
				builder.append("[0" + i + "]");
			else
				builder.append("[" + i + "]");

			// Make a new row every column number of cards
			if (i % column == 0)
				builder.append("\n");
		}

		// Due to 0 as an edge case, we add board.getCard(0) at the end
		if (board.getCard(0).isFaceUp())
			builder.append("[" + board.getCard(0) + "]");
		else
			builder.append("[30]");

		// Actually draw the game board
		System.out.println(builder.toString());
	}
	
	/**
	 * Draw board face up for cheating
	 * @param board
	 * @param row
	 * @param column
	 * @throws Exception
	 */
	public static void drawCheatBoard(MemoryGameBoard board, int row, int column)
			throws Exception {
		if ((row * column) != board.totalCards())
			throw new RuntimeException();

		// Create an entirely new list to print
		ArrayList<MemoryCard> cardList = new ArrayList<MemoryCard>();
		for(int i = 0; i < board.totalCards(); i++){
			MemoryCard mem = board.getCard(i);
			if(cardList.contains(mem))
				continue;
			else
				cardList.add(mem.copyCard());
		}
		
		//Overwrite the original board with the copy
		board = new MemoryGameBoard(cardList);
		
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < board.totalCards(); i++) {
			// if the card is face down, put a number for selection
			if (board.getCard(i).isFaceUp())
				builder.append("[" + board.getCard(i).toString() + "]");
			else if(!board.getCard(i).isFaceUp())
				board.getCard(i).flip();
				builder.append("[" + board.getCard(i).toString() + "]");
				board.getCard(i).flip();
			// Make a new row every column number of cards
			if (i % column == 0)
				builder.append("\n");
		}

		// Due to 0 as an edge case, we add board.getCard(0) at the end
		if (board.getCard(0).isFaceUp())
			builder.append("[" + board.getCard(0) + "]");
		else
			board.getCard(0).flip();
			builder.append("[" + board.getCard(0) + "]");
			board.getCard(0).flip();

		// Actually draw the game board
		System.out.println(builder.toString());
	}
}
