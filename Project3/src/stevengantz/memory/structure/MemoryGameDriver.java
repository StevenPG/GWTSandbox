package stevengantz.memory.structure;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.Duration;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.data.Appdata;
import stevengantz.memory.data.GameData;
import stevengantz.memory.player.ComputerPlayer;
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
     * Flag whether cards are clickable or not
     */
    public boolean clickable;

    /**
     * Instantiate reference for duration object
     */
    protected Duration timeObject;

    /**
     * Contains game fields internally
     */
    final public GameData gamedata;

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
     * Audio elements
     */
    public Audio cardFlipNoise;
    public Audio wrongNoise;
    public Audio matchNoise;

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
        clickable = true;
        timeObject = new Duration();
        this.cardFlipNoise = Audio.createIfSupported();
        this.wrongNoise = Audio.createIfSupported();
        this.matchNoise = Audio.createIfSupported();

        this.cardFlipNoise.setSrc("flipcard.wav");
        this.wrongNoise.setSrc("wrong.wav");
        this.matchNoise.setSrc("match.wav");
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

        // TODO
        // Adding prototype async code
        // MessageServiceAsync memAsync = GWT.create(MemoryGameService.class);
        // memAsync.getMessage(currentPlayer.getPlayerName(), new
        // MessageCallBack());
        // End prototype async code

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

        // After gui update finishes
        // Assign the phase to 3 if computer player
        if (currentPlayer instanceof ComputerPlayer) {
            gamedata.gamePhase = 3;

            // Play AI turn
            playAITurn();

            Timer wait = new Timer() {
                @Override
                public void run() {
                    // Once AI is done, shift back to standard game phase
                    gamedata.gamePhase = 0;

                    // Move to next player
                    nextPlayer();
                }
            };

            wait.schedule(1500);

        } else {
            gamedata.gamePhase = 0;
        }
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
        Label totalPoints = (Label) panel.getWidget(3);

        float attempts = currentPlayer.getTotalAttempts();
        float matches = currentPlayer.getTotalMatches();
        float points = currentPlayer.getTotalPoints();

        totalAttempts.setText("Total Attempts: " + String.valueOf(attempts));
        totalMatches.setText("Total Matches: " + String.valueOf(matches));
        totalPoints.setText("Total points: " + String.valueOf(points));

        if (attempts != 0 && matches != 0) {
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

        if (!clickable)
            return;

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

        // Still processing matches, don't do anything
        if (gamedata.gamePhase == 2) {
            return;
        }

        // Start Computer player processing
        if (gamedata.gamePhase == 3) {
            return;
        }
    }

    /**
     * Play a turn as the AI without GUI input. The game is currently in phase 3
     * to let the AI play. After the AI flips their two cards, the game will
     * allow enough time for them to flip back before handing control to the
     * next player whether AI or human.
     */
    protected void playAITurn() {

        // Mark cards untouchable
        clickable = false;

        gamedata.firstCard = null;
        gamedata.secondCard = null;

        // Save current player locally for better readability
        final ComputerPlayer ai = (ComputerPlayer) currentPlayer;

        final MemoryCard firstCard = ai.getFirstChoice(board);
        final MemoryCard secondCard = ai.getSecondChoice(board);

        // Flip cards for visuals
        firstCard.face.setUrl(firstCard.frontFace.getUrl());
        secondCard.face.setUrl(secondCard.frontFace.getUrl());

        String card1 = firstCard.frontFace.getUrl();
        String card2 = secondCard.frontFace.getUrl();
        if (card1.equals(card2)) {
            firstCard.paired = true;
            secondCard.paired = true;

            // Reshow the cards to fix bug?
            firstCard.face.setUrl(firstCard.frontFace.getUrl());
            secondCard.face.setUrl(secondCard.frontFace.getUrl());

            // Add this match for the player
            ai.addAttempt();
            ai.addMatch();

            // Update the AI
            ai.addCardsToMatchList(firstCard, secondCard);

            // Update the info panel
            updateInfoPanel();

            // Check for win
            checkForWin();

            clickable = true;

            Timer wait = new Timer() {
                @Override
                public void run() {
                    // Play again if it was a match
                    playAITurn();
                }
            };

            wait.schedule(1500);

        } else {
            // It wasn't a match
            ai.addAttempt();

            // Update the info panel
            updateInfoPanel();

            // flip the cards back after 2 seconds, then send back to phase 0
            Timer wait = new Timer() {
                @Override
                public void run() {
                    firstCard.face.setUrl(Appdata.REARIMAGE);
                    secondCard.face.setUrl(Appdata.REARIMAGE);
                    clickable = true;
                    return;
                }
            };

            // Schedule timer to run
            wait.schedule(1500);
            // return;

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
        this.cardFlipNoise.play();
        gamedata.firstCard = currentCard;
        
        if (gamedata.firstCard.paired) {
            Window.alert("That card is already part of a matched pair!");
            // Reset phase to 0
            gamedata.gamePhase = 0;
            return;
        } else {
            gamedata.gamePhase = 1;
            if(Appdata.AiIsPlaying){
                for(Player player : this.players){
                    if(player instanceof ComputerPlayer){
                        ((ComputerPlayer) player).rememberCard(currentCard);
                    }
                }
            }
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
                gamedata.gamePhase = 1;
                return;
            } else {
                currentCard.face.setUrl(currentCard.frontFace.getUrl());
                gamedata.secondCard = currentCard;
                gamedata.gamePhase = 2;
                if(Appdata.AiIsPlaying){
                    for(Player player : this.players){
                        if(player instanceof ComputerPlayer){
                            ((ComputerPlayer) player).rememberCard(currentCard);
                        }
                    }
                }
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

            // Add this match for the player
            currentPlayer.addAttempt();
            currentPlayer.addMatch();

            this.matchNoise.play();
            currentPlayer.addPoints(Appdata.POINTSPERMATCH);

            // Update the info panel
            updateInfoPanel();

            // Check for win
            checkForWin();

            gamedata.gamePhase = 0;

        } else {

            // Place game in phase 2 to lock down cardPress
            gamedata.gamePhase = 2;

            // flip the cards back after 2 seconds, then send back to phase 0
            Timer wait = new Timer() {
                @Override
                public void run() {
                    gamedata.firstCard.face.setUrl(Appdata.REARIMAGE);
                    gamedata.secondCard.face.setUrl(Appdata.REARIMAGE);

                    // Call next player after cards have gone down
                    // Move on to the next player after failed match
                    nextPlayer();
                }
            };

            // This is considered an attempt, increment for current player
            currentPlayer.addAttempt();

            this.wrongNoise.play();

            // Check for win
            checkForWin();

            // Update the info panel
            updateInfoPanel();

            // Schedule timer to run
            wait.schedule(1000);
        }
    }

    /**
     * Check to see if the game is over and who won
     */
    protected boolean checkForWin() {

        //TODO
        // Play animation just for testing
        Animation anim = new CustomAnimation();
        anim.run(1000);
        
        
        // Look and see if every card is paired, then see who won
        for (int i = 0; i < this.board.totalCards(); i++) {
            if (!this.board.getCard(i).paired) {
                return false;
            }
        }

        // Display info for players
        StringBuilder builder = new StringBuilder();
        for (Player player : this.players) {
            builder.append(player.getPlayerName() + " took " + player.getTotalAttempts() + " turns to get "
                    + player.getTotalMatches() + " matches.\n");
        }

        String stats = builder.toString();

        Window.alert("Display animation based on current user's score");

        if (Window.confirm("Game over!\n" + stats + "\nPlay again? Cancel to quit.")) {
            // Play again
            Window.Location.reload();
            RootLayoutPanel.get().clear();
            Window.Location.reload();
        } else {
            if (Window.confirm("GWT provides no way to leave... Do you want to go to Google or something?")) {
                Window.Location.assign("https://www.google.com");
            } else {
                Window.alert("Well I don't know where to send you... so just stay here!! *Leaves*");
            }
        }

        return true;
    }
}
