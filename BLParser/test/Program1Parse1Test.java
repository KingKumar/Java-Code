import components.program.Program;
import components.program.Program1;

/**
 * Customized JUnit test fixture for {@code Program1Parse1}.
 */
public class Program1Parse1Test extends ProgramTest {

    @Override
    protected final Program constructorTest() {
        return new Program1Parse1();
    }

    @Override
    protected final Program constructorRef() {
        return new Program1();
    }

}
