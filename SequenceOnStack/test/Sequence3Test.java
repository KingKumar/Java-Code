import components.sequence.Sequence;

/**
 * Customized JUnit test fixture for {@code Sequence3}.
 */
public class Sequence3Test extends SequenceTest {

    @Override
    protected final Sequence<String> constructor() {
        return new Sequence3<String>();
    }

}
