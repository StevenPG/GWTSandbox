package stevengantz.memory.card;

/**
 * @author: Steven Gantz
 * @date: 2/2/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This interface allows for a polymorphic implementation of a Memory Card by
 *       having any sub-class of CardFace to be used in the Memory Card.
 **/
public interface CardFace {

	/**
	 * Every cardface must implement some toString method
	 * 
	 * @return string representation of object
	 */
	@Override
	public String toString();
}
