package stevengantz.memory.structure;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.data.GameData;
import stevengantz.memory.player.Player;

/**
 * @author Steven Gantz
 * @date 2/25/2016
 * @file This file will drive the memory card game, making necessary changes by
 *       altering objects passed in by reference in the constructor.
 * 
 */
public class MemoryGameDriver {

    /**
     * Stored internally for easy access
     */
    protected MemoryGameBoard board;

    /**
     * Contains game fields internally
     */
    public GameData gamedata;

    /**
     * Array of players initialized in the constructor
     */
    protected ArrayList<Player> players;

    /**
     * Keep track of which player is currently playing the board
     */
    public Player currentPlayer;

    /**
     * This constructor uses an internal board of Memory cards to make changes
     * and drive the course of the game.
     */
    public MemoryGameDriver(MemoryGameBoard board, ArrayList<Player> players) {
        this.board = board;
        this.players = players;
        currentPlayer = players.get(0);
        gamedata = new GameData();
    }

    /**
     * This method plays an entire game of memory based on the inputs retrieved
     * from the class constructor.
     */
    public void playGame() {
        assignClickHandlers();
    }

    /**
     * Assign the ClickHandler that will call methods based on current gamestate
     */
    protected void assignClickHandlers() {
        for (int i = 0; i < this.board.totalCards(); i++) {

            // Save i in a final variable for use inside ClickHandler
            final int index = i;

            this.board.getCard(i).face.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    cardClicked(index);
                }
            });
        }
    }

    /**
     * Method called when a card is clicked
     * 
     * @param selectedCard
     *            The value of the card selected
     */
    public void cardClicked(int selectedCard) {
        MemoryCard currentCard = this.board.getCard(selectedCard);

        // First card has been selected
        if (gamedata.gamePhase == 0) {
            selectedFirstCard(currentCard, selectedCard);
            return;
        }

        // Second card has been selected
        if (gamedata.gamePhase == 1) {
            selectedSecondCard(currentCard, selectedCard);
            return;
        }

        // Still processing something, don't do anything
        if (gamedata.gamePhase == 2) {
            return;
        }
    }

    /**
     * Internal changes made when in phase 0
     * 
     * @param currentCard
     *            The card that was clicked on
     * @param selectedCard
     *            The value of the card that was selected
     */
    protected void selectedFirstCard(MemoryCard currentCard, int selectedCard) {
        currentCard.face.setUrl(currentCard.frontFace.getUrl());
        gamedata.firstCard = currentCard;
        gamedata.gamePhase = 1;
    }

    /**
     * Internal changes made when in phase 1
     * 
     * @param currentCard
     * @param selectedCard
     */
    protected void selectedSecondCard(MemoryCard currentCard, int selectedCard) {
        if (currentCard == gamedata.firstCard) {
            Window.alert("You've already selected that card, pick again!");
        } else {
            currentCard.face.setUrl(currentCard.frontFace.getUrl());
            gamedata.secondCard = currentCard;
            gamedata.gamePhase = 2;
        }
        
        // Check for a match
        checkForMatch();
    }
    
    /**
     * Check for a match and return a boolean
     */
    protected void checkForMatch(){
        String card1 = gamedata.firstCard.frontFace.getUrl();
        String card2 = gamedata.secondCard.frontFace.getUrl();
        Window.alert(String.valueOf(card1==card2));
    }
}
