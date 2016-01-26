package components.list;

/**
 * {@code ListKernel} enhanced with secondary methods.
 * 
 * @param <T>
 *            type of {@code List} entries
 */
public interface List<T> extends ListKernel<T> {

    /**
     * Retreats the position in {@code this} by one.
     * 
     * @updates {@code this}
     * @requires <pre>
     * {@code this.left /= <>}
     * </pre>
     * @ensures <pre>
     * {@code this.left * this.right = #this.left * #this.right  and
     * |this.left| = |#this.left| - 1}
     * </pre>
     */
    void retreat();

    /**
     * Swaps the right strings of {@code this} and {@code list}.
     * 
     * @param list
     *            the {@code List} whose right string is to be swapped with the
     *            right string of {@code this}
     * @updates {@code this.right, list.right}
     * @ensures <pre>
     * {@code this.left = #this.left  and  list.left = #list.left  and
     * this.right = #list.right  and  list.right = #this.right}
     * </pre>
     */
    void swapRights(List<T> list);

}
