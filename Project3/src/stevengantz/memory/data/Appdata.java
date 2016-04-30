package stevengantz.memory.data;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;

/**
 * @author Steven Gantz
 * @date 2/25/2016
 * @file This file contains all CONSTANT values used throughout the application
 *       for ease of change management.
 */
public class Appdata {

    /**
     * Save the window information for quick access
     */
    public static int WINDOWWIDTH = Window.getClientWidth();
    public static int WINDOWHEIGHT = Window.getClientHeight();

    /**
     * There are 3 levels of AI Difficulty that decides how the AI makes it's
     * decisions. These are set in the main menu and in the context menu.
     */
    public static int AiDifficulty;

    /**
     * Hold whether to display AI or not
     */
    public static boolean AiIsPlaying;

    /**
     * Card pixel sizes, playing cards are 16x9 Useful resource:
     * http://andrew.hedges.name/experiments/aspect_ratio/
     */
    public static int CARDHEIGHT = Appdata.WINDOWHEIGHT / 6;
    public static int CARDWIDTH = Appdata.WINDOWWIDTH / 20;

    /**
     * Total number of cards
     */
    public static int NUMROWS = 5;
    public static int NUMCOLS = 6;
    public static int NUMBEROFCARDS = Appdata.NUMROWS * Appdata.NUMCOLS / 2;

    /**
     * Point values
     */
    public static int POINTSPERMATCH = 25;
    public static int MATCHWILDSPOINTS = 100;
    public static int TWOCARDSLEFTPOINTS = 20;

    /**
     * Error image - TODO temporarily front image
     */
    public static String ERRORIMAGE = "http://i.imgur.com/74FVTpV.jpg?1";
    
    /**
     * Multiplayer lobby ready button
     */
    public static String READY = "img/ready.png";

    public static String CHEATSON = "img/cheatsEnabled.png";
    public static String CHEATSOFF = "img/cheatsDisabled.png";

    /**
     * Card image location for back
     */
    public static String REARIMAGE = "img/cardback.jpg";
    public static String SELECTEDREARIMAGE = "img/selectedcardback.jpg";

    public static ArrayList<String> GetImageStringList() {
        ArrayList<String> imagelist = new ArrayList<String>();

        imagelist.add("img/cardfaces/CPlusPlus.jpg"); // 1
        imagelist.add("img/cardfaces/java.jpg"); // 2
        imagelist.add("img/cardfaces/python.png"); // 3
        imagelist.add("img/cardfaces/js.png"); // 4
        imagelist.add("img/cardfaces/Csharp.jpg"); // 5
        imagelist.add("img/cardfaces/scala.png"); // 6
        imagelist.add("img/cardfaces/perl.jpg"); // 7
        imagelist.add("img/cardfaces/php.png"); // 8
        imagelist.add("img/cardfaces/ruby.png"); // 9
        imagelist.add("img/cardfaces/swift.jpg"); // 10
        imagelist.add("img/cardfaces/Haskell.png"); // 11
        imagelist.add("img/cardfaces/visualbasic.jpg"); // 12
        imagelist.add("img/cardfaces/ada.png"); // 13
        imagelist.add("img/cardfaces/sql.jpg"); // 14
        imagelist.add("img/cardfaces/go.png"); // 15
        imagelist.add("img/cardfaces/x86.png"); // 16

        return imagelist;
    }

}
