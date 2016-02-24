package stevengantz.memory.test;

import java.util.ArrayList;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.card.TextCardFace;
import stevengantz.memory.structure.MemoryGameBoard;

/**
 * @author: Steven Gantz
 * @date: 2/2/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This file tests the memory game board class.
 **/
public class MemoryGameBoardTest {
	public static void main(String[] args) {
		ArrayList<MemoryCard> cardList = new ArrayList<MemoryCard>();
		for (int i = 1; i < 16; i++) {
			if (i < 10)
				cardList.add(new MemoryCard(new TextCardFace("0" + i),
						new TextCardFace("-")));
			else
				cardList.add(new MemoryCard(
						new TextCardFace(new Integer(i).toString()),
						new TextCardFace("-")));
		}
		
		MemoryGameBoard board = new MemoryGameBoard(cardList);
		MemoryCard card = board.getCard(5);
		card.flip();
		System.out.println(card);
		System.out.println(board.totalCards());
	}
}
