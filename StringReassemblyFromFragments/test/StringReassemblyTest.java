import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;

public class StringReassemblyTest {

    /**
     * Routine test of combination.
     */
    @Test
    public void testCombination() {
        String str1 = "ronitkumarhasswag";
        String str2 = "swagsoreal";
        int overlap = 4;
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals("ronitkumarhasswagsoreal", result);
    }

    /**
     * Routine test of combination.
     */
    @Test
    public void testCombination2() {
        String str1 = "removethis";
        String str2 = "thisshouldberemoved";
        int overlap = 4;
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals("removethisshouldberemoved", result);
    }

    /**
     * Routine test of combination.
     */
    @Test
    public void testCombination3() {
        String str1 = "letstrybothstringsbeingthesame";
        String str2 = "letstrybothstringsbeingthesame";
        int overlap = 30;
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals("letstrybothstringsbeingthesame", result);
    }

    /**
     * Routine test of combination.
     */
    @Test
    public void testCombination4() {
        String str1 = "letstryoneletter";
        String str2 = "rfromthequeen";
        int overlap = 1;
        String result = StringReassembly.combination(str1, str2, overlap);
        assertEquals("letstryoneletterfromthequeen", result);
    }

    /**
     * Routine test of addToSetAvoidingSubstrings.
     */
    @Test
    public void testaddToSetAvoidingSubstrings() {
        Set<String> strset = new Set1L<String>();
        strset.add("Yo Dawg!");
        strset.add("Vat's Upppp Mannn?");
        strset.add("Nm jc.");
        Set<String> strset2 = new Set1L<String>();
        strset2.add("Yo Dawg!");
        strset2.add("Vat's Upppp Mannn?");
        strset2.add("Nm jc.");
        String str = "Mannn";
        StringReassembly.addToSetAvoidingSubstrings(strset, str);
        assertEquals(strset, strset2);
    }

    /**
     * Routine test of addToSetAvoidingSubstrings.
     */
    @Test
    public void testaddToSetAvoidingSubstrings2() {
        Set<String> strset = new Set1L<String>();
        strset.add("Yo Dawg!");
        strset.add("Vat's Upppp Mannn?");
        strset.add("Nm jc.");
        Set<String> strset2 = new Set1L<String>();
        strset2.add("Yo Dawg!");
        strset2.add("Vat's Upppp Mannn?");
        strset2.add("Nm jc.");
        String str = "Kumar";
        StringReassembly.addToSetAvoidingSubstrings(strset, str);
        assertEquals(strset, strset2);
    }

}
