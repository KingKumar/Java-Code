import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.program.Program;
import components.sequence.Sequence;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * JUnit test fixture for {@code Program}'s generatedCode method.
 * 
 * @author Paolo Bucci
 * 
 */
public abstract class ProgramTest {

    /**
     * The names of a files containing (possibly invalid) BL programs.
     */
    private static final String FILE_NAME_1 = "data/TestProgram1.bl",
            FILE_NAME_2 = "data/TestProgram2.bl",
            FILE_NAME_3 = "data/TestProgram3.bl",
            FILE_NAME_4 = "data/TestProgram4.bl";

    /**
     * Invokes the {@code Program} constructor for the implementation under test
     * and returns the result.
     * 
     * @return the new program
     * @ensures <pre>
     * {@code constructorTest = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))}
     * </pre>
     */
    protected abstract Program constructorTest();

    /**
     * Invokes the {@code Program} constructor for the reference implementation
     * and returns the result.
     * 
     * @return the new program
     * @ensures <pre>
     * {@code constructorRef = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))}
     * </pre>
     */
    protected abstract Program constructorRef();

    /**
     * Test of generatedCode on valid input.
     */
    @Test
    public final void testGeneratedCodeValid() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        pRef.parse(file);
        file.close();
        Sequence<Integer> cpRef = pRef.generatedCode();
        Program pTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        pTest.parse(file);
        file.close();
        /*
         * The call
         */
        Sequence<Integer> cpTest = pTest.generatedCode();
        /*
         * Evaluation
         */
        assertEquals(cpRef, cpTest);
        assertEquals(pRef, pTest);
    }

    /**
     * Test of generatedCode on input with undefined instruction.
     */
    @Test(expected = RuntimeException.class)
    public final void testGeneratedCodeCallUndefined() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        pTest.parse(file);
        file.close();
        /*
         * The call--should result in an error being found
         */
        Sequence<Integer> cpTest = pTest.generatedCode();
    }

    /**
     * Test of generatedCode on input with direct recursion.
     */
    @Test(expected = RuntimeException.class)
    public final void testGeneratedCodeDirectRecursion() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_3);
        pTest.parse(file);
        file.close();
        /*
         * The call--should result in an error being found
         */
        Sequence<Integer> cpTest = pTest.generatedCode();
    }

    /**
     * Test of generatedCode on input with indirect recursion.
     */
    @Test(expected = RuntimeException.class)
    public final void testGeneratedCodeIndirectRecursion() {
        /*
         * Setup
         */
        Program pTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_4);
        pTest.parse(file);
        file.close();
        /*
         * The call--should result in an error being found
         */
        Sequence<Integer> cpTest = pTest.generatedCode();
    }

    // TODO - if needed, add more test cases with smaller valid inputs

}
