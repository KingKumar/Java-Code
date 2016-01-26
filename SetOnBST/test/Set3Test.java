import components.set.Set;

/**
 * Customized JUnit test fixture for {@code Set3}.
 * 
 * @author Ronit Kumar
 * 
 */

public final class Set3Test extends SetTest {

    @Override
    protected Set<String> constructor() {
        return new Set3<String>();
    }

}
