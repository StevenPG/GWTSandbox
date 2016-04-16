package stevengantz.memory.player;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.structure.MemoryGameBoard;

/**
 * @author Steven Gantz
 * @date 3/7/2016
 * @file This class uses the MemoryPlayer abstract class to define its methods
 *       when called. A human player is just a container class and the actually
 *       game play is done via the gui interface. HumanPlayer objects are
 *       effectively a container for player data.
 *
 */
public class InternetPlayer extends MemoryPlayer implements Player {

    /**
     * General Constructor
     * 
     * @param name
     *            Player name
     */
    public InternetPlayer(String name) {
        this.playerName = name;
        this.state = new PlayerState();
    }

    @Override
    public MemoryCard getFirstChoice(MemoryGameBoard board) {
        return null;
    }

    @Override
    public MemoryCard getSecondChoice(MemoryGameBoard board) {
        return null;
    }

    @Override
    public void addCardsToMatchList(MemoryCard firstCard, MemoryCard secondCard) {
        // Do nothing, this method comes from leftover AI
    }
}
