package stevengantz.memory.player;

/**
 * @author Steven Gantz
 * @date 3/6/2016
 * @file This abstract class implements all methods and fields that extending
 *       classes must use in order to be considered MemoryGamePlayers.
 *       MemoryPlayer implements the Player interface which contains the
 *       contract for all players.
 */
public abstract class MemoryPlayer implements Player {

    /**
     * Contains the player's state data to be shown onscreen and used for
     * scoring.
     */
    public PlayerState state;

    /**
     * Contains the name of the player for viewing purposes
     */
    public String playerName;

    /**
     * Give the player a name and assign internally
     * 
     * @param playerName
     *            Value to use as player name
     */
    public void assignPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get the player's total number of attempts
     * 
     * @return Total attempts at matching a pair
     */
    public int getTotalAttempts() {
        return state.getTotalAttempts();

    }

    /**
     * Get the player's total number of matched pairs
     * 
     * @return Total successful matched pairs
     */
    public int getTotalMatches() {
        return state.getTotalMatches();

    }

    /**
     * Retrieve the player's name for printing/viewing
     * 
     * @return Name of the player
     */
    public String getPlayerName() {
        return this.playerName;
    }
}
