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
public final class Newton2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     * 
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double r = x;
        double error = 0.0001;
        if (x != 0) {
            while ((Math.abs(r * r - x)) / x > error) {
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
        out.println("Would you like to find the square root of a number? "
                + "(Enter 'y' for yes and anything else to terminate)");
        String response = in.nextLine();

        // Asks for number to find square root of. Will continue to ask user if
        // they would like to find the square root after computation.
        while (response.equals("y")) {
            out.println("Enter number: ");
            double x = in.nextDouble();
            double sqroot = sqrt(x);
            out.println("Square root estimate: " + sqroot);

            // Asks the user if they would like to find the square root
            out.println("Would you like to find the square root of a number? "
                    + "(Enter 'y' for yes and anything else to terminate)");
            response = in.nextLine();
        }
        if (response != "y") {
            out.println("Terminating Program");
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
