package stevengantz.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import stevengantz.client.MemoryGameService;
import stevengantz.client.Message;

/**
 * @author Steven Gantz
 * @date 3/29/2016 This file is the concrete implementation of the
 *       MemoryGameService.
 */
public class MemoryGameServiceImpl extends RemoteServiceServlet implements MemoryGameService {

    private static final long serialVersionUID = 1929274862103034778L;

    /*
     * (non-Javadoc)
     * 
     * @see stevengantz.client.MemoryGameService#getMessage(java.lang.String)
     */
    @Override
    public Message getMessage(String input) {
        String messageString = "Test: " + input;
        Message message = new Message();
        message.setMessage(messageString);
        return message;
    }

}
