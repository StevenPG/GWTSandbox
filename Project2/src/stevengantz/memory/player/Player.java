package stevengantz.memory.player;

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
}
