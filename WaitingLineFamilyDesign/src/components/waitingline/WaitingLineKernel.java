package components.waitingline;

import components.standard.Standard;

/**
 * First-in-first-out (FIFO) queue kernel component with primary methods. (Note:
 * by package-wide convention, all references are non-null.)
 * 
 * @param <T>
 *            type of {@code WaitingLineKernel} entries
 * @mathmodel <pre>
 * {@code type WaitingLineKernel is modeled by string of T}
 * </pre>
 * @initially <pre>
 * {@code default:
 *  ensures
 *   this = <>}
 * </pre>
 * @iterator <pre>
 * {@code ~this.seen * ~this.unseen = this}
 * </pre>
 */
public interface WaitingLineKernel<T> extends Standard<WaitingLine<T>>,
        Iterable<T> {

    /**
     * Adds {@code x} to the end of {@code this}.
     * 
     * @param x
     *            the entry to be added
     * @aliases reference {@code x}
     * @updates {@code this}
     * @ensures <pre>
     * {@code this = #this * <x>}
     * </pre>
     */
    void enqueue(T x);

    /**
     * Removes {@code x} from the front of {@code this}.
     * 
     * @return the entry removed
     * @updates {@code this}
     * @requires <pre>
     * {@code this /= <>}
     * </pre>
     * @ensures <pre>
     * {@code #this = <dequeue> * this}
     * </pre>
     */
    T dequeue();

    /**
     * Reports the front of {@code this}.
     * 
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code front}
     * @requires <pre>
     * {@code this /= <>}
     * </pre>
     * @ensures <pre>
     * {@code <front> is prefix of this}
     * </pre>
     */
    T front();

    /**
     * Reports length of {@code this}.
     * 
     * @return the length of {@code this}
     * @ensures <pre>
     * {@code length = |this|}
     * </pre>
     */
    int length();
}
