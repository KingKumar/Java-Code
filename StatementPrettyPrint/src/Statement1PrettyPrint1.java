import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code prettyPrint} for
 * {@code Statement}.
 */
public final class Statement1PrettyPrint1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constructs into the given {@code Statement} the BLOCK statement read from
     * the given input file.
     * 
     * @param fileName
     *            the name of the file containing 0 or more statements
     * @param s
     *            the constructed BLOCK statement
     * @replaces {@code s}
     * @requires <pre>
     * [fileName is the name of a file containing 0 or more valid BL statements]
     * </pre>
     * @ensures <pre>
     * {@code s = [BLOCK statement from file fileName]}
     * </pre>
     */
    private static void loadStatement(String fileName, Statement s) {
        SimpleReader in = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(in);
        s.parseBlock(tokens);
        in.close();
    }

    /**
     * Prints the given number of spaces to the given output stream.
     * 
     * @param out
     *            the output stream
     * @param numSpaces
     *            the number of spaces to print
     * @updates {@code out.content}
     * @requires <pre>
     * {@code out.is_open  and  spaces >= 0}
     * </pre>
     * @ensures <pre>
     * {@code out.content = #out.content * [numSpaces spaces]}
     * </pre>
     */
    private static void printSpaces(SimpleWriter out, int numSpaces) {
        for (int i = 0; i < numSpaces; i++) {
            out.print(' ');
        }
    }

    /**
     * Converts c into the corresponding BL condition.
     * 
     * @param c
     *            the Condition to convert
     * @return the BL condition corresponding to c
     * @ensures <pre>
     * {@code toStringCondition = [BL condition corresponding to c]}
     * </pre>
     */
    private static String toStringCondition(Condition c) {
        String result;
        switch (c) {
            case NEXT_IS_EMPTY: {
                result = "next-is-empty";
                break;
            }
            case NEXT_IS_NOT_EMPTY: {
                result = "next-is-not-empty";
                break;
            }
            case NEXT_IS_ENEMY: {
                result = "next-is-enemy";
                break;
            }
            case NEXT_IS_NOT_ENEMY: {
                result = "next-is-not-enemy";
                break;
            }
            case NEXT_IS_FRIEND: {
                result = "next-is-friend";
                break;
            }
            case NEXT_IS_NOT_FRIEND: {
                result = "next-is-not-friend";
                break;
            }
            case NEXT_IS_WALL: {
                result = "next-is-wall";
                break;
            }
            case NEXT_IS_NOT_WALL: {
                result = "next-is-not-wall";
                break;
            }
            case RANDOM: {
                result = "random";
                break;
            }
            case TRUE: {
                result = "true";
                break;
            }
            default: {
                result = ""; // this will never happen...
                break;
            }
        }
        return result;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Statement1PrettyPrint1() {
        super();
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    @Override
    public void prettyPrint(SimpleWriter out, int indent) {
        final int indentation = 4;
        switch (this.kind()) {
            case BLOCK: {

                for (int count = 0; count < this.lengthOfBlock(); count++) {
                    Statement s = this.removeFromBlock(count);
                    s.prettyPrint(out, indent);
                    this.addToBlock(count, s);
                }

                break;
            }
            case IF: {

                Statement s = this.newInstance();
                Condition kase = this.disassembleIf(s);
                String outey = toStringCondition(kase);
                printSpaces(out, indent);
                out.println("IF " + outey + " THEN");
                s.prettyPrint(out, indent + indentation);
                out.println("END IF");
                this.assembleIf(kase, s);

                break;
            }
            case IF_ELSE: {

                Statement s1 = this.newInstance();
                Statement s2 = this.newInstance();
                Condition kase = this.disassembleIfElse(s1, s2);
                String outey = toStringCondition(kase);
                printSpaces(out, indent);
                out.print("IF " + outey + " THEN");
                s1.prettyPrint(out, indent + indentation);
                printSpaces(out, indent);
                out.print("ELSE");
                s2.prettyPrint(out, indent + indentation);
                printSpaces(out, indent);
                out.print("END IF");
                this.assembleIfElse(kase, s1, s2);

                break;
            }
            case WHILE: {

                Statement s = this.newInstance();
                Condition kase = this.disassembleWhile(s);
                String outey = toStringCondition(kase);
                printSpaces(out, indent);
                out.print("WHILE " + outey + " DO");
                s.prettyPrint(out, indent + indentation);
                out.println("END WHILE");
                this.assembleWhile(kase, s);

                break;
            }
            case CALL: {

                String kase = this.disassembleCall();
                printSpaces(out, indent);
                out.println(kase);
                this.assembleCall(kase);

                break;
            }
            default: {
                // this will never happen...
                break;
            }
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
        out.print("Enter valid BL statement file name: ");
        String fileName = in.nextLine();
        /*
         * Generate expected output in file "data/expected-output.txt"
         */
        out.println("*** Generating expected output ***");
        Statement s1 = new Statement1();
        loadStatement(fileName, s1);
        SimpleWriter ppOut = new SimpleWriter1L("data/expected-output.txt");
        s1.prettyPrint(ppOut, 2);
        ppOut.close();
        /*
         * Generate actual output in file "data/actual-output.txt"
         */
        out.println("*** Generating actual output ***");
        Statement s2 = new Statement1PrettyPrint1();
        loadStatement(fileName, s2);
        ppOut = new SimpleWriter1L("data/actual-output.txt");
        s2.prettyPrint(ppOut, 2);
        ppOut.close();
        /*
         * Check that prettyPrint restored the value of the statement
         */
        if (s2.equals(s1)) {
            out.println("Statement value restored correctly.");
        } else {
            out.println("Error: statement value was not restored.");
        }

        in.close();
        out.close();
    }

}
