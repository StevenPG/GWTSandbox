package stevengantz.memory.player;

import java.util.ArrayList;
import java.util.Random;

import com.google.gwt.user.client.Window;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.data.Appdata;
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
     * Contains the cards the computer player has committed to memory in a pair
     * of associative arrays
     */
    private ArrayList<MemoryCard> cardMemory;

    /**
     * List of all pairs already found
     */
    private ArrayList<MemoryCard> pairList;

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
        this.cardMemory = new ArrayList<MemoryCard>();
        this.pairList = new ArrayList<MemoryCard>();
        setInternalPhase(0);
        random = new Random();
        firstNumberRetrieved = -1;
        // cardSpots = new ArrayList<Integer>();
    }

    /**
     * Commit a card to memory with some random chance
     * 
     * @param card
     *            to attempt to remember based on difficulty level
     */
    public void rememberCard(MemoryCard card) {
        Random rnd = new Random();

        // If card is already in memory, don't remember it
        if (this.cardMemory.contains(card)) {
            return;
        }

        // Easy mode
        if (Appdata.AiDifficulty == 0) {
            int randomNumber = rnd.nextInt(10);
            // 30% chance to remember card
            if (randomNumber < 3) {
                this.cardMemory.add(card);
            } else {
                // Don't remember the card
            }
            return;
        }

        // Medium mode
        if (Appdata.AiDifficulty == 1) {
            int randomNumber = rnd.nextInt(10);
            // 60% chance to remember card
            if (randomNumber < 6) {
                this.cardMemory.add(card);
                Window.alert("Remembered " + card.getFrontFace().getUrl());
            } else {
                // Don't remember the card
            }
        }

        // Hard mode
        if (Appdata.AiDifficulty == 2) {
            int randomNumber = rnd.nextInt(10);
            // 100% chance to remember card
            if (randomNumber < 10) {
                this.cardMemory.add(card);
            } else {
                // Don't remember the card
            }
        }
    }

    /**
     * Guess a card directly from memory if it exists
     * 
     * @param card
     *            card to compare against
     * @return
     */
    private MemoryCard guessCardFromMemory(MemoryCard card) {

        // DEBUG
        StringBuilder builder = new StringBuilder();
        for (MemoryCard mem : this.cardMemory) {
            builder.append(mem.getFrontFace().getUrl() + "\n");
        }
        Window.alert("Memory: " + builder.toString());

        for (MemoryCard mem : this.cardMemory) {
            if (mem.getFrontFace().getUrl().equals(card.getFrontFace().getUrl())) {
                // They also aren't the same card
                if (mem != card) {
                    Window.alert("Guessed from Memory");
                    return mem;
                } else {
                    // The game picked the same card twice from memory
                    Window.alert("Game picked same card twice from memory");
                }
            }
        }

        return null;
    }

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

        // If there is a pair of cards in memory, choose one of the pair
        // automagically using double foreach loop
        for (MemoryCard outercard : this.cardMemory) {
            if(outercard.paired){
                continue;
            }
            for (MemoryCard innercard : this.cardMemory) {
                if(innercard.paired){
                    continue;
                }
                if (outercard != innercard) {
                    if (outercard.getFrontFace().getUrl().equals(innercard.getFrontFace().getUrl())) {
                        // There is a pair already in memory, choose the outer
                        // card
                        Window.alert("Default to a match in memory!");
                        return outercard;
                    }
                } else {
                    // This is the same card reference
                }
            }
        }

        // Easy mode
        if (Appdata.AiDifficulty == 0) {
            firstNumberRetrieved = random.nextInt(30);
            MemoryCard firstCard = board.getCard(firstNumberRetrieved);
            while (!isCardValid(firstCard)) {
                firstNumberRetrieved = random.nextInt(30);
                firstCard = board.getCard(firstNumberRetrieved);
            }
            this.rememberCard(firstCard);
            return firstCard;
        }
        // Medium mode
        if (Appdata.AiDifficulty == 1) {
            firstNumberRetrieved = random.nextInt(30);
            MemoryCard firstCard = board.getCard(firstNumberRetrieved);
            while (!isCardValid(firstCard)) {
                firstNumberRetrieved = random.nextInt(30);
                firstCard = board.getCard(firstNumberRetrieved);
            }
            this.rememberCard(firstCard);
            return firstCard;
        }
        // Hard mode
        if (Appdata.AiDifficulty == 2) {
            firstNumberRetrieved = random.nextInt(30);
            MemoryCard firstCard = board.getCard(firstNumberRetrieved);
            while (!isCardValid(firstCard)) {
                firstNumberRetrieved = random.nextInt(30);
                firstCard = board.getCard(firstNumberRetrieved);
            }
            this.rememberCard(firstCard);
            return firstCard;
        }

        assert false;
        return null;
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

        // Check in memory first
        MemoryCard firstCard = board.getCard(firstNumberRetrieved);
        MemoryCard matchedCard = this.guessCardFromMemory(firstCard);
        if (matchedCard != null) {
            // Found the card in memory, return that card
            Window.alert("Matched card from memory");
            return matchedCard;
        } else {
            // otherwise

            // Easy mode
            if (Appdata.AiDifficulty == 0) {
                int secondNumber = 0;
                while (!valid) {
                    secondNumber = random.nextInt(30);
                    if (!(secondNumber == firstNumberRetrieved)) {
                        if (isCardValid(board.getCard(secondNumber))) {
                            valid = true;
                        } else {
                            valid = false;
                        }
                    } else {
                        valid = false;
                    }
                }
                this.rememberCard(firstCard);
                return board.getCard(secondNumber);
            }

            // Medium mode
            if (Appdata.AiDifficulty == 1) {
                int secondNumber = 0;
                while (!valid) {
                    secondNumber = random.nextInt(30);
                    if (!(secondNumber == firstNumberRetrieved)) {
                        if (isCardValid(board.getCard(secondNumber))) {
                            valid = true;
                        } else {
                            valid = false;
                        }
                    } else {
                        valid = false;
                    }
                }
                this.rememberCard(firstCard);
                return board.getCard(secondNumber);
            }

            // Hard mode
            if (Appdata.AiDifficulty == 2) {
                int secondNumber = 0;
                while (!valid) {
                    secondNumber = random.nextInt(30);
                    if (!(secondNumber == firstNumberRetrieved)) {
                        if (isCardValid(board.getCard(secondNumber))) {
                            valid = true;
                        } else {
                            valid = false;
                        }
                    } else {
                        valid = false;
                    }
                }
                this.rememberCard(firstCard);
                return board.getCard(secondNumber);
            }
            return null;
        }
    }

    /**
     * @return the difficultyLevel
     */
    public int getDifficultyLevel() {
        return Appdata.AiDifficulty;
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
