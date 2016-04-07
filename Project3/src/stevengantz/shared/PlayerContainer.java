package stevengantz.shared;

import java.util.ArrayList;

/**
 * @author Steven Gantz
 * Contains the player data on the server of each player in the game.
 */
public class PlayerContainer {
    
    /**
     * List of player names
     */
    public ArrayList<String> PlayerNames;
    
    /**
     * General constructor
     */
    public PlayerContainer(){
        PlayerNames = new ArrayList<>();
    }
}
