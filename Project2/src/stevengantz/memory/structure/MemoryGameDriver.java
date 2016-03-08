package stevengantz.memory.structure;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.data.Appdata;
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
     * Give access to the tablayoutpanel for gui changes
     */
    public TabLayoutPanel infoPanel;

    /**
     * Keep track of which player is currently playing the board
     */
    public Player currentPlayer;
    public int currentPlayerNumber;

    /**
     * This constructor uses an internal board of Memory cards to make changes
     * and drive the course of the game.
     */
    public MemoryGameDriver(MemoryGameBoard board, ArrayList<Player> players, TabLayoutPanel infoPanel) {
        this.board = board;
        this.players = players;
        currentPlayerNumber = 0;
        currentPlayer = players.get(currentPlayerNumber);
        gamedata = new GameData();
        this.infoPanel = infoPanel;
    }

    /**
     * Increment to the next player
     */
    protected void nextPlayer() {
        // Remove turn marker from name
        Label tabTitle = (Label) infoPanel.getTabWidget(currentPlayerNumber);
        tabTitle.setText(currentPlayer.getPlayerName());
        
        // Move on to next player in array
        currentPlayerNumber++;

        // If this is larger than number of players, reset to 0
        if (currentPlayerNumber == players.size()) {
            currentPlayerNumber = 0;
            currentPlayer = players.get(currentPlayerNumber);
        } else {
            currentPlayer = players.get(currentPlayerNumber);
        }

        infoPanel.selectTab(currentPlayerNumber);
        tabTitle = (Label) infoPanel.getTabWidget(currentPlayerNumber);
        tabTitle.setText(currentPlayer.getPlayerName() + "*");
    }

    /**
     * Small local field to update whose turn it is
     */
    protected Label whoseTurn;

    /**
     * Update the info panel
     */
    public void updateInfoPanel() {
        VerticalPanel panel = (VerticalPanel) infoPanel.getWidget(currentPlayerNumber);
        Label totalAttempts = (Label) panel.getWidget(0);
        Label totalMatches = (Label) panel.getWidget(1);
        Label successRate = (Label) panel.getWidget(2);
        
        float attempts = currentPlayer.getTotalAttempts();
        float matches = currentPlayer.getTotalMatches();
        
        totalAttempts.setText("Total Attempts: " + String.valueOf(attempts));
        totalMatches.setText("Total Matches: " + String.valueOf(matches));
        
        if(attempts != 0 && matches != 0){
            float rate = matches / attempts;
            BigDecimal bd = new BigDecimal(Float.toString(rate));
            bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
            successRate.setText("Successful Match Rate: " + String.valueOf(bd));
        }
        
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
        if (gamedata.firstCard.paired) {
            Window.alert("That card is already part of a matched pair!");
            return;
        } else {
            gamedata.gamePhase = 1;
        }
    }

    /**
     * Internal changes made when in phase 1
     * 
     * @param currentCard
     *            The card that was clicked on
     * @param selectedCard
     *            The value of the card that was selected
     */
    protected void selectedSecondCard(MemoryCard currentCard, int selectedCard) {
        if (currentCard == gamedata.firstCard) {
            Window.alert("You've already selected that card, pick again!");
        } else {
            // Make sure the second card selected isn't a part of a pair
            // if so, leave in phase 1
            if (currentCard.paired) {
                Window.alert("That card is already part of a matched pair!");
                return;
            } else {
                currentCard.face.setUrl(currentCard.frontFace.getUrl());
                gamedata.secondCard = currentCard;
                gamedata.gamePhase = 2;
            }

            // Check for a match
            checkForMatch();
        }
    }

    /**
     * Check for a match and return a boolean
     */
    protected void checkForMatch() {
        String card1 = gamedata.firstCard.frontFace.getUrl();
        String card2 = gamedata.secondCard.frontFace.getUrl();
        if (card1 == card2) {
            gamedata.firstCard.paired = true;
            gamedata.secondCard.paired = true;
            gamedata.gamePhase = 0;

            // Add this match for the player
            currentPlayer.addAttempt();
            currentPlayer.addMatch();

            // Update the info panel
            updateInfoPanel();

            // Check for win
            checkForWin();

        } else {
            // flip the cards back after 2 seconds, then send back to phase 0
            Timer wait = new Timer() {
                @Override
                public void run() {
                    gamedata.firstCard.face.setUrl(Appdata.REARIMAGE);
                    gamedata.secondCard.face.setUrl(Appdata.REARIMAGE);
                    gamedata.gamePhase = 0;
                }
            };

            // Schedule timer to run
            wait.schedule(1000);

            // This is considered an attempt, increment for current player
            currentPlayer.addAttempt();

            // Check for win
            checkForWin();

            // Update the info panel
            updateInfoPanel();

            // Move on to the next player after failed match
            nextPlayer();

        }
    }

    /**
     * Check to see if the game is over and who won
     */
    protected boolean checkForWin() {
        // Look and see if every card is paired, then see who won
        for (int i = 0; i < this.board.totalCards(); i++) {
            if (!this.board.getCard(i).paired) {
                return false;
            }
        }
        Window.alert("Game is over!");
        return true;
    }
}
