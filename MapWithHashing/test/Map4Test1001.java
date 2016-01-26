import components.map.Map;

/**
 * Customized JUnit test fixture for {@code Map4} using non-default constructor
 * and hash table size 1001.
 * 
 * @author Ronit Kumar
 * 
 */
public class Map4Test1001 extends MapTest {

    /**
     * Size of hash table to be used in tests.
     */
    private static final int TEST_HASH_TABLE_SIZE = 1001;

    @Override
    protected final Map<String, String> constructor() {
        return new Map4<String, String>(TEST_HASH_TABLE_SIZE);
    }

}
