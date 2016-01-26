import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.StatementKernel.Condition;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 * 
 * @author Ronit Kumar
 * 
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     * 
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces {@code body}
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [<"INSTRUCTION"> is a proper prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code if [an instruction string is a proper prefix of #tokens] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]}
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens, Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        tokens.dequeue();
        String name = tokens.dequeue();
        tokens.dequeue();
        int count = 0;
        while (count < tokens.length()) {
            if (tokens.front() != "IF" || tokens.front() != "WHILE") {
                body.assembleCall(tokens.dequeue());
                count++;
            } else {
                count = tokens.length();
            }
        }
        return name;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Statement newBody = this.newBody();
        String newName;
        Map<String, Statement> newContext = this.newContext();
        while (tokens.front() != "END") {
            if (tokens.front() == "PROGRAM") {
                tokens.dequeue();
                newName = tokens.dequeue();
                tokens.dequeue();
            } else if (tokens.front() == "INSTRUCTION") {
                parseInstruction(tokens, newBody);
            } else if (tokens.front() == "IF") {
                Statement s = this.newBody();
                Statement s2 = this.newBody();
                tokens.dequeue();
                Condition c = tokens.dequeue();
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
                Statement s = this.newBody();
                tokens.dequeue();
                Condition c = tokens.dequeue();
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
        this.replaceBody(newBody);
        this.replaceName(newName);
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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
