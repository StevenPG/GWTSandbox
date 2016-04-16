package stevengantz.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Steven Gantz The asynchronous counterpart to the MemoryGameService
 */
public interface MemoryGameServiceAsync {

    /**
     * Tell the server you've clicked start. Then poll on a timer to see if
     * the game can start.
     */
    public void startGame(String playerName, AsyncCallback<Void> callback);
    
    /**
     * Poll server if everyone has clicked start
     */
    public void haveAllStarted(AsyncCallback<Boolean> callback);
    
    /**
     * Declare to the client that everything it working by returning nothing
     * 
     * @see stevengantz.client.MemoryGameService#attemptHandshake()
     */
    public void attemptHandshake(AsyncCallback<Boolean> callback);

    /**
     * Return whether or not there is a game currently running.
     * 
     * @return whether the game is currently running
     * 
     * @see stevengantz.client.MemoryGameService#isGameRunning(com.google.gwt.user.client.rpc.AsyncCallback)
     */
    public void isGameRunning(AsyncCallback<Boolean> callback);

    /**
     * Start game lobby for players to join before the game
     */
    public void startLobby(String playerName, AsyncCallback<Boolean> callback);

    /**
     * Stop game lobby completely
     */
    public void closeLobby(AsyncCallback<Boolean> callback);

    /**
     * Return whether or not there is an open lobby
     * 
     * @see stevengantz.client.MemoryGameService#isLobbyRunning()
     */
    public void isLobbyRunning(AsyncCallback<Boolean> callback);

    /**
     * Sends a message into the chat log, which is then returned to client for
     * display. The server does the work of converting the arraylist into a
     * string for the client.
     */
    public void addToChat(String sender, String msg, AsyncCallback<String> callback);

    /**
     * Retrieves the current chat log and turns into a string for display. This
     * is used when the client updates the chat log, now while entering a new
     * message.
     */
    public void getChat(AsyncCallback<String> callback);

    /**
     * Join a pre-created game lobby before game
     */
    public void joinLobby(String PlayerName, AsyncCallback<Boolean> callback);

    /**
     * Disconnects player from lobby so another can join.
     * @param PlayerName
     *            - the name of the caller to be removed from party
     * @param callback
     *            - the callback method for success or failure.
     */
    public void disconnectFromLobby(String PlayerName, AsyncCallback<Void> callback);

    /**
     * Retrieve the current players in the lobby for display in client.
     * 
     * @return list of the current players
     */
    public void getCurrentPlayers(AsyncCallback<ArrayList<String>> callback);
}
