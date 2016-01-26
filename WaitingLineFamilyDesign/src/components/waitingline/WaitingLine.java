package components.waitingline;

/**
 * {@code WaitingLineKernel} enhanced with secondary methods.
 * 
 * @param <T>
 *            type of {@code WaitingLine} entries
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
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Concatenates ("appends") {@code q} to the end of {@code this}.
     * 
     * @param q
     *            the {@code WaitingLine} to be appended to the end of
     *            {@code this}
     * @updates {@code this}
     * @clears {@code q}
     * @ensures <pre>
     * {@code this = #this * #q}
     * </pre>
     */
    void append(WaitingLine<T> q);

    /**
     * Reports the entry at index of {@code this}.
     * 
     * @return the entry of {@code this} at given position
     * @aliases reference returned by {@code remove}
     * @updates this
     * @requires <pre>
     * {@code this /= <>}
     * </pre>
     * @ensures <pre>
     * {@code <remove> is entry of this at index given by position}
     * </pre>
     */
    T remove(T x);

    /**
     * Reports index of entry in {@code this}.
     * 
     * @return the index of entry in {@code this}
     * @ensures <pre>
     * {@code length = |this|}
     * </pre>
     */
    int position(T entry);
}
