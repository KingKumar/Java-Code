import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

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
    @Test
    public final void testDefaultConstructor() {
        Map<String, String> s = this.constructor();
        assertEquals(s.size(), 0);
        assertEquals("{}", s.toString());
    }

    @Test
    public final void testAddNonEmpty() {
        Map<String, String> s = this.createFromArgs("red", "blue");
        s.add("green", "orange");
        Map<String, String> s2 = this.createFromArgs("red", "blue", "green",
                "orange");
        assertEquals(s, s2);
    }

    @Test
    public final void testAddEmpty() {
        Map<String, String> s = this.constructor();
        s.add("green", "orange");
        Map<String, String> s2 = this.createFromArgs("green", "orange");
        assertEquals(s, s2);
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        Map<String, String> s = this.createFromArgs("red", "orange");
        Pair<String, String> x = s.remove("red");
        assertEquals("{}", s.toString());
        assertEquals("<red,orange>", x);
    }

    @Test
    public final void testRemoveLeavingNonEmpty() {
        Map<String, String> s = this.createFromArgs("red", "blue", "orange",
                "green");
        Pair<String, String> x = s.remove("red");
        assertEquals("<red,blue>", x);
        assertEquals("<orange,green>", s.toString());
    }

    @Test
    public final void testValueSingleEntry() {
        Map<String, String> s = this.createFromArgs("red", "orange");
        String x = s.value("red");
        assertEquals("orange", x);
    }

    @Test
    public final void testValueTripleEntry() {
        Map<String, String> s = this.createFromArgs("red", "blue", "green",
                "one", "two", "three");
        String x = s.value("two");
        assertEquals("three", x);
    }

    @Test
    public final void testHasKeySingleEntry() {
        Map<String, String> s = this.createFromArgs("red", "orange");
        boolean x = s.hasKey("red");
        assertEquals(true, x);
    }

    @Test
    public final void testHasKeyTripleEntry() {
        Map<String, String> s = this.createFromArgs("red", "blue", "green",
                "one", "two", "three");
        boolean x = s.hasKey("two");
        assertEquals(true, x);
    }

    @Test
    public final void testLengthNonEmpty() {
        Map<String, String> s = this.createFromArgs("red", "blue");
        int i = s.size();
        assertEquals(1, i);
    }

    @Test
    public final void testLengthEmpty() {
        Map<String, String> s = this.createFromArgs();
        int i = s.size();
        assertEquals(0, i);
    }

    @Test
    public final void testLengthTripleEmpty() {
        Map<String, String> s = this.createFromArgs("red", "blue", "green",
                "one", "two", "three");
        int i = s.size();
        assertEquals(3, i);
    }
}
