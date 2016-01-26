import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the value of an expression consisting of numbers,
 * arithmetic operators, and parentheses.
 * 
 * @author Ronit Kumar
 * 
 */
public final class ExpressionEvaluator {

    /**
     * Base used in number representation.
     */
    private static final int RADIX = 10;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ExpressionEvaluator() {
    }

    /**
     * Evaluates a digit and returns its value.
     * 
     * @param source
     *            the {@code StringBuilder} that starts with a digit
     * @return value of the digit
     * @updates {@code source}
     * @requires <pre>
     * {@code 1 < |source|  and  [the first character of source is a digit]}
     * </pre>
     * @ensures <pre>
     * {@code valueOfDigit = [value of the digit at the start of #source]  and
     * #source = [digit string at start of #source] * source}
     * </pre>
     */
    private static int valueOfDigit(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        char dig = source.charAt(0);
        source.deleteCharAt(0);
        int digInt = Character.digit(dig, RADIX);
        return digInt;
    }

    /**
     * Evaluates a digit sequence and returns its value.
     * 
     * @param source
     *            the {@code StringBuilder} that starts with a digit-seq string
     * @return value of the digit sequence
     * @updates {@code source}
     * @requires <pre>
     * {@code [a digit-seq string is a proper prefix of source, which
     * contains a character that is not a digit]}
     * </pre>
     * @ensures <pre>
     * {@code valueOfDigitSeq =
     *   [value of longest digit-seq string at start of #source]  and
     * #source = [longest digit-seq string at start of #source] * source}
     * </pre>
     */
    private static int valueOfDigitSeq(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int digInt = valueOfDigit(source);
        while (Character.isDigit(source.charAt(0))) {
            digInt = (digInt * RADIX) + valueOfDigit(source);
        }
        return digInt;
    }

    /**
     * Evaluates a factor and returns its value.
     * 
     * @param source
     *            the {@code StringBuilder} that starts with a factor string
     * @return value of the factor
     * @updates {@code source}
     * @requires <pre>
     * {@code [a factor string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any factor string]}
     * </pre>
     * @ensures <pre>
     * {@code valueOfFactor =
     *   [value of longest factor string at start of #source]  and
     * #source = [longest factor string at start of #source] * source}
     * </pre>
     */
    private static int valueOfFactor(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value;
        if (source.charAt(0) == '(') {
            source.deleteCharAt(0);
            value = valueOfExpr(source);
            source.deleteCharAt(0);
        } else {
            value = valueOfDigitSeq(source);
        }
        return value;
    }

    /**
     * Evaluates a term and returns its value.
     * 
     * @param source
     *            the {@code StringBuilder} that starts with a term string
     * @return value of the term
     * @updates {@code source}
     * @requires <pre>
     * {@code [a term string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any term string]}
     * </pre>
     * @ensures <pre>
     * {@code valueOfTerm =
     *   [value of longest term string at start of #source]  and
     * #source = [longest term string at start of #source] * source}
     * </pre>
     */
    private static int valueOfTerm(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = valueOfFactor(source);
        if (Character.compare(source.charAt(0), '*') == 0
                || Character.compare(source.charAt(0), '/') == 0) {
            char op = source.charAt(0);
            source.deleteCharAt(0);
            if (Character.compare(op, '*') == 0) {
                value = value * valueOfFactor(source);
            } else {
                value = value / valueOfFactor(source);
            }
        }
        return value;
    }

    /**
     * Evaluates an expression and returns its value.
     * 
     * @param source
     *            the {@code StringBuilder} that starts with an expr string
     * @return value of the expression
     * @updates {@code source}
     * @requires <pre>
     * {@code [an expr string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any expr string]}
     * </pre>
     * @ensures <pre>
     * {@code valueOfExpr =
     *   [value of longest expr string at start of #source]  and
     * #source = [longest expr string at start of #source] * source}
     * </pre>
     */
    public static int valueOfExpr(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = valueOfTerm(source);
        if (Character.compare(source.charAt(0), '+') == 0
                || Character.compare(source.charAt(0), '-') == 0) {
            char op = source.charAt(0);
            source.deleteCharAt(0);
            if (Character.compare(op, '+') == 0) {
                value = value + valueOfTerm(source);
            } else {
                value = value - valueOfTerm(source);
            }
        }
        return value;
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.print("Enter an expression followed by !: ");
        String source = in.nextLine();
        while (source.length() > 0) {
            /*
             * Parse and evaluate the expression after removing all white space
             * (spaces and tabs) from the user input.
             */
            int value = valueOfExpr(new StringBuilder(source.replaceAll(
                    "[ \t]", "")));
            out.println(source.substring(0, source.length() - 1) + " = "
                    + value);
            out.print("Enter an expression followed by !: ");
            source = in.nextLine();
        }
        in.close();
        out.close();
    }

}
