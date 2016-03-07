package stevengantz.memory.data;

import com.google.gwt.user.client.Window;

/**
 * @author Steven Gantz
 * @date 2/25/2016
 * @file This file contains all CONSTANT values used
 * throughout the application for ease of change management.
 */
public class Appdata {
    
    /**
     * Save the window information for quick access
     */
    public static int WINDOWWIDTH = Window.getClientWidth();
    public static int WINDOWHEIGHT = Window.getClientHeight();
    
    /**
     * Card pixel sizes, playing cards are 16x9
     * Useful resource: http://andrew.hedges.name/experiments/aspect_ratio/
     */
    public static int CARDHEIGHT = Appdata.WINDOWHEIGHT/6;
    public static int CARDWIDTH = Appdata.WINDOWWIDTH/20;
    
    /**
     * Total number of cards
     */
    public static int NUMROWS = 5;
    public static int NUMCOLS = 6;
    public static int NUMBEROFCARDS = Appdata.NUMROWS * Appdata.NUMCOLS/2;    
    
    /**
     * Error image - TODO temporarily front image
     */
    public static String ERRORIMAGE = "http://i.imgur.com/74FVTpV.jpg?1";
    
    /**
     * Card image location for back
     */
    public static String REARIMAGE = "img/card-back.jpg";
    
}
