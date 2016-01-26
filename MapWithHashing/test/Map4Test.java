import components.map.Map;

/**
 * Customized JUnit test fixture for {@code Map4} using default constructor.
 * 
 * @author Ronit Kumar
 * 
 */
public class Map4Test extends MapTest {

    @Override
    protected final Map<String, String> constructor() {
        return new Map4<String, String>();
    }

}
