package components.naturalnumber;

import java.math.BigInteger;

/**
 * {@code NaturalNumber} represented as a {@code java.math.BigInteger} with
 * implementations of primary methods.
 * 
 * @convention <pre>
 * {@code
 * $this.rep >= 0
 * }
 * </pre>
 * @correspondence <pre>
 * {@code 
 * this = $this.rep
 * }
 * </pre>
 */
public class NaturalNumber1L extends NaturalNumberSecondary {

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
    private BigInteger rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = BigInteger.valueOf(0);
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public NaturalNumber1L() {
        this.createNewRep();
    }

    /**
     * Constructor from {@code int}.
     * 
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber1L(int i) {
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
    public NaturalNumber1L(String s) {
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
    public NaturalNumber1L(NaturalNumber n) {
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
        assert source instanceof NaturalNumber1L : ""
                + "Violation of: source is of dynamic type NaturalNumber1L";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber1L localSource = (NaturalNumber1L) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < TEN : "Violation of: k < 10";
        BigInteger bigK = BigInteger.valueOf(k);
        this.rep = this.rep.multiply(BigInteger.TEN);
        this.rep = this.rep.add(bigK);
    }

    @Override
    public final int divideBy10() {
        BigInteger[] results = this.rep.divideAndRemainder(BigInteger.TEN);
        this.rep = results[0];
        return results[1].intValue();
    }

    @Override
    public final boolean isZero() {
        return this.rep.compareTo(BigInteger.ZERO) == 0;
    }

}
