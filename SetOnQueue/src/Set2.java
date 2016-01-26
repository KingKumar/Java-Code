import java.util.Iterator;
import java.util.NoSuchElementException;

import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code Queue} of elements with implementations
 * of primary methods.
 * 
 * @param <T>
 *            type of {@code Set} elements
 * @convention <pre>
 * {@code for all x1, x2: T, a, b, c: string of T
 *     where ($this.elements = a * <x1> * b * <x2> * c)
 *   (x1 /= x2)}
 * </pre>
 * @correspondence <pre>
 * {@code this = entries($this.elements)}
 * </pre>
 */
public class Set2<T> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private Queue<T> elements;

    /**
     * Finds {@code x} in {@code q} and, if such exists, moves it to the front
     * of {@code q}.
     * 
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to be searched
     * @param x
     *            the entry to be searched for
     * @updates {@code q}
     * @ensures <pre>
     * {@code perms(q, #q)  and
     * if <x> is substring of q
     *  then <x> is prefix of q}
     * </pre>
     */
    private static <T> void moveToFront(Queue<T> q, T x) {
        assert q != null : "Violation of: q is not null";
        Queue<T> q2 = q.newInstance();
        int count = 0, qlength = q.length();
        while (count < qlength) {
            T sub = q.dequeue();
            count++;
            if (sub.equals(x)) {
                q2.enqueue(sub);
                while (q.length() != 0) {
                    sub = q.dequeue();
                    q2.enqueue(sub);
                }
                q.transferFrom(q2);
            } else {
                q.enqueue(sub);
            }
        }
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.elements = new Queue1L<T>();
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Set2() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
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
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set2<?> : ""
                + "Violation of: source is of dynamic type Set2<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set2<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set2<T> localSource = (Set2<T>) source;
        this.elements = localSource.elements;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";
        this.elements.enqueue(x);
    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";
        moveToFront(this.elements, x);
        return this.elements.dequeue();
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";
        moveToFront(this.elements, x);
        if (this.elements.front().equals(x)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final int size() {
        return this.elements.length();
    }

    @Override
    public final Iterator<T> iterator() {
        return new Set2Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Set2}.
     */
    private final class Set2Iterator implements Iterator<T> {

        /**
         * Representation iterator.
         */
        private final Iterator<T> iterator;

        /**
         * Default constructor.
         */
        public Set2Iterator() {
            this.iterator = Set2.this.elements.iterator();
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
