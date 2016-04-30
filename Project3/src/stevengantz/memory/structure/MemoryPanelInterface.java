package stevengantz.memory.structure;

/**
 * @author Steven Gantz
 * @date 3/7/2016
 * @file Define the contract that all panels used within the memory card game
 *       must follow.
 */
public interface MemoryPanelInterface {
    
    /**
     * Every class must set all of it's attributes internally. This
     * is done so that the main class is not littered with calls to
     * the panel structure and everything can be encapsulated within
     * these classes.
     */
    public void setAttributes();
}
