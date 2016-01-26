package components.queue;

import java.util.Comparator;

/**
 * {@code QueueKernel} enhanced with secondary methods.
 * 
 * @param <T>
 *            type of {@code Queue} entries
 * @mathdefinitions <pre>
 * {@code IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 * 
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  perms(s, #s)  and
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))}
 * </pre>
 */
public interface Queue<T> extends QueueKernel<T> {

    /**
     * Concatenates ("appends") {@code q} to the end of {@code this}.
     * 
     * @param q
     *            the {@code Queue} to be appended to the end of {@code this}
     * @updates {@code this}
     * @clears {@code q}
     * @ensures <pre>
     * {@code this = #this * #q}
     * </pre>
     */
    void append(Queue<T> q);

    /**
     * Reverses ("flips") {@code this}.
     * 
     * @updates {@code this}
     * @ensures <pre>
     * {@code this = reverse(#this)}
     * </pre>
     */
    void flip();

    /**
     * Sorts {@code this} according to the ordering provided by the
     * {@code compare} method from {@code order}.
     * 
     * @param order
     *            ordering by which to sort
     * @updates {@code this}
     * @requires <pre>
     * IS_TOTAL_PREORDER([relation computed by order.compare method])
     * </pre>
     * @ensures <pre>
     * {@code IS_SORTED(this, [relation computed by order.compare method])}
     * </pre>
     */
    void sort(Comparator<T> order);

}
