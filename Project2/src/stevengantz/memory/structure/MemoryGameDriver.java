package stevengantz.memory.structure;

import java.util.ArrayList;

import stevengantz.memory.player.Player;

/**
 * @author Steven Gantz
 * @date 2/25/2016
 * @file This file will drive the memory card game, making necessary changes by
 *       altering objects passed in by reference in the constructor.
 * 
 */
public class MemoryGameDriver {

    /**
     * Stored internally for easy access
     */
    protected MemoryGameBoard board;

    /**
     * Array of players initialized in the constructor
     */
    protected ArrayList<Player> players;

    /**
     * This constructor uses an internal board of Memory cards to make changes
     * and drive the course of the game.
     */
    public MemoryGameDriver(MemoryGameBoard board, ArrayList<Player> players) {
        this.board = board;
        this.players = players;
    }

    /**
     * This method plays an entire game of memory based on the inputs retrieved
     * from the class constructor.
     */
    public void playGame() {
        
    }

    /**
     * This method flips a card visually, and returns the selected card
     * internally
     */
    protected void selectCard() {
        // Set ClickHandler for each card, return the selected card
    }

}
