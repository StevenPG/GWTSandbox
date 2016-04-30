package stevengantz.memory.card;

import com.google.gwt.user.client.ui.Image;

/**
 * @author: Steven Gantz
 * @date: 2/2/2016
 * @Created for CSC421, Dr. Spiegel
 * @This file is a container that represents a card used to play the card game
 *       memory.
 **/
public class MemoryCard {

    /**
     * Front face of the memory card This face is the face to check for matches
     */
    public Image frontFace;

    /**
     * Rear face of the memory card
     */
    public Image rearFace;

    /**
     * The face that is showing to the screen
     */
    public Image face;

    /**
     * Whether the card is front-face-up or not
     */
    private boolean faceUp;

    /**
     * Whether the card has been paired with another card.
     */
    public boolean paired;

    /**
     * Direct reference to the paired card, null if not paired.
     */
    private MemoryCard pairedCard;

    /**
     * General constructor that builds a memory card with two faces, a front and
     * rear.
     * 
     * @param frontFace
     *            Front face of the Memory Card
     * @param rearFace
     *            Rear face of the Memory Card
     */
    public MemoryCard(Image frontFace, Image rearFace) {
        this.frontFace = frontFace;
        this.rearFace = rearFace;
        this.faceUp = false;
        this.paired = false;
        this.face = rearFace;
    }

    /**
     * Accessor method for retrieving the front card face
     * 
     * @return front card face
     */
    public Image getFrontFace() {
        return this.frontFace;
    }

    /**
     * Accessor method for retrieving the rear card face
     * 
     * @return rear card face
     */
    public Image getRearFace() {
        return this.rearFace;
    }

    /**
     * Retrieve the current face value, overridden based on current conditions
     * within the MemoryCard
     * 
     * @return
     */
    public Image getFace() {
        if (this.isFaceUp()) {
            this.face = this.frontFace;
            return this.face;
        } else {
            this.face = this.rearFace;
            return this.face;
        }
    }

    /**
     * Lock the card into a pair with another card
     * 
     * @param paired
     */
    public void lockInPair(MemoryCard paired) {
        this.paired = true;
        this.pairedCard = paired;
    }

    /**
     * Return the card this card is paired with currently
     * 
     * @return null if not paired, paired card if paired
     */
    public MemoryCard getPairedCard() {
        if (!this.paired)
            return null;
        // otherwise, this card is paired
        return this.pairedCard;
    }

    /**
     * Accessor method for seeing whether the card is front face up
     * 
     * @return true or false if the card is front face up or not
     */
    public boolean isFaceUp() {
        return this.faceUp;
    }

    /**
     * Mutator method for changing the card's face from front to rear or vice
     * versa. If the card is front face up, this.faceUp is set to false (ie.
     * front face down).
     */
    public void flip() {
        // this.faceUp = (faceUp) ? false : true;
        this.faceUp = !this.faceUp;
        if (this.isFaceUp()) {
            this.face = this.rearFace;
        } else {
            this.face = this.frontFace;
        }
    }

    /**
     * Create a real copy of the memory card (hard copy) rather than copying the
     * reference itself (soft copy).
     * 
     * @return new MemoryCard that is identical to the calling MemoryCard
     */
    public MemoryCard copyCard() {
        return new MemoryCard(this.frontFace, this.rearFace);
    }

    public boolean equals(MemoryCard mem) {
        // Make sure the types are correct
        if (!(mem instanceof MemoryCard)) {
            return false;
        }

        // compare the two cards front faces
        return this.getFrontFace() == mem.getFrontFace();
    }

    /**
     * This method overrides java.lang.Object.toString and returns the current
     * upward face as a string. If the front is face up, it.toString is
     * returned, whereas if the rear is face up, that.toString is returned.
     */
    @Override
    public String toString() {
        return (faceUp) ? this.frontFace.toString() : this.rearFace.toString();
    }

}
