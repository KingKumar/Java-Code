import components.set.Set;

/**
 * Customized JUnit test fixture for {@code Set2}.
 */
public class Set2Test extends SetTest {

    @Override
    protected final Set<String> constructor() {
        return new Set2<String>();
    }

}
