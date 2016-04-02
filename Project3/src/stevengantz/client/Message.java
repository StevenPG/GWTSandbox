package stevengantz.client;

import java.io.Serializable;

/**
 * @author Steven Gantz
 * @date 3/29/2016
 * Serializable message that will be sent to the server.
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 5330874509536516780L;
    public String message;
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Zero constructor
     */
    public Message(){
        
    }
}
