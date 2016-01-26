import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test fixture for HashTest.mod() that computes the modulo function using
 * "clock arithmetic". For more information see:
 * "http://cse.osu.edu/software/2221/web-sw1/extras/slides/14a.Clock-Arithmetic.pdf"
 */
public class ModTest {

    @Test
    public void testPositiveMod1() {
        int x = HashingExploration.mod(7, 1);
        assertEquals(0, x);
    }

    @Test
    public void testNegativeMod1() {
        int x = HashingExploration.mod(-11, 1);
        assertEquals(0, x);
    }

    @Test
    public void testPositiveMod3Rem0() {
        int x = HashingExploration.mod(243, 3);
        assertEquals(0, x);
    }

    @Test
    public void testPositiveMod3Rem1() {
        int x = HashingExploration.mod(730, 3);
        assertEquals(1, x);
    }

    @Test
    public void testPositiveMod3Rem2() {
        int x = HashingExploration.mod(2189, 3);
        assertEquals(2, x);
    }

    @Test
    public void testNegativeMod7Rem0() {
        int x = HashingExploration.mod(-49, 7);
        assertEquals(0, x);
    }

    @Test
    public void testNegativeMod7Rem1() {
        int x = HashingExploration.mod(-342, 7);
        assertEquals(1, x);
    }

    @Test
    public void testNegativeMod7Rem2() {
        int x = HashingExploration.mod(-2399, 7);
        assertEquals(2, x);
    }

    @Test
    public void testNegativeMod7Rem3() {
        int x = HashingExploration.mod(-16790, 7);
        assertEquals(3, x);
    }

    @Test
    public void testNegativeMod7Rem4() {
        int x = HashingExploration.mod(-117547, 7);
        assertEquals(4, x);
    }

    @Test
    public void testNegativeMod7Rem5() {
        int x = HashingExploration.mod(-822852, 7);
        assertEquals(5, x);
    }

    @Test
    public void testNegativeMod7Rem6() {
        int x = HashingExploration.mod(-5759993, 7);
        assertEquals(6, x);
    }

    @Test
    public void testPositiveModBigger() {
        int x = HashingExploration.mod(23, 101);
        assertEquals(23, x);
    }

    @Test
    public void testNegativeModBigger() {
        int x = HashingExploration.mod(-37, 101);
        assertEquals(64, x);
    }

    @Test
    public void testMaxModMax() {
        int x = HashingExploration.mod(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(0, x);
    }

    @Test
    public void testMinModMax() {
        int x = HashingExploration.mod(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE - 1, x);
    }

    @Test
    public void testMinPlus1ModMax() {
        int x = HashingExploration
                .mod(Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
        assertEquals(0, x);
    }

}
