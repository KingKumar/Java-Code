package components.naturalnumber;

/**
 * {@code NaturalNumberKernel} enhanced with secondary methods.
 */
public interface NaturalNumber extends Comparable<NaturalNumber>,
        NaturalNumberKernel {

    /**
     * Sets the value of {@code this} to {@code i}, when {@code i} is
     * non-negative.
     * 
     * @param i
     *            the new value
     * @replaces {@code this}
     * @requires <pre>
     * {@code i >= 0}
     * </pre>
     * @ensures <pre>
     * {@code this = i}
     * </pre>
     */
    void setFromInt(int i);

    /**
     * Reports whether {@code this} is small enough to convert to {@code int}.
     * 
     * @return true iff {@code this} is small enough
     * @ensures <pre>
     * {@code canConvertToInt = (this <= Integer.MAX_VALUE)}
     * </pre>
     */
    boolean canConvertToInt();

    /**
     * Reports the value of {@code this} as an {@code int}, when {@code this} is
     * small enough.
     * 
     * @return the value
     * @requires <pre>
     * {@code this <= Integer.MAX_VALUE}
     * </pre>
     * @ensures <pre>
     * {@code intValue = this}
     * </pre>
     */
    int toInt();

    /**
     * Reports whether {@code s} is of the right form to convert to a
     * {@code NaturalNumber}.
     * 
     * @param s
     *            the {@code String} to be converted
     * @return true iff {@code s} is of the right form
     * @ensures <pre>
     * {@code canConvertToNatural = there exists n: NATURAL (s = TO_STRING(n))}
     * </pre>
     */
    boolean canSetFromString(String s);

    /**
     * Sets the value of {@code this} to the number whose standard decimal
     * representation as a {@code String} is {@code s}, when {@code s} has the
     * appropriate form (i.e., {@code s} is the result of the function
     * {@code toString} for some {@code NaturalNumber}).
     * 
     * @param s
     *            the {@code String} to be converted
     * @replaces {@code this}
     * @requires <pre>
     * {@code there exists n: NATURAL (s = TO_STRING(n))}
     * </pre>
     * @ensures <pre>
     * {@code s = TO_STRING(this)}
     * </pre>
     */
    void setFromString(String s);

    /**
     * Copies {@code n} to {@code this}.
     * 
     * @param n
     *            {@code NaturalNumber} to copy from
     * @replaces {@code this}
     * @ensures <pre>
     * {@code this = n}
     * </pre>
     */
    void copyFrom(NaturalNumber n);

    /**
     * Increments {@code this}.
     * 
     * @updates {@code this}
     * @ensures <pre>
     * {@code this = #this + 1}
     * </pre>
     */
    void increment();

    /**
     * Decrements {@code this}.
     * 
     * @updates {@code this}
     * @requires <pre>
     * {@code this > 0}
     * </pre>
     * @ensures <pre>
     * {@code this = #this - 1}
     * </pre>
     */
    void decrement();

    /**
     * Adds {@code n} to {@code this}.
     * 
     * @param n
     *            {@code NaturalNumber} to add
     * @updates {@code this}
     * @ensures <pre>
     * {@code this = #this + n}
     * </pre>
     */
    void add(NaturalNumber n);

    /**
     * Subtracts {@code n} from {@code this}.
     * 
     * @param n
     *            {@code NaturalNumber} to subtract
     * @updates {@code this}
     * @requires <pre>
     * {@code this >= n}
     * </pre>
     * @ensures <pre>
     * {@code this = #this - n}
     * </pre>
     */
    void subtract(NaturalNumber n);

    /**
     * Multiplies {@code this} by {@code n}.
     * 
     * @param n
     *            {@code NaturalNumber} to multiply by
     * @updates {@code this}
     * @ensures <pre>
     * {@code this = #this * n}
     * </pre>
     */
    void multiply(NaturalNumber n);

    /**
     * Divides {@code this} by {@code n}, returning the remainder.
     * 
     * @param n
     *            {@code NaturalNumber} to divide by
     * @return remainder after division
     * @updates {@code this}
     * @requires <pre>
     * {@code n > 0}
     * </pre>
     * @ensures <pre>
     * {@code #this = this * n + divide  and
     * 0 <= divide < n}
     * </pre>
     */
    NaturalNumber divide(NaturalNumber n);

    /**
     * Raises {@code this} to the power {@code p}.
     * 
     * @param p
     *            power to raise to
     * @updates {@code this}
     * @requires <pre>
     * {@code p >= 0}
     * </pre>
     * @ensures <pre>
     * {@code this = #this ^ (p)}
     * </pre>
     */
    void power(int p);

    /**
     * Updates {@code this} to the {@code r}-th root of its incoming value.
     * 
     * @param r
     *            root
     * @updates {@code this}
     * @requires <pre>
     * {@code r >= 2}
     * </pre>
     * @ensures <pre>
     * {@code this ^ (r) <= #this < (this + 1) ^ (r)}
     * </pre>
     */
    void root(int r);

}
