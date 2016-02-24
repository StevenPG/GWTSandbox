package stevengantz.memory.test;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.card.TextCardFace;

/**
 * @author: Steven Gantz
 * @date: 2/2/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This file tests the memory card class.
 **/
public class MemoryCardTest {
	public static void main(String[] args) {
		System.out.println("Start Memory Card Test");

		MemoryCard mem = new MemoryCard(new TextCardFace("A"),
				new TextCardFace("-"));
		System.out.println(mem.getFrontFace());
		System.out.println(mem.getRearFace());
		System.out.println(mem.isFaceUp());
		mem.flip();
		System.out.println(mem.isFaceUp());
		mem.flip();
		System.out.println(mem.isFaceUp());

		System.out.println("Copy Test");
		MemoryCard card2 = mem.copyCard();
		MemoryCard card3 = new MemoryCard(new TextCardFace("B"),
				new TextCardFace("-"));
		System.out.println(mem.equals(card2));
		System.out.println(mem.equals(card3));

		System.out.println("ToString Test");
		System.out.println(mem);
		mem.flip();
		System.out.println(mem);

		System.out.println("End Memory Card Test");
	}
}
