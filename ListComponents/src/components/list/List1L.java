package components.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * {@code List} represented as a {@code java.util.List} with implementations of
 * primary methods.
 * 
 * @param <T>
 *            type of {@code List} entries
 * @correspondence <pre>
 * {@code this.left = [$this.leftList, based on java.util.List's "proper sequence"]  and
 * this.right = [$this.rightList, based on java.util.List's "proper sequence"]}
 * </pre>
 */
public class List1L<T> extends ListSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this.left}.
     */
    private java.util.List<T> leftList;

    /**
     * Representation of {@code this.right}.
     */
    private java.util.List<T> rightList;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.leftList = new LinkedList<T>();
        this.rightList = new LinkedList<T>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public List1L() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final List1L<T> newInstance() {
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
    public final void transferFrom(List<T> source) {
        assert source instanceof List1L<?> : ""
                + "Violation of: source is of dynamic type List1L<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type List1L<?>, and
         * the ? must be T or the call would not have compiled.
         */
        List1L<T> localSource = (List1L<T>) source;
        this.leftList = localSource.leftList;
        this.rightList = localSource.rightList;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void addRight(T x) {
        assert x != null : "Violation of: x is not null";
        this.rightList.add(0, x);
    }

    @Override
    public final T removeRight() {
        assert this.rightLength() > 0 : "Violation of: this.right /= <>";
        return this.rightList.remove(0);
    }

    @Override
    public final void advance() {
        assert this.rightLength() > 0 : "Violation of: this.right /= <>";
        T x = this.rightList.remove(0);
        this.leftList.add(x);
    }

    @Override
    public final T rightFront() {
        assert this.rightLength() > 0 : "Violation of: this.right /= <>";
        return this.rightList.get(0);
    }

    @Override
    public final void moveToStart() {
        this.rightList.addAll(0, this.leftList);
        this.leftList.clear();
    }

    @Override
    public final void moveToFinish() {
        this.leftList.addAll(this.rightList);
        this.rightList.clear();
    }

    @Override
    public final int leftLength() {
        return this.leftList.size();
    }

    @Override
    public final int rightLength() {
        return this.rightList.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return new List1LIterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code List1L}.
     */
    private final class List1LIterator implements Iterator<T> {

        /**
         * Current position of iterator.
         */
        private int notSeenCount;

        /**
         * Representation left iterator.
         */
        private Iterator<T> iterator;

        /**
         * Default constructor.
         */
        private List1LIterator() {
            this.iterator = List1L.this.leftList.iterator();
            this.notSeenCount = List1L.this.leftList.size()
                    + List1L.this.rightList.size();
        }

        @Override
        public boolean hasNext() {
            return this.notSeenCount > 0;
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
            if (!this.iterator.hasNext()) {
                this.iterator = List1L.this.rightList.iterator();
            }
            this.notSeenCount--;
            return this.iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }

}
