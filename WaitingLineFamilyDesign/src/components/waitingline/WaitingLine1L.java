package components.waitingline;

import java.util.Iterator;
import java.util.NoSuchElementException;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * {@code Queue} represented as a {@code java.util.List} with implementations of
 * primary methods.
 * 
 * @param <T>
 *            type of {@code Queue} entries
 * @correspondence <pre>
 * {@code this = [value of $this.rep based on List's "proper sequence"]}
 * </pre>
 */
public class WaitingLine1L<T> extends WaitingLineSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private Queue<T> rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = new Queue1L<T>();
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public WaitingLine1L() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final WaitingLine<T> newInstance() {
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
    public final void transferFrom(WaitingLine<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof WaitingLine1L<?> : ""
                + "Violation of: source is of dynamic type Queue1L<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Queue1L<?>,
         * and the ? must be T or the call would not have compiled.
         */
        WaitingLine1L<T> localSource = (WaitingLine1L<T>) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void enqueue(T x) {
        assert x != null : "Violation of: x is not null";
        this.rep.enqueue(x);
    }

    @Override
    public final T dequeue() {
        assert this.length() > 0 : "Violation of: this /= <>";
        return this.rep.dequeue();
    }

    @Override
    public final T front() {
        assert this.length() > 0 : "Violation of: this /= <>";
        return this.rep.front();
    }

    @Override
    public final int length() {
        return this.rep.length();
    }

    /*
     * This implementation is simple, but technically permits a client to call
     * the optional remove method of the iterator; the more complex code that
     * follows prevents this.
     */
    // @Override
    // public final Iterator<T> iterator() {
    // return this.rep.iterator();
    // }

    @Override
    public final Iterator<T> iterator() {
        return new WaitingLine1LIterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Queue1L}.
     */
    private final class WaitingLine1LIterator implements Iterator<T> {

        /**
         * Representation iterator.
         */
        private final Iterator<T> iterator;

        /**
         * Default constructor.
         */
        private WaitingLine1LIterator() {
            this.iterator = WaitingLine1L.this.rep.iterator();
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
