package stevengantz.memory.data;

import java.util.ArrayList;

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
    
    public static ArrayList<String> GetImageStringList(){
        ArrayList<String> imagelist = new ArrayList<String>();
        
        imagelist.add("img/cardfaces/CPlusPlus.jpg"); //1
        imagelist.add("img/cardfaces/java.jpg"); //2
        imagelist.add("img/cardfaces/python.png"); //3
        imagelist.add("img/cardfaces/js.png"); //4
        imagelist.add(ERRORIMAGE); //5
        imagelist.add(ERRORIMAGE); //6
        imagelist.add(ERRORIMAGE); //7
        imagelist.add(ERRORIMAGE); //8
        imagelist.add(ERRORIMAGE); //9
        imagelist.add(ERRORIMAGE); //10
        imagelist.add(ERRORIMAGE); //11
        imagelist.add(ERRORIMAGE); //12
        imagelist.add(ERRORIMAGE); //13
        imagelist.add(ERRORIMAGE); //14
        imagelist.add(ERRORIMAGE); //15
        imagelist.add(ERRORIMAGE); //16
        imagelist.add(ERRORIMAGE); //17
        imagelist.add(ERRORIMAGE); //18
        imagelist.add(ERRORIMAGE); //19
        imagelist.add(ERRORIMAGE); //20
        
        return imagelist;
    }
    
}
