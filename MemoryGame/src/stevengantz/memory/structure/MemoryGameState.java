package stevengantz.memory.structure;

/**
 * @author: Steven Gantz
 * @date: 2/4/2016
 * @Created for CSC421, Dr. Spiegel
 * @Project Due:
 * @This file is a container for all data that may need to be stored and
 *       accessed within a Memory game.
 **/
public class MemoryGameState {
	private int totalMatches;
	private int totalAttempts;

	public MemoryGameState() {
		this.totalAttempts = 0;
		this.totalMatches = 0;
	}

	public void addMatch() {
		this.totalMatches += 1;
	}

	public void addAttempt() {
		this.totalAttempts += 1;
	}

	// Getters and Setters
	public int getTotalMatches() {
		return this.totalMatches;
	}

	public int getTotalAttempts() {
		return this.totalAttempts;
	}

	public void setTotalAttempts(int totalAttempts) {
		this.totalAttempts = totalAttempts;
	};

	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	};
}
