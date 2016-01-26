package components.naturalnumber;

import components.standard.Standard;

/**
 * Natural number component with primary methods. (Note: by package-wide
 * convention, all references are non-null.)
 * 
 * @mathsubtypes <pre>
 * {@code NATURAL is integer
 *  exemplar n
 *  constraint n >= 0}
 * </pre>
 * @mathmodel <pre>
 * {@code type NaturalNumberKernel is modeled by NATURAL}
 * </pre>
 * @initially <pre>
 * {@code default:
 *  ensures
 *   this = 0
 * int i:
 *  requires
 *   i >= 0
 *  ensures
 *   this = i
 * String s:
 *  requires
 *   there exists n: NATURAL (s = TO_STRING(n))
 *  ensures
 *   s = TO_STRING(this)
 * NaturalNumber n:
 *  ensures
 *   this = n}
 * </pre>
 */
public interface NaturalNumberKernel extends Standard<NaturalNumber> {

    /**
     * Multiplies {@code this} by 10 and adds {@code k}.
     * 
     * @param k
     *            the {@code int} to be added
     * @updates {@code this}
     * @requires <pre>
     * {@code 0 <= k < 10}
     * </pre>
     * @ensures <pre>
     * {@code this = 10 * #this + k}
     * </pre>
     */
    void multiplyBy10(int k);

    /**
     * Divides {@code this} by 10 and reports the remainder.
     * 
     * @return the remainder
     * @updates {@code this}
     * @ensures <pre>
     * {@code #this = 10 * this + divideBy10  and
     * 0 <= divideBy10 < 10}
     * </pre>
     */
    int divideBy10();

    /**
     * Reports whether {@code this} is zero.
     * 
     * @return true iff {@code this} is zero
     * @ensures <pre>
     * {@code isZero = (this = 0)}
     * </pre>
     */
    boolean isZero();

}
