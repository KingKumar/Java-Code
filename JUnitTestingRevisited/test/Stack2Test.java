import components.stack.Stack;
import components.stack.Stack2;

/**
 * Customized JUnit test fixture for {@code Stack1L}.
 */
public class Stack2Test extends StackTest {

    @Override
    protected final Stack<String> constructor() {
        Stack<String> stack = new Stack2<String>();
        return stack;
    }

}