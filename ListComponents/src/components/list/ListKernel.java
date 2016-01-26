package components.list;

import components.standard.Standard;

/**
 * List kernel component with primary methods. (Note: by package-wide
 * convention, all references are non-null.)
 * 
 * @param <T>
 *            type of {@code ListKernel} entries
 * @mathsubtypes <pre>
 * {@code LIST_MODEL is (
 *   left: string of T,
 *   right: string of of T
 *  )}
 * </pre>
 * @mathmodel <pre>
 * {@code type ListKernel is modeled by LIST_MODEL}
 * </pre>
 * @initially <pre>
 * {@code this = ( <>, <> )}
 * </pre>
 * @iterator <pre>
 * {@code ~this.seen * ~this.unseen = this.left * this.right}
 * </pre>
 */
public interface ListKernel<T> extends Standard<List<T>>, Iterable<T> {

    /**
     * Adds {@code x} to the beginning of {@code this.right}.
     * 
     * @param x
     *            the entry to be added
     * @aliases reference {@code x}
     * @updates {@code this.right}
     * @ensures <pre>
     * {@code this.right = <x> * #this.right}
     * </pre>
     */
    void addRight(T x);

    /**
     * Removes and returns the entry at the front of {@code this.right}.
     * 
     * @return the front entry of {@code this.right}
     * @updates {@code this.right}
     * @requires <pre>
     * {@code this.right /= <>}
     * </pre>
     * @ensures <pre>
     * {@code #this.right = <removeRight> * this.right}
     * </pre>
     */
    T removeRight();

    /**
     * Reports the front of {@code this.right}.
     * 
     * @return the front entry of {@code this.right}
     * @aliases reference returned by {@code rightFront}
     * @requires <pre>
     * {@code this.right /= <>}
     * </pre>
     * @ensures <pre>
     * {@code <rightFront> is prefix of this.right}
     * </pre>
     */
    T rightFront();

    /**
     * Advances the position in {@code this} by one.
     * 
     * @updates {@code this}
     * @requires <pre>
     * {@code this.right /= <>}
     * </pre>
     * @ensures <pre>
     * {@code this.left * this.right = #this.left * #this.right  and
     * |this.left| = |#this.left| + 1}
     * </pre>
     */
    void advance();

    /**
     * Moves the position in {@code this} to the beginning.
     * 
     * @updates {@code this}
     * @ensures <pre>
     * {@code this.right = #this.left * #this.right  and  |this.left| = 0}
     * </pre>
     */
    void moveToStart();

    /**
     * Moves the position in {@code this} to the end.
     * 
     * @updates {@code this}
     * @ensures <pre>
     * {@code this.left = #this.left * #this.right  and  |this.right| = 0}
     * </pre>
     */
    void moveToFinish();

    /**
     * Reports length of {@code this.left}.
     * 
     * @return the length of {@code this.left}
     * @ensures <pre>
     * {@code leftLength = |this.left|}
     * </pre>
     */
    int leftLength();

    /**
     * Reports length of {@code this.right}.
     * 
     * @return the length of {@code this.right}
     * @ensures <pre>
     * {@code rightLength = |this.right|}
     * </pre>
     */
    int rightLength();

}
