import components.program.Program;
import components.program.Program1;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * BL program parser and pretty-printer to test Program2 kernel student
 * implementation against Program1 kernel library implementation.
 * 
 * @author Paolo Bucci
 * 
 */
public final class ProgramTester {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ProgramTester() {
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
        out.print("Enter a file name for a valid BL program: ");
        String fileName = in.nextLine();
        /*
         * Input program using the library implementation Program1
         */
        out.print("  Reading program using the library implementation Program1...");
        SimpleReader file = new SimpleReader1L(fileName);
        Program p1 = new Program1();
        p1.parse(file);
        file.close();
        out.println("done!");
        /*
         * Input program again using the student implementation Program2
         */
        out.print("  Reading program using the student implementation Program2...");
        file = new SimpleReader1L(fileName);
        Program p2 = new Program2();
        p2.parse(file);
        file.close();
        out.println("done!");
        /*
         * Check for equality
         */
        out.print("  Checking for equality of two programs...");
        if (p2.equals(p1)) {
            out.println("they are equal, good!");
        } else {
            out.println("error: programs are not equal!");
        }
        /*
         * Output program with library implementation
         */
        out.println("  Pretty printing program with library implementation...");
        p1.prettyPrint(out, 0);
        out.println("done!");
        /*
         * Output program with student implementation
         */
        out.println("  Pretty printing program with student implementation...");
        p2.prettyPrint(out, 0);
        out.println("done!");
        /*
         * Check for equality again
         */
        out.print("  Checking for equality of two programs...");
        if (p2.equals(p1)) {
            out.println("they are equal, good!");
        } else {
            out.println("error: programs are not equal!");
        }
        in.close();
        out.close();
    }

}
