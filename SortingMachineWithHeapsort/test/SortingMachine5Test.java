import java.util.Comparator;

import components.sortingmachine.SortingMachine;

/**
 * 
 * @author Ronit Kumar
 * 
 * 
 *         Customized JUnit test fixture for {@code SortingMachine5}.
 */
public final class SortingMachine5Test extends SortingMachineTest {

    @Override
    protected SortingMachine<String> constructor(Comparator<String> order) {
        return new SortingMachine5<String>(order);
    }

}
