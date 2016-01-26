package components.naturalnumber;

/**
 * Layered implementations of {@code equals}, {@code hashCode}, and
 * {@code toString} for {@code NaturalNumber}.
 * 
 * <p>
 * Let n = log {@code this}, i.e., essentially the number of digits in
 * {@code this}. Execution-time performance of all methods implemented in this
 * class is O(n).
 */
public abstract class NaturalNumberCommon implements NaturalNumber {

    /**
     * Reports whether {@code this} equals {@code n}.
     * 
     * @param n
     *            the other value
     * @return true iff {@code this} equals {@code n}
     * @ensures <pre>
     * {@code equals = (this = n)}
     * </pre>
     */
    private boolean equalsForNaturalNumber(NaturalNumber n) {
        /**
         * @decreases {@code this}
         */
        if (this.isZero()) {
            return n.isZero();
        } else {
            int lowDigit = this.divideBy10();
            int nLowDigit = n.divideBy10();
            boolean result = (lowDigit == nLowDigit)
                    && this.equalsForNaturalNumber(n);
            this.multiplyBy10(lowDigit);
            n.multiplyBy10(nLowDigit);
            return result;
        }
    }

    /**
     * Reports conventional decimal string representation of {@code this}, when
     * it is positive; else empty string when it is zero.
     * 
     * @return {@code String} representation of {@code this}
     * @ensures <pre>
     * {@code positiveToString =
     *  [conventional decimal string representation of this if it is positive,
     *   or empty string if it is zero]}
     * </pre>
     */
    private StringBuilder positiveToString() {
        StringBuilder result;
        if (this.isZero()) {
            result = new StringBuilder();
        } else {
            int lowDigit = this.divideBy10();
            result = this.positiveToString();
            result.append(lowDigit);
            this.multiplyBy10(lowDigit);
        }
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NaturalNumber)) {
            return false;
        }
        NaturalNumber n = (NaturalNumber) obj;
        return this.equalsForNaturalNumber(n);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int hashCode() {
        final int a = 37;
        final int b = 17;
        if (this.isZero()) {
            return 0;
        }
        int lowDigit = this.divideBy10();
        int result = a * this.hashCode() + b * lowDigit;
        this.multiplyBy10(lowDigit);
        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        StringBuilder result;
        if (this.isZero()) {
            result = new StringBuilder("0");
        } else {
            result = this.positiveToString();
        }
        return result.toString();
    }

}
