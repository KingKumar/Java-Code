import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method.
 * 
 * @author Ronit Kumar
 * 
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    // Method to compute n to the p power
    /**
     * Returns {@code n} to the power {@code p}.
     * 
     * @param n
     *            the number to which we want to apply the power
     * @param p
     *            the power
     * @return the number to the power
     * @requires <pre>
     * {@code Integer.MIN_VALUE <= n ^ (p) <= Integer.MAX_VALUE and p >= 0}
     * </pre>
     * @ensures <pre>
     * {@code power = n ^ (p)}
     * </pre>
     */
    private static double power(double n, double p) {
        double result = 1;
        result = Math.pow(n, p);
        return result;
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     * 
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @return
     * @updates {@code n}
     * @requires <pre>
     * {@code r >= 2}
     * </pre>
     * @ensures <pre>
     * {@code n ^ (r) <= #n < (n + 1) ^ (r)}
     * </pre>
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is  not null";
        assert r >= 2 : "Violation of: r >= 2";

        // Set the lower limit and higher limit of the possible guesses the r'th
        // root of n can be
        double low = 0;
        double high = n.toInt();

        // Assigns value of 1/r to variable double power
        double power = 1.0 / r;

        // Computes the value of n^(1/r)
        double value = power(high, power);

        // Computes guess for value found by interval halving method
        double guess = (1.0) * (low + high) / 2;

        // While loop to find value through use of interval halving method
        while ((int) guess != (int) value) {

            // Computes guess for value
            guess = (1.0) * (low + high) / 2;

            // Conditional Statements to adjust lower and upper bound
            if (guess < value) {
                low = guess;
            } else {
                high = guess;
            }
        }

        // Creates an object natural number for parameter natural number n to
        // use copyFrom method
        NaturalNumber k = new NaturalNumber2((int) guess);

        // Updates n with copyFrom method
        n.copyFrom(k);
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final int[] numbers = { 0, 1, 13, 1024, 189943527, 0, 1, 13, 4096,
                189943527, 0, 1, 13, 1024, 189943527, 82, 82, 82, 82, 82, 9,
                27, 81, 243, 143489073 };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15 };
        final int[] results = { 0, 1, 3, 32, 13782, 0, 1, 2, 16, 574, 0, 1, 1,
                1, 3, 9, 4, 3, 2, 1, 3, 3, 3, 3, 3 };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}
