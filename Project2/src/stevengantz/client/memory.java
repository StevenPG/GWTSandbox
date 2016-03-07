package stevengantz.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.data.Appdata;
import stevengantz.memory.player.Player;
import stevengantz.memory.structure.MemoryGameBoard;
import stevengantz.memory.structure.MemoryGameDriver;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class memory implements EntryPoint {

    /**
     * Game board stored in memory
     */
    MemoryGameBoard board;

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
        players.add(null);
        int totalPlayers = 2; //TODO TMP

        // Assign background image to RootPanel
        RootPanel.get().setStyleName("RootPanelStyle");

        // Build general layout of page
        DockPanel mainPanel = createVisualStructure(totalPlayers);

        // Initialize the GUI and game driver
        MemoryGameDriver driver = new MemoryGameDriver(this.board, players);

        // Start GUI
        RootPanel.get().add(mainPanel);
    }

    /**
     * Create the overall visual structure of the game screen.
     * 
     * @return DockPanel initialized with all elements
     */
    protected DockPanel createVisualStructure(int numberOfPlayers) {
        DockPanel mainPanel = new DockPanel();

        // Generate game panel
        VerticalPanel centerGamePanel = buildGamePanel();

        // Generate information panel
        TabLayoutPanel infoPanel = createInfoPanel(numberOfPlayers);

        // Add individual pieces to DockPanel
        mainPanel.add(infoPanel, DockPanel.WEST);
        mainPanel.add(centerGamePanel, DockPanel.CENTER);

        return mainPanel;
    }

    /**
     * Create an information panel that will let you view all of the player's
     * current statistics by their specific tab.
     * 
     * @return infoPanel initialized with the correct number of players
     */
    protected TabLayoutPanel createInfoPanel(int numberOfPlayers) {
        TabLayoutPanel infoPanel = new TabLayoutPanel(Appdata.WINDOWWIDTH/3, Unit.PX);
        infoPanel.setAnimationDuration(1000);
        
        // Add the correct number of panels
        String[] tabTitles = new String[numberOfPlayers];
        
        for(int i = 0; i < numberOfPlayers; i++){
            HTML text = new HTML("Test");
            //infoPanel.add(text, String.valueOf(i));
            infoPanel.add(text, text);
        }

        infoPanel.selectTab(0);
        infoPanel.ensureDebugId("infoPanel");
        
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
     * @return
     */
    protected VerticalPanel buildGamePanel() {

        // Generate overall vertical panel
        VerticalPanel vert = new VerticalPanel();

        // Assign attributes to the vertical panel
        vert.setWidth("50%");
        vert.setHeight("50%");
        vert.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        vert.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        // Keep track of each card being entered
        int currentCardIndex = 0;

        // Add NUMROWS horizontal panels
        for (int i = 0; i < Appdata.NUMROWS; i++) {
            HorizontalPanel horiz = new HorizontalPanel();

            for (int j = 0; j < Appdata.NUMCOLS; j++) {

                // Add image and iterate card
                horiz.add(this.board.getCard(currentCardIndex).face);
                currentCardIndex++;
            }

            vert.add(horiz);
        }
        return vert;
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

        // Loop through for each card
        for (int index = 0; index < Appdata.NUMBEROFCARDS; index++) {

            // Generate the image in memory
            Image front = new Image();
            Image back = new Image();

            // Generate card's pair
            Image frontPair = new Image();
            Image backPair = new Image();

            // Assign the image
            front.setUrl(Appdata.ERRORIMAGE);
            back.setUrl(Appdata.REARIMAGE);

            frontPair.setUrl(Appdata.ERRORIMAGE);
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
