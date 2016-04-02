package stevengantz.memory.structure;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * @author Steven Gantz
 * @date 3/30/2016
 * Custom animation that runs when the game ends
 */
public class CustomAnimation extends Animation {
    
    /* (non-Javadoc)
     * @see com.google.gwt.animation.client.Animation#onUpdate(double)
     */
    @Override
    protected void onUpdate(double progress) {
        
    }
    
    @Override
    protected void onComplete() {
        super.onComplete();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        // Pop the popup panel
        PopupPanel animation = new PopupPanel();
        
        animation.show();
    }

}
