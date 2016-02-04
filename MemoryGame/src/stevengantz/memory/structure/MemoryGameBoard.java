package stevengantz.memory.structure;

import java.util.ArrayList;
import java.util.Collections;

import stevengantz.memory.card.MemoryCard;

/**
 * @author: Steven Gantz
 * @date: 2/2/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This file is a container for a virtual representation of a memory game area.
 *       It is filled with memory cards to be organized into a usable form to
 *       represent the game itself.
 **/
public class MemoryGameBoard {

	/**
	 * This attribute holds the internal representation of the game board.
	 */
	private ArrayList<MemoryCard> board;

	/**
	 * General use case constructor, pass in a list of all possible cards to be
	 * doubled into pairs internally.
	 * 
	 * @param cardList
	 *            List of all cards to be duplicated for the game
	 */
	public MemoryGameBoard(ArrayList<MemoryCard> cardList) {
		// Generate the board to be built
		this.board = new ArrayList<MemoryCard>();

		// Duplicate cardlist to create pairs for game
		ArrayList<MemoryCard> pairs = new ArrayList<MemoryCard>();
		for (MemoryCard mem : cardList) {
			pairs.add(mem.copyCard());
		}

		// Join the lists together to form the game board
		this.board.addAll(cardList);
		this.board.addAll(pairs);
	}
	
	public void randomizeBoard(){
		Collections.shuffle(this.board);
	}
	
	public boolean checkIfBoardIsAllPairs(){
		boolean gameOver = false;
		for(MemoryCard mem : this.board){
			if(mem.getPairedCard() == null){
				return gameOver;
			}
		}
		
		return true;
		
	}

	/**
	 * Return the card at a certain location on the board
	 * 
	 * @param choice
	 *            The card to retrieve from 0-n
	 * @return The MemoryCard reference pointer at that location on the board
	 */
	public MemoryCard getCard(int choice) {
		return this.board.get(choice);
	}

	/**
	 * This method returns the total number of cards on the board
	 * 
	 * @return Total cards on the board
	 */
	public int totalCards() {
		return this.board.size();
	}
}
