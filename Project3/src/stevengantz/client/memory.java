package stevengantz.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.data.Appdata;
import stevengantz.memory.player.ComputerPlayer;
import stevengantz.memory.player.HumanPlayer;
import stevengantz.memory.player.Player;
import stevengantz.memory.structure.ContextMenu;
import stevengantz.memory.structure.CustomAnimation;
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
     * Open connection to server with deferred binding RPC class
     */
    MemoryGameServiceAsync gameServer;

    /**
     * The name of the person who created a lobby
     */
    String hostPlayerName;

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
     * For passing audio track
     */
    private Audio audioTrack;
    private boolean audioPlaying = false;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Initialize data members for game
        players = new ArrayList<Player>();
        this.board = buildBoard();
        names = new ArrayList<String>();
        isHuman = new ArrayList<Boolean>();

        gameServer = (MemoryGameServiceAsync) GWT.create(MemoryGameService.class);

        // Start the music
        this.audioTrack = Audio.createIfSupported();
        this.audioTrack.setLoop(true);
        this.audioTrack.setSrc("Futures.mp3");
        this.audioTrack.setVolume(0.05);

        // Create a panel that centers everything in main menu
        VerticalPanel mainmenu = buildMainMenuVisual();

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
        driver = new MemoryGameDriver(this.board, players, this.infoPanel, this.gameServer);

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
        mainMenu.setHeight(Window.getClientHeight() + "px");
        mainMenu.setWidth(Window.getClientWidth() + "px");
        mainMenu.setStyleName("RootLayoutPanelBackground");
        mainMenu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        // Add title bar
        Label title = new Label();
        title.setText("Memory");
        title.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 70, Unit.EM);
        title.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);
        title.getElement().getStyle().setColor("white");

        // Add player number section
        Label Question = new Label();
        Question.setText("Number of Players?");
        Question.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 225, Unit.EM);
        Question.getElement().getStyle().setColor("white");
        Question.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");

        HorizontalPanel radioButtonPanel = new HorizontalPanel();
        radioButtonPanel.setSpacing(25);
        final RadioButton onePlayer = new RadioButton("choice", "1");
        final RadioButton twoPlayer = new RadioButton("choice", "2");
        final RadioButton threePlayer = new RadioButton("choice", "3");
        final RadioButton fourPlayer = new RadioButton("choice", "4");
        onePlayer.setValue(true);
        onePlayer.getElement().getStyle().setColor("white");
        onePlayer.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        twoPlayer.getElement().getStyle().setColor("white");
        twoPlayer.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        threePlayer.getElement().getStyle().setColor("white");
        threePlayer.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        fourPlayer.getElement().getStyle().setColor("white");
        fourPlayer.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");

        radioButtonPanel.add(onePlayer);
        radioButtonPanel.add(twoPlayer);
        radioButtonPanel.add(threePlayer);
        radioButtonPanel.add(fourPlayer);

        HorizontalPanel internetPanel = new HorizontalPanel();
        final TextBox handle = new TextBox();
        handle.setText("Enter name");
        final Button internet = new Button("Attempting to Connect to Server...");

        internetPanel.add(internet);
        internetPanel.add(handle);

        Button next = new Button();
        next.setText("Next");
        next.setHeight(String.valueOf(Appdata.WINDOWHEIGHT / 15) + "px");
        next.setWidth(String.valueOf(Appdata.WINDOWWIDTH / 6) + "px");
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

        HorizontalPanel audio = new HorizontalPanel();
        final Button toggleMusic = new Button("Enable Music");
        toggleMusic.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (audioPlaying) {
                    audioTrack.pause();
                    audioPlaying = false;
                    toggleMusic.setText("Enable Music");
                } else {
                    audioTrack.play();
                    audioPlaying = true;
                    toggleMusic.setText("Disable Music");
                }
            }
        });
        audio.add(toggleMusic);

        // Add static data
        HorizontalPanel horiz = new HorizontalPanel();
        horiz.setHeight("100%");
        horiz.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        Label author = new Label();
        author.setText("Created by: Steven Gantz");
        author.getElement().getStyle().setColor("white");
        author.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        author.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 35, Unit.PX);
        horiz.add(author);

        mainMenu.add(title);
        mainMenu.add(Question);
        mainMenu.add(radioButtonPanel);
        mainMenu.add(internetPanel);
        mainMenu.add(next);
        mainMenu.add(audio);
        mainMenu.add(horiz);

        // Do initial handshake to make sure server is up and running
        this.gameServer.attemptHandshake(new AsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                internet.setText("Connected to Server...");
            }

            @Override
            public void onFailure(Throwable caught) {
                internet.setText("Could not connect to server...");
                Window.alert(caught.getMessage());
            }
        });

        // Set text to play or join based on whether game exists
        // Check if game is running to display text
        this.gameServer.isGameRunning(new AsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean gameRunning) {
                if (gameRunning) {
                    internet.setText("Game currently in progress... Unable to join...");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        });

        // See if there is an available lobby, if not, button may create one
        // The internet button's handler is defined here based on whether lobby
        // exists
        this.gameServer.isLobbyRunning(new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(Boolean isLobbyActive) {
                if (isLobbyActive) {
                    // Add click handler for join button
                    internet.setText("Join multiplayer lobby");
                    internet.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            gameServer.joinLobby(handle.getText().toString(), new AsyncCallback<Boolean>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert(caught.getMessage());
                                    Window.alert(String.valueOf(caught.getStackTrace()));
                                }

                                @Override
                                public void onSuccess(Boolean result) {
                                    if (result) {
                                        hostPlayerName = handle.getText().toString();
                                        multiplayerLobbyMenu();
                                    } else {
                                        internet.setText("Lobby is full... Try again later");
                                    }
                                }
                            });
                        }
                    });
                    handle.addKeyPressHandler(new KeyPressHandler() {
                        @Override
                        public void onKeyPress(KeyPressEvent event) {
                            if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                                gameServer.joinLobby(handle.getText().toString(), new AsyncCallback<Boolean>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert(caught.getMessage());
                                        Window.alert(String.valueOf(caught.getStackTrace()));
                                    }

                                    @Override
                                    public void onSuccess(Boolean result) {
                                        if (result) {
                                            hostPlayerName = handle.getText().toString();
                                            multiplayerLobbyMenu();
                                        } else {
                                            internet.setText("Lobby is full... Try again later");
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    internet.setText("Start multiplayer lobby");
                    internet.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            gameServer.startLobby(handle.getText().toString(), new AsyncCallback<Boolean>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert(caught.getMessage());
                                    Window.alert(String.valueOf(caught.getStackTrace()));
                                }

                                @Override
                                public void onSuccess(Boolean result) {
                                    return;
                                }
                            });
                            hostPlayerName = handle.getText().toString();
                            if (!hostPlayerName.equals("\"wipeserver\"")) {
                                multiplayerLobbyMenu();
                            }
                        }
                    });
                    handle.addKeyPressHandler(new KeyPressHandler() {
                        @Override
                        public void onKeyPress(KeyPressEvent event) {
                            if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                                gameServer.startLobby(handle.getText().toString(), new AsyncCallback<Boolean>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert(caught.getMessage());
                                        Window.alert(String.valueOf(caught.getStackTrace()));
                                    }

                                    @Override
                                    public void onSuccess(Boolean result) {
                                        return;
                                    }
                                });
                                hostPlayerName = handle.getText().toString();
                                if (!hostPlayerName.equals("\"wipeserver\"")) {
                                    multiplayerLobbyMenu();
                                }
                            }
                        }
                    });
                }

            }
        });

        return mainMenu;
    }

    /**
     * Replaces the transitionMainMenu by creating a lobby view for users before
     * the game starts.
     */
    protected void multiplayerLobbyMenu() {
        // Remove everything except title from RootLayoutPanel
        final VerticalPanel lobbyPanel = (VerticalPanel) RootLayoutPanel.get().getWidget(0);

        // Save title and clear the panel
        Label title = (Label) lobbyPanel.getWidget(0);
        lobbyPanel.clear();
        title.setText("Multiplayer Lobby");

        HorizontalPanel midPanel = new HorizontalPanel();

        // Create player listing panel
        VerticalPanel playerList = new VerticalPanel();

        // Create player horizontal panels
        HorizontalPanel player1Panel = new HorizontalPanel();
        HorizontalPanel player2Panel = new HorizontalPanel();
        HorizontalPanel player3Panel = new HorizontalPanel();
        HorizontalPanel player4Panel = new HorizontalPanel();

        // Create player listings
        final Label player1 = new Label(this.hostPlayerName);
        final Label player2 = new Label("");
        final Label player3 = new Label("");
        final Label player4 = new Label("");

        // Create ready buttons
        final Image player1Ready = new Image(Appdata.READY);
        final Image player2Ready = new Image(Appdata.READY);
        final Image player3Ready = new Image(Appdata.READY);
        final Image player4Ready = new Image(Appdata.READY);

        // hide other buttons
        player2Ready.setVisible(false);
        player3Ready.setVisible(false);
        player4Ready.setVisible(false);

        // Set label attributes
        player1.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 225, Unit.EM);
        player1.getElement().getStyle().setColor("white");
        player1.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        player2.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 225, Unit.EM);
        player2.getElement().getStyle().setColor("white");
        player2.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        player3.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 225, Unit.EM);
        player3.getElement().getStyle().setColor("white");
        player3.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        player4.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 225, Unit.EM);
        player4.getElement().getStyle().setColor("white");
        player4.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");

        // Add player listings to panels
        player1Panel.add(player1);
        player1Panel.add(player1Ready);

        player2Panel.add(player2);
        player2Panel.add(player2Ready);

        player3Panel.add(player3);
        player3Panel.add(player3Ready);

        player4Panel.add(player4);
        player4Panel.add(player4Ready);

        playerList.add(player1Panel);
        playerList.add(player2Panel);
        playerList.add(player3Panel);
        playerList.add(player4Panel);

        // Create chat box
        VerticalPanel chatPanel = new VerticalPanel();
        final TextArea chat = new TextArea();
        chat.setCharacterWidth(Window.getClientWidth() / 18);
        chat.setVisibleLines(Window.getClientHeight() / 30);
        chat.getElement().setScrollTop(chat.getElement().getScrollHeight());
        chat.setEnabled(false);
        chat.getElement().getStyle().setProperty("resize", "none");

        HorizontalPanel chatEntryPanel = new HorizontalPanel();
        final TextBox chatEntry = new TextBox();
        chatEntry.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {

                    // Send message to server and retrieve new message
                    gameServer.addToChat(hostPlayerName, chatEntry.getText().toString(), new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert("Error sending...\n" + caught.getMessage());
                        }

                        @Override
                        public void onSuccess(String chatString) {
                            // Empty the chat log
                            chat.setText("");
                            chat.setText(chatString);
                            chat.getElement().setScrollTop(chat.getElement().getScrollHeight());
                        }
                    });

                    // Clear the chat box after retrieving it
                    chatEntry.setText("");
                }
            }
        });
        chatEntry.setVisibleLength(Window.getClientWidth() / 20);

        final Button send = new Button();
        send.setText("Send");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                // Send message to server and retrieve new message
                gameServer.addToChat(hostPlayerName, chatEntry.getText().toString(), new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Error sending...\n" + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(String chatString) {
                        // Empty the chat log
                        chat.setText("");
                        chat.setText(chatString);
                        chat.getElement().setScrollTop(chat.getElement().getScrollHeight());
                    }

                });

                // Clear the chat box after retrieving it
                chatEntry.setText("");
            }
        });

        // Horizontal panel below textarea
        chatEntryPanel.add(chatEntry);
        chatEntryPanel.add(send);

        chatPanel.add(chat);
        chatPanel.add(chatEntryPanel);

        midPanel.add(playerList);
        midPanel.add(chatPanel);

        // Add single panel for disconnect button
        HorizontalPanel disconnectPanel = new HorizontalPanel();
        final Button disconnect = new Button("Disconnect from lobby");
        final Button startGame = new Button("Start Game");
        disconnect.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // disconnect from server
                gameServer.disconnectFromLobby(hostPlayerName, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to disconnect from game: " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        Location.reload();
                    }
                });
            }
        });
        startGame.addClickHandler(new ClickHandler() {            
            @Override
            public void onClick(ClickEvent event) {
                // Set number of players from server
                // build playerlist
                gameServer.getCurrentPlayers(new AsyncCallback<ArrayList<String>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Couldn't get players from server: " + caught.getMessage());                      
                    }
                    @Override
                    public void onSuccess(ArrayList<String> serverPlayers) {
                        ArrayList<Player> serverEntries = new ArrayList<Player>();
                        
                        // Add self as a human player                       
                        for(String player : serverPlayers){
                            if(player.equals(hostPlayerName)){
                                serverEntries.add(new HumanPlayer(hostPlayerName));
                            } else {
                                serverEntries.add(new ComputerPlayer(player, 2));
                            }
                        }                       
                        players = serverEntries;                    
                        closeMainMenuStartGame();
                    }
                });
            }
        });
        disconnectPanel.add(disconnect);
        disconnectPanel.add(startGame);

        // Add static data
        HorizontalPanel horiz = new HorizontalPanel();
        horiz.setHeight("100%");
        horiz.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        Label author = new Label();
        author.setText("Created by: Steven Gantz");
        author.getElement().getStyle().setColor("white");
        author.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        author.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 35, Unit.PX);
        horiz.add(author);

        // Add onclicks for buttons to set as ready
        player1Ready.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (hostPlayerName.equals(player1.getText().toString())) {
                    gameServer.addToChat(hostPlayerName, player1.getText().toString() + " is ready...",
                            new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(String chatString) {
                            // Empty the chat log
                            chat.setText("");
                            chat.setText(chatString);
                            chat.getElement().setScrollTop(chat.getElement().getScrollHeight());
                        }

                    });
                }
            }
        });
        player2Ready.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (hostPlayerName.equals(player2.getText().toString())) {

                    gameServer.addToChat(hostPlayerName, player2.getText().toString() + " is ready...",
                            new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(String chatString) {
                            // Empty the chat log
                            chat.setText("");
                            chat.setText(chatString);
                            chat.getElement().setScrollTop(chat.getElement().getScrollHeight());
                        }
                    });
                }
            }
        });
        player3Ready.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (hostPlayerName.equals(player3.getText().toString())) {
                    gameServer.addToChat(hostPlayerName, player3.getText().toString() + " is ready...",
                            new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(String chatString) {
                            // Empty the chat log
                            chat.setText("");
                            chat.setText(chatString);
                            chat.getElement().setScrollTop(chat.getElement().getScrollHeight());
                        }
                    });
                }
            }
        });
        player4Ready.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (hostPlayerName.equals(player4.getText().toString())) {
                    gameServer.addToChat(hostPlayerName, player4.getText().toString() + " is ready...",
                            new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(String chatString) {
                            // Empty the chat log
                            chat.setText("");
                            chat.setText(chatString);
                            chat.getElement().setScrollTop(chat.getElement().getScrollHeight());
                        }
                    });
                }
            }
        });

        // Build gui to gather data
        lobbyPanel.add(title);
        lobbyPanel.add(midPanel);
        lobbyPanel.add(disconnectPanel);
        lobbyPanel.add(horiz);

        // After created, occasionally retrieve new data from server
        Timer updateTimer = new Timer() {
            @Override
            public void run() {
                // Retrieve messages
                gameServer.getChat(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Couldn't get chat: \n" + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        chat.setText("");
                        chat.setText(result);
                        chat.getElement().setScrollTop(chat.getElement().getScrollHeight());
                    }
                });

                // Get the names of first four connected players
                gameServer.getCurrentPlayers(new AsyncCallback<ArrayList<String>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Couldn't retrieve other players: \n" + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(ArrayList<String> serverPlayerList) {
                        // Empty all names so none stay
                        player1.setText("");
                        player1Ready.setVisible(false);
                        player2.setText("");
                        player2Ready.setVisible(false);
                        player3.setText("");
                        player3Ready.setVisible(false);
                        player4.setText("");
                        player4Ready.setVisible(false);
                        try {
                            player1.setText(serverPlayerList.get(0));
                            if (!player1.getText().equals(""))
                                player1Ready.setVisible(true);
                            player2.setText(serverPlayerList.get(1));
                            if (!player1.getText().equals(""))
                                player2Ready.setVisible(true);
                            player3.setText(serverPlayerList.get(2));
                            if (!player1.getText().equals(""))
                                player3Ready.setVisible(true);
                            player4.setText(serverPlayerList.get(3));
                            if (!player1.getText().equals(""))
                                player4Ready.setVisible(true);
                        } catch (NullPointerException npe) {
                            // Exceptions shouldn't be used for control flow.
                            // This is a good example of that!
                            return;
                        } catch (IndexOutOfBoundsException ibe){
                            return;
                        }
                    }
                });

                // Retrieve all ready statuses and set ready or not for each
            }
        };

        // Schedule to repeat until it is destroyed when game starts
        updateTimer.scheduleRepeating(1000);

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
            playerLabel.getElement().getStyle().setColor("white");

            TextBox playerName = new TextBox();
            playerNames.add(playerName);
            RadioButton human = new RadioButton("choice" + String.valueOf(i), "Human");
            humanButtons.add(human);
            RadioButton computer = new RadioButton("choice" + String.valueOf(i), "Computer");
            human.setValue(true);
            human.getElement().getStyle().setColor("white");
            computer.getElement().getStyle().setColor("white");

            // First player can't be an AI
            if (i == 0) {
                computer.setEnabled(false);
            }

            // Build panel with labels
            {
                dataPanel.add(playerLabel);
                dataPanel.add(playerName);
            }

            // Build values in panel
            {
                dataPanel.add(human);
                dataPanel.add(computer);
            }

            // Add new horizontal panel to main panel
            main.add(dataPanel);
        }

        // Build button to initialize game details
        Button startGame = new Button();
        startGame.setText("Begin Game");
        startGame.setHeight(String.valueOf(Appdata.WINDOWHEIGHT / 15) + "px");
        startGame.setWidth(String.valueOf(Appdata.WINDOWWIDTH / 6) + "px");
        startGame.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                // initially no cpu players playing
                Appdata.AiIsPlaying = false;
                for (int i = 0; i < playerNames.size(); i++) {

                    // Create player
                    if (humanButtons.get(i).getValue()) {
                        players.add(new HumanPlayer(playerNames.get(i).getText()));
                    } else {
                        players.add(new ComputerPlayer(playerNames.get(i).getText(), 0));
                        Appdata.AiIsPlaying = true;
                    }
                }

                // enable or disable cheats
                RadioButton yesCheat = cheatButtons.get(0);
                if (yesCheat.getValue()) {
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
        yesCheat.getElement().getStyle().setColor("white");
        yesCheat.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        yesCheat.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 40, Unit.PX);
        RadioButton noCheat = new RadioButton("cheats", "Disable cheats");
        noCheat.getElement().getStyle().setColor("white");
        noCheat.getElement().getStyle().setProperty("textShadow", "2px 2px 2px #000");
        noCheat.getElement().getStyle().setFontSize(Appdata.WINDOWHEIGHT / 40, Unit.PX);
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
        centerGamePanel.addDomHandler(new ContextMenu(), ContextMenuEvent.getType());

        // Generate information panel
        this.infoPanel = createInfoPanel(numberOfPlayers);
        this.infoPanel.addDomHandler(new ContextMenu(), ContextMenuEvent.getType());

        // Generate general game data and static info panel
        VerticalPanel staticPanel = createStaticPanel();
        staticPanel.addDomHandler(new ContextMenu(), ContextMenuEvent.getType());

        // Add individual pieces to DockPanel
        mainPanel.addEast(staticPanel, Appdata.WINDOWWIDTH / 5);
        mainPanel.addWest(infoPanel, Appdata.WINDOWWIDTH / 5);
        mainPanel.addSouth(centerGamePanel, Appdata.WINDOWHEIGHT);

        return mainPanel;
    }

    protected VerticalPanel createStaticPanel() {
        VerticalPanel staticPanel = new VerticalPanel();
        staticPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        // Create structure
        HorizontalPanel horiz = new HorizontalPanel();

        // Generate data
        Label titleLabel = new Label();
        titleLabel.setText("Information Panel");
        titleLabel.getElement().getStyle().setFontSize(3, Unit.EM);

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
        rules.get(0).getElement().getStyle().setFontSize(1.5, Unit.EM);
        rules.get(1).setText("1. Select a card and commit it to memory as best you can.");
        rules.get(1).getElement().getStyle().setFontSize(1.5, Unit.EM);
        rules.get(2).setText("2. Select another card, try to match them up!");
        rules.get(2).getElement().getStyle().setFontSize(1.5, Unit.EM);
        rules.get(3).setText("3. If you don't make a match, it is the next player's turn.");
        rules.get(3).getElement().getStyle().setFontSize(1.5, Unit.EM);
        rules.get(4).setText("4. Otherwise you found a match! Go again!");
        rules.get(4).getElement().getStyle().setFontSize(1.5, Unit.EM);
        rules.get(5).setText("5. Whoever has the most matches at the end wins!");
        rules.get(5).getElement().getStyle().setFontSize(1.5, Unit.EM);
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

        hr.setText("------------------------------------------------------------");
        author.setText("Steven Gantz");
        DateTimeFormat dtf = DateTimeFormat.getFormat("EEE, MMM d, yyyy");
        date.setText(dtf.format(new Date()));
        fsDesc.setText("Press F11 to play in fullscreen");

        staticInfo.add(hr);
        staticInfo.add(author);
        staticInfo.add(date);
        staticInfo.add(fsDesc);

        final Button toggleButton = new Button();
        toggleButton.setText("Cheat: Disabled");
        toggleButton.setWidth("100%");

        if (cheatsShown) {
            toggleButton.setVisible(true);
            toggleButton.setEnabled(true);
        } else {
            toggleButton.setVisible(false);
            toggleButton.setEnabled(false);
        }

        toggleButton.addMouseDownHandler(new MouseDownHandler() {

            @Override
            public void onMouseDown(MouseDownEvent event) {
                toggleButton.setText("Cheat: Enabled");
                // assign phase into phase 2 to stop play
                driver.gamedata.gamePhase = 2;
                // Window.alert("Enable cheat");
                for (int i = 0; i < board.totalCards(); i++) {
                    board.getCard(i).face.setUrl(board.getCard(i).frontFace.getUrl());
                }
            }
        });

        toggleButton.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
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

        // Add audio controls
        HorizontalPanel audioControls = new HorizontalPanel();
        final Button toggle = new Button("");
        if (audioPlaying) {
            toggle.setText("Music: On");
        } else {
            toggle.setText("Music: Off");
        }
        toggle.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (audioPlaying) {
                    audioTrack.pause();
                    audioPlaying = false;
                    toggle.setText("Music: Off");
                } else {
                    audioTrack.play();
                    audioPlaying = true;
                    toggle.setText("Music: On");
                }
            }
        });
        Label Volume = new Label("Volume:");
        Button volume0 = new Button("5%");
        volume0.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {                
                if(event.isShiftKeyDown()){
                    
                } else {
                    audioTrack.setVolume(0.05);
                }
            }
        });
        Button volume1 = new Button("25%");
        volume1.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(event.isShiftKeyDown()){
                    Animation anim = new CustomAnimation(0);
                } else {
                    audioTrack.setVolume(0.25);
                }
            }
        });
        Button volume2 = new Button("50%");
        volume2.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(event.isShiftKeyDown()){
                    Animation anim = new CustomAnimation(1);
                } else {
                    audioTrack.setVolume(0.50);
                }
            }
        });
        Button volume3 = new Button("75%");
        volume3.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(event.isShiftKeyDown()){
                    Animation anim = new CustomAnimation(2);
                } else {
                    audioTrack.setVolume(0.75);
                }
            }
        });
        Button volume4 = new Button("100%");
        volume4.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(event.isShiftKeyDown()){
                    Animation anim = new CustomAnimation(3);
                } else {
                    audioTrack.setVolume(1);
                }
            }
        });

        audioControls.add(Volume);
        audioControls.add(volume0);
        audioControls.add(volume1);
        audioControls.add(volume2);
        audioControls.add(volume3);
        audioControls.add(volume4);
        audioControls.add(toggle);

        staticPanel.add(audioControls);

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
            Label totalPoints = new Label();

            totalAttempts.setHeight("200");
            totalMatches.setHeight("200");
            guessingSuccess.setHeight("200");
            totalPoints.setHeight("200");

            totalAttempts.setText("Total Attempts: 0");
            totalMatches.setText("Total Matches: 0");
            guessingSuccess.setText("Successful Match Rate: 0");
            totalPoints.setText("Total points: 0");

            totalAttempts.getElement().getStyle().setFontSize(2.5, Unit.EM);
            totalMatches.getElement().getStyle().setFontSize(2.5, Unit.EM);
            guessingSuccess.getElement().getStyle().setFontSize(2.5, Unit.EM);
            totalPoints.getElement().getStyle().setFontSize(2.5, Unit.EM);

            VerticalPanel panel = new VerticalPanel();

            // Order of vertical panel
            panel.add(totalAttempts);
            panel.add(totalMatches);
            panel.add(guessingSuccess);
            panel.add(totalPoints);

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
                this.board.getCard(currentCardIndex).face.addStyleName("Padding");

                final Image face = this.board.getCard(currentCardIndex).face;

                final int currentCardNum = currentCardIndex;

                // Add a mouse over handler to the face
                face.addDomHandler(new MouseOverHandler() {
                    @Override
                    public void onMouseOver(MouseOverEvent event) {
                        // if rear face is showing, show selected
                        if (face.getUrl().equals(board.getCard(currentCardNum).frontFace.getUrl())) {
                            return;
                        }
                        if (face.getUrl().equals(board.getCard(currentCardNum).rearFace.getUrl())) {
                            face.setUrl(Appdata.SELECTEDREARIMAGE);
                        } else {
                            // Don't do anything
                        }
                    }
                }, MouseOverEvent.getType());

                face.addDomHandler(new MouseOutHandler() {
                    @Override
                    public void onMouseOut(MouseOutEvent event) {
                        if (face.getUrl().equals(board.getCard(currentCardNum).selectedRearFace.getUrl())) {
                            face.setUrl(Appdata.REARIMAGE);
                        } else {
                            // Don't do anything
                        }
                    }
                }, MouseOutEvent.getType());

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
            Image selected = new Image();

            // Generate card's pair
            Image frontPair = new Image();
            Image backPair = new Image();
            Image selectedPair = new Image();

            // Assign the image
            front.setUrl(frontImageList.get(index));
            back.setUrl(Appdata.REARIMAGE);
            selected.setUrl(Appdata.SELECTEDREARIMAGE);

            frontPair.setUrl(frontImageList.get(index));
            backPair.setUrl(Appdata.REARIMAGE);
            selectedPair.setUrl(Appdata.SELECTEDREARIMAGE);

            // Assign relative sizes
            setCardPixelSize(front);
            setCardPixelSize(back);
            setCardPixelSize(selected);

            setCardPixelSize(frontPair);
            setCardPixelSize(backPair);
            setCardPixelSize(selectedPair);

            // Generate MemoryCard object
            MemoryCard mem = generateCard(front, back, selected);
            MemoryCard memPair = generateCard(frontPair, backPair, selectedPair);

            // Set card to wild if it is ada
            if (frontImageList.get(index).equals("img/cardfaces/ada.png")) {
                mem.iswild = true;
                memPair.iswild = true;
            } else {
                mem.iswild = false;
                memPair.iswild = false;
            }

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

    protected MemoryCard generateCard(Image front, Image back, Image selected) {
        return new MemoryCard(front, back, selected);
    }
}
