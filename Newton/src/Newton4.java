import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Finds square root of input number with Newton Iteration.
 * 
 * @author Ronit Kumar
 * 
 */
public final class Newton4 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton4() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     * 
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x, double e) {
        double r = x;
        double error = e;
        if (x != 0) {
            while ((Math.abs(r * r - x)) / x > e) {
                r = (r + (x / r)) / 2;
            }
        }
        return r;

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

        // Asks the user if they would like to find the square root of a number

        out.println("Enter number: ");
        double x = in.nextDouble();
        // Asks for number to find square root of. Will continue to ask user if
        // they would like to find the square root after computation.
        while (x >= 0) {
            out.println("Enter error percentage margin: ");
            double e = in.nextDouble();
            double sqroot = sqrt(x, e);
            out.println("Square Root estimate: " + sqroot);

            // Asks the user if they would like to find the square root
            out.println("Enter number: ");
            x = in.nextDouble();
        }
        if (x <= 0) {
            out.println("Program Terminated");
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
