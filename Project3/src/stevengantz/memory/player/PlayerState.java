package stevengantz.memory.player;

/**
 * @author: Steven Gantz
 * @date: 2/4/2016
 * @Created for CSC421, Dr. Spiegel
 * @This file is a container for all data that may need to be stored and
 *       accessed within a Memory game based within a player.
 * 
 *       Modified 3/6/2016: Updated to be stored within a player.
 **/
public class PlayerState {

    /**
     * Total number of matches up to called point in game
     */
    private int totalMatches;

    /**
     * Total number of times the user played a turn
     */
    private int totalAttempts;
    
    /**
     * Total points scored
     */
    private int totalPoints;

    /**
     * General constructor initializes values at 0
     */
    public PlayerState() {
        this.totalAttempts = 0;
        this.totalMatches = 0;
        this.totalPoints = 0;
    }

    /**
     * Increment how many matched pairs exist within the current game session
     */
    public void addMatch() {
        this.totalMatches += 1;
    }

    /**
     * Increment how many times the user has played a turn
     */
    public void addAttempt() {
        this.totalAttempts += 1;
    }

    // Getters and Setters
    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
    
    public int getTotalMatches() {
        return this.totalMatches;
    }

    public int getTotalAttempts() {
        return this.totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    };

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    };
}
