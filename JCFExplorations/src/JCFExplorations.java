import components.map.Map.Pair;
import components.naturalnumber.NaturalNumber;

/**
 * Simple class to experiment with the Java Collections Framework and how it
 * compares with the OSU CSE collection components.
 * 
 * @author Ronit Kumar
 * 
 */
public final class JCFExplorations {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private JCFExplorations() {
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name is in
     * {@code set} by the given {@code raisePercent}.
     * 
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates {@code map}
     * @requires <pre>
     * {@code [the salaries in map are positive]  and  raisePercent > 0}
     * </pre>
     * @ensures <pre>
     * {@code DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]}
     * </pre>
     */
    public static void giveRaise(components.map.Map<String, Integer> map,
            char initial, int raisePercent) {
        assert map != null : "Violation of: map is not null";
        assert raisePercent > 0 : "Violation of: raisePercent > 0";

        components.map.Map<String, Integer> map2 = map.newInstance();
        String person = String.valueOf(initial);
        while (map.size() > 0) {
            Pair<String, Integer> pair = map.removeAny();
            if (pair.key().startsWith(person)) {
                int value = pair.value()
                        + ((raisePercent * pair.value()) / 100);
                map2.add(pair.key(), value);
            } else {
                map2.add(pair.key(), pair.value());
            }
        }
        map.transferFrom(map2);
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name is in
     * {@code set} by the given {@code raisePercent}.
     * 
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates {@code map}
     * @requires <pre>
     * {@code [the salaries in map are positive]  and  raisePercent > 0}
     * </pre>
     * @ensures <pre>
     * {@code DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]}
     * </pre>
     */
    public static void giveRaise(java.util.Map<String, Integer> map,
            char initial, int raisePercent) {
        assert map != null : "Violation of: map is not null";
        assert raisePercent > 0 : "Violation of: raisePercent > 0";

        for (java.util.Map.Entry<String, Integer> poop : map.entrySet()) {
            if (poop.getKey().toString().charAt(0) == initial) {
                poop.setValue(poop.getValue()
                        + (poop.getValue() * raisePercent) / 100);
            }
        }
    }

    /**
     * Increments by 1 every element in the given {@code Set}.
     * 
     * @param set
     *            the set whose elements to increment
     * @updates {@code set}
     * @ensures <pre>
     * {@code DOMAIN(map) = DOMAIN(#map)  and
     * [set is the set of integers that are elements of #set incremented by 1]}
     * </pre>
     */
    public static void incrementAll(components.set.Set<NaturalNumber> set) {
        assert set != null : "Violation of: set is not null";

        while (set.size() > 0) {
            NaturalNumber setPiece = set.removeAny();
            setPiece.increment();
            set.add(setPiece);
        }
    }

    /**
     * Increments by 1 every element in the given {@code Set}.
     * 
     * @param set
     *            the set whose elements to increment
     * @updates {@code set}
     * @ensures <pre>
     * {@code DOMAIN(map) = DOMAIN(#map)  and
     * [set is the set of integers that are elements of #set incremented by 1]}
     * </pre>
     */
    public static void incrementAll(java.util.Set<NaturalNumber> set) {
        assert set != null : "Violation of: set is not null";

        for (NaturalNumber poop : set) {
            set.remove(poop);
            incrementAll(set);
            poop.increment();
            set.add(poop);
            // Iterator.remove
        }
    }
}
