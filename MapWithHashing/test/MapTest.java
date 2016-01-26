import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 * 
 * @author Ronit Kumar
 * 
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor and returns the result.
     * 
     * @return the new map
     * @ensures <pre>
     * {@code constructor = {}}
     * </pre>
     */
    protected abstract Map<String, String> constructor();

    /**
     * 
     * Creates and returns a {@code Map<String, String>} with the given entries.
     * 
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * {@code [args.length is even]}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgs = [pairs in args]}
     * </pre>
     */
    private Map<String, String> createFromArgs(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructor();
        for (int i = 0; i < args.length; i += 2) {
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, value, hasKey, and
    // size

    /**
     * Test for Default Constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        Map s = this.constructor();
        Map r = this.constructor();
        assertEquals(s, r);
    }

    /**
     * Test for Constructor.
     */
    @Test
    public final void testConstructor2() {
        Map s = this.createFromArgs("A", "5", "B", "6");
        Map r = this.createFromArgs("A", "5", "B", "6");
        boolean truth = s.equals(r);
        assertEquals(truth, true);
    }

    /**
     * Test Add method.
     */
    @Test
    public final void testAdd() {
        Map s = this.createFromArgs("A", "5", "B", "6");
        Map r = this.createFromArgs("A", "5");
        r.add("B", "6");
        boolean truth = s.equals(r);
        assertEquals(truth, true);
    }

    /**
     * Test Add method.
     */
    @Test
    public final void testAdd2() {
        Map s = this.createFromArgs("A", "5");
        Map r = this.createFromArgs();
        r.add("A", "5");
        boolean truth = s.equals(r);
        assertEquals(truth, true);
    }

    /**
     * Test Add method.
     */
    @Test
    public final void testAdd3() {
        Map s = this.createFromArgs("A", "5", "B", "6");
        Map r = this.createFromArgs();
        r.add("A", "5");
        r.add("B", "6");
        boolean truth = s.equals(r);
        assertEquals(truth, true);
    }

    /**
     * Test Remove method.
     */
    @Test
    public final void testRemove() {
        Map s = this.createFromArgs("A", "5");
        Map r = this.createFromArgs("A", "5", "B", "6");
        r.remove("B");
        boolean truth = s.equals(r);
        assertEquals(truth, true);
    }

    /**
     * Test Remove method.
     */
    @Test
    public final void testRemove2() {
        Map s = this.createFromArgs("A", "5");
        Map r = this.createFromArgs();
        s.remove("A");
        boolean truth = s.equals(r);
        assertEquals(truth, true);
    }

    /**
     * Test Remove method.
     */
    @Test
    public final void testRemove3() {
        Map s = this.createFromArgs("A", "5", "B", "6", "C", "7");
        Map r = this.createFromArgs("A", "5", "B", "6");
        s.remove("C");
        boolean truth = s.equals(r);
        assertEquals(truth, true);
    }

    /**
     * Test value method.
     */
    @Test
    public final void testValue() {
        Map r = this.createFromArgs("A", "5", "B", "6");
        assertEquals("6", r.value("B"));
    }

    /**
     * Test hasKey method.
     */
    public final void testStringConstructorfor0() {
        Map r = this.createFromArgs("A", "5", "B", "6");
        assertEquals(true, r.hasKey("B"));
    }

    /**
     * Test size method.
     */
    @Test
    public final void testStringConstructorforSingleDigit() {
        Map r = this.createFromArgs("A", "5", "B", "6");
        assertEquals(2, r.size());
    }
}
