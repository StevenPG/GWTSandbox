package stevengantz.memory.structure;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;

/**
 * @author Steven Gantz
 * @date 3/7/2016
 * @file This panel is a custom panel for the MemoryGame that displays the
 *       entire game and contains the main viewed panels
 *
 */
public class MemoryLayoutPanel extends DockLayoutPanel implements MemoryPanelInterface{

    /**
     * General constructor, uses DockLayoutPanel's constructor to initialize
     * class
     * 
     * @param unit
     *            Unit to base all internal layouts with
     */
    public MemoryLayoutPanel(Unit unit) {
        super(unit);
        setAttributes();
    }

    /**
     * Assign individual attributes to the panel
     */
    @Override
    public void setAttributes() {
        setWidth("100%");
        setHeight("100%");
        setStyleName("RootBackgroundStyle");
    }

}
