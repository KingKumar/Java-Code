import components.sequence.Sequence;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 * 
 * @author Ronit Kumar
 * 
 */
public final class SequenceSmooth {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmooth() {
    }

    /**
     * Smooths a given {@code Sequence<Integer>}.
     * 
     * @param s1
     *            the sequence to smooth
     * @param s2
     *            the resulting sequence
     * @replaces s2
     * @requires <pre>
     * {@code |s1| >= 1 }
     * </pre>
     * @ensures <pre>
     * {@code |s2| = |s1| - 1  and 
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))}
     * </pre>
     */
    public static void smooth(Sequence<Integer> s1, Sequence<Integer> s2) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s2 is not null";
        assert s1.length() >= 1 : "|s1| >= 1";

        int count = 0, var1 = 0, var2 = 0, comp1 = 0;
        s2.clear();
        while (count < s1.length() - 1) {
            var1 = s1.entryAt(count);
            var2 = s1.entryAt(count + 1);
            comp1 = (var1 + var2) / 2;
            s2.add(count, comp1);
            count++;
        }

        s2.clear();
        if (s1.length() > 1) {
            int var1 = s1.remove(0);
            int var2 = s1.entryAt(0);
            smooth(s1, s2);
            s2.add(0, (var1 + var2) / 2);
            s1.add(0, var1);
        }
    }
}