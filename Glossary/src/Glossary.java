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
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
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
     * @param q
     *            the {@code Queue} to be replaced
     * @replaces {@code strSet}
     * @replaces {@code q}
     * @ensures <pre>
     * {@code strMap = elements(file)}
     * </pre>
     */
    private static void generateTerms(SimpleReader file,
            Map<String, String> strMap, Sequence<String> q) {

        int count = 0;
        String term = "";
        String defHolder = "blah";
        String finalDef = "";
        while (!file.atEOS()) {
            term = file.nextLine();
            q.add(count, term);
            count++;
            defHolder = "blah";
            finalDef = "";
            while (!defHolder.equals("")) {
                defHolder = file.nextLine();
                finalDef = finalDef.concat(defHolder);
            }
            strMap.add(term, finalDef);
        }
    }

    /**
     * Alphabetizes the given {@code Sequence}.
     * 
     * @param q
     *            the given {@code Sequence}
     * @replaces {@code q}
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
                if (next.compareTo(q2.entryAt(count + 1)) > 0) {
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
    private static void mapUpdate(Map<String, String> m, Sequence<String> s) {
        Map<String, String> copy = new Map1L<String, String>();
        int count = 0;
        while (count < s.length()) {
            copy.add(s.entryAt(count), m.value(s.entryAt(count)));
            count++;
        }
        m.transferFrom(copy);
    }

    /**
     * Outputs the opening tags in the generated HTML file.
     * 
     * @param terms
     *            the Sequence of terms
     * @param out
     *            the output stream
     * @updates {@code out.content}
     * @ensures <pre>
     * {@code out.content = #out.content * [the HTML opening tags]}
     * </pre>
     */
    private static void outputIndex(Sequence<String> terms, SimpleWriter out) {
        // Prints to the file the opening tags
        out.println("<?xml version='1.0' encoding='ISO-8859-1' ?>");
        out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'"
                + " 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
        out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
        out.println("<head>");
        out.println("<meta http-equiv='Content-Type'"
                + " content='text/html; charset=ISO-8859-1' />");

        // Prints the title
        out.println("<title>Glossary</title>");

        // Prints out more opening tags
        out.println("</head>");
        out.println("<body background=\"http://boomoy.files.wordpress.com/2011/09/white-snow-background-1.jpeg\">");
        out.println("<h2>Glossary</h2>");
        out.println("<h3>Index</h3>");
        out.println("<ul>");
        int k = 0;
        while (k < terms.length()) {
            String termname = terms.entryAt(k);
            out.println("<li><a href=\"" + termname + ".html" + "\">"
                    + termname + "</a></li>");
            k++;
        }

        out.println("</ul>");

        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Outputs the opening tags in the generated HTML file.
     * 
     * @param m
     *            the Map of terms and definitions
     * @param terms
     *            the Sequence of terms
     * @param foldername
     *            the name of the folder the html files are output to
     * @updates {@code out.content}
     * @ensures <pre>
     * {@code out.content = #out.content * [the HTML tags]}
     * </pre>
     */
    private static void outputDefinitions(Map<String, String> m,
            Sequence<String> terms, String foldername) {
        int count = 0;
        String filename = "";
        while (count < terms.length()) {
            filename = terms.entryAt(count);
            SimpleWriter foo = new SimpleWriter1L(foldername + "/" + filename
                    + ".html");
            foo.println("<?xml version='1.0' encoding='ISO-8859-1' ?>");
            foo.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'"
                    + " 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
            foo.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
            foo.println("<head>");
            foo.println("<meta http-equiv='Content-Type'"
                    + " content='text/html; charset=ISO-8859-1' />");
            // Prints the title
            foo.println("<title>" + terms.entryAt(count) + "</title>");
            // Prints out more opening tags
            foo.println("</head>");
            foo.println("<body background=\"http://boomoy.files.wordpress.com/2011/09/white-snow-background-1.jpeg\">");
            foo.println("<h2><b><i><font color=\"red\">" + terms.entryAt(count)
                    + "</font></i></b></h2>");
            foo.println("<blockquote>" + m.value(terms.entryAt(count))
                    + "</blockquote>");
            foo.println("<p>Return to <a href=\"index.html\">index</a></p>");
            foo.println("</body>");
            foo.println("</html>");
            foo.close();
            count++;
        }
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
     * Updates given {@code Map} values with html hyperlink tags included next
     * to terms appearing in definitions.
     * 
     * @param m
     *            the given {@code Map}
     * @param s
     *            the {@code Sequence} to be used to update m
     * @param separatorSet
     *            the {@code Set} to be used to separate words from each other
     * @restores {@code s}
     * @replaces {@code m}
     */
    private static void mapUpdateLink(Map<String, String> m,
            Sequence<String> s, Set<Character> separatorSet) {
        Map<String, String> copy = new Map1L<String, String>();
        Set<String> terms = new Set1L<String>();
        String testCopy = "";
        int position = 0;
        int count = 0;
        int size = s.length();
        while (terms.size() < size) {
            testCopy = s.remove(count);
            terms.add(testCopy);
            s.add(count, testCopy);
            count++;
        }
        count = 0;
        testCopy = "";
        while (count < terms.size()) {
            position = 0;
            testCopy = "";
            String testStr = m.value(s.entryAt(count));
            while (position < testStr.length()) {
                String token = nextWordOrSeparator(testStr, position,
                        separatorSet);
                if (separatorSet.contains(token.charAt(0))) {
                    testCopy = testCopy + token;
                } else if (terms.contains(token)) {
                    testCopy = testCopy + "<a href=\"" + token + ".html\">"
                            + token + "</a>";
                } else {
                    testCopy = testCopy.concat(token);
                }
                position += token.length();
            }
            copy.add(s.entryAt(count), testCopy);
            count++;
        }
        m.transferFrom(copy);
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
        SimpleReader file = new SimpleReader1L(in.nextLine());
        /*
         * Ask for output folder name
         */
        out.println("Insert name of output folder: ");
        String folder = in.nextLine();
        /*
         * Creates a Map of associated terms and definitions. Additionally
         * creates a Sequence of all the terms in no particular order.
         */
        Map<String, String> m = new Map1L<String, String>();
        Sequence<String> terms = new Sequence1L<String>();
        generateTerms(file, m, terms);
        /*
         * Sort the q of terms into alphabetical order
         */
        alphabetize(terms);
        /*
         * Update the Map to have alphabetized terms and definitions paired
         * together
         */
        mapUpdate(m, terms);
        /*
         * Update terms in Map to have hyperlink html tags associated with every
         * term appearing in definitions. Define separator characters for test.
         */
        final String separatorStr = " \t, ";
        Set<Character> separatorSet = new Set1L<Character>();
        generateElements(separatorStr, separatorSet);
        mapUpdateLink(m, terms, separatorSet);
        /*
         * Create Index page
         */
        SimpleWriter index = new SimpleWriter1L(folder + "/index.html");
        outputIndex(terms, index);
        index.close();
        /*
         * Create definition pages
         */
        outputDefinitions(m, terms, folder);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
        file.close();
        //
    }
}
