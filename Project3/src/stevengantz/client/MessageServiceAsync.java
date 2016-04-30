package stevengantz.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Steven Gantz
 * @date 3/29/2016 The service used on client side to connect to server.
 */
public interface MessageServiceAsync {
    void getMessage(String input, AsyncCallback<Message> callback);
}
