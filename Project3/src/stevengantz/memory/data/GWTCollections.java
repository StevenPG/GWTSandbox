package stevengantz.memory.data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

import stevengantz.memory.card.MemoryCard;

/**
 * @author Steven Gantz
 * @date 2/25/2016 This class was created replace the java.util.Collections API.
 * 
 *       GWT does not support Java Collections, and will break any classes that
 *       use the Collections.shuffle method.
 * 
 *       I am generating this class as a direct copy from the OpenJDK
 *       implementation of java.util.Collections
 */
public class GWTCollections {

    // Defined Constants
    /**
     * Attribute used by java.util.Collections.shuffle Found on line: 94
     */
    private static final int SHUFFLE_THRESHOLD = 5;

    /**
     * Taken directly from the OpenJDK's implementation of
     * java.util.Collections.shuffle.
     * 
     * Source:
     * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-
     * b14/java/util/Collections.java#Collections
     * 
     * Starting Line #: 455
     * 
     * Ending Line #: 474
     * 
     * Randomly permute the specified list using the specified source of
     * randomness. All permutations occur with equal likelihood assuming that
     * the source of randomness is fair. This implementation traverses the list
     * backwards, from the last element up to the second, repeatedly swapping a
     * randomly selected element into the "current position". Elements are
     * randomly selected from the portion of the list that runs from the first
     * element to the current position, inclusive. This method runs in linear
     * time. If the specified list does not implement the RandomAccess interface
     * and is large, this implementation dumps the specified list into an array
     * before shuffling it, and dumps the shuffled array back into the list.
     * This avoids the quadratic behavior that would result from shuffling a
     * "sequential access" list in place.
     * 
     * @param board
     *            - the list to be shuffled.
     * @param rnd
     *            - the source of randomness to use to shuffle the list.
     * @throws -
     *             java.lang.UnsupportedOperationException if the specified list
     *             or its list-iterator does not support the set operation.
     */
    public static void shuffle(ArrayList<MemoryCard> board, Random rnd) {
        int size = board.size();
        if (size < SHUFFLE_THRESHOLD || board instanceof RandomAccess) {
            for (int i = size; i > 1; i--)
                swap(board, i - 1, rnd.nextInt(i));
        } else {
            Object arr[] = board.toArray();

            // Shuffle array
            for (int i = size; i > 1; i--)
                swap(arr, i - 1, rnd.nextInt(i));

            // Dump array back into list
            ListIterator<MemoryCard> it = board.listIterator();
            for (int i = 0; i < arr.length; i++) {
                it.next();
                it.set((MemoryCard) arr[i]);
            }
        }
    }

    /**
     * Taken directly from the OpenJDK's implementation of
     * java.util.Collections.shuffle.
     * 
     * Source:
     * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-
     * b14/java/util/Collections.java#Collections
     * 
     * Starting Line #: 455
     * 
     * Ending Line #: 424
     * 
     * Randomly permutes the specified list using a default source of
     * randomness. All permutations occur with approximately equal likelihood.
     * The hedge "approximately" is used in the foregoing description because
     * default source of randomness is only approximately an unbiased source of
     * independently chosen bits. If it were a perfect source of randomly chosen
     * bits, then the algorithm would choose permutations with perfect
     * uniformity. This implementation traverses the list backwards, from the
     * last element up to the second, repeatedly swapping a randomly selected
     * element into the "current position". Elements are randomly selected from
     * the portion of the list that runs from the first element to the current
     * position, inclusive. This method runs in linear time. If the specified
     * list does not implement the RandomAccess interface and is large, this
     * implementation dumps the specified list into an array before shuffling
     * it, and dumps the shuffled array back into the list. This avoids the
     * quadratic behavior that would result from shuffling a "sequential access"
     * list in place.
     * 
     * @param board
     *            - the list to be shuffled.
     * @throws -
     *             java.lang.UnsupportedOperationException if the specified list
     *             or its list-iterator does not support the set operation.
     */
    public static void shuffle(ArrayList<MemoryCard> board) {
        if (r == null) {
            r = new Random();
        }
        GWTCollections.shuffle(board, r);
    }

    /**
     * This is a part of java.util.Collections noted at line # 430
     */
    private static Random r;

    /**
     * Taken directly from the OpenJDK's implementation of
     * java.util.Collections.swap.
     * 
     * Source:
     * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-
     * b14/java/util/Collections.java#Collections
     * 
     * Starting Line #: 489
     * 
     * Ending Line #: 491
     * 
     * Swaps the elements at the specified positions in the specified list. (If
     * the specified positions are equal, invoking this method leaves the list
     * unchanged.)
     * 
     * @param board
     *            - list The list in which to swap elements.
     * @param i
     *            - i the index of one element to be swapped.
     * @param j
     *            - j the index of the other element to be swapped.
     * @throws -
     *             java.lang.IndexOutOfBoundsException if either i or j is out
     *             of range (i < 0 || i >= list.size() || j < 0 || j >=
     *             list.size()).
     */
    private static void swap(ArrayList<MemoryCard> board, int i, int j) {
        final List<MemoryCard> l = board;
        l.set(i, l.set(j, l.get(i)));
    }

    /**
     * Swaps the two specified elements in the specified array.
     * 
     * Starting Line #: 497
     * 
     * Ending Line #: 500
     * 
     * @param list
     *            - list The list in which to swap elements.
     * @param i
     *            - i the index of one element to be swapped.
     * @param j
     *            - j the index of the other element to be swapped.
     * @throws -
     *             java.lang.IndexOutOfBoundsException if either i or j is out
     *             of range (i < 0 || i >= list.size() || j < 0 || j >=
     *             list.size()).
     */
    private static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
