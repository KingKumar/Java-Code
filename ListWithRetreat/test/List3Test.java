import components.list.List;
import components.list.List1L;

/**
 * @author Ronit Kumar
 * 
 *         Customized JUnit test fixture for {@code List3}.
 */
public class List3Test extends ListTest {

    @Override
    protected final List<String> constructorTest() {
        return new List3<String>();
    }

    @Override
    protected final List<String> constructorRef() {
        return new List1L<String>();
    }

}
