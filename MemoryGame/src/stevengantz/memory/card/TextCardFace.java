package stevengantz.memory.card;

/**
 * @author: Steven Gantz
 * @date: 2/2/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This file is a container for a Card face that is implemented in text and
 *       meant to be output to a terminal.
 **/
public class TextCardFace implements CardFace {

	/**
	 * This string will contain what will represent the face of a card.
	 */
	private String cardFace;

	public TextCardFace(String cardFace) {
		this.cardFace = cardFace;
	}

	/**
	 * Accessor method for retrieving the card face
	 * 
	 * @return card face as a string
	 */
	public String getFace() {
		return this.cardFace;
	}

	/**
	 * This method overrides the java.lang.Object.toString method.
	 * 
	 * @return string representation of object
	 */
	@Override
	public String toString() {
		return new String(this.cardFace);
	}

}
