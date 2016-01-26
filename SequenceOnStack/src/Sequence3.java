import java.util.Iterator;
import java.util.NoSuchElementException;

import components.sequence.Sequence;
import components.sequence.SequenceSecondary;
import components.stack.Stack;
import components.stack.Stack1L;

/**
 * {@code Sequence} represented as a pair of {@code Stack}s with implementations
 * of primary methods.
 * 
 * @param <T>
 *            type of {@code Sequence} entries
 * @correspondence <pre>
 * {@code this = rev($this.left) * $this.right}
 * </pre>
 */
public class Sequence3<T> extends SequenceSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Left stack.
     */
    private Stack<T> left;

    /**
     * Right stack.
     */
    private Stack<T> right;

    /**
     * Shifts entries between {@code leftStack} and {@code rightStack}, keeping
     * reverse of the former concatenated with the latter fixed, and resulting
     * in length of the former equal to {@code newLeftLength}.
     * 
     * @param <T>
     *            type of {@code Stack} entries
     * @param leftStack
     *            the left {@code Stack}
     * @param rightStack
     *            the right {@code Stack}
     * @param newLeftLength
     *            desired new length of {@code leftStack}
     * @updates {@code leftStack, rightStack}
     * @requires <pre>
     * {@code 0 <= newLeftLength  and
     * newLeftLength <= |leftStack| + |rightStack|}
     * </pre>
     * @ensures <pre>
     * {@code rev(leftStack) * rightStack =
     *   rev(#leftStack) * #rightStack  and
     * |leftStack| = newLeftLength}
     * }
     * </pre>
     */
    private static <T> void setLengthOfLeftStack(Stack<T> leftStack,
            Stack<T> rightStack, int newLeftLength) {
        assert leftStack != null : "Violation of: rightStack is not null";
        assert leftStack != null : "Violation of: rightStack is not null";
        assert 0 <= newLeftLength : "Violation of: 0 <= newLeftLength";
        assert newLeftLength <= leftStack.length() + rightStack.length() : ""
                + "Violation of: newLeftLength <= |leftStack| + |rightStack|";

        if (leftStack.length() < newLeftLength) {
            while (leftStack.length() != newLeftLength) {
                leftStack.push(rightStack.pop());
            }
        } else if (leftStack.length() > newLeftLength) {
            while (leftStack.length() != newLeftLength) {
                rightStack.push(leftStack.pop());
            }
        }
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.left = new Stack1L<T>();
        this.right = new Stack1L<T>();
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Sequence3() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Sequence<T> newInstance() {
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
    public final void transferFrom(Sequence<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Sequence3<?> : ""
                + "Violation of: source is of dynamic type Sequence3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Sequence3<?>,
         * and the ? must be T or the call would not have compiled.
         */
        Sequence3<T> localSource = (Sequence3<T>) source;
        this.left = localSource.left;
        this.right = localSource.right;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(int pos, T x) {
        assert x != null : "Violation of: x is not null";
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos <= this.length() : "Violation of: pos <= |this|";
        setLengthOfLeftStack(this.left, this.right, pos);
        this.right.push(x);
    }

    @Override
    public final T remove(int pos) {
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos < this.length() : "Violation of: pos < |this|";
        setLengthOfLeftStack(this.left, this.right, pos);
        return this.right.pop();
    }

    @Override
    public final T entryAt(int pos) {
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos < this.length() : "Violation of: pos < |this|";
        setLengthOfLeftStack(this.left, this.right, pos);
        return this.right.top();
    }

    @Override
    public final int length() {
        return (this.left.length() + this.right.length());
    }

    @Override
    public final Iterator<T> iterator() {
        return new Sequence3Iterator();
    }

    @Override
    public final T replaceEntryAt(int pos, T x) {
        setLengthOfLeftStack(this.left, this.right, pos);
        T sub = this.right.pop();
        this.right.push(x);
        return sub;
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Sequence3}.
     */
    private final class Sequence3Iterator implements Iterator<T> {

        /**
         * Representation iterator.
         */
        private final Iterator<T> iterator;

        /**
         * Default constructor.
         */
        private Sequence3Iterator() {
            Sequence3.setLengthOfLeftStack(Sequence3.this.left,
                    Sequence3.this.right, 0);
            this.iterator = Sequence3.this.right.iterator();
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
