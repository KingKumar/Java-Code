import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * Example of JUnit test fixture for {@code Stack<String>}. Note: these are just
 * some sample test cases. The fixture is neither complete nor systematically
 * designed.
 * 
 * @author Paolo Bucci
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
        for (int count = 0; count < args.length; count++) {
            stack.push(args[count]);
        }
        stack.flip();
        return stack;
    }

    /*
     * Test StackKernel constructor(s) and methods. Note: these are just some
     * sample test cases, not complete nor systematic.
     */

    @Test
    public final void testDefaultConstructor() {
        Stack<String> s = this.constructor();
        assertEquals(s.length(), 0);
        assertEquals("<>", s.toString());
    }

    @Test
    public final void testPushNonEmpty() {
        Stack<String> s = this.createFromArgs("red", "blue");
        s.push("green");
        assertEquals("<green,red,blue>", s.toString());
    }

    @Test
    public final void testPopLeavingEmpty() {
        Stack<String> s = this.createFromArgs("red");
        String x = s.pop();
        assertEquals("<>", s.toString());
        assertEquals("red", x);
    }

    @Test
    public final void testTop() {
        Stack<String> s = this.createFromArgs("red");
        String x = s.top();
        assertEquals("<red>", s.toString());
        assertEquals("red", x);
    }

    @Test
    public final void testLengthNonEmpty() {
        Stack<String> s = this.createFromArgs("red", "blue");
        int i = s.length();
        assertEquals("<red,blue>", s.toString());
        assertEquals(2, i);
    }

    /*
     * Test StackCommon methods. Note: these are just some sample test cases,
     * not complete nor systematic.
     */

    @Test
    public final void testEqualsNonEmptyEmpty() {
        Stack<String> s1 = this.createFromArgs("red", "blue");
        Stack<String> s2 = this.createFromArgs();
        boolean eq = s1.equals(s2);
        assertEquals(false, eq);
        assertEquals("<red,blue>", s1.toString());
        assertEquals("<>", s2.toString());
    }

    @Test
    public final void testToStringEmpty() {
        Stack<String> s = this.createFromArgs();
        assertEquals("<>", s.toString());
    }

    @Test
    public final void testHashCodeSame() {
        Stack<String> s1 = this.createFromArgs("red", "blue");
        Stack<String> s2 = this.createFromArgs("red", "blue");
        int hc1 = s1.hashCode();
        int hc2 = s2.hashCode();
        assertEquals(hc1, hc2);
    }

    /*
     * Test Standard methods. Note: these are just some sample test cases, not
     * complete nor systematic.
     */

    @Test
    public final void testNewInstanceEmpty() {
        Stack<String> s1 = this.createFromArgs();
        Stack<String> s2 = s1.newInstance();
        assertEquals("<>", s1.toString());
        assertEquals("<>", s2.toString());
        assertEquals(s1.getClass(), s2.getClass());
    }

    @Test
    public final void testTransferFromNonEmpty() {
        Stack<String> s1 = this.createFromArgs("red", "blue");
        Stack<String> s2 = this.createFromArgs();
        s2.transferFrom(s1);
        assertEquals("<>", s1.toString());
        assertEquals("<red,blue>", s2.toString());
    }

    @Test
    public final void testClearNonEmpty() {
        Stack<String> s = this.createFromArgs("red", "blue");
        s.clear();
        assertEquals("<>", s.toString());
    }

    /*
     * Test StackSecondary methods. Note: these are just some sample test cases,
     * not complete nor systematic.
     */

    @Test
    public final void testFlipThree() {
        Stack<String> s = this.createFromArgs("red", "green", "blue");
        s.flip();
        assertEquals("<blue,green,red>", s.toString());
    }

}