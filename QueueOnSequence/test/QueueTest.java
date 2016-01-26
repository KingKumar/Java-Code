import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;

/**
 * JUnit test fixture for {@code Queue<String>}'s constructor and kernel
 * methods.
 * 
 * @author Ronit Kumar
 * 
 */
public abstract class QueueTest {

    /**
     * Invokes the appropriate {@code Queue} constructor and returns the result.
     * 
     * @return the new stack
     * @ensures <pre>
     * {@code constructor = <>}
     * </pre>
     */
    protected abstract Queue<String> constructor();

    /**
     * 
     * Creates and returns a {@code Queue<String>} with the given entries.
     * 
     * @param args
     *            the entries for the queue
     * @return the constructed queue
     * @ensures <pre>
     * {@code createFromArgs = [entries in args]}
     * </pre>
     */
    private Queue<String> createFromArgs(String... args) {
        Queue<String> queue = this.constructor();
        for (String s : args) {
            queue.enqueue(s);
        }
        return queue;
    }

    // TODO - add test cases for constructor, enqueue, dequeue, front, and
    // length
    /**
     * Test constructor!
     */
    @Test
    public final void enqueueTest() {
        /*
         * Set up arguments and call method under test
         */
        Queue<String> q1 = this.createFromArgs("jack", "and");
        Queue<String> q2 = this.createFromArgs("jack", "and", "jill");
        q1.enqueue("jill");
        assertEquals(q1, q2);
    }

    @Test
    public final void dequeueTest() {
        /*
         * Set up arguments and call method under test
         */
        Queue<String> q1 = this.createFromArgs("jack", "and", "jill");
        Queue<String> q2 = this.createFromArgs("and", "jill");
        q1.dequeue();
        assertEquals(q1, q2);
    }

    @Test
    public final void frontTest() {
        /*
         * Set up arguments and call method under test
         */
        Queue<String> q1 = this.createFromArgs("jack", "and", "jill");
        String q2 = "";
        q2 = q1.front();
        assertEquals("jack", q2);
    }

    @Test
    public final void lengthTest() {
        /*
         * Set up arguments and call method under test
         */
        Queue<String> q1 = this.createFromArgs("jack", "and", "jill");
        int length = q1.length();
        assertEquals(length, 3);
    }

    @Test
    public final void constructorTest() {
        /*
         * Set up arguments and call method under test
         */
        Queue<String> q1 = this.createFromArgs("jack", "and");
        Queue<String> q2 = this.createFromArgs("jack", "and", "jill");
        q1.enqueue("jill");
        assertEquals(q1, q2);
    }
}
