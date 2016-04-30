package stevengantz.memory.player;

import java.util.ArrayList;
import java.util.Random;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.structure.MemoryGameBoard;

/**
 * @author Steven Gantz
 * @date 3/7/2016
 * @file This class uses the MemoryPlayer abstract class to define its methods
 *       when called. A computer player is fully autonomous and has its methods
 *       called manually from within the driver. ComputerPlayer objects act
 *       independently of any human players and remove game input while in use
 *       by placing the game into phase 3.
 *
 */
public class ComputerPlayer extends MemoryPlayer implements Player {

    /**
     * Contains the cards the computer player has committed to memory
     */
    // private ArrayList<MemoryCard> cardMemory;
    // private ArrayList<Integer> cardSpots;

    /**
     * List of all pairs already found
     */
    private ArrayList<MemoryCard> pairList;

    /**
     * Difficulty level of the computer player
     */
    private int difficultyLevel;

    /**
     * Internal state of computer player
     */
    private int internalPhase;

    /**
     * Number gotten from first retrieval method
     */
    private int firstNumberRetrieved;

    /**
     * Object to retrieve random values
     */
    private Random random;

    /**
     * General Constructor
     * 
     * @param name
     *            Player name
     */
    public ComputerPlayer(String name, int difficulty) {
        this.playerName = name;
        this.state = new PlayerState();
        // this.cardMemory = new ArrayList<MemoryCard>();
        this.pairList = new ArrayList<MemoryCard>();
        setInternalPhase(0);
        random = new Random();
        // TODO
        difficultyLevel = 0;
        firstNumberRetrieved = -1;
        // cardSpots = new ArrayList<Integer>();
    }

    /**
     * public void rememberCard(int chance) {
     * 
     * }
     * 
     * private void guessCardFromMemory() {
     * 
     * }
     **/

    /**
     * When successfully matched, add the cards to the internal match list
     * 
     * @param mem1
     *            First selected card
     * @param mem2
     *            second selected card
     */
    public void addCardsToMatchList(MemoryCard mem1, MemoryCard mem2) {
        pairList.add(mem1);
        pairList.add(mem2);
    }

    /**
     * See if the computer player has matched the card yet
     * 
     * @param compareCard
     *            The card to check
     * @return Whether the card is in the matched list
     */
    private boolean isCardInMatchList(MemoryCard compareCard) {
        if (pairList.contains(compareCard))
            return true;
        else
            return false;
    }

    /**
     * See if the card choice is a valid choice
     * 
     * @param currentCard
     *            Chosen card
     * @return Whether or not to continue with that card
     */
    private boolean isCardValid(MemoryCard currentCard) {
        // If card has been matched before, it is invalid choice
        if (isCardInMatchList(currentCard))
            return false;

        // If card has a pair, it is invalid choice
        if (currentCard.paired)
            return false;

        return true;
    }

    /**
     * Select first card
     * 
     * @param board
     *            Game board of memory cards
     * @return Memory card selected by player
     */
    public MemoryCard getFirstChoice(MemoryGameBoard board) {
        // Easy mode
        if (difficultyLevel == 0) {
            firstNumberRetrieved = random.nextInt(30);
            MemoryCard firstCard = board.getCard(firstNumberRetrieved);
            while (!isCardValid(firstCard)) {
                firstNumberRetrieved = random.nextInt(30);
                firstCard = board.getCard(firstNumberRetrieved);
            }
            return firstCard;

        } else {
            // TODO
            return null;
        }
    }

    /**
     * Select second card
     * 
     * @param board
     *            Game board of memory cards
     * @return Memory card selected by player
     */
    public MemoryCard getSecondChoice(MemoryGameBoard board) {
        
        boolean valid = false;
        // Easy mode
        if (difficultyLevel == 0) {
            int secondNumber = 0;
            while(!valid){
                secondNumber = random.nextInt(30);
                if(!(secondNumber == firstNumberRetrieved)){
                    if(isCardValid(board.getCard(secondNumber))){
                        valid = true;
                    } else {
                        valid = false;
                    }
                } else {
                    valid = false;
                }
            }
            return board.getCard(secondNumber);
        } else {
            // TODO
            return null;
        }
    }

    /**
     * @return the difficultyLevel
     */
    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * @param difficultyLevel
     *            the difficultyLevel to set
     */
    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * @return the internalPhase
     */
    public int getInternalPhase() {
        return internalPhase;
    }

    /**
     * @param internalPhase
     *            the internalPhase to set
     */
    public void setInternalPhase(int internalPhase) {
        this.internalPhase = internalPhase;
    }

}
