package stevengantz.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Steven Gantz
 * @date 3/29/2016
 * The RPC service for passing data from the server
 * to the client.
 */
@RemoteServiceRelativePath("message")
public interface MemoryGameService extends RemoteService{
    Message getMessage(String input);
}
