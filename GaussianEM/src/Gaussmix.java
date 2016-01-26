/* GMMain 
 * @author Ronit Kumar
 */
public class Gaussmix {
    public static GMModel Gmm;
    public static String DataFile;
    public static String ModelFile;
    public static int NumClusters;

    public static void main(String[] args) { // main function

        NumClusters = Integer.parseInt(args[0]);
        DataFile = args[1];
        ModelFile = args[2];

        // NumClusters = 3;
        // DataFile = "src/wine.test";
        // ModelFile = "model";

        Gmm = new GMModel(NumClusters, DataFile, ModelFile);

        for (int i = 0; i < Gmm.thresholds.length; i++) {
            Gmm.GaussianMixtureModel(i); // To build GM Model
            Gmm.OutputFinalReport(i); // To Output Final Report
            Gmm.ResetValues(); // Resetting Value for Next Threshold GMM
        }

    } // main
} // GMMain