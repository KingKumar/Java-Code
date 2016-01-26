import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to receive a text file of words and to output an html page with a
 * table of words and word counts.
 * 
 * @author Ronit Kumar
 * 
 */
public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * Generates the pairs of words (Strings) and word counts (Integers) in the
     * given {@code file} into the given {@code Map}. Also generates a Set of
     * all the words in no particular order into the given {@code Set}.
     * 
     * @param file
     *            the given {@code file}
     * @param strMap
     *            the {@code Map} to be replaced
     * @param q
     *            the {@code Sequence} to be replaced
     * @replaces {@code q}
     * @ensures <pre>
     * {@code strMap = elements(file)}
     * </pre>
     */
    private static void generateTerms(SimpleReader file,
            Map<String, Integer> strMap, Sequence<String> q) {

        String line = "";
        int wordcount = 0;
        String word = "";
        int qpos = 0;

        final String separatorStr = " \t,.'-`~: ";
        Set<Character> separatorSet = new Set1L<Character>();
        generateElements(separatorStr, separatorSet);

        while (!file.atEOS()) {
            wordcount = 0;
            line = file.nextLine();
            while (!line.isEmpty()) {
                word = nextWordOrSeparator(line, 0, separatorSet);
                line = line.substring(word.length(), line.length());
                if (!separatorSet.contains(word.charAt(0))) {
                    if (strMap.hasKey(word)) {
                        wordcount = strMap.remove(word);
                        wordcount++;
                        strMap.add(word, wordcount);
                    } else {
                        wordcount = 0;
                        q.add(qpos, word);
                        qpos++;
                        wordcount++;
                        strMap.add(word, wordcount);
                    }
                }
            }
        }
    }

    /**
     * Alphabetizes the given {@code Sequence}.
     * 
     * @param q
     *            the given {@code Sequence}
     * @replaces {@code q}
     * @ensures <pre>
     * {@code q = alphabetized}
     * </pre>
     */
    private static void alphabetize(Sequence<String> q) {
        Sequence<String> q2 = new Sequence1L<String>();
        q2.transferFrom(q);
        String next = "";
        int addcount = 0;
        while (q2.length() != 0) {
            int count = 0;
            int nextcount = 0;
            while (count < q2.length() - 1) {
                next = q2.entryAt(nextcount);
                if (next.compareToIgnoreCase(q2.entryAt(count + 1)) > 0) {
                    nextcount = count + 1;
                }
                count++;
            }
            q.add(addcount, q2.entryAt(nextcount));
            q2.remove(nextcount);
            addcount++;
        }
    }

    /**
     * Updates given {@code Map} with the utilization of the given alphabetized
     * {@code Sequence}.
     * 
     * @param m
     *            the given {@code Map}
     * @param s
     *            the {@code Sequence} to be used to update m
     * @replaces {@code m}
     */
    private static void mapUpdate(Map<String, Integer> m, Sequence<String> s) {
        Map<String, Integer> copy = new Map1L<String, Integer>();
        int count = 0;
        while (count < s.length()) {
            copy.add(s.entryAt(count), m.value(s.entryAt(count)));
            count++;
        }
        m.transferFrom(copy);
    }

    /**
     * Creates an HTML page containing a list of words and their associated word
     * count.
     * 
     * @param filename
     *            the title to be displayed
     * @param terms
     *            list of alphabetized words to reference
     * @param out
     *            the output stream
     * @param m
     *            Map of words and associated word count
     * @updates {@code out.content}
     * @ensures <pre>
     * {@code out.content = #out.content * [the HTML opening tags]}
     * </pre>
     */
    private static void outputWordList(String filename, Sequence<String> terms,
            SimpleWriter out, Map<String, Integer> m) {
        // Prints to the file the opening tags
        out.println("<?xml version='1.0' encoding='ISO-8859-1' ?>");
        out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'"
                + " 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
        out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
        out.println("<head>");
        out.println("<meta http-equiv='Content-Type'"
                + " content='text/html; charset=ISO-8859-1' />");

        // Prints the title
        out.println("<title>Word Counter</title>");

        // Prints out more opening tags
        out.println("</head>");
        out.println("<body background=\"http://boomoy.files.wordpress.com/2011/09/white-snow-background-1.jpeg\">");
        out.println("<h2>Word Counter</h2>");
        out.println("<h3>Filename: " + filename + "</h3>");

        // Creates a table of words and associated word counts
        int k = 0;
        out.println("<table border = '1'>");
        out.println("<tr>");
        out.println("<th>Word</th>");
        out.println("<th>Word Count</th>");
        out.println("</tr>");
        while (k < terms.length()) {
            String termname = terms.entryAt(k);
            out.println("<tr>");
            out.println("<td>" + termname + "</td>");
            out.println("<td>" + m.value(termname) + "</td>");
            out.println("</tr>");
            k++;
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
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
        char returnedPiece = 'a';
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
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     * 
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces {@code strSet}
     * @ensures <pre>
     * {@code strSet = elements(str)}
     * </pre>
     */
    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        int count = 0;
        char setPiece = 'a';
        strSet.clear();
        while (count < str.length()) {
            if (!strSet.contains(str.charAt(count))) {
                setPiece = str.charAt(count);
                strSet.add(setPiece);
            }
            count++;
        }
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Ask for input file
         */
        out.println("Insert name of input file: ");
        String title = in.nextLine();
        SimpleReader file = new SimpleReader1L(title);
        /*
         * Ask for output folder name
         */
        out.println("Insert name of output folder: ");
        String folder = in.nextLine();
        /*
         * Creates a Map of words and associated word counts. Additionally
         * creates a Sequence of all the words in no particular order.
         */
        Map<String, Integer> m = new Map1L<String, Integer>();
        Sequence<String> terms = new Sequence1L<String>();
        generateTerms(file, m, terms);
        /*
         * Sort the Sequence of words into alphabetical order
         */
        alphabetize(terms);
        /*
         * Update the Map to have alphabetized words and associated word counts
         * paired
         */
        mapUpdate(m, terms);
        /*
         * Create html page of table with words and word count
         */
        SimpleWriter index = new SimpleWriter1L(folder + "/index.html");
        outputWordList(title, terms, index, m);
        /*
         * Close input and output streams
         */
        index.close();
        in.close();
        out.close();
        file.close();
    }
}