/**
 * 
 */
package stevengantz.memory.player;

/**
 * @author Steven Gantz
 * @date 3/7/2016
 * @file This class 
 *
 */
public class HumanPlayer extends MemoryPlayer implements Player {

    /**
     * General Constructor
     * @param name Player name
     */
    public HumanPlayer(String name){
        this.playerName = name;
    }
}
