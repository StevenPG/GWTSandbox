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
     * List of all players
     */
    ArrayList<Player> players;
    
    /**
     * Give access to the tablayoutpanel
     * for gui changes
     */
    public TabLayoutPanel infoPanel;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Initialize data members for game
        players = new ArrayList<Player>();
        this.board = buildBoard();

        // TODO Create a panel that centers everything

        // TODO create an initial menu
        // Inside menu, number of players and their types will be returned
        players.add(new HumanPlayer("Steve"));
        players.add(new HumanPlayer("Zach"));
        int totalPlayers = players.size();

        // Build general layout of page
        MemoryLayoutPanel mainPanel = createVisualStructure(totalPlayers);

        // Initialize the GUI and game driver
        driver = new MemoryGameDriver(this.board, players, this.infoPanel);

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
        this.infoPanel = createInfoPanel(numberOfPlayers);

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
        Label titleLabel = new Label();
        titleLabel.setText("Information Panel");

        // Display the rules in individual labels
        ArrayList<Label> rules = new ArrayList<Label>();
        rules.add(new Label());
        rules.add(new Label());
        rules.add(new Label());
        rules.add(new Label());
        rules.add(new Label());
        rules.add(new Label());

        // Fill in each value for rules
        rules.get(0).setText("How to play");
        rules.get(1).setText("1. Select a card and commit it to memory as best you can.");
        rules.get(2).setText("2. Select another card, try to match them up!");
        rules.get(3).setText("3. If you don't make a match, it is the next player's turn.");
        rules.get(4).setText("4. Otherwise you found a match! Go again!");
        rules.get(5).setText("5. Whoever has the most matches at the end wins!");

        final Button toggleButton = new Button();
        toggleButton.setText("Cheat: Disabled");
        toggleButton.setWidth("100%");

        toggleButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                cheatEnabled = !cheatEnabled;
                if (cheatEnabled) {
                    toggleButton.setText("Cheat: Enabled");
                    // assign phase into phase 2 to stop play
                    driver.gamedata.gamePhase = 2;
                    // Window.alert("Enable cheat");
                    for (int i = 0; i < board.totalCards(); i++) {
                        board.getCard(i).face.setUrl(board.getCard(i).frontFace.getUrl());
                    }
                } else {
                    toggleButton.setText("Cheat: Disabled");
                    // assign phase back to phase 0 to resume play
                    driver.gamedata.gamePhase = 0;
                    // Window.alert("Disable cheat");
                    for (int i = 0; i < board.totalCards(); i++) {
                        // Only flip them back over if they are not a part of a
                        // pair
                        if (!board.getCard(i).paired) {
                            board.getCard(i).face.setUrl(Appdata.REARIMAGE);
                        } else {
                            // Don't do anything and leave the card face up
                        }
                    }
                }
            }
        });

        // Assign data to structures
        horiz.add(toggleButton);

        // Wrap structures into panel
        staticPanel.add(titleLabel);
        staticPanel.add(horiz);

        // Add rules
        for (Label lbl : rules) {
            staticPanel.add(lbl);
        }

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
            Label totalAttempts = new Label();
            Label totalMatches = new Label();
            totalAttempts.setHeight("200");
            totalMatches.setHeight("200");
            totalAttempts.setText("Total Attempts: 0");
            totalMatches.setText("Total Matches: 0");
            
            VerticalPanel panel = new VerticalPanel();
            
            // Order of vertical panel
            panel.add(totalAttempts);
            panel.add(totalMatches);
            
            String tabTitle = players.get(i).getPlayerName();
            infoPanel.add(panel, tabTitle);
        }

        infoPanel.selectTab(0);
        
        // Assign as first player
        Label tabTitle = (Label) infoPanel.getTabWidget(0);
        tabTitle.setText(players.get(0).getPlayerName() + "*");
        
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
