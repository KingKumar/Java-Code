import java.util.Comparator;

import components.sortingmachine.SortingMachine;

/**
 * Customized JUnit test fixture for {@code SortingMachine4}.
 */
public final class SortingMachine4Test extends SortingMachineTest {

    @Override
    protected SortingMachine<String> constructor(Comparator<String> order) {
        return new SortingMachine4<String>(order);
    }

}
