package stevengantz.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Steven Gantz
 * The asynchronous counterpart to the MemoryGameService
 */
public interface MemoryGameServiceAsync {
    public void attemptHandshake(AsyncCallback<Boolean> callback);
    public void isGameRunning(AsyncCallback<Boolean> callback);
    public void startLobby(AsyncCallback<Boolean> callback);
    public void closeLobby(AsyncCallback<Boolean> callback);
    public void isLobbyRunning(AsyncCallback<Boolean> callback);
}
