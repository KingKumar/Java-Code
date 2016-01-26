import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 * 
 * @author Ronit Kumar
 * 
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     * 
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires <pre>
     * {@code [c is a condition string]}
     * </pre>
     * @ensures <pre>
     * {@code parseCondition = [Condition corresponding to c]}
     * </pre>
     */
    private static Condition parseCondition(String c) {
        assert c.equals("next-is-empty") || c.equals("next-is-not-empty")
                || c.equals("next-is-enemy") || c.equals("next-is-not-enemy")
                || c.equals("next-is-friend") || c.equals("next-is-not-friend")
                || c.equals("next-is-wall") || c.equals("next-is-not-wall")
                || c.equals("random") || c.equals("true") : ""
                + "Violation of: c is a condition string";
        Condition result;
        if (c.equals("next-is-empty")) {
            result = Condition.NEXT_IS_EMPTY;
        } else if (c.equals("next-is-not-empty")) {
            result = Condition.NEXT_IS_NOT_EMPTY;
        } else if (c.equals("next-is-enemy")) {
            result = Condition.NEXT_IS_ENEMY;
        } else if (c.equals("next-is-not-enemy")) {
            result = Condition.NEXT_IS_NOT_ENEMY;
        } else if (c.equals("next-is-friend")) {
            result = Condition.NEXT_IS_FRIEND;
        } else if (c.equals("next-is-not-friend")) {
            result = Condition.NEXT_IS_NOT_FRIEND;
        } else if (c.equals("next-is-wall")) {
            result = Condition.NEXT_IS_WALL;
        } else if (c.equals("next-is-not-wall")) {
            result = Condition.NEXT_IS_NOT_WALL;
        } else if (c.equals("random")) {
            result = Condition.RANDOM;
        } else { // c.equals("true")
            result = Condition.TRUE;
        }
        return result;
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     * 
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces {@code s}
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [<"IF"> is a proper prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]}
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";

        Statement s2 = s.newInstance();
        tokens.dequeue();
        Condition c = parseCondition(tokens.dequeue());
        tokens.dequeue();
        while (tokens.front() != "ELSE" && tokens.front() != "END") {
            s.assembleCall(tokens.dequeue());
        }
        if (tokens.front() == "ELSE") {
            tokens.dequeue();
            while (tokens.front() != "END") {
                s2.assembleCall(tokens.dequeue());
            }
            s.assembleIfElse(c, s, s2);
        } else {
            s.assembleIf(c, s);
        }
    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     * 
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces {@code s}
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [<"WHILE"> is a proper prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]}
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        tokens.dequeue();
        Condition c = parseCondition(tokens.dequeue());
        tokens.dequeue();
        while (tokens.front() != "IF" && tokens.front() != "END") {
            s.assembleCall(tokens.dequeue());
        }
        s.assembleWhile(c, s);
    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     * 
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces {@code s}
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [identifier string is a proper prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens}
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && Tokenizer.isIdentifier(tokens.front()) : ""
                + "Violation of: identifier string is proper prefix of tokens";

        while (tokens.front() != "WHILE" && tokens.front() != "IF"
                && tokens.front() != "END" && tokens.front() != "ELSE") {
            s.assembleCall(tokens.dequeue());
        }
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Statement newBody = this.newInstance();
        String newName;
        while (tokens.front() != "END") {
            if (tokens.front() == "PROGRAM") {
                tokens.dequeue();
                newName = tokens.dequeue();
                tokens.dequeue();
            } else if (tokens.front() == "IF") {
                Statement s = this.newInstance();
                Statement s2 = this.newInstance();
                tokens.dequeue();
                Condition c = parseCondition(tokens.dequeue());
                tokens.dequeue();
                while (tokens.front() != "ELSE" && tokens.front() != "END") {
                    s.assembleCall(tokens.dequeue());
                }
                if (tokens.front() == "ELSE") {
                    tokens.dequeue();
                    while (tokens.front() != "END") {
                        s2.assembleCall(tokens.dequeue());
                    }
                    newBody.assembleIfElse(c, s, s2);
                } else {
                    newBody.assembleIf(c, s);
                }
            } else if (tokens.front() == "WHILE") {
                Statement s = this.newInstance();
                tokens.dequeue();
                Condition c = parseCondition(tokens.dequeue());
                tokens.dequeue();
                while (tokens.front() != "IF" && tokens.front() != "END") {
                    s.assembleCall(tokens.dequeue());
                }
                newBody.assembleWhile(c, s);
            } else if (tokens.front() == "BEGIN") {
                tokens.dequeue();
                while (tokens.front() != "WHILE" && tokens.front() != "IF"
                        && tokens.front() != "END") {
                    newBody.assembleCall(tokens.dequeue());
                }
            }
        }
        this.transferFrom(newBody);
    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        int count = 0;
        Statement s = this.newInstance();
        while (tokens.front() != "WHILE" && tokens.front() != "IF"
                && tokens.front() != "END" && tokens.front() != "ELSE") {
            s.clear();
            s.assembleCall(tokens.dequeue());
            this.addToBlock(count, s);
            count++;
        }

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
