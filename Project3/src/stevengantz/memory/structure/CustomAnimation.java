package stevengantz.memory.structure;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Steven Gantz
 * @date 3/30/2016 Custom animation that runs when the game ends
 */
public class CustomAnimation extends Animation {

    private int localVal;

    public CustomAnimation(int val) {
        localVal = val;

        Image fireworks = new Image("img/fireworks.gif");
        Image worst = new Image("img/worst.gif");
        Image meh = new Image("img/meh.gif");
        Image best = new Image("img/best.gif");

        fireworks.setHeight(String.valueOf(Window.getClientHeight() / 2));
        fireworks.setWidth(String.valueOf(Window.getClientWidth() / 2));

        // Pop the popup panel
        final PopupPanel animation = new PopupPanel();
        animation.setHeight(String.valueOf(Window.getClientHeight() / 2));
        animation.setWidth(String.valueOf(Window.getClientWidth() / 2));

        VerticalPanel filler = new VerticalPanel();

        Timer hide = new Timer() {
            @Override
            public void run() {
                animation.hide();
            }
        };

        if (this.localVal == 0) {
            filler.add(new Label("You matched wilds!!! 100 points!!!"));
            filler.add(fireworks);
            animation.add(filler);
            animation.show();
            hide.schedule(3750);
        } else if (this.localVal == 1) {
            filler.add(new Label("You played terribly..."));
            filler.add(worst);
            animation.add(filler);
            animation.show();
            hide.schedule(6000);
        } else if (this.localVal == 2) {
            filler.add(new Label("You played alright..."));
            filler.add(meh);
            animation.add(filler);
            animation.show();
            hide.schedule(6000);
        } else if (this.localVal == 3) {
            filler.add(new Label("You did great!!!"));
            filler.add(best);
            animation.add(filler);
            animation.show();
            hide.schedule(15000);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.animation.client.Animation#onUpdate(double)
     */
    @Override
    protected void onUpdate(double progress) {

    }

    @Override
    protected void onComplete() {
        super.onComplete();
    }

    /**
     * If 0: Play fireworks animation If 1: Play worst animation If 2: Play Meh
     * animation If 3: Play Best animation
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

}
