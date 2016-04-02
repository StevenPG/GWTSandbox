package stevengantz.memory.structure;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import stevengantz.memory.data.Appdata;

/**
 * @author Steven Gantz
 * @date 3/24/2016
 */
public class ContextMenu extends Composite implements ContextMenuHandler {

    /**
     * Classwide attributes
     */
    final Label reload;
    final Label setAiEasy;
    final Label setAiMed;
    final Label setAiHard;
    final Label cancel;

    String setAiEasyDefaultText = "Easy AI";
    String setAiMedDefaultText = "Medium AI";
    String setAiHardDefaultText = "Hard AI";

    /**
     * Context menu panel
     */
    private PopupPanel contextMenu;

    public ContextMenu() {
        final VerticalPanel menuPanel = new VerticalPanel();

        this.reload = new Label("Start Over");
        reload.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window.Location.reload();
            }
        });
        reload.getElement().getStyle().setFontSize(1.5, Unit.EM);
        reload.addMouseOverHandler(new MenuItemHoverHandler());
        reload.addMouseOutHandler(new MenuItemOutHandler());

        this.setAiEasy = new Label("Easy AI");
        setAiEasy.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setAiEasy.getElement().getStyle().setBackgroundColor("#FFFFFF");
                setAiEasy.getElement().getStyle().setColor("black");
                Appdata.AiDifficulty = 0;
                contextMenu.hide();
            }
        });
        setAiEasy.getElement().getStyle().setFontSize(1.5, Unit.EM);
        setAiEasy.addMouseOverHandler(new MenuItemHoverHandler());
        setAiEasy.addMouseOutHandler(new MenuItemOutHandler());

        this.setAiMed = new Label("Medium AI");
        setAiMed.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setAiMed.getElement().getStyle().setBackgroundColor("#FFFFFF");
                setAiMed.getElement().getStyle().setColor("black");
                Appdata.AiDifficulty = 1;
                contextMenu.hide();
            }
        });
        setAiMed.getElement().getStyle().setFontSize(1.5, Unit.EM);
        setAiMed.addMouseOverHandler(new MenuItemHoverHandler());
        setAiMed.addMouseOutHandler(new MenuItemOutHandler());

        this.setAiHard = new Label("Hard AI");
        setAiHard.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setAiHard.getElement().getStyle().setBackgroundColor("#FFFFFF");
                setAiHard.getElement().getStyle().setColor("black");
                Appdata.AiDifficulty = 2;
                contextMenu.hide();
            }
        });
        setAiHard.getElement().getStyle().setFontSize(1.5, Unit.EM);
        setAiHard.addMouseOverHandler(new MenuItemHoverHandler());
        setAiHard.addMouseOutHandler(new MenuItemOutHandler());

        this.cancel = new Label("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                cancel.getElement().getStyle().setBackgroundColor("#FFFFFF");
                cancel.getElement().getStyle().setColor("black");
                contextMenu.hide();
            }
        });
        cancel.getElement().getStyle().setFontSize(1.5, Unit.EM);
        cancel.addMouseOverHandler(new MenuItemHoverHandler());
        cancel.addMouseOutHandler(new MenuItemOutHandler());

        this.contextMenu = new PopupPanel(true);
        this.contextMenu.setAutoHideEnabled(true);

        menuPanel.addDomHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                menuPanel.getElement().getStyle().setCursor(Cursor.POINTER);
            }
        }, MouseOverEvent.getType());

        menuPanel.add(reload);
        if (Appdata.AiIsPlaying) {
            menuPanel.add(setAiEasy);
            menuPanel.add(setAiMed);
            menuPanel.add(setAiHard);
        } else {
            // Don't add these components
        }
        menuPanel.add(cancel);

        this.contextMenu.add(menuPanel);

        this.contextMenu.hide();
    }

    /**
     * @see com.google.gwt.event.dom.client.ContextMenuHandler#onContextMenu(com.google.gwt.event.dom.client.ContextMenuEvent)
     **/
    @Override
    public void onContextMenu(ContextMenuEvent event) {
        event.preventDefault();
        event.stopPropagation();

        switch (Appdata.AiDifficulty) {
        case 0:
            this.setAiEasy.setText("*" + this.setAiEasyDefaultText);
            this.setAiMed.setText(this.setAiMedDefaultText);
            this.setAiHard.setText(this.setAiHardDefaultText);
            break;
        case 1:
            this.setAiEasy.setText(this.setAiEasyDefaultText);
            this.setAiMed.setText("*" + this.setAiMedDefaultText);
            this.setAiHard.setText(this.setAiHardDefaultText);
            break;
        case 2:
            this.setAiEasy.setText(this.setAiEasyDefaultText);
            this.setAiMed.setText(this.setAiMedDefaultText);
            this.setAiHard.setText("*" + this.setAiHardDefaultText);
            break;
        }

        this.contextMenu.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());

        this.contextMenu.show();
    }

    /**
     * Used for labels inside the context menu
     */
    private class MenuItemHoverHandler implements MouseOverHandler {
        /**
         * This is only used with labels, assume event is a label
         */
        @Override
        public void onMouseOver(MouseOverEvent event) {
            Label selected = (Label) event.getSource();
            selected.getElement().getStyle().setBackgroundColor("#007FFF");
            selected.getElement().getStyle().setColor("white");
        }
    }

    /**
     * Used for labels inside the context menu
     */
    private class MenuItemOutHandler implements MouseOutHandler {
        @Override
        public void onMouseOut(MouseOutEvent event) {
            Label selected = (Label) event.getSource();
            selected.getElement().getStyle().setBackgroundColor("#FFFFFF");
            selected.getElement().getStyle().setColor("black");
        }
    }

}
