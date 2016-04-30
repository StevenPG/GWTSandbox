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
    
    public ArrayList<String> getPlayers(){
        if(PlayerNames.size() > 4){
            ArrayList<String> firstFourList = new ArrayList<String>();
            firstFourList.add(PlayerNames.get(0));
            firstFourList.add(PlayerNames.get(1));
            firstFourList.add(PlayerNames.get(2));
            firstFourList.add(PlayerNames.get(3));
            return firstFourList;
        } else {
            return PlayerNames;
        }
    }
}
