package stevengantz.memory.player;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.structure.MemoryGameBoard;

/**
 * @author Steven Gantz
 * @date 3/6/2016
 * @file This file is the contract that all players must follow in order to be
 *       valid memory card game players.
 */
public interface Player {
    
    /**
     * Retrieve the player's name for printing/viewing
     * @return
     */
    public String getPlayerName();
    
    /**
     * Retrieve the total number of attempts for a player
     * @return Total attempts for a player
     */
    public int getTotalAttempts();
    
    /**
     * Retrieve the total number of matches for a player
     * @return Total matches for a player
     */
    public int getTotalMatches();
    
    /**
     * Get total player points
     * @return total points
     */
    public int getTotalPoints();

    /**
     * Add attempt for player
     */
    public void addAttempt();
    
    /**
     * Add a match for this player
     */
    public void addMatch();
    
    /**
     * Add some number of points to player
     * @param points number of points
     */
    public void addPoints(int points);

    public MemoryCard getFirstChoice(MemoryGameBoard board);

    public MemoryCard getSecondChoice(MemoryGameBoard board);

    public void addCardsToMatchList(MemoryCard firstCard, MemoryCard secondCard);
}
