/**
 * 
 */
package stevengantz.memory.data;

import stevengantz.memory.card.MemoryCard;

/**
 * @author Steven Gantz
 * @date 3/7/2016
 * @file This file contains all of the data for the game in a neat package that
 *       allows fields to be set and used from the game driver class
 *
 */
public class GameData {

    /**
     * Interally held memory cards
     */
    public MemoryCard firstCard;
    public MemoryCard secondCard;
    
    /**
     * Phase of the game
     * Select First Card = 0
     * Select Second Card = 1
     * Make sure nothing can be clicked, make data changes = 2
     * Game over = 3
     */
    public int gamePhase = 0;
    
}
