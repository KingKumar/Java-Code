import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Provides estimate for input number using the de Jager formula.
 * 
 * @author Ronit Kumar
 * 
 */
public final class ABCDGuesser1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser1() {
    }

    /**
     * This method will approximate insert constant within a certain error.
     */
    private static void myMethod(double constant, double w, double x, double y,
            double z) {
        int a = 0, b = 0, c = 0, d = 0;
        double acount = 0, bcount = 0, ccount = 0, dcount = 0;
        double[] array = { -5, -4, -3, -2, -1, -1.0 / 2, -1.0 / 3, -1.0 / 4, 0,
                1.0 / 4, 1.0 / 3, 1.0 / 2, 1, 2, 3, 4, 5 };
        double difference = ((Math.pow(w, array[0])) * (Math.pow(x, array[0]))
                * (Math.pow(y, array[0])) * (Math.pow(z, array[0])))
                - constant;
        while (d <= 16) {
            while (c <= 16) {
                while (b <= 16) {
                    while (a <= 16) {
                        double estimate = ((Math.pow(w, array[a]))
                                * (Math.pow(x, array[b]))
                                * (Math.pow(y, array[c])) * (Math.pow(z,
                                array[d]))) - constant;
                        if (Math.abs(estimate) < Math.abs(difference)) {
                            difference = estimate;
                            acount = array[a];
                            bcount = array[b];
                            ccount = array[c];
                            dcount = array[d];
                        }
                        a++;
                    }
                    b++;
                    a = 0;
                }
                c++;
                b = 0;
            }
            d++;
            c = 0;
        }
        double error = (difference / constant) * 100;
        System.out.println(constant + difference);
        System.out.println("The error is: " + error);
        System.out.println("The exponent of the first number you chose was "
                + acount);
        System.out.println("The exponent of the second number you chose was "
                + bcount);
        System.out.println("The exponent of the third number you chose was "
                + ccount);
        System.out.println("The exponent of the fourth number you chose was "
                + dcount);
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

        /* Ask user for constant to approximate */
        out.println("Please insert constant to approximate: ");
        double constant = in.nextDouble();

        /* Ask user for w, x, y, and z */
        out.println("Please choose positive "
                + "numbers that are significant to you that are not '1'");
        out.println("First number: ");
        double w = in.nextDouble();
        out.println("Second number: ");
        double x = in.nextDouble();
        out.println("Third number: ");
        double y = in.nextDouble();
        out.println("Fourth number: ");
        double z = in.nextDouble();
        myMethod(constant, w, x, y, z);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
