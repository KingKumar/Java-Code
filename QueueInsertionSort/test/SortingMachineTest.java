import java.util.Comparator;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 * 
 * @author Ronit Kumar
 * 
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor and returns
     * the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires <pre>
     * {@code IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code constructor = (true, order, {})}
     * </pre>
     */
    protected abstract SortingMachine<String> constructor(
            Comparator<String> order);

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} with the given
     * entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires <pre>
     * {@code IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgs = (insertionMode, order, [multiset of entries in args])}
     * </pre>
     */
    protected final SortingMachine<String> createFromArgs(
            Comparator<String> order, boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructor(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    // TODO - add test cases for constructor, add, changeToExtractionMode, 
    // removeFirst, isInInsertionMode, order, and size

}
