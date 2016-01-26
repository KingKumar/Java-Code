import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 * 
 * @author Ronit Kumar
 * 
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor and returns the
     * result.
     * 
     * @return the new number
     * @ensures <pre>
     * {@code constructor = 0}
     * </pre>
     */
    protected abstract NaturalNumber constructor();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor and returns the
     * result.
     * 
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires <pre>
     * {@code i >= 0}
     * </pre>
     * @ensures <pre>
     * {@code constructor = i}
     * </pre>
     */
    protected abstract NaturalNumber constructor(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor and returns the
     * result.
     * 
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires <pre>
     * {@code there exists n: NATURAL (s = TO_STRING(n))}
     * </pre>
     * @ensures <pre>
     * {@code s = TO_STRING(constructor)}
     * </pre>
     */
    protected abstract NaturalNumber constructor(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor and returns the
     * result.
     * 
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures <pre>
     * {@code constructor = n}
     * </pre>
     */
    protected abstract NaturalNumber constructor(NaturalNumber n);

    /**
     * Test for Default Constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        NaturalNumber s = this.constructor();
        NaturalNumber r = this.constructor();
        assertEquals(s, r);
    }

    /**
     * Test for Int Constructor for 0.
     */
    @Test
    public final void testIntConstructorfor0() {
        NaturalNumber n = this.constructor(0);
        assertEquals(0, n.toInt());
    }

    /**
     * Test for Int Constructor for single digit.
     */
    @Test
    public final void testIntConstructorforSingleDigit() {
        NaturalNumber n = this.constructor(7);
        assertEquals(7, n.toInt());
    }

    /**
     * Test for Int Constructor for double digit.
     */
    @Test
    public final void testIntConstructorforDoubleDigit() {
        NaturalNumber n = this.constructor(75);
        assertEquals(75, n.toInt());
    }

    /**
     * Test for Int Constructor for triple digit.
     */
    @Test
    public final void testIntConstructorforTripleDigit() {
        NaturalNumber n = this.constructor(750);
        assertEquals(750, n.toInt());
    }

    /**
     * Test for String Constructor for 0.
     */
    public final void testStringConstructorfor0() {
        NaturalNumber n = this.constructor("0");
        assertEquals(0, n.toInt());
    }

    /**
     * Test for String Constructor for single digit.
     */
    @Test
    public final void testStringConstructorforSingleDigit() {
        NaturalNumber n = this.constructor("7");
        assertEquals(7, n.toInt());
    }

    /**
     * Test for String Constructor for double digit.
     */
    @Test
    public final void testStringConstructorforDoubleDigit() {
        NaturalNumber n = this.constructor("75");
        assertEquals(75, n.toInt());
    }

    /**
     * Test for String Constructor for triple digit.
     */
    @Test
    public final void testStringConstructorforTripleDigit() {
        NaturalNumber n = this.constructor("750");
        assertEquals(750, n.toInt());
    }

    /**
     * Test for Natural Number Constructor for 0.
     */
    public final void testNNConstructorfor0() {
        NaturalNumber n = this.constructor("");
        NaturalNumber m = this.constructor(n);
        assertEquals(0, m.toInt());
    }

    /**
     * Test for Natural Number Constructor for single digit.
     */
    @Test
    public final void testNNConstructorforSingleDigit() {
        NaturalNumber n = this.constructor("7");
        NaturalNumber m = this.constructor(n);
        assertEquals(7, m.toInt());
    }

    /**
     * Test for Natural Number Constructor for double digit.
     */
    @Test
    public final void testNNConstructorforDoubleDigit() {
        NaturalNumber n = this.constructor("75");
        NaturalNumber m = this.constructor(n);
        assertEquals(75, m.toInt());
    }

    /**
     * Test for Natural Number Constructor for triple digit.
     */
    @Test
    public final void testNNConstructorforTripleDigit() {
        NaturalNumber n = this.constructor("750");
        NaturalNumber m = this.constructor(n);
        assertEquals(750, m.toInt());
    }

    /**
     * Test for multiplyBy10 on 0.
     */
    @Test
    public final void testMultiplyBy10on0() {
        NaturalNumber s = this.constructor();
        NaturalNumber r = this.constructor();
        s.multiplyBy10(0);
        assertEquals(s, r);
    }

    /**
     * Test for multiplyBy10 on single digit number.
     */
    @Test
    public final void testMultiplyBy10onOneDigitNumber() {
        NaturalNumber s = this.constructor(7);
        NaturalNumber r = this.constructor(70);
        s.multiplyBy10(0);
        assertEquals(s, r);
    }

    /**
     * Test for multiplyBy10 on multiple digit number.
     */
    @Test
    public final void testMultiplyBy10onMultipleDigitNumber() {
        NaturalNumber s = this.constructor(767);
        NaturalNumber r = this.constructor(7670);
        s.multiplyBy10(0);
        assertEquals(s, r);
    }

    /**
     * Test for multiplyBy10 on 0 with addition of value.
     */
    @Test
    public final void testMultiplyBy10on0withAdd() {
        NaturalNumber s = this.constructor();
        NaturalNumber r = this.constructor(5);
        s.multiplyBy10(5);
        assertEquals(s, r);
    }

    /**
     * Test for multiplyBy10 on single digit number with addition of value.
     */
    @Test
    public final void testMultiplyBy10onOneDigitwithAdd() {
        NaturalNumber s = this.constructor(7);
        NaturalNumber r = this.constructor(75);
        s.multiplyBy10(5);
        assertEquals(s, r);
    }

    /**
     * Test for multiplyBy10 on multiple digit number with addition of value.
     */
    @Test
    public final void testMultiplyBy10onMultipleDigitNumberWithAdd() {
        NaturalNumber s = this.constructor(767);
        NaturalNumber r = this.constructor(7675);
        s.multiplyBy10(5);
        assertEquals(s, r);
    }

    /**
     * Test for divideBy10 on 0.
     */
    @Test
    public final void testDivideBy10on0() {
        NaturalNumber s = this.constructor();
        NaturalNumber r = this.constructor();
        int remainder = 0;
        remainder = s.divideBy10();
        assertEquals(s, r);
        assertEquals(remainder, 0);
    }

    /**
     * Test for divideBy10 on single digit number.
     */
    @Test
    public final void testDivideBy10onOneDigitNumber() {
        NaturalNumber s = this.constructor(70);
        NaturalNumber r = this.constructor(7);
        int remainder = 0;
        remainder = s.divideBy10();
        assertEquals(s, r);
        assertEquals(remainder, 0);
    }

    /**
     * Test for divideBy10 on multiple digit number.
     */
    @Test
    public final void testDivideBy10onMultipleDigitNumber() {
        NaturalNumber s = this.constructor(7670);
        NaturalNumber r = this.constructor(767);
        int remainder = 0;
        remainder = s.divideBy10();
        assertEquals(s, r);
        assertEquals(remainder, 0);
    }

    /**
     * Test for divideBy10 on single digit number extracting remainder.
     */
    @Test
    public final void testDivideBy10on5withRemainder() {
        NaturalNumber s = this.constructor(5);
        NaturalNumber r = this.constructor();
        int remainder = 0;
        remainder = s.divideBy10();
        assertEquals(s, r);
        assertEquals(remainder, 5);
    }

    /**
     * Test for divideBy10 on double digit number extracting remainder.
     */
    @Test
    public final void testDivideBy10onTwoDigitwithRemainder() {
        NaturalNumber s = this.constructor(75);
        NaturalNumber r = this.constructor(7);
        int remainder = 0;
        remainder = s.divideBy10();
        assertEquals(s, r);
        assertEquals(remainder, 5);
    }

    /**
     * Test for divideBy10 on multiple digit number extracting remainder.
     */
    @Test
    public final void testDivideBy10onMultipleDigitNumberWithRemainder() {
        NaturalNumber s = this.constructor(7675);
        NaturalNumber r = this.constructor(767);
        int remainder = 0;
        remainder = s.divideBy10();
        assertEquals(s, r);
        assertEquals(remainder, 5);
    }

    /**
     * Test for isZero (true) from NN built by Default Constructor.
     */
    @Test
    public final void testIsZeroTrueWithDefaultArg() {
        NaturalNumber s = this.constructor();
        boolean x = s.isZero();
        assertEquals(x, true);
    }

    /**
     * Test for isZero (true) from NN built by Int Constructor.
     */
    @Test
    public final void testIsZeroTrueWithIntArg() {
        NaturalNumber s = this.constructor(0);
        boolean x = s.isZero();
        assertEquals(x, true);
    }

    /**
     * Test for isZero (true) from NN built by String Constructor.
     */
    @Test
    public final void testIsZeroTrueWithStringArg() {
        NaturalNumber s = this.constructor("0");
        boolean x = s.isZero();
        assertEquals(x, true);
    }

    /**
     * Test for isZero (true) from NN built by NN Constructor.
     */
    @Test
    public final void testIsZeroTrueWithNNArg() {
        NaturalNumber s = this.constructor(0);
        NaturalNumber n = this.constructor(s);
        boolean x = n.isZero();
        assertEquals(x, true);
    }

    /**
     * Test for isZero (false) from NN built by Int Constructor.
     */
    @Test
    public final void testIsZeroFalseWithIntArg() {
        NaturalNumber s = this.constructor(17);
        boolean x = s.isZero();
        assertEquals(x, false);
    }

    /**
     * Test for isZero (false) from NN built by String Constructor.
     */
    @Test
    public final void testIsZeroFalseWithStringArg() {
        NaturalNumber s = this.constructor("978");
        boolean x = s.isZero();
        assertEquals(x, false);
    }

    /**
     * Test for isZero (false) from NN built by NN Constructor.
     */
    @Test
    public final void testIsZeroFalseWithNNArg() {
        NaturalNumber s = this.constructor(77);
        NaturalNumber n = this.constructor(s);
        boolean x = n.isZero();
        assertEquals(x, false);
    }

    /**
     * Test for newInstance.
     */
    @Test
    public final void testNewInstance() {
        NaturalNumber s = this.constructor(7);
        NaturalNumber n = s.newInstance();
        NaturalNumber m = s.newInstance();
        assertEquals(n, m);
    }

    /**
     * Test for clear.
     */
    @Test
    public final void testClear() {
        NaturalNumber s = this.constructor(7);
        NaturalNumber r = this.constructor();
        s.clear();
        assertEquals(s, r);
    }

    /**
     * Test for transferFrom.
     */
    @Test
    public final void testTransferFrom() {
        NaturalNumber s = this.constructor(7);
        NaturalNumber m = this.constructor(7);
        NaturalNumber r = this.constructor();
        NaturalNumber n = this.constructor();
        r.transferFrom(s);
        assertEquals(s, n);
        assertEquals(r, m);
    }
}
