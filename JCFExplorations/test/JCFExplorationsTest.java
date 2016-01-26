import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.utilities.FormatChecker;

/**
 * JUnit test fixture for {@code JCFExplorations}'s static methods.
 * 
 * @author Ronit Kumar
 * 
 */
public class JCFExplorationsTest {

    /**
     * Invokes the {@code components.map.Map4} constructor and returns the
     * result.
     * 
     * @return the new map
     * @ensures <pre>
     * {@code mapConstructorOSU = {}}
     * </pre>
     */
    private components.map.Map<String, Integer> mapConstructorOSU() {
        return new components.map.Map4<String, Integer>();
    }

    /**
     * 
     * Creates and returns a {@code components.map.Map<String, Integer>} with
     * the given entries.
     * 
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * {@code [args.length is even]  and  [every other entry in args is a number]}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgs = [pairs in args]}
     * </pre>
     */
    private components.map.Map<String, Integer> createFromArgsOSU(
            String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        components.map.Map<String, Integer> map = this.mapConstructorOSU();
        for (int i = 0; i < args.length; i += 2) {
            assert FormatChecker.canParseInt(args[i + 1]) : ""
                    + "Violation of: every other entry in args is a number";
            map.add(args[i], Integer.parseInt(args[i + 1]));
        }
        return map;
    }

    /**
     * Invokes the {@code java.util.HashMap} constructor and returns the result.
     * 
     * @return the new map
     * @ensures <pre>
     * {@code mapConstructorJCF = {}}
     * </pre>
     */
    private java.util.Map<String, Integer> mapConstructorJCF() {
        return new java.util.HashMap<String, Integer>();
    }

    /**
     * 
     * Creates and returns a {@code java.util.Map<String, Integer>} with the
     * given entries.
     * 
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * {@code [args.length is even]  and  [every other entry in args is a number]}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgs = [pairs in args]}
     * </pre>
     */
    private java.util.Map<String, Integer> createFromArgsJCF(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        java.util.Map<String, Integer> map = this.mapConstructorJCF();
        for (int i = 0; i < args.length; i += 2) {
            assert FormatChecker.canParseInt(args[i + 1]) : ""
                    + "Violation of: every other entry in args is a number";
            map.put(args[i], Integer.parseInt(args[i + 1]));
        }
        return map;
    }

    @Test
    public final void testRaiseSalaryOSUExample() {
        components.map.Map<String, Integer> m1 = this.createFromArgsOSU(
                "stark", "30000", "lannister", "100000", "snow", "40000");
        components.map.Map<String, Integer> m2 = this.createFromArgsOSU(
                "lannister", "100000", "stark", "33000", "snow", "44000");
        JCFExplorations.giveRaise(m1, 's', 10);
        assertEquals(m2, m1);
    }

    @Test
    public final void testRaiseSalaryJCFExample() {
        java.util.Map<String, Integer> m1 = this.createFromArgsJCF("stark",
                "30000", "lannister", "100000", "snow", "40000");
        java.util.Map<String, Integer> m2 = this.createFromArgsJCF("lannister",
                "100000", "stark", "33000", "snow", "44000");
        JCFExplorations.giveRaise(m1, 's', 10);
        assertEquals(m2, m1);
    }

    // TODO - add other test cases for both versions of raiseSalary
    // TODO - add test cases for both versions of incrementAll

}
