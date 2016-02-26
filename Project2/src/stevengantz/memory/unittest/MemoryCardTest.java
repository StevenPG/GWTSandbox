package stevengantz.memory.unittest;


import org.junit.Test;

import junit.framework.TestCase;
import stevengantz.memory.card.MemoryCard;
import stevengantz.memory.card.TextCardFace;

/**
 * @author Steven Gantz
 */
public class MemoryCardTest extends TestCase {
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void test() {
		MemoryCard mem = new MemoryCard(new TextCardFace("1"), new TextCardFace("2"));
		assertEquals(false, mem.isFaceUp());
		mem.flip();
		assertEquals(true, mem.isFaceUp());
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
