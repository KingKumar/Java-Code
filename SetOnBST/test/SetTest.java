import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 * 
 * @author Ronit Kumar
 * 
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     * 
     * @return the new set
     * @ensures <pre>
     * {@code constructor = {}}
     * </pre>
     */
    protected abstract Set<String> constructor();

    /**
     * 
     * Creates and returns a {@code Set<String>} with the given elements.
     * 
     * @param args
     *            the elements for the map
     * @return the constructed set
     * @requires <pre>
     * {@code [args entries are all different]}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgs = [entries in args]}
     * </pre>
     */
    private final Set<String> createFromArgs(String... args) {
        Set<String> set = this.constructor();
        for (int i = 0; i < args.length; i++) {
            set.add(args[i]);
        }
        return set;
    }

    /**
     * Test case for Default Constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        Set<String> s = this.constructor();
        assertEquals(s.size(), 0);
        assertEquals("{}", s.toString());
    }

    /**
     * Test case for Add method on non empty set.
     */
    @Test
    public final void testAddNonEmpty() {
        Set<String> s = this.createFromArgs("red", "blue");
        s.add("green");
        Set<String> s2 = this.createFromArgs("red", "blue", "green");
        assertEquals(s, s2);
    }

    /**
     * Test case for Add method on empty set.
     */
    @Test
    public final void testAddEmpty() {
        Set<String> s = this.constructor();
        s.add("green");
        assertEquals("{green}", s.toString());
    }

    /**
     * Test case for Remove method, yielding empty set.
     */
    @Test
    public final void testRemoveLeavingEmpty() {
        Set<String> s = this.createFromArgs("red");
        String x = s.remove("red");
        assertEquals("{}", s.toString());
        assertEquals("red", x);
    }

    /**
     * Test case for Remove method, yielding non empty set.
     */
    @Test
    public final void testRemoveLeavingNonEmpty() {
        Set<String> s = this.createFromArgs("red", "blue");
        String x = s.remove("blue");
        assertEquals("{red}", s.toString());
        assertEquals("blue", x);
    }

    /**
     * Test case for Contains method, yielding true.
     */
    @Test
    public final void testContainsSingleEntry() {
        Set<String> s = this.createFromArgs("red");
        Boolean x = s.contains("red");
        assertEquals(true, x);
    }

    /**
     * Test case for Contains method, yielding false.
     */
    @Test
    public final void testContainsSingleEntryFalse() {
        Set<String> s = this.createFromArgs("red");
        Boolean x = s.contains("blue");
        assertEquals(false, x);
    }

    /**
     * Test case for Contains method on set of 3 elements, yielding true.
     */
    @Test
    public final void testContainsTripleEntry() {
        Set<String> s = this.createFromArgs("red", "blue", "green");
        Boolean x = s.contains("green");
        assertEquals(true, x);
    }

    /**
     * Test case for Contains method on set of 3 elements, yielding false.
     */
    @Test
    public final void testContainsTripleEntryFalse() {
        Set<String> s = this.createFromArgs("red", "blue", "green");
        Boolean x = s.contains("orange");
        assertEquals(false, x);
    }

    /**
     * Test case for Size method on set of 2 elements.
     */
    @Test
    public final void testSizeNonEmpty() {
        Set<String> s = this.createFromArgs("red", "blue");
        int i = s.size();
        assertEquals(2, i);
    }

    /**
     * Test case for Size method on empty set.
     */
    @Test
    public final void testSizeEmpty() {
        Set<String> s = this.createFromArgs();
        int i = s.size();
        assertEquals(0, i);
    }

    /**
     * Test case for Size method on set of 9 elements.
     */
    @Test
    public final void testSizeBigSet() {
        Set<String> s = this.createFromArgs("1", "2", "3", "4", "5", "6", "7",
                "8", "9");
        int i = s.size();
        assertEquals(9, i);
    }
}
