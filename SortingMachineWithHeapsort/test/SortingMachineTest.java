import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

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
     * Comparator<String> implementation to be used in all test cases.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

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

    /**
     * Test case for Default Constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        SortingMachine<String> s = this.constructor(ORDER);
        assertEquals(s.size(), 0);
        assertEquals("(true,[order],{})", s.toString());
    }

    /**
     * Test case for Add method on non empty set.
     */
    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> s = this.createFromArgs(ORDER, true, "red",
                "blue");
        s.add("green");
        SortingMachine<String> s2 = this.createFromArgs(ORDER, true, "red",
                "blue", "green");
        assertEquals(s, s2);
    }

    /**
     * Test case for Add method on empty set.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> s = this.constructor(ORDER);
        s.add("green");
        assertEquals("(true,[order],{green})", s.toString());
    }

    /**
     * Test case for RemoveFirst method, yielding empty SortingMachine.
     */
    @Test
    public final void testRemoveLeavingEmpty() {
        SortingMachine<String> s = this.createFromArgs(ORDER, false, "red");
        String x = s.removeFirst();
        assertEquals("{}", s.toString());
        assertEquals("red", x);
    }

    /**
     * Test case for Remove method, yielding non empty set.
     */
    @Test
    public final void testRemoveLeavingNonEmpty() {
        SortingMachine<String> s = this.createFromArgs(ORDER, false, "red",
                "blue");
        String x = s.removeFirst();
        assertEquals("(true,[order],{red})", s.toString());
        assertEquals("blue", x);
    }

    /**
     * Test case for change to extraction mode method.
     */
    @Test
    public final void testChangeToExtractionModeSingleEntry() {
        SortingMachine<String> s = this.createFromArgs(ORDER, true, "red");
        s.changeToExtractionMode();
        assertEquals(false, s.isInInsertionMode());
    }

    /**
     * Test case for change to extraction mode method on SM of 3 elements.
     */
    @Test
    public final void testChangeToExtractionModeTripleEntry() {
        SortingMachine<String> s = this.createFromArgs(ORDER, true, "red",
                "blue", "green");
        s.changeToExtractionMode();
        assertEquals(false, s.isInInsertionMode());
    }

    /**
     * Test case for is in insertion mode method.
     */
    @Test
    public final void testIsInInsertionModeSingleEntry() {
        SortingMachine<String> s = this.createFromArgs(ORDER, true, "red");
        assertEquals(true, s.isInInsertionMode());
    }

    /**
     * Test case for is in insertion mode method on SM of 3 elements.
     */
    @Test
    public final void testIsInInsertionModeTripleEntry() {
        SortingMachine<String> s = this.createFromArgs(ORDER, true, "red",
                "blue", "green");
        assertEquals(true, s.isInInsertionMode());
    }

    /**
     * Test case for Size method on empty Sorting Machine.
     */
    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> s = this.createFromArgs(ORDER, true);
        s.changeToExtractionMode();
        int i = s.size();
        assertEquals(0, i);
    }

    /**
     * Test case for Size method on Sorting Machine of 9 elements.
     */
    @Test
    public final void testSizeBigSet() {
        SortingMachine<String> s = this.createFromArgs(ORDER, true, "1", "2",
                "3", "4", "5", "6", "7", "8", "9");
        s.changeToExtractionMode();
        int i = s.size();
        assertEquals(9, i);
    }

    /**
     * Test case for Order method on Sorting Machine.
     */
    @Test
    public final void testOrder() {
        SortingMachine<String> s = this.createFromArgs(ORDER, true, "1", "2",
                "3", "4", "5", "6", "7", "8", "9");
        assertEquals(s, ORDER);
    }

}
