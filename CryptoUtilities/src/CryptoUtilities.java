import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 * 
 * @author Ronit Kumar
 * 
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Pseudo-random number generator.
     */
    private static Random generator = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     * 
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires <pre>
     * {@code n > 0}
     * </pre>
     * @ensures <pre>
     * {@code randomNumber = [a random number uniformly distributed in [0, n]]}
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d], restore n, and return
             */
            int result = (int) ((d + 1) * generator.nextDouble());
            n.multiplyBy10(d);
            return new NaturalNumber2(result);
        }
        /*
         * Incoming n has more than one digit, so generate a random number
         * (NaturalNumber) uniformly distributed in [0, n], and another (int)
         * uniformly distributed in [0, 9] (i.e., a random digit)
         */
        NaturalNumber result = randomNumber(n);
        int lastDigit = (int) (base * generator.nextDouble());
        if (result.equals(n) && (lastDigit >= d + 1)) {
            /*
             * In this case, we need to try again because generated number would
             * be greater than n; the recursive call's argument is not "smaller"
             * than the incoming value of n, but this recursive call has no more
             * than a 90% chance of being made (and for large n, far less than
             * that), so the probability of termination is 1
             */
            n.multiplyBy10(d);
            return randomNumber(n);
        }
        /*
         * Put together full random number, restore n, and return
         */
        result.multiplyBy10(lastDigit);
        n.multiplyBy10(d);
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     * 
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures <pre>
     * {@code n = [greatest common divisor of #n and #m]}
     * </pre>
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {
        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */

        // Conditional Statement to check if value of m is zero
        if (!m.isZero()) {
            // Create new natural number k to hold value of n mod m
            NaturalNumber k = new NaturalNumber2(n.divide(m));
            // Recursively call reduce to GCD
            reduceToGCD(m, k);
            // transferFrom m in order to update n and clear m
            n.transferFrom(m);
        }
    }

    /**
     * Reports whether n is even.
     * 
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures <pre>
     * {@code isEven = (n mod 2 = 0)}
     * </pre>
     */
    public static boolean isEven(NaturalNumber n) {
        // Create mirror natural number to keep n the same value while computing
        NaturalNumber nTemp = new NaturalNumber2(n);
        // Conditional statement to assess if n mod 2 is zero
        if (nTemp.divide(new NaturalNumber2(2)).isZero()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates n to its p-th power modulo m.
     * 
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires <pre>
     * {@code m > 1}
     * </pre>
     * @ensures <pre>
     * {@code n = #n ^ (p) mod m}
     * </pre>
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p,
            NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";
        /*
         * Use the fast-powering algorithm as previously discussed in class,
         * with the additional feature that every multiplication is followed
         * immediately by "reducing the result modulo m"
         */

        // Declare new Natural Number that will have the value of the mod of the
        // base times the mod from the previous step
        NaturalNumber c = new NaturalNumber2(1);
        // Counter variable
        int e = 0;
        // While loop to multiply c by base and then moding it p number of times
        while (e < p.toInt()) {
            c.multiply(n);
            c = c.divide(m);
            e++;
        }
        n.copyFrom(c);
    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     * 
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires <pre>
     * {@code n > 2  and  1 < w < n - 1}
     * </pre>
     * @ensures <pre>
     * {@code isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)}
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w,
            NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();

        // Declaration of new natural numbers to make sure the value of the
        // parameters are not altered during the method computation
        NaturalNumber case1 = new NaturalNumber2(1);
        NaturalNumber case2 = new NaturalNumber2(1);
        NaturalNumber wclone = new NaturalNumber2(1);
        NaturalNumber wclone2 = new NaturalNumber2(1);

        // More variables to ensure the parameters aren't altered
        int temp = 0;
        int wValue = w.toInt();

        // Declaration of new natural numbers to make sure the value of the
        // parameters are not altered during the method computation
        wclone.copyFrom(w);
        wclone2.copyFrom(w);

        // First condition
        wclone.power(2);
        case1 = wclone.divide(n);
        n.decrement();
        temp = n.toInt();
        n.increment();

        // Second condition
        wclone2.power(temp);
        case2 = wclone2.divide(n);

        // Restores w
        w = new NaturalNumber2(wValue);

        // Conditional statement to see if w is a witness to compositeness
        if (case1.equals(new NaturalNumber2(1))
                || !case2.equals(new NaturalNumber2(1))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     * 
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires <pre>
     * {@code n > 1}
     * </pre>
     * @ensures <pre>
     * {@code isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]}
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        /*
         * 2 and 3 are primes; other evens are composite; rest of the code works
         * for odd n >= 5
         */
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            return true;
        }
        if (isEven(n)) {
            return false;
        }
        /*
         * Simply check whether 2 is a witness that n is composite (which works
         * surprisingly well :-)
         */
        return !isWitnessToCompositeness(new NaturalNumber2(2), n);
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     * 
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires <pre>
     * {@code n > 1}
     * </pre>
     * @ensures <pre>
     * {@code isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]}
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate several witness candidates --
         * say, 10 to 50 candidates -- guessing that n is prime only if none of
         * these candidates is a witness to n being composite (based on fact #3
         * as described in the project description); use the code for isPrime1
         * as a guide for how to do this, and pay attention to the requires
         * clause of isWitnessToCompositeness
         */

        // Declared counter variable
        int count = 0;
        // While loop to select 50 candidate witnesses and check if each s
        // witness to compositeness
        while (count < 50) {
            n.decrement();
            NaturalNumber random = new NaturalNumber2(randomNumber(n).toInt());
            n.increment();
            if (isWitnessToCompositeness(random, n)) {
                return false;
            }
            count++;
        }
        return true;
    }

    /**
     * Generates a likely prime number at least as large as some given number.
     * 
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires <pre>
     * {@code n > 1}
     * </pre>
     * @ensures <pre>
     * {@code n >= #n  and  [n is very likely a prime number]}
     * </pre>
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use isPrime2 to check numbers, starting at n and increasing through
         * the odd numbers only (why?), until n is likely prime
         */

        // Decrement n to create interval for random number to be generated on
        n.decrement();
        // Declare witness candidate
        NaturalNumber w = new NaturalNumber2(randomNumber(n).toInt());
        // Make n what it was
        n.increment();
        // While loop to generate next likely prime
        while (!isWitnessToCompositeness(w, n)) {
            if (isEven(n)) {
                n.increment();
            } else {
                n.increment();
                n.increment();
            }
        }
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */

        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println("  expected value = " + (double) testSamples
                / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */

        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */

        in.close();
        out.close();
    }
}