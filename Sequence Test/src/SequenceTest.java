import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 * 
 * @author Ronit Kumar
 * 
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor and returns the
     * result.
     * 
     * @return the new sequence
     * @ensures <pre>
     * {@code constructor = <>}
     * </pre>
     */
    protected abstract Sequence<String> constructor();

    /**
     * 
     * Creates and returns a {@code Sequence<String>} with the given entries.
     * 
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures <pre>
     * {@code createFromArgs = [entries in args]}
     * </pre>
     */
    private Sequence<String> createFromArgs(String... args) {
        Sequence<String> sequence = this.constructor();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    // test cases for constructor, add, remove, entryAt, replaceEntryAt, and
    // length
    @Test
    public final void testDefaultConstructor() {
        Sequence<String> s = this.constructor();
        assertEquals(s.length(), 0);
        assertEquals("<>", s.toString());
    }

    @Test
    public final void testAddNonEmpty() {
        Sequence<String> s = this.createFromArgs("red", "blue");
        s.add(0, "green");
        assertEquals("<green,red,blue>", s.toString());
    }

    @Test
    public final void testAddEmpty() {
        Sequence<String> s = this.constructor();
        s.add(0, "green");
        assertEquals("<green>", s.toString());
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        Sequence<String> s = this.createFromArgs("red");
        String x = s.remove(0);
        assertEquals("<>", s.toString());
        assertEquals("red", x);
    }

    @Test
    public final void testRemoveLeavingNonEmpty() {
        Sequence<String> s = this.createFromArgs("red", "blue");
        String x = s.remove(1);
        assertEquals("<red>", s.toString());
        assertEquals("blue", x);
    }

    @Test
    public final void testEntryAtSingleEntry() {
        Sequence<String> s = this.createFromArgs("red");
        String x = s.entryAt(0);
        assertEquals("<red>", s.toString());
        assertEquals("red", x);
    }

    @Test
    public final void testEntryAtTripleEntry() {
        Sequence<String> s = this.createFromArgs("red", "blue", "green");
        String x = s.entryAt(2);
        assertEquals("<red,blue>", s.toString());
        assertEquals("green", x);
    }

    @Test
    public final void testLengthNonEmpty() {
        Sequence<String> s = this.createFromArgs("red", "blue");
        int i = s.length();
        assertEquals("<red,blue>", s.toString());
        assertEquals(2, i);
    }

    @Test
    public final void testReplaceEntryAt() {
        Sequence<String> s = this.createFromArgs("red", "blue");
        String x = "blue";
        s.replaceEntryAt(0, x);
        assertEquals("<blue,blue>", s.toString());
        assertEquals(s.entryAt(0), "blue");
    }
}