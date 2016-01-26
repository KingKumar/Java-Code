import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Sample JUnit test fixture for SequenceSmooth.
 * 
 * @author Ronit Kumar
 * 
 */
public final class SequenceSmoothTest {

    /**
     * Constructs and returns a sequence of the integers provided as arguments.
     * 
     * @param args
     *            0 or more integer arguments
     * @return the sequence of the given arguments
     * @ensures <pre>
     * {@code createFromArgs= [the sequence of integers in args]}
     * </pre>
     */
    private Sequence<Integer> createFromArgs(Integer... args) {
        Sequence<Integer> s = new Sequence1L<Integer>();
        for (Integer x : args) {
            s.add(s.length(), x);
        }
        return s;
    }

    /**
     * Test smooth with s1 = <2, 4, 6> and s2 = <-5, 12>.
     */
    @Test
    public void test1() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(2, 4, 6);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(2, 4, 6);
        Sequence<Integer> seq2 = this.createFromArgs(-5, 12);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(3, 5);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <7> and s2 = <13, 17, 11>.
     */
    @Test
    public void test2() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(7);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(7);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs();
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <4, 8> and s2 = <13, 17, 11>.
     */
    @Test
    public void test3() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(4, 8);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(4, 8);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(6);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <4, 5, 12, 20> and s2 = <13, 17, 11>.
     */
    @Test
    public void test4() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(4, 5, 12, 20);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(4, 5, 12, 20);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(4, 8, 16);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <4, 5, 12, 20, 30> and s2 = <13, 17, 11>.
     */
    @Test
    public void test5() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(4, 5, 12, 20, 30);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(4, 5, 12, 20, 30);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(4, 8, 16, 25);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <4, 5, 12, 20, 30, 14> and s2 = <13, 17, 11>.
     */
    @Test
    public void test6() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(4, 5, 12, 20, 30, 14);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(4, 5, 12, 20, 30,
                14);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(4, 8, 16, 25, 22);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <-6> and s2 = <13, 17, 11>.
     */
    @Test
    public void test7() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(-6);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(-6);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs();
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <-6, -20> and s2 = <13, 17, 11>.
     */
    @Test
    public void test8() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(-6, -20);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(-6, -20);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(-13);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <1, 3, 5, 7> and s2 = <13, 17, 11>.
     */
    @Test
    public void test9() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(1, 3, 5, 7);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(1, 3, 5, 7);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(2, 4, 6);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <1, 2, 5, 7, 11, 13> and s2 = <13, 17, 11>.
     */
    @Test
    public void test10() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(1, 2, 5, 7, 11, 13);
        Sequence<Integer> expectedSeq1 = this
                .createFromArgs(1, 2, 5, 7, 11, 13);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(1, 3, 6, 9, 12);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <-1, -2, -5, -7, -11, -13> and s2 = <13, 17, 11>.
     */
    @Test
    public void test11() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(-1, -2, -5, -7, -11, -13);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(-1, -2, -5, -7,
                -11, -13);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(-1, -3, -6, -9,
                -12);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }

    /**
     * Test smooth with s1 = <Integer.MAX_VALUE, Integer.MAX_VALUE> and s2 =
     * <13, 17, 11>.
     */
    @Test
    public void test12() {
        /*
         * Set up arguments and call method under test
         */
        Sequence<Integer> seq1 = this.createFromArgs(Integer.MAX_VALUE,
                Integer.MAX_VALUE);
        Sequence<Integer> expectedSeq1 = this.createFromArgs(Integer.MAX_VALUE,
                Integer.MAX_VALUE);
        Sequence<Integer> seq2 = this.createFromArgs(13, 17, 11);
        Sequence<Integer> expectedSeq2 = this.createFromArgs(Integer.MAX_VALUE);
        SequenceSmooth.smooth(seq1, seq2);
        /*
         * Check results (parameter modes and ensures clause)
         */
        assertEquals(expectedSeq1, seq1);
        assertEquals(expectedSeq2, seq2);
    }
}