package stevengantz.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("memorygame")
public interface MemoryGameService extends RemoteService {

    // Pre game methods
    /**
     * Declare to the client that everything it working by returning nothing
     * 
     * @see stevengantz.client.MemoryGameService#attemptHandshake()
     */
    void attemptHandshake();

    /**
     * Return whether or not there is a game currently running.
     * 
     * @return whether the game is currently running
     * 
     * @see stevengantz.client.MemoryGameService#isGameRunning(com.google.gwt.user.client.rpc.AsyncCallback)
     */
    boolean isGameRunning();

    /**
     * Start game lobby for players to join before the game
     */
    void startLobby(String playerName);

    /**
     * Stop game lobby completely
     */
    void closeLobby();

    /**
     * Return whether or not there is an open lobby
     * 
     * @see stevengantz.client.MemoryGameService#isLobbyRunning()
     */
    boolean isLobbyRunning();

    /**
     * Sends a message into the chat log, which is then returned to client for
     * display. The server does the work of converting the arraylist into a
     * string for the client.
     */
    String addToChat(String sender, String msg);
    
    /**
     * Retrieves the current chat log and turns into a string for display.
     * This is used when the client updates the chat log, now while entering a new message.
     */
    String getChat();
    
    /**
     * Join a pre-created game lobby before game
     */
    boolean joinLobby(String PlayerName);

    /**
     * Retrieve the current players in the lobby for display in client.
     * 
     * @return list of the current players
     */
    ArrayList<String> getCurrentPlayers();
}
