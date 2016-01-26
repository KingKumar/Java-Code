import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Program to receive a text file of words and to output an html page with a tag
 * cloud of words.
 * 
 * @author Ronit Kumar
 * 
 */
public final class TagCloud {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloud() {
    }

    /**
     * The maximum font size.
     */
    private static final int MAX_FONT_SIZE = 48;
    /**
     * The maximum font size.
     */
    private static final int DEFAULT_FONT_SIZE = 19;
    /**
     * The maximum value.
     */
    private static int maxValue;
    /**
     * The minimum value.
     */
    private static int minValue;

    /**
     * Compare {@code Map}s in alphabetical order.
     */
    private static class Order implements
            Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    }

    /**
     * Compare {@code Map}s in numerical order.
     */
    private static class Order2 implements
            Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    }

    /**
     * Generates the pairs of terms and definitions (Strings) in the given
     * {@code file} into the given {@code Map}. Also generates a Queue of all
     * the terms in no particular order into the given {@code Queue}.
     * 
     * @param file
     *            the given {@code file}
     * @param strMap
     *            the {@code Map} to be replaced
     * @param separators
     *            the {@code Set} that contains the separators
     * @replaces {@code strSet}
     * @replaces {@code q}
     * @ensures <pre>
     * {@code strMap = elements(file)}
     * </pre>
     */
    private static void generateTerms(BufferedReader file,
            Map<String, Integer> strMap, Set<Character> separators) {

        String term = "";
        String nexTerm = "";
        int index = 0;
        try {
            term = file.readLine();
            while (term != null) {
                index = 0;
                while (index < term.length()) {
                    nexTerm = nextWordOrSeparator(term, index, separators);
                    index += nexTerm.length();
                    if (!separators.contains(nexTerm.charAt(0))) {
                        if (strMap.containsKey(nexTerm)) {
                            int tempValue = strMap.remove(nexTerm);
                            strMap.put(nexTerm, tempValue + 1);
                        } else {
                            strMap.put(nexTerm, 1);
                        }
                    }
                }
                term = file.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }
    }

    /**
     * Outputs the HTML file that has code for a generated Tag Cloud.
     * 
     * @param filename
     *            the file name of the input folder
     * @param m
     *            the Map of terms and definitions
     * @param weight
     *            the Map of terms and assigned weights
     * @param foldername
     *            the name of the folder the html files are output to
     * @param keySet
     *            the Sequence of key values
     * @param numterms
     *            the number of words in the generated Tagcloud
     * @throws IOException
     *             throws IOException
     * @updates {@code out.content}
     * @ensures <pre>
     * {@code out.content = #out.content * [the HTML tags]}
     * </pre>
     */
    private static void outputPage(Map<String, Integer> m, String foldername,
            String filename, Map<String, Integer> weight, List<String> keySet,
            int numterms) throws IOException {
        PrintWriter foo = new PrintWriter(new BufferedWriter(new FileWriter(
                foldername + "/tagcloud.html")));
        foo.println("<?xml version='1.0' encoding='ISO-8859-1' ?>");
        foo.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'"
                + " 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
        foo.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
        foo.println("<head>");
        foo.println("<meta http-equiv='Content-Type'"
                + " content='text/html; charset=ISO-8859-1' />");
        // Prints the title
        foo.println("<title>Top " + numterms + " words in " + filename
                + "</title>");
        // Prints out the css referencing
        foo.println("<link href=\"http://www.cse.ohio-state.edu/software/2231"
                + "/web-sw2/assignments/projects/tag-cloud-generator/data/tagc"
                + "loud.css\" rel=\"stylesheet\" type=\"text/css\">");
        // Prints out more opening tags
        foo.println("</head>");
        foo.println("<body>");
        foo.println("<h2>Top " + numterms + " words in " + filename + "</h2>");
        foo.println("<hr>");
        foo.println("<div class=\"cdiv\">");
        foo.println("<p class=\"cbox\">");
        for (int count = 0; count < keySet.size(); count++) {
            foo.println("<span style=\"cursor:default\" class=\"f"
                    + weight.get(keySet.get(count)) + "\" title=\"count: "
                    + m.get(keySet.get(count)) + "\">" + keySet.get(count)
                    + "</span>");
        }
        foo.println("</p>");
        foo.println("</div>");
        foo.println("</body>");
        foo.println("</html>");
        foo.close();
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     * 
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires <pre>
     * {@code 0 <= position < |text|}
     * </pre>
     * @ensures <pre>
     * {@code nextWordOrSeparator =
     *   text[ position .. position + |nextWordOrSeparator| )  and
     * if elements(text[ position .. position + 1 )) intersection separators = {}
     * then
     *   elements(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    elements(text[ position .. position + |nextWordOrSeparator| + 1 ))
     *      intersection separators /= {})
     * else
     *   elements(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    elements(text[ position .. position + |nextWordOrSeparator| + 1 ))
     *      is not subset of separators)}
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int count = 0;
        char returnedPiece = 'k';
        String returned = "";
        if (separators.contains(text.charAt(position))) {
            while (count < text.substring(position, text.length()).length()) {
                returnedPiece = text.charAt(position + count);
                if (separators.contains(text.charAt(position + count))) {
                    returned = returned + returnedPiece;
                    count++;
                } else {
                    count = text.substring(position, text.length()).length();
                }
            }
            count = 0;
        } else {
            while (count < text.substring(position, text.length()).length()) {
                returnedPiece = text.charAt(position + count);
                if (!separators.contains(text.charAt(position + count))) {
                    returned = returned + returnedPiece;
                    count++;
                } else {
                    count = text.substring(position, text.length()).length();
                }
            }
            count = 0;
        }
        return returned;
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) {
        /*
         * Open input stream
         */
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(
                System.in));
        /*
         * Ask for input file
         */
        System.out.println("Insert name of input file: ");
        String fil = null;
        try {
            fil = keyIn.readLine();
        } catch (IOException e1) {
            System.err.println("Error reading from keyboard");
        }
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader(fil));
        } catch (FileNotFoundException e1) {
            System.err
                    .println("Error producing reader of input file (file not found)");
        }
        /*
         * Ask for output folder name
         */
        System.out.println("Insert name of output folder: ");
        String folder = null;
        try {
            folder = keyIn.readLine();
        } catch (IOException e1) {
            System.err.println("Error reading from keyboard");
        }
        /*
         * Ask for number of words to be included in the generated tag cloud
         */
        System.out
                .println("Insert number of words in the generated tag cloud: ");
        int numberWords = 0;
        try {
            numberWords = Integer.parseInt(keyIn.readLine());
        } catch (IOException e1) {
            System.err.println("Error reading from keyboard");
        }
        /*
         * Create a Set of separators
         */
        Set<Character> separatorSet = new HashSet<Character>();
        String separatorStr = " _/@#$%^&*()+=`~><;:,-.!?[]'";
        separatorSet.add('\t');
        separatorSet.add('\n');
        separatorSet.add('\r');
        separatorSet.add('\"');
        int count = 0;
        while (count < separatorStr.length()) {
            separatorSet.add(separatorStr.charAt(count));
            count++;
        }
        /*
         * Creates a Map of associated terms and word counts.
         */
        Map<String, Integer> m = new HashMap<String, Integer>();
        generateTerms(file, m, separatorSet);
        /*
         * Declare comparators to sort Map entries
         */
        Comparator<Map.Entry<String, Integer>> alphabet = new Order();
        Comparator<Map.Entry<String, Integer>> numerical = new Order2();
        /*
         * Sort N Map entries by decreasing value (numerically), where N is the
         * number of words in tag cloud provided by user, into a List containing
         * N entries
         */
        List<Map.Entry<String, Integer>> si = new LinkedList<Map.Entry<String, Integer>>(
                m.entrySet());
        List<Map.Entry<String, Integer>> is = new LinkedList<Map.Entry<String, Integer>>();
        Collections.sort(si, numerical);
        is = si.subList(0, numberWords);
        /*
         * Sort top Map entries in alphabetical order
         */
        Collections.sort(is, alphabet);
        /*
         * Transfer Pairs in List to a map, and create a Sequence of key values
         * to reference for data manipulation of map
         */
        List<String> keySet = new LinkedList<String>();
        count = 0;
        while (count < numberWords && is.size() > 0) {
            if (count == 0) {
                Entry<String, Integer> temp1 = is.remove(0);
                maxValue = temp1.getValue();
                keySet.add(count, temp1.getKey());
                m.put(temp1.getKey(), temp1.getValue());
                count++;
            } else if (count == numberWords) {
                Entry<String, Integer> temp1 = is.remove(0);
                minValue = temp1.getValue();
                keySet.add(count, temp1.getKey());
                m.put(temp1.getKey(), temp1.getValue());
                count++;
            } else {
                Entry<String, Integer> temp1 = is.remove(0);
                keySet.add(count, temp1.getKey());
                m.put(temp1.getKey(), temp1.getValue());
                count++;
            }
        }
        /*
         * Create a second map of weight values for terms in map
         */
        count = 0;
        Map<String, Integer> weight = new HashMap<String, Integer>();
        for (Entry<String, Integer> poop : m.entrySet()) {
            if (minValue == maxValue) {
                count = DEFAULT_FONT_SIZE;
            } else {
                count = (MAX_FONT_SIZE * (poop.getValue() - minValue))
                        / (maxValue - minValue);
            }
            if (count > MAX_FONT_SIZE) {
                count = MAX_FONT_SIZE;
            }
            weight.put(poop.getKey(), count);
        }
        /*
         * Output an html page with a Tag Cloud
         */
        try {
            outputPage(m, folder, fil, weight, keySet, numberWords);
        } catch (IOException e1) {
            System.err.println("Error outputting html page");
        }
        /*
         * Close input and output streams
         */
        try {
            keyIn.close();
        } catch (IOException e) {
            System.err.println("Error closing keyboard input stream");
        }
        try {
            file.close();
        } catch (IOException e) {
            System.err.println("Error closing file output stream");
        }
    }
}
