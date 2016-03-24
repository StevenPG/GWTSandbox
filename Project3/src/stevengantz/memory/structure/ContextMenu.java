package stevengantz.memory.structure;

import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * @author Steven Gantz
 * @date 3/24/2016
 */
public class ContextMenu extends Composite implements ContextMenuHandler {

    /**
     * Context menu panel
     */
    private PopupPanel contextMenu;
    
    public ContextMenu() {
        HTML menuHTML = new HTML("<button onclick=location.reload()>Start Over</button>");
        this.contextMenu = new PopupPanel(true);
        this.contextMenu.add(menuHTML);
        this.contextMenu.hide();
    }
    
    /**
     * @see com.google.gwt.event.dom.client.ContextMenuHandler#onContextMenu(com.google.gwt.event.dom.client.ContextMenuEvent)
     **/
    @Override
    public void onContextMenu(ContextMenuEvent event) {
        event.preventDefault();
        event.stopPropagation();
        
        this.contextMenu.setPopupPosition(event.getNativeEvent().getClientX(),
                event.getNativeEvent().getClientY());
        
        this.contextMenu.show();
    }

}
