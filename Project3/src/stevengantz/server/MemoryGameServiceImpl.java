package stevengantz.server;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import stevengantz.client.MemoryGameService;
import stevengantz.shared.PlayerContainer;
import stevengantz.shared.ServerGameDataObject;

/**
 * @author Steven Gantz Server side implementation of the RPC service
 */
public class MemoryGameServiceImpl extends RemoteServiceServlet implements MemoryGameService {

    /**
     * Generated SerialID for servlet
     */
    private static final long serialVersionUID = -8624123217630438824L;

    /**
     * Contains data important to the current running game
     */
    ServerGameDataObject game;

    /**
     * Contains data important to currently connected players
     */
    PlayerContainer players;

    /**
     * Server configuration for use in initialization
     */
    @SuppressWarnings("unused")
    private ServletConfig config;

    /**
     * Constructor for servlet where initial values for servlet are generated
     * and assigned.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.config = config;
        this.game = new ServerGameDataObject();
        players = new PlayerContainer();
    }

    /**
     * Retrieve the current players in the lobby for display in client.
     * 
     * @return list of the current players
     */
    @Override
    public ArrayList<String> getCurrentPlayers() {
        return this.players.getPlayers();
    }

    /**
     * Sends a message into the chat log, which is then returned to client for
     * display. The server does the work of converting the arraylist into a
     * string for the client. If the message entered is "clear", the chat is
     * globally cleared.
     */
    @Override
    public String addToChat(String sender, String msg) {

        // Empty the chat log if clear command comes through
        if (msg.equals("\"clear\"")) {
            this.game.lobbychat.clear();
            this.game.lobbychat.add("Chat Log Cleared...");
            return this.game.lobbychat.get(0);
        }

        // Build message and save
        StringBuilder msgbuilder = new StringBuilder();
        msgbuilder.append(new Date());
        msgbuilder.append(" : ");
        msgbuilder.append(sender);
        msgbuilder.append(" : ");
        msgbuilder.append(msg);
        msgbuilder.append("\n");
        this.game.lobbychat.add(msgbuilder.toString());

        // Break the lobbychat into a string to display
        StringBuilder chatbuilder = new StringBuilder();
        for (String line : this.game.lobbychat) {
            chatbuilder.append(line);
        }
        return chatbuilder.toString();
    }

    /**
     * Retrieves the current chat log and turns into a string for display. This
     * is used when the client updates the chat log, now while entering a new
     * message.
     */
    @Override
    public String getChat() {
        // Break the lobbychat into a string to display
        StringBuilder chatbuilder = new StringBuilder();
        for (String line : this.game.lobbychat) {
            chatbuilder.append(line);
        }
        return chatbuilder.toString();
    }

    /**
     * Declare to the client that everything it working by returning nothing
     * 
     * @see stevengantz.client.MemoryGameService#attemptHandshake()
     */
    @Override
    public void attemptHandshake() {
        return;
    }

    /**
     * Return whether or not there is a game currently running.
     * 
     * @return whether the game is currently running
     * 
     * @see stevengantz.client.MemoryGameService#isGameRunning(com.google.gwt.user.client.rpc.AsyncCallback)
     */
    @Override
    public boolean isGameRunning() {
        return this.game.isGameRunning();
    }

    /**
     * Start game lobby for players to join before the game
     */
    @Override
    public void startLobby(String PlayerName) {
        this.game.startLobby();
        this.players.PlayerNames.add(PlayerName);
    }

    /**
     * Join a pre-created game lobby before game if there is no game currently
     * running and there is a lobby running. Also cannot join if there are already
     * four players in the lobby. 
     * 
     *  There is a secret command that will claer out the server. If you attempt to join
     *  with the name, "wipeserver", all current players with disconnect.
     */
    @Override
    public boolean joinLobby(String PlayerName) {
        if(PlayerName.equals("\"wipeserver\"")){
            this.players.PlayerNames.clear();
            this.closeLobby();
            return false;
        }
        
        if(this.players.getPlayers().size() >= 4){
            // Can't join, lobby is full.
            return false;
        }
        
        if (!this.game.isGameRunning() && this.isLobbyRunning()) {
            // Lobby is open, successfully joined
            this.players.PlayerNames.add(PlayerName);
            return true;
        } else {
            // Game is running, can't join
            return false;
        }
    }

    /**
     * Stop game lobby completely
     */
    @Override
    public void closeLobby() {
        this.game.closeLobby();
        this.players.PlayerNames.clear();
    }

    /**
     * Return whether or not there is an open lobby
     * 
     * @see stevengantz.client.MemoryGameService#isLobbyRunning()
     */
    @Override
    public boolean isLobbyRunning() {
        return this.game.isLobbyStarted();
    }

    /**
     * Override the default exception so the container can receive exceptions
     * 
     * Note that the default RemoteServiceServlet implementation never throws
     * exceptions to the servlet container. All exceptions that escape the
     * RemoteServiceServlet.processCall(String) method will be caught, logged in
     * the servlet context, and will cause a generic failure message to be sent
     * to the GWT client -- with a 500 status code. To customize this behavior,
     * override RemoteServiceServlet.doUnexpectedFailure(java.lang.Throwable).
     */
    @Override
    protected void doUnexpectedFailure(Throwable e) {
        e.printStackTrace();
        super.doUnexpectedFailure(e);
        /**
         * Override this method to control what should happen when an exception
         * escapes the doPost(javax.servlet.http.HttpServletRequest,
         * javax.servlet.http.HttpServletResponse) method. The default
         * implementation will log the failure and send a generic failure
         * response to the client. An "expected failure" is an exception thrown
         * by a service method that is declared in the signature of the service
         * method. These exceptions are serialized back to the client, and are
         * not passed to this method. This method is called only for exceptions
         * or errors that are not part of the service method's signature, or
         * that result from SecurityExceptions, SerializationExceptions, or
         * other failures within the RPC framework.
         * 
         * Note that if the desired behavior is to both send the
         * GENERIC_FAILURE_MSG response AND to rethrow the exception, then this
         * method should first send the GENERIC_FAILURE_MSG response itself
         * (using getThreadLocalResponse), and then rethrow the exception.
         * Rethrowing the exception will cause it to escape into the servlet
         * container.
         */
    }
}
