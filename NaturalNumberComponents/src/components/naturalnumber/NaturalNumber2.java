package components.naturalnumber;

import components.stack.Stack;
import components.stack.Stack1L;

/**
 * {@code NaturalNumber} represented as a {@code Stack<Integer>} with
 * implementations of primary methods.
 * 
 * <p>
 * Execution-time performance of all methods implemented in this class is O(1),
 * except the constructors from {@code String} (which is O(|{@code s}|)) and
 * from {@code NaturalNumber} (which is O(log {@code n})).
 * 
 * @mathdefinitions <pre>
 * HAS_ONLY_DIGITS (
 *   s: string of integer
 *  ): boolean satisfies
 *  if s = empty_string
 *   then HAS_ONLY_DIGITS (s) = true
 *   else for all a: string of integer, k: integer where (s = a * <k>)
 * 		   (HAS_ONLY_DIGITS (s) = (HAS_ONLY_DIGITS (a)  and  0 <= k < 10))
 * 
 * IS_WELL_FORMED_RADIX_REPRESENTATION (
 *   s: string of integer
 *  ): boolean is
 *  s = empty_string  or
 *      there exists k: integer, a: string of integer 
 *       (s = <k> * a  and
 *        1 <= k < 10  and
 *        HAS_ONLY_DIGITS (a))
 * 
 * NUMERICAL_VALUE (
 *   s: string of integer
 *  ): integer satisfies
 *  if s = empty_string
 *   then NUMERICAL_VALUE (s) = 0
 *   else for all a: string of integer, k: integer where (s = a * <k>)
 *         (NUMERICAL_VALUE (s) = NUMERICAL_VALUE (a) * 10 + k)
 * </pre>
 * @convention <pre>
 * {@code IS_WELL_FORMED_RADIX_REPRESENTATION (rev($this.digits))}
 * </pre>
 * @correspondence <pre>
 * {@code this = NUMERICAL_VALUE (rev($this.digits))}
 * </pre>
 */
public class NaturalNumber2 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constant 10 (so it's not a "magic number" in the code).
     */
    private static final int TEN = 10;

    /**
     * Representation of {@code this}.
     */
    private Stack<Integer> digits;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.digits = new Stack1L<Integer>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public NaturalNumber2() {
        this.createNewRep();
    }

    /**
     * Constructor from {@code int}.
     * 
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber2(int i) {
        assert i >= 0 : "Violation of: i >= 0";
        this.createNewRep();
        this.setFromInt(i);
    }

    /**
     * Constructor from {@code String}.
     * 
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber2(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*") : ""
                + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";
        this.createNewRep();
        this.setFromString(s);
    }

    /**
     * Constructor from {@code NaturalNumber}.
     * 
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber2(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        this.createNewRep();
        this.copyFrom(n);
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
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
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber2 : ""
                + "Violation of: source is of dynamic type NaturalNumber2";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber2 localSource = (NaturalNumber2) source;
        this.digits = localSource.digits;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < TEN : "Violation of: k < 10";
        if (this.digits.length() > 0 || k > 0) {
            this.digits.push(k);
        }
    }

    @Override
    public final int divideBy10() {
        if (this.digits.length() > 0) {
            Integer k = this.digits.pop();
            return k;
        }
        return 0;
    }

    @Override
    public final boolean isZero() {
        return this.digits.length() == 0;
    }

}
