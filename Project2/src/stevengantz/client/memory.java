package stevengantz.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.data.Appdata;
import stevengantz.memory.player.HumanPlayer;
import stevengantz.memory.player.Player;
import stevengantz.memory.structure.MemoryGameBoard;
import stevengantz.memory.structure.MemoryGameDriver;
import stevengantz.memory.structure.MemoryLayoutPanel;
import stevengantz.memory.structure.VerticalGamePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class memory implements EntryPoint {

    /**
     * Game board stored in memory
     */
    MemoryGameBoard board;

    /**
     * Reference for whether the cheat is enabled or not
     */
    boolean cheatEnabled = false;

    /**
     * Contain the driver at a high level for easier access throughout code
     */
    MemoryGameDriver driver;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Initialize data members for game
        ArrayList<Player> players = new ArrayList<Player>();
        this.board = buildBoard();

        // TODO Create a panel that centers everything

        // TODO create an initial menu
        // Inside menu, number of players and their types will be returned
        players.add(new HumanPlayer("Test1"));
        int totalPlayers = 1; // TODO TMP

        // Build general layout of page
        MemoryLayoutPanel mainPanel = createVisualStructure(totalPlayers);

        // Initialize the GUI and game driver
        driver = new MemoryGameDriver(this.board, players);

        // Start GUI
        RootLayoutPanel.get().add(mainPanel);

        // Begin game using driver's external method call
        driver.playGame();
    }

    /**
     * Create the overall visual structure of the game screen.
     * 
     * @return DockPanel initialized with all elements
     */
    protected MemoryLayoutPanel createVisualStructure(int numberOfPlayers) {
        MemoryLayoutPanel mainPanel = new MemoryLayoutPanel(Unit.PX);

        // Generate game panel
        VerticalGamePanel centerGamePanel = buildGamePanel();

        // Generate information panel
        TabLayoutPanel infoPanel = createInfoPanel(numberOfPlayers);

        // Generate general game data and static info panel
        VerticalPanel staticPanel = createStaticPanel();

        // Add individual pieces to DockPanel
        mainPanel.addEast(staticPanel, Appdata.WINDOWWIDTH / 5);
        mainPanel.addWest(infoPanel, Appdata.WINDOWWIDTH / 5);
        mainPanel.addSouth(centerGamePanel, Appdata.WINDOWHEIGHT);

        return mainPanel;
    }

    protected VerticalPanel createStaticPanel() {
        VerticalPanel staticPanel = new VerticalPanel();

        // Create structure
        HorizontalPanel horiz = new HorizontalPanel();

        // Generate data
        TextBox testData = new TextBox();
        testData.setText("Super wide test");

        Button toggleButton = new Button();
        toggleButton.setText("Cheat");
        toggleButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                cheatEnabled = !cheatEnabled;
                if (cheatEnabled) {
                    // assign phase into phase 2 to stop play
                    driver.gamedata.gamePhase = 2;
                    //Window.alert("Enable cheat");
                    for (int i = 0; i < board.totalCards(); i++) {
                        board.getCard(i).face.setUrl(board.getCard(i).frontFace.getUrl());
                    }
                } else {
                    // assign phase back to phase 0 to resume play
                    driver.gamedata.gamePhase = 0;
                    //Window.alert("Disable cheat");
                    for (int i = 0; i < board.totalCards(); i++) {
                        board.getCard(i).face.setUrl(Appdata.REARIMAGE);
                    }

                    // TODO Reassign the cards that should be faced up to the
                    // correct image
                }
            }
        });

        // Assign data to structures
        horiz.add(testData);

        // Wrap structures into panel
        staticPanel.add(horiz);
        staticPanel.add(toggleButton);

        return staticPanel;
    }

    /**
     * Create an information panel that will let you view all of the player's
     * current statistics by their specific tab.
     * 
     * @return infoPanel initialized with the correct number of players
     */
    protected TabLayoutPanel createInfoPanel(int numberOfPlayers) {
        TabLayoutPanel infoPanel = new TabLayoutPanel(2.5, Unit.EM);
        infoPanel.setAnimationDuration(500);
        infoPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);

        for (int i = 0; i < numberOfPlayers; i++) {
            Label label = new Label("This is a label");
            label.setHeight("200");
            String labelTitle = "Test " + String.valueOf(i);
            infoPanel.add(label, labelTitle);
        }

        infoPanel.selectTab(0);
        infoPanel.ensureDebugId("infoPanel");
        infoPanel.setVisible(true);

        return infoPanel;
    }

    /**
     * This method will create a main menu panel that will appear for the user
     * to make selections. The panel will then disappear and the game will begin
     * with the set selections.
     */
    protected Panel buildMainMenuVisual() {
        return null;
    }

    /**
     * Generate the vertical panel that holds all of the cards. This board will
     * be interacted with throughout the game.
     * 
     * @return VerticalPanel filled with horizontal panels of images
     */
    protected VerticalGamePanel buildGamePanel() {

        // Generate overall vertical panel
        VerticalGamePanel game = new VerticalGamePanel();

        // Keep track of each card being entered
        int currentCardIndex = 0;

        // Add NUMROWS horizontal panels
        for (int i = 0; i < Appdata.NUMROWS; i++) {
            HorizontalPanel horiz = new HorizontalPanel();
            for (int j = 0; j < Appdata.NUMCOLS; j++) {

                // Add image and iterate card
                this.board.getCard(currentCardIndex).face.setStyleName("Padding");
                horiz.add(this.board.getCard(currentCardIndex).face);
                currentCardIndex++;
            }

            game.add(horiz);
        }
        return game;
    }

    /**
     * This method returns a fully filled game board based on the Singleton
     * class.
     * 
     * @return - filled out game board based on Appdata.java
     */
    protected MemoryGameBoard buildBoard() {

        // List of filled out Memory Cards
        ArrayList<MemoryCard> cardList = new ArrayList<MemoryCard>();

        // List of possible images
        ArrayList<String> frontImageList = Appdata.GetImageStringList();

        // Loop through for each card
        for (int index = 0; index < Appdata.NUMBEROFCARDS; index++) {

            // Generate the image in memory
            Image front = new Image();
            Image back = new Image();

            // Generate card's pair
            Image frontPair = new Image();
            Image backPair = new Image();

            // Assign the image
            front.setUrl(frontImageList.get(index));
            back.setUrl(Appdata.REARIMAGE);

            frontPair.setUrl(frontImageList.get(index));
            backPair.setUrl(Appdata.REARIMAGE);

            // Assign relative sizes
            setCardPixelSize(front);
            setCardPixelSize(back);

            setCardPixelSize(frontPair);
            setCardPixelSize(backPair);

            // Generate MemoryCard object
            MemoryCard mem = generateCard(front, back);
            MemoryCard memPair = generateCard(frontPair, backPair);

            // Add memory card to the list
            cardList.add(mem);
            cardList.add(memPair);
        }

        // Create the board with a list of cards
        MemoryGameBoard board = new MemoryGameBoard(cardList, true);

        // shuffle the board
        board.randomizeBoard();

        return board;
    }

    /**
     * Set image pixel size helper method
     * 
     * @param image
     *            image to set pixel size
     */
    protected void setCardPixelSize(Image image) {
        image.setPixelSize(Appdata.CARDWIDTH, Appdata.CARDHEIGHT);
    }

    protected MemoryCard generateCard(Image front, Image back) {
        return new MemoryCard(front, back);
    }
}
