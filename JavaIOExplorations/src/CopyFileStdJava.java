import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Program to copy a text file into another file.
 * 
 * @author Ronit Kumar
 * 
 */
public final class CopyFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava() {
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) throws IOException {

        BufferedReader keyinput = new BufferedReader(new InputStreamReader(
                System.in));
        System.out.println("Please enter the name of an input file: ");
        String filename = keyinput.readLine();
        System.out
                .println("Please enter the name of an output file to write to: ");
        String outfile = keyinput.readLine();
        BufferedReader file = new BufferedReader(new FileReader(filename));
        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(
                outfile)));
        try {
            String s = file.readLine();
            while (s != null) {
                write.print(s);
                s = file.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }
        keyinput.close();
        file.close();
        write.close();
    }
}
