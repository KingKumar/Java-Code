import components.map.Map;

/**
 * Customized JUnit test fixture for {@code Map2}.
 */
public class Map2Test extends MapTest {

    @Override
    protected final Map<String, String> constructor() {
        return new Map2<String, String>();
    }

}
