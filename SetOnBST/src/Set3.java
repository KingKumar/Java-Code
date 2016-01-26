import java.util.Iterator;
import java.util.NoSuchElementException;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * 
 * @author Ronit Kumar
 * 
 */

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 * 
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * {@code IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]}
 * </pre>
 * @convention <pre>
 * {@code IS_BST($this.tree)}
 * </pre>
 * @correspondence <pre>
 * {@code this = labels($this.tree)}
 * </pre>
 */
public class Set3<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     * 
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires <pre>
     * {@code IS_BST(t)}
     * </pre>
     * @ensures <pre>
     * {@code isInTree = (x is in labels(t))}
     * </pre>
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        for (T a : t) {
            if (a.equals(x)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inserts {@code x} in {@code t}.
     * 
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates {@code t}
     * @requires <pre>
     * {@code IS_BST(t)  and  x is not in labels(t)}
     * </pre>
     * @ensures <pre>
     * {@code IS_BST(t)  and  labels(t) = labels(#t) union {x}}
     * </pre>
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        if (t.size() != 0) {
            T label = t.disassemble(left, right);
            if (x.compareTo(label) < 0) {
                insertInTree(left, x);
            } else {
                insertInTree(right, x);
            }
            t.assemble(label, left, right);
        } else {
            t.assemble(x, left, right);
        }
    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     * 
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates {@code t}
     * @requires <pre>
     * {@code IS_BST(t)  and  |t| > 0}
     * </pre>
     * @ensures <pre>
     * {@code IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";

        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T min;
        T root = t.disassemble(left, right);
        if (left.height() > 0) {
            min = removeSmallest(left);
            t.assemble(root, left, right);
        } else {
            min = root;
            t.transferFrom(right);
        }
        return min;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     * 
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates {@code t}
     * @requires <pre>
     * {@code IS_BST(t)  and  x is in labels(t)}
     * </pre>
     * @ensures <pre>
     * {@code IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        T remove = null;
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T label = t.disassemble(left, right);
        if (label.equals(x)) {
            remove = label;
            if (right.size() > 0) {
                t.assemble(removeSmallest(right), left, right);
            } else {
                t.transferFrom(left);
            }
        } else if (x.compareTo(label) < 0) {
            remove = removeFromTree(left, x);
            t.assemble(label, left, right);
        } else if (x.compareTo(label) > 0) {
            remove = removeFromTree(right, x);
            t.assemble(label, left, right);
        }
        return remove;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.tree = new BinaryTree1<T>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Set3() {

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
        assert source instanceof Set3<?> : ""
                + "Violation of: source is of dynamic type Set5<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set5<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3<T> localSource = (Set3<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        insertInTree(this.tree, x);

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        return removeFromTree(this.tree, x);

    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return new Set5Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Set5}.
     */
    private final class Set5Iterator implements Iterator<T> {

        /**
         * Representation iterator.
         */
        private final Iterator<T> iterator;

        /**
         * Default constructor.
         */
        public Set5Iterator() {
            this.iterator = Set3.this.tree.iterator();
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
