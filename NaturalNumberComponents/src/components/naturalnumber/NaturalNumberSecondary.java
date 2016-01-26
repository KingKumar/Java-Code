package components.naturalnumber;

/**
 * Layered implementations of secondary methods for {@code NaturalNumber}.
 */
public abstract class NaturalNumberSecondary extends NaturalNumberCommon {

    /**
     * Constant 10 (so it's not a "magic number" in the code).
     */
    private static final int TEN = 10;

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */int compareTo(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        assert n != this : "Violation of: n is not this";

        /*
         * 2221/2231 assignment code deleted.
         */

        // This line added just to make the component compilable.
        return 0;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void setFromInt(int i) {
        assert i >= 0 : "Violation of: i >= 0";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */boolean canConvertToInt() {

        /*
         * 2221/2231 assignment code deleted.
         */

        // This line added just to make the component compilable.
        return false;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */int toInt() {
        assert this.canConvertToInt() : "Violation of: this <= Integer.MAX_VALUE";

        /*
         * 2221/2231 assignment code deleted.
         */

        // This line added just to make the component compilable.
        return 0;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */boolean canSetFromString(String s) {
        assert s != null : "Violation of: s is not null";

        /*
         * 2221/2231 assignment code deleted.
         */

        // This line added just to make the component compilable.
        return false;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void setFromString(String s) {
        assert s != null : "Violation of: s is not null";
        assert this.canSetFromString(s) : ""
                + "Violation of: s = [result of toString for some NaturalNumber]";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void copyFrom(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        assert n != this : "Violation of: n is not this";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void increment() {

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void decrement() {
        assert !this.isZero() : "Violation of: this > 0";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void add(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        assert n != this : "Violation of: n is not this";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void subtract(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        assert n != this : "Violation of: n is not this";
        assert this.compareTo(n) >= 0 : "Violation of: this >= n";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void multiply(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        assert n != this : "Violation of: n is not this";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */NaturalNumber divide(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        assert n != this : "Violation of: n is not this";
        assert !n.isZero() : "Violation of: n > 0";

        /*
         * 2221/2231 assignment code deleted.
         */

        // This line added just to make the component compilable.
        return null;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void power(int p) {
        assert p >= 0 : "Violation of: p >= 0";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public/* final except for lab */void root(int r) {
        assert r >= 2 : "Violation of: r >= 2";

        /*
         * 2221/2231 assignment code deleted.
         */
    }

}
