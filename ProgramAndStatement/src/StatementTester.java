import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Tokenizer;

/**
 * BL statement parser and pretty-printer to test Statement2 kernel student
 * implementation against Statement1 kernel library implementation.
 * 
 * @author Paolo Bucci
 * 
 */
public final class StatementTester {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private StatementTester() {
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
        /*
         * Get file name
         */
        out.print("Enter a file name for a valid BL statement or "
                + "sequence of statements: ");
        String fileName = in.nextLine();
        /*
         * Input statement(s) using the library implementation Statement1
         */
        out.print("  Reading statement(s) using the library "
                + "implementation Statement1...");
        SimpleReader file = new SimpleReader1L(fileName);
        Statement s1 = new Statement1();
        Queue<String> tokens = Tokenizer.tokens(file);
        s1.parseBlock(tokens);
        file.close();
        out.println("done!");
        /*
         * Input statement(s) again using the student implementation Statement2
         */
        out.print("  Reading statement(s) using the student "
                + "implementation Statement2...");
        file = new SimpleReader1L(fileName);
        Statement s2 = new Statement2();
        tokens = Tokenizer.tokens(file);
        s2.parseBlock(tokens);
        file.close();
        out.println("done!");
        /*
         * Check for equality
         */
        out.print("  Checking for equality of two statements...");
        if (s2.equals(s1)) {
            out.println("they are equal, good!");
        } else {
            out.println("error: statements are not equal!");
        }
        /*
         * Output statement(s) with library implementation
         */
        out.println("  Pretty printing statement with library implementation...");
        s1.prettyPrint(out, 0);
        out.println("done!");
        /*
         * Output statement(s) with student implementation
         */
        out.println("  Pretty printing statement with student implementation...");
        s2.prettyPrint(out, 0);
        out.println("done!");
        /*
         * Check for equality again
         */
        out.print("  Checking for equality of two statements...");
        if (s2.equals(s1)) {
            out.println("they are equal, good!");
        } else {
            out.println("error: statements are not equal!");
        }
        in.close();
        out.close();
    }

}
