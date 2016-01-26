import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 * 
 * @author Ronit Kumar
 * 
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor and returns the result.
     * 
     * @return the new stack
     * @ensures <pre>
     * {@code constructor = <>}
     * </pre>
     */
    protected abstract Stack<String> constructor();

    /**
     * 
     * Creates and returns a {@code Stack<String>} with the given entries.
     * 
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures <pre>
     * {@code createFromArgs = [entries in args]}
     * </pre>
     */
    private Stack<String> createFromArgs(String... args) {
        Stack<String> stack = this.constructor();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    // TODO - add test cases for constructor, push, pop, top, and length

}
