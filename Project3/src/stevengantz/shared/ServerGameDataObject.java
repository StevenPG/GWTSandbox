package stevengantz.shared;

import java.util.ArrayList;

/**
 * @author Steven Gantz This class will be stored in the server and contain all
 *         information required to keep track of the single game being played
 *         using RPC routed through the RemoteServiceServlet.
 */
public class ServerGameDataObject {

    /**
     * Specify whether the game has started and new players are able to join.
     */
    private boolean allowToJoin;

    /**
     * Total number of players in the game
     */
    private int totalPlayers;

    /**
     * Boolean declaring whether a game is currently running
     */
    private boolean isGameRunning;
    
    /**
     * Boolean declaring whether a lobby exists
     */
    private boolean isLobbyStarted;
    
    /**
     * The list containing all of the strings in the chat log.
     */
    public ArrayList<String> lobbychat;

    /**
     * @return the isLobbyStarted
     */
    public boolean isLobbyStarted() {
        return isLobbyStarted;
    }

    /**
     * Start the lobby
     */
    public void startLobby() {
        this.isLobbyStarted = true;
    }
    
    /**
     * Close the lobby
     */
    public void closeLobby(){
        this.isLobbyStarted = false;
    }

    /**
     * @return the totalPlayers
     */
    public int getTotalPlayers() {
        return totalPlayers;
    }
    
    /**
     * General constructor that initializes values
     */
    public ServerGameDataObject() {
        this.isGameRunning = false;
        this.totalPlayers = 0;
        this.allowToJoin = true;
        this.lobbychat = new ArrayList<String>();
    }

    /**
     * Set the current total number of players
     * 
     * @param totalPlayers
     * @throws IllegalArgumentException
     *             if too many or too little players are set
     */
    public void setTotalPlayers(int totalPlayers) throws IllegalArgumentException {
        if (totalPlayers > 4 || totalPlayers < 1) {
            throw new IllegalArgumentException();
        } else {
            this.totalPlayers = totalPlayers;
        }
    }

    /**
     * Add players to the current total number of players
     * 
     * @param addedPlayers
     * @throws IllegalArgumentException
     *             if too many or too little players are added
     */
    public void addToTotalPlayers(int addedPlayers) throws IllegalArgumentException {
        if (addedPlayers > 4 || totalPlayers < 1) {
            throw new IllegalArgumentException();
        } else {
            this.totalPlayers += addedPlayers;
        }
    }

    /**
     * @return whether new users can join the lobby
     */
    public boolean isAllowToJoin() {
        return allowToJoin;
    }

    /**
     * @param allowToJoin
     *            set if new users can join the lobby
     */
    public void setAllowToJoin(boolean allowToJoin) {
        this.allowToJoin = allowToJoin;
    }

    /**
     * @return if there is a game currently running in the servlet
     */
    public boolean isGameRunning() {
        return isGameRunning;
    }

    /**
     * This method sets allowToJoin depending on whether the game is active or
     * not
     * 
     * @param isGameRunning
     *            set whether there is a game currently running
     */
    public void setGameRunning(boolean isGameRunning) {
        this.isGameRunning = isGameRunning;
        if (isGameRunning) {
            this.allowToJoin = false;
        } else {
            this.allowToJoin = true;
        }
    }

}
