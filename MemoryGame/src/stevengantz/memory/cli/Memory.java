package stevengantz.memory.cli;

import stevengantz.memory.structure.MemoryGameTextDriver;

/**
 * @author: Steven Gantz
 * @date: 2/4/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This file contains the main static method to play memory in a text based
 *       environment.
 **/
public class Memory {
	public static void main(String[] args) {
		// Play game
		MemoryGameTextDriver driver = new MemoryGameTextDriver(6, 5);
		driver.beginGame();
		try {
			System.out.println("Welcome to Memory!!\n");
			while(true){
				driver.playTurn();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
