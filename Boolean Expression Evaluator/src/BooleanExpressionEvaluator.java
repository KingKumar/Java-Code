import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the value of a Boolean expression consisting of
 * Boolean operators NOT, AND, and OR, operands T and F, and parentheses.
 * 
 * @author Ronit Kumar
 * 
 */
public final class BooleanExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BooleanExpressionEvaluator() {
    }

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Queue<String>}. Note the
     * requires clause that the input be well-formed. This implementation
     * assumes the input does not contain any invalid characters or tokens and
     * does not do any error checking.
     * 
     * @param source
     *            the input
     * @return the queue of tokens
     * @requires <pre>
     * {@code [source only contains valid tokens for the Boolean expression
     *  grammar and white space to separate them]}
     * </pre>
     * @ensures <pre>
     * {@code tokens = [the non-whitespace tokens in source]}
     * </pre>
     */
    private static Queue<String> tokens(String source) {
        Queue<String> tokens = new Queue1L<String>();
        int pos = 0;
        while (pos < source.length()) {
            if (source.charAt(pos) == 'T') {
                tokens.enqueue("T");
                pos++;
            } else if (source.charAt(pos) == 'F') {
                tokens.enqueue("F");
                pos++;
            } else if (source.charAt(pos) == '(') {
                tokens.enqueue("(");
                pos++;
            } else if (source.charAt(pos) == ')') {
                tokens.enqueue(")");
                pos++;
            } else if (source.charAt(pos) == 'N') {
                tokens.enqueue("NOT");
                pos += 3;
            } else if (source.charAt(pos) == 'A') {
                tokens.enqueue("AND");
                pos += 3;
            } else if (source.charAt(pos) == 'O') {
                tokens.enqueue("OR");
                pos += 2;
            } else {
                pos++;
            }
        }
        return tokens;
    }

    /**
     * Evaluates a Boolean expression and returns its value.
     * 
     * @param tokens
     *            the {@code Queue<String>} that starts with a bool-expr string
     * @return value of the expression
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [a bool-expr string is a prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code valueOfBoolExpr =
     *   [value of longest bool-expr string at start of #tokens]  and
     * #tokens = [longest bool-expr string at start of #tokens] * tokens}
     * </pre>
     */
    public static boolean valueOfBoolExpr(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";

        boolean returned;
        while (tokens.length() > 0) {
            String front = tokens.dequeue();
            if (front == "NOT") {
                returned = !valueOfBoolExpr(tokens);
            } else if (front == "T") {
                returned = true;
                valueOfBoolExpr(tokens);
            } else if (front == "F") {
                returned = false;
                valueOfBoolExpr(tokens);
            } else if (front == "AND") {
                returned = returned && valueOfBoolExpr(tokens);
            } else if (front == "OR") {
                returned = true;
                returned = returned || valueOfBoolExpr(tokens);
            }
            return returned;
        }
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
        out.print("Enter a valid Boolean expression: ");
        String source = in.nextLine();
        while (source.length() > 0) {
            boolean value = valueOfBoolExpr(tokens(source));
            out.println(source + " = " + value);
            out.print("Enter a valid Boolean expression: ");
            source = in.nextLine();
        }
        in.close();
        out.close();
    }

}