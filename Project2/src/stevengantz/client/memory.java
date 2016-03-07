package stevengantz.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
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
        int totalPlayers = 2; // TODO TMP

        // Build general layout of page
        DockLayoutPanel mainPanel = createVisualStructure(totalPlayers);

        // Initialize the GUI and game driver
        MemoryGameDriver driver = new MemoryGameDriver(this.board, players);

        // Start GUI
        RootLayoutPanel.get().add(mainPanel);
    }

    /**
     * Create the overall visual structure of the game screen.
     * 
     * @return DockPanel initialized with all elements
     */
    protected DockLayoutPanel createVisualStructure(int numberOfPlayers) {
        DockLayoutPanel mainPanel = new DockLayoutPanel(Unit.PX);

        // Attributes of base panel
        mainPanel.setWidth("100%");
        mainPanel.setHeight("100%");
        mainPanel.setStyleName("RootBackgroundStyle");

        // Generate title panel
        HorizontalPanel title = new HorizontalPanel();
        TextBox titleBox = new TextBox();
        titleBox.setText("Test");
        title.add(titleBox);

        // Generate game panel
        VerticalPanel centerGamePanel = buildGamePanel();

        // Generate information panel
        TabLayoutPanel infoPanel = createInfoPanel(numberOfPlayers);

        // Generate general game data and static info panel
        VerticalPanel staticPanel = new VerticalPanel();
        HorizontalPanel a = new HorizontalPanel();
        TextBox test = new TextBox();
        test.setText("Super wide test");
        a.add(test);
        staticPanel.add(a);

        // Add individual pieces to DockPanel
        mainPanel.addEast(staticPanel, Appdata.WINDOWWIDTH/5);
        mainPanel.addWest(infoPanel, Appdata.WINDOWWIDTH/5);
        mainPanel.addSouth(centerGamePanel, Appdata.WINDOWWIDTH/2);

        return mainPanel;
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

        // create contents for tabs of tabpanel
        Label label1 = new Label("This is contents of TAB 1");
        label1.setHeight("200");
        Label label2 = new Label("This is contents of TAB 2");
        label2.setHeight("200");
        Label label3 = new Label("This is contents of TAB 3");
        label3.setHeight("200");

        // create titles for tabs
        String tab1Title = "TAB 1";
        String tab2Title = "TAB 2";
        String tab3Title = "TAB 3";

        // create tabs
        infoPanel.add(label1, tab1Title);
        infoPanel.add(label2, tab2Title);
        infoPanel.add(label3, tab3Title);

        
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
    protected VerticalPanel buildGamePanel() {

        // Generate overall vertical panel
        VerticalPanel vert = new VerticalPanel();

        // Assign attributes to the vertical panel
        vert.setWidth("100%");
        vert.setHeight("100%");
        vert.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        vert.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        vert.setStyleName("RootBackgroundLayout");

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
