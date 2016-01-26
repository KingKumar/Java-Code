import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 * 
 * @author Ronit Kumar
 * 
 */
public final class MonteCarlo {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private MonteCarlo() {
    }

    /**
     * Checks whether the given point (xCoord, yCoord) is inside the circle of
     * radius 1.0 centered at the point (1.0, 1.0).
     * 
     * @param xCoord
     *            the x coordinate of the point
     * @param yCoord
     *            the y coordinate of the point
     * @return true if the point is inside the circle, false otherwise
     */
    private static boolean pointIsInCircle(double xCoord, double yCoord) {
        if ((xCoord - 1) * (xCoord - 1) + (yCoord - 1) * (yCoord - 1) < 1) {
            out.println("The point is in the circle.");
        } else {
            out.println("The point is not in the circle.");
        }
    }

    /**
     * Generates n pseudo-random points in the [0.0,2.0) x [0.0,2.0) square and
     * returns the number that fall in the circle of radius 1.0 centered at the
     * point (1.0, 1.0).
     * 
     * @param n
     *            the number of points to generate
     * @return the number of points that fall in the circle
     */
    private static int numberOfPointsInCircle(int n) {
        int count = 0;
        int pointsin = 0;
        while (count < n) {
            double x = rnd.nextDouble();
            double y = rnd.nextDouble();
            if (((2 * x) - 1) * ((2 * x) - 1) + ((2 * y) - 1) * ((2 * y) - 1) < 1) {
                pointsin++;
            }
            count++;
        }
        out.println(n + "many random points were generated.");
        out.println(pointsin + "many were inside the circle.");
        double ratio = 1.0 * pointsin / n;
        out.println("Ratio: " + ratio);
        double estimate = ratio * 4;
        out.println("Estimate of pi: " + estimate);
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
        out.println("How many points would you like to test?");
        int n = in.nextInteger();
        numberOfPointsInCircle(n);
        in.close();
        out.close();
    }

}
