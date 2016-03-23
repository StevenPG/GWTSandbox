package stevengantz.memory.structure;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author Steven Gantz
 * @date 3/7/2016
 * @file This file contains multiple horizontalpanels that make up the visuals
 *       within the game.
 *
 */
public class VerticalGamePanel extends VerticalPanel implements MemoryPanelInterface {

    /**
     * The internal board is built using image references from the cards that
     * make up the game board. The board must be passed in to the constructor to
     * retrieve these references and build the UI.
     */
    public MemoryGameBoard board;

    /**
     * General constructor, takes the MemoryGameBoard filled with references to
     * build the visual structure of the panels with
     */
    public VerticalGamePanel() {
        super();
        setAttributes();
    }

    /**
     * Assign individual attributes to the panel
     */
    @Override
    public void setAttributes() {
        setWidth("100%");
        setHeight("100%");
        setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        setStyleName("RootBackgroundLayout");
    }
}
