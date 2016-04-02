package stevengantz.memory.structure;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import stevengantz.client.Message;

/**
 * @author Steven Gantz
 * @date 3/29/2016
 * The actual callback class that handles callbacks.
 */
public class MessageCallBack implements AsyncCallback<Message> {

    @Override
    public void onFailure(Throwable caught) {
        Window.alert("Unable to get message");
        
    }

    @Override
    public void onSuccess(Message result) {
        Window.alert(result.message);
    }
    
}
