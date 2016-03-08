package stevengantz.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
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
    boolean cheatsShown = true;

    /**
     * Contain the driver at a high level for easier access throughout code
     */
    MemoryGameDriver driver;

    /**
     * List of all players
     */
    ArrayList<Player> players;

    /**
     * Retrieve the total number of players entered by the user in the main menu
     */
    int totalPlayers;

    /**
     * Retrieve the list of strings from main menu textboxes. Goes along with an
     * associative array of whether human or not.
     */
    ArrayList<String> names;
    ArrayList<Boolean> isHuman;

    /**
     * Give access to the TabLayoutPanel for gui changes
     */
    public TabLayoutPanel infoPanel;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Initialize data members for game
        players = new ArrayList<Player>();
        this.board = buildBoard();
        names = new ArrayList<String>();
        isHuman = new ArrayList<Boolean>();

        // Create a panel that centers everything in main menu
        VerticalPanel mainmenu = buildMainMenuVisual();

        // Inside menu, number of players and their types will be returned
        for (int i = 0; i < names.size(); i++) {
            /**
             * if (isHuman.get(i)) { players.add(new HumanPlayer(names.get(i)));
             * } else { // Do nothing for now }
             **/
        }

        // Add main menu to RootLayoutPanel
        RootLayoutPanel.get().add(mainmenu);

    }

    /**
     * This method will start the main game by adding the event listening panels
     * to the RootLayoutPanel. The method is to be called from the mainmenu when
     * it is finished running.
     */
    protected void closeMainMenuStartGame() {
        // Remove the main menu
        RootLayoutPanel.get().clear();

        // Build general layout of page
        MemoryLayoutPanel mainPanel = createVisualStructure(players.size());

        // Initialize the GUI and game driver
        driver = new MemoryGameDriver(this.board, players, this.infoPanel);

        // Start GUI
        RootLayoutPanel.get().add(mainPanel);

        // Begin game using driver's external method call
        driver.playGame();
    }

    /**
     * This method will create a main menu panel that will appear for the user
     * to make selections. The panel will then disappear and the game will begin
     * with the set selections.
     */
    protected VerticalPanel buildMainMenuVisual() {
        VerticalPanel mainMenu = new VerticalPanel();
        mainMenu.setWidth("100%");
        mainMenu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        // Add title bar
        Label title = new Label();
        title.setText("Memory");
        title.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 70, Unit.EM);
        title.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);

        // Add player number section
        Label Question = new Label();
        Question.setText("Number of Players? ");
        HorizontalPanel radioButtonPanel = new HorizontalPanel();
        final RadioButton onePlayer = new RadioButton("choice", "1");
        onePlayer.setValue(true);
        final RadioButton twoPlayer = new RadioButton("choice", "2");
        final RadioButton threePlayer = new RadioButton("choice", "3");
        final RadioButton fourPlayer = new RadioButton("choice", "4");
        radioButtonPanel.add(onePlayer);
        radioButtonPanel.add(twoPlayer);
        radioButtonPanel.add(threePlayer);
        radioButtonPanel.add(fourPlayer);
        Button next = new Button();
        next.setText("Next");
        next.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Get how many players
                if (onePlayer.getValue()) {
                    totalPlayers = 1;
                } else if (twoPlayer.getValue()) {
                    totalPlayers = 2;
                } else if (threePlayer.getValue()) {
                    totalPlayers = 3;
                } else {
                    totalPlayers = 4;
                }

                transitionMainMenu();

            }
        });

        // Add static data
        HorizontalPanel horiz = new HorizontalPanel();
        horiz.setHeight("100%");
        horiz.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        Label author = new Label();
        author.setText("Created by: Steven Gantz");
        horiz.add(author);

        mainMenu.add(title);
        mainMenu.add(Question);
        mainMenu.add(radioButtonPanel);
        mainMenu.add(next);
        mainMenu.add(horiz);

        return mainMenu;
    }

    /**
     * Move from the first part of the main menu to the second.
     */
    protected void transitionMainMenu() {
        // Remove everything except title from RootLayoutPanel
        final VerticalPanel main = (VerticalPanel) RootLayoutPanel.get().getWidget(0);

        // Save title and clear the panel
        Label title = (Label) main.getWidget(0);
        main.clear();

        // Build gui to gather data
        main.add(title);

        // Retrieve data out of scope without fussing with panels
        final ArrayList<TextBox> playerNames = new ArrayList<TextBox>();
        final ArrayList<RadioButton> humanButtons = new ArrayList<RadioButton>();
        final ArrayList<RadioButton> cheatButtons = new ArrayList<RadioButton>();

        // Create prompts for player names
        for (int i = 0; i < totalPlayers; i++) {
            HorizontalPanel dataPanel = new HorizontalPanel();

            Label playerLabel = new Label();
            playerLabel.setText("Player " + String.valueOf(i + 1) + " ");
            TextBox playerName = new TextBox();
            playerNames.add(playerName);
            RadioButton human = new RadioButton("choice" + String.valueOf(i), "Human");
            humanButtons.add(human);
            RadioButton computer = new RadioButton("choice" + String.valueOf(i), "Computer");
            human.setValue(true);

            // Build panel
            dataPanel.add(playerLabel);
            dataPanel.add(playerName);
            dataPanel.add(human);
            dataPanel.add(computer);

            // Add new horizontal panel to main panel
            main.add(dataPanel);
        }

        // Build button to initialize game details
        Button startGame = new Button();
        startGame.setText("Begin Game");
        startGame.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                for (int i = 0; i < playerNames.size(); i++) {

                    // Create player
                    if (humanButtons.get(i).getValue()) {
                        // Window.alert("Player " + String.valueOf(i) + " is a
                        // human");
                        players.add(new HumanPlayer(playerNames.get(i).getText()));
                    } else {
                        // Window.alert("Player " + String.valueOf(i) + " is a
                        // cpu");
                        players.add(new HumanPlayer(playerNames.get(i).getText()));
                    }
                }

                // enable or disable cheats
                RadioButton yesCheat = cheatButtons.get(0);
                if(yesCheat.getValue()){
                    cheatsShown = true;
                } else {
                    cheatsShown = false;
                }
                
                // Start the game with the newly-gotten data
                closeMainMenuStartGame();
            }
        });
        
        main.add(startGame);
        
        // Create cheat options
        HorizontalPanel cheatPanel = new HorizontalPanel();
        RadioButton yesCheat = new RadioButton("cheats", "Enable cheats");
        yesCheat.setValue(true);
        RadioButton noCheat = new RadioButton("cheats", "Disable cheats");
        cheatPanel.add(yesCheat);
        cheatPanel.add(noCheat);
        cheatButtons.add(yesCheat);
        cheatButtons.add(noCheat);
        main.add(cheatPanel);
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

        // Create a bottom aligned VerticalPanel for information
        VerticalPanel staticInfo = new VerticalPanel();
        // staticInfo.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);

        Label hr = new Label();
        Label author = new Label();
        Label date = new Label();
        Label fsDesc = new Label();

        hr.setHeight("400");
        author.setHeight("200");
        date.setHeight("200");
        fsDesc.setHeight("200");

        hr.setText("------------------------------------------------------");
        author.setText("Steven Gantz");
        date.setText(String.valueOf(new Date()));
        fsDesc.setText("Press F11 to play in fullscreen");

        staticInfo.add(hr);
        staticInfo.add(author);
        staticInfo.add(date);
        staticInfo.add(fsDesc);

        final Button toggleButton = new Button();
        toggleButton.setText("Cheat: Disabled");
        toggleButton.setWidth("100%");
        
        if(cheatsShown){
            toggleButton.setVisible(true);
            toggleButton.setEnabled(true);
        } else {
            toggleButton.setVisible(false);
            toggleButton.setEnabled(false);
        }

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

        staticPanel.add(staticInfo);

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
            Label guessingSuccess = new Label();

            totalAttempts.setHeight("200");
            totalMatches.setHeight("200");
            guessingSuccess.setHeight("200");

            totalAttempts.setText("Total Attempts: 0");
            totalMatches.setText("Total Matches: 0");
            guessingSuccess.setText("Successful Match Rate: 0");

            VerticalPanel panel = new VerticalPanel();

            // Order of vertical panel
            panel.add(totalAttempts);
            panel.add(totalMatches);
            panel.add(guessingSuccess);

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
