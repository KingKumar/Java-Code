import java.util.Iterator;
import java.util.NoSuchElementException;

import components.queue.Queue;
import components.queue.QueueSecondary;
import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * {@code Queue} represented as a {@code Sequence} of entries, with
 * implementations of primary methods.
 * 
 * @param <T>
 *            type of {@code Queue} entries
 * @correspondence <pre>
 * {@code this = $this.entries}
 * </pre>
 */
public class Queue3<T> extends QueueSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Entries included in {@code this}.
     */
    private Sequence<T> entries;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.entries = new Sequence1L<T>();
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Queue3() {
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
        assert source instanceof Queue3<?> : ""
                + "Violation of: source is of dynamic type Queue3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Queue3<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Queue3<T> localSource = (Queue3<T>) source;
        this.entries = localSource.entries;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void enqueue(T x) {
        assert x != null : "Violation of: x is not null";
        this.entries.add(this.entries.length(), x);
    }

    @Override
    public final T dequeue() {
        assert this.length() > 0 : "Violation of: this /= <>";
        Sequence copy = new Sequence1L<T>();
        this.entries.extract(0, 1, copy);
        return (T) this;
    }

    @Override
    public final T front() {
        assert this.length() > 0 : "Violation of: this /= <>";
        Sequence copy = new Sequence1L<T>();
        return this.entries.entryAt(0);
    }

    @Override
    public final int length() {
        int length = this.entries.length();
        return length;
    }

    @Override
    public final Iterator<T> iterator() {
        return new Queue3Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Queue3}.
     */
    private final class Queue3Iterator implements Iterator<T> {

        /**
         * Representation iterator.
         */
        private final Iterator<T> iterator;

        /**
         * Default constructor.
         */
        public Queue3Iterator() {
            this.iterator = Queue3.this.entries.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
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
            return this.iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }

}
