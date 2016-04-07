package stevengantz.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("memorygame")
public interface MemoryGameService extends RemoteService{
    void attemptHandshake();
    boolean isGameRunning();
    void startLobby(String playerName);
    void closeLobby();
    boolean isLobbyRunning();
}
