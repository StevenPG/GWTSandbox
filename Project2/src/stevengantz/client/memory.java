package stevengantz.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import stevengantz.memory.data.Appdata;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class memory implements EntryPoint {

    ArrayList<Image> backCardImageArray;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        backCardImageArray = loadImages();

        Grid grid = new Grid(Appdata.NUMROWS, Appdata.NUMCOLS);

        for (int col = 0, index = 0; col < Appdata.NUMCOLS; col++) {
            for (int row = 0; row < Appdata.NUMROWS; row++) {

                // This is where the image is drawn
                Image img = backCardImageArray.get(index);
                grid.setWidget(row, col, img);

                // iterate image
                index++;
            }
        }

        RootPanel.get().add(grid);
    }

    /**
     * Load all images for the application
     * 
     * @return arraylist of images
     */
    private ArrayList<Image> loadImages() {

        // Create an array of images
        this.backCardImageArray = new ArrayList<Image>();

        // Create and assign images
        for (int index = 0; index < Appdata.NUMBEROFCARDS; index++) {

            // back card face image
            Image backImage = new Image();

            // Set Url for all backCardImages
            backImage.setUrl("img/card-back.jpg");

            // Assign error handlers for each card
            backImage.addErrorHandler(new ErrorHandler() {
                @Override
                public void onError(ErrorEvent event) {
                    // backImage.setUrl(Appdata.ERRORIMAGE);
                }
            });

            // Set Pixel size from constants
            backImage.setPixelSize(Appdata.CARDWIDTH, Appdata.CARDHEIGHT);

            // Add the image to the cardList
            this.backCardImageArray.add(backImage);
        }

        return backCardImageArray;
    }
}
