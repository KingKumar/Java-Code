import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;

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
     * Creates and returns a {@code Set<String>} with the given entries.
     * 
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @ensures <pre>
     * {@code createFromArgs = [entries in args]}
     * </pre>
     */
    private Set<String> createFromArgs(String... args) {
        Set<String> set = this.constructor();
        for (String s : args) {
            set.add(s);
        }
        return set;
    }

    private Set<String> createFromArgs2(String... args) {
        Set1L<String> set = (Set1L<String>) this.constructor();
        for (String s : args) {
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, contains, and size
    @Test
    public final void testDefaultConstructor() {
        Set<String> s = this.constructor();
        assertEquals(s.size(), 0);
        assertEquals("<>", s.toString());
    }

    @Test
    public final void testAddNonEmpty() {
        Set<String> s = this.createFromArgs("red", "blue");
        s.add("green");
        Set<String> s2 = this.createFromArgs2("red", "blue", "green");
        assertEquals(s, s2);
    }

    @Test
    public final void testAddEmpty() {
        Set<String> s = this.constructor();
        s.add("green");
        assertEquals("<green>", s.toString());
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        Set<String> s = this.createFromArgs("red");
        String x = s.remove("red");
        assertEquals("<>", s.toString());
        assertEquals("red", x);
    }

    @Test
    public final void testRemoveLeavingNonEmpty() {
        Set<String> s = this.createFromArgs("red", "blue");
        String x = s.remove("blue");
        assertEquals("<red>", s.toString());
        assertEquals("blue", x);
    }

    @Test
    public final void testEntryAtSingleEntry() {
        Set<String> s = this.createFromArgs("red");
        Boolean x = s.contains("red");
        assertEquals(true, x);
    }

    @Test
    public final void testEntryAtTripleEntry() {
        Set<String> s = this.createFromArgs("red", "blue", "green");
        Boolean x = s.contains("green");
        assertEquals(true, x);
    }

    @Test
    public final void testLengthNonEmpty() {
        Set<String> s = this.createFromArgs("red", "blue");
        int i = s.size();
        assertEquals(2, i);
    }
}