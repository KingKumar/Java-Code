package components.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * {@code Queue} represented as a singly linked list, done "bare-handed", with
 * implementations of primary methods.
 * 
 * <p>
 * Execution-time performance of all methods implemented in this class is O(1).
 * 
 * @param <T>
 *            type of {@code Queue} entries
 * @convention <pre>
 * {@code $this.length >= 0  and
 * [$this.preFront is not null]  and
 * [$this.rear is not null]  and
 * [$this.preFront points to the first node of a singly linked list
 *  containing $this.length + 1 nodes]  and
 * [$this.rear points to the last node in that singly linked list]  and
 * [$this.rear.next is null]}
 * </pre>
 * @correspondence <pre>
 * {@code this = [data in nodes starting at $this.preFront.next and
 *  running through $this.rear]}
 * </pre>
 */
public class Queue2<T> extends QueueSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Node class for singly linked list nodes.
     */
    private final class Node {

        /**
         * Data in node.
         */

        private T data;

        /**
         * Next node in singly linked list, or null.
         */
        private Node next = null;

    }

    /**
     * "Smart node" before front node of singly linked list.
     */
    private Node preFront;

    /**
     * Rear node of singly linked list.
     */
    private Node rear;

    /**
     * One less than number of nodes in singly linked list, i.e., length =
     * |this|.
     */
    private int length;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.preFront = new Node();
        this.rear = this.preFront;
        this.length = 0;
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Queue2() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Queue<T> newInstance() {
        try {
            return this.getClass().newInstance();
        } catch (Exception e) { // ReflectiveOperationException in Java 7
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Queue<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Queue2<?> : ""
                + "Violation of: source is of dynamic type Queue2<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Queue2<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Queue2<T> localSource = (Queue2<T>) source;
        this.preFront = localSource.preFront;
        this.rear = localSource.rear;
        this.length = localSource.length;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void enqueue(T x) {
        assert x != null : "Violation of: x is not null";
        Node p = new Node();
        Node q = this.rear;
        p.data = x;
        q.next = p;
        this.rear = p;
        this.length++;
    }

    @Override
    public final T dequeue() {
        assert this.length() > 0 : "Violation of: this /= <>";
        Node p = this.preFront;
        Node q = p.next;
        T result = q.data;
        this.preFront = q;
        this.length--;
        return result;
    }

    @Override
    public final T front() {
        assert this.length() > 0 : "Violation of: this /= <>";
        return this.preFront.next.data;
    }

    @Override
    public final int length() {
        return this.length;
    }

    @Override
    public final Iterator<T> iterator() {
        return new Queue2Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Queue2}.
     */
    private final class Queue2Iterator implements Iterator<T> {

        /**
         * Current node in the linked list.
         */
        private Node current;

        /**
         * Default constructor.
         */
        private Queue2Iterator() {
            this.current = Queue2.this.preFront.next;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public T next() {
            assert this.hasNext() : "Violation of: ~this.unseen /= <>";
            if (!this.hasNext()) {
                /*
                 * Exception is supposed to be thrown in this case, but with
                 * assertion-checking enabled it cannot happen because of assert
                 * above.
                 */
                throw new NoSuchElementException();
            }
            T x = this.current.data;
            this.current = this.current.next;
            return x;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }

}
