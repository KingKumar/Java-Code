/* Class GMModel
 * This Class have all functions that are required to 
 * create and train a Gaussian Mixture Model using Expectation Maximization (EM algorithm)
 * @author Ronit Kumar
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class GMModel {

    public String datafile;
    public String modelfile;
    public int numclusters;

    public int numOfFeaturePoints;
    public double[] featureValues;

    public double[] thresholds = { 0.1, 0.01, 0.001, 0.0001 };
    public double[] pi, mu, sigma;

    public int emIterationCount = 0;
    public double totalLoglikelihood = 0;

    public GMModel(int c, String datafile, String modelfile) { // Constructor
        this.datafile = datafile;
        this.modelfile = modelfile;
        this.numclusters = c;
        System.out.println("Number of clusters: " + c);
        this.numOfFeaturePoints = CountTotalDataPointsInFile(this.datafile);
        this.featureValues = this.readFile(this.datafile,
                this.numOfFeaturePoints);
        System.out.println("Size of this.featurevalues: "
                + this.featureValues.length);
        this.pi = new double[this.numclusters];
        this.mu = new double[this.numclusters];
        this.sigma = new double[this.numclusters];
    }

    public void GaussianMixtureModel(int index) {

        this.InitializeParameters(index);
        double ChangeInLogLikelihood = 1.0;

        while (ChangeInLogLikelihood > this.thresholds[index]) {
            double LogLikelihood = 0, ProbOfXn = 0;

            // Total LogLikelihood...
            for (int i = 0; i < this.featureValues.length; i++) {
                ProbOfXn = 0;
                for (int j = 0; j < this.numclusters; j++) {
                    ProbOfXn = ProbOfXn
                            + this.pi[j]
                            * this.GaussianFunction(this.featureValues[i],
                                    this.mu[j], this.sigma[j]);
                }
                LogLikelihood = LogLikelihood + log2(ProbOfXn);
            }

            // E- Step calculating maximum likelihood

            double[][] expectedValues = new double[this.featureValues.length][this.numclusters];

            for (int i = 0; i < this.featureValues.length; i++) {
                ProbOfXn = 0;
                for (int j = 0; j < this.numclusters; j++) {
                    expectedValues[i][j] = this.pi[j]
                            * this.GaussianFunction(this.featureValues[i],
                                    this.mu[j], this.sigma[j]);
                    if (Double.isNaN(expectedValues[i][j])) {
                        expectedValues[i][j] = 1; /* TODO */
                    }
                    ProbOfXn = ProbOfXn + expectedValues[i][j];
                }
                expectedValues[i][0] = expectedValues[i][0] / ProbOfXn;
                expectedValues[i][1] = expectedValues[i][1] / ProbOfXn;
                expectedValues[i][2] = expectedValues[i][2] / ProbOfXn;

            }

            // M-Step maximizing parameters

            double[] N_k = new double[this.numclusters];

            for (int k = 0; k < this.numclusters; k++) { // Calculating N_k's
                for (int n = 0; n < this.featureValues.length; n++) {
                    N_k[k] = N_k[k] + expectedValues[n][k];
                }
            }

            // Calculating new mu's
            for (int k = 0; k < this.numclusters; k++) {
                this.mu[k] = 0;
                for (int n = 0; n < this.featureValues.length; n++) {
                    this.mu[k] = this.mu[k] + expectedValues[n][k]
                            * this.featureValues[n];
                }
                this.mu[k] = this.mu[k] / N_k[k];
            }

            // Calculating new sigma's
            for (int k = 0; k < this.numclusters; k++) {
                this.sigma[k] = 0;
                for (int n = 0; n < this.featureValues.length; n++) {
                    this.sigma[k] = this.sigma[k] + expectedValues[n][k]
                            * (this.featureValues[n] - this.mu[k])
                            * (this.featureValues[n] - this.mu[k]);
                }
                this.sigma[k] = this.sigma[k] / N_k[k];
            }

            // Calculating new pi's
            for (int k = 0; k < this.numclusters; k++) {
                this.pi[k] = N_k[k] / this.featureValues.length;
            }

            double NewLogLikelihood = 0, ProbOfX = 0;

            // New Total LogLikelihood...
            for (int i = 0; i < this.featureValues.length; i++) {
                ProbOfX = 0;
                for (int j = 0; j < this.numclusters; j++) {
                    ProbOfX = ProbOfX
                            + this.pi[j]
                            * this.GaussianFunction(this.featureValues[i],
                                    this.mu[j], this.sigma[j]);
                }
                NewLogLikelihood = NewLogLikelihood + log2(ProbOfX);
            }

            // ChangeInTotalLogLikelihood
            ChangeInLogLikelihood = NewLogLikelihood - LogLikelihood;
            this.totalLoglikelihood = NewLogLikelihood;

            System.out.println("Total LogLikelihood: "
                    + this.totalLoglikelihood);
            System.out.println("Change in LogLikelihood: "
                    + ChangeInLogLikelihood);
            System.out.println("EM Iteration #: " + this.emIterationCount++);

        }// while

    } // GaussianMixtureModel

    public double GaussianFunction(double x_n, double Mu_k, double Sigma_k) { // return
                                                                              // N(x_n|...)

        double Prob = 0;
        Prob = Math.pow(2 * 3.14159265 * Sigma_k, -0.5)
                * Math.exp(-(Math.pow(x_n - Mu_k, 2)) / (2 * Sigma_k));
        return Prob;
    }

    public void ResetValues() {
        this.emIterationCount = 0;
        this.totalLoglikelihood = 0;
    }

    public void OutputFinalReport(int i) {

        System.out.println("\nTotalLogLikelihood: " + this.totalLoglikelihood);
        System.out.println("Number of EM Iterations: " + this.emIterationCount
                + "\n");
        for (int j = 0; j < this.numclusters; j++) {
            System.out.println(j + " Pi_k: " + this.pi[j] + "\nMu_k: "
                    + this.mu[j] + "\nSigma_k: " + this.sigma[j] + "\n");
        }

        StringBuilder builder = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.#######");

        builder.append("EM algorithm results:  \n\n");
        builder.append(this.numclusters + " Component Mixture\n");
        builder.append("Total DataPoints: " + this.numOfFeaturePoints + "\n\n");

        builder.append("Best Set of Parameters For Threshold = "
                + this.thresholds[i] + "\n\n");
        builder.append("Total LogLikelihood : "
                + df.format(this.totalLoglikelihood) + "\n");
        builder.append("Number of EM Iterations : " + this.emIterationCount
                + "\n\n");

        for (int j = 0; j < this.numclusters; j++) {
            builder.append("Group: " + j + "\n\n");
            builder.append("Pi for Group " + j + "         :  "
                    + df.format(this.pi[j]) + "\n");
            builder.append("Mu for Group " + j + "       :  "
                    + df.format(this.mu[j]) + "\n");
            builder.append("Sigma for Group " + j + " :  "
                    + df.format(this.sigma[j]) + "\n\n");

        }

        PrintWriter writer;
        try {
            writer = new PrintWriter(this.modelfile, "UTF-8");
            writer.print(builder.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, builder.toString());

    }

    public static double log2(double num) {
        if (num == 0) {
            return 0;
        } else {
            return (Math.log(num) / Math.log(2));
        }
    }

    public void InitializeParameters(int index) { /* TODO */

        int basemu = 2;

        for (int x = 0; x < this.numclusters; x++) {
            this.pi[x] = 1.00 / this.numclusters;
            this.mu[x] = basemu;
            this.sigma[x] = 0;
            basemu += 3;
        }

        // this.pi[0] = 0.33;
        // this.pi[1] = 0.33;
        // this.pi[2] = 0.34;
    }

    public double[] readFile(String fileName, int TotalDataPoints) // Length of
                                                                   // array to
                                                                   // be filled
    {
        double[] alldatapoints = new double[TotalDataPoints];

        TextFileInput tfi = new TextFileInput(fileName);
        String line = tfi.readLine(); // first line of file
        int count = 0;
        while (line != null) {
            line = tfi.readLine(); // second line of file (given first
                                   // iteration)
            if (line != null) {
                // create a new scanner with the specified String Object
                Scanner scanner = new Scanner(line);

                // use US locale to be able to identify doubles in the string
                scanner.useLocale(Locale.US);

                // find the next double token and print it
                // loop for the whole scanner
                while (scanner.hasNext()) {

                    // if the next is a double, add double to data set
                    if (scanner.hasNextDouble()) {
                        alldatapoints[count] = scanner.nextDouble() * 1.0;
                        count++;
                    }

                    // if the next is an int, parse to double and add to data
                    // set
                    if (scanner.hasNextInt()) {
                        alldatapoints[count] = scanner.nextInt() * 1.0;
                        count++;
                    }
                }
                // close the scanner
                scanner.close();
            }
        }
        System.out.println("Size of data set: " + alldatapoints.length);
        return alldatapoints;
    }// Read File

    public static int CountTotalDataPointsInFile(String fileName) // length of
                                                                  // array to be
                                                                  // filled
    {
        int numExamples;
        int numFeatures;
        int predictedValuesCount = 0;
        int valuesCount = 0;
        TextFileInput tfi = new TextFileInput(fileName);
        String line = tfi.readLine();
        // create a new scanner with the specified String Object
        Scanner scanner = new Scanner(line);
        numExamples = scanner.nextInt();
        numFeatures = scanner.nextInt();
        // close the scanner
        scanner.close();
        predictedValuesCount = numExamples * numFeatures;

        while (line != null) {
            line = tfi.readLine();
            if (line != null) {
                // create a new scanner with the specified String Object
                scanner = new Scanner(line);

                // use US locale to be able to identify doubles in the string
                scanner.useLocale(Locale.US);

                // find the next double token and print it
                // loop for the whole scanner
                while (scanner.hasNext()) {

                    // if the next is a double, print found and the double
                    if (scanner.hasNextDouble()) {
                        System.out.println("Found :" + scanner.nextDouble());
                        valuesCount++;
                    }

                    // if the next is an int, print found and the int
                    if (scanner.hasNextInt()) {
                        System.out.println("Found :" + scanner.nextInt() * 1.0);
                        valuesCount++;
                    }
                }
                // close the scanner
                scanner.close();
            }
        }
        if (predictedValuesCount != valuesCount) {
            System.out.println("Error parsing file. Expected: "
                    + predictedValuesCount + ". Parsed: " + valuesCount);
        }
        return valuesCount;
    }

    public int random() {
        int temp = 0;
        temp = (int) (Math.random() * 1000) + 1;
        return temp;
    }

    public int random_Num_between(int a, int b) {

        int num;
        while (true) {
            num = a + this.random() / 100;
            if (num >= a && num <= b) {
                break;
            }
        }
        return num;
    }
}