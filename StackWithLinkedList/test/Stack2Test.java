import components.stack.Stack;

/**
 * Customized JUnit test fixture for {@code Stack2}.
 */
public class Stack2Test extends StackTest {

    @Override
    protected final Stack<String> constructor() {
        return new Stack2<String>();
    }

}
