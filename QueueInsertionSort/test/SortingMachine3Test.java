import java.util.Comparator;

import components.sortingmachine.SortingMachine;

/**
 * Customized JUnit test fixture for {@code SortingMachine3}.
 */
public final class SortingMachine3Test extends SortingMachineTest {

    @Override
    protected SortingMachine<String> constructor(Comparator<String> order) {
        return new SortingMachine3<String>(order);
    }

}
