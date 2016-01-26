import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Program to receive a text file of a number between 0-25 per line Based on
 * number perceieved the ReflexRover will either GRAB or NOOP the number
 * 
 * @author Ronit Kumar
 * 
 *         Created 9/30/14
 */
public class ReflexRover {

    // Main function run at execution
    public static void main(String args[]) {
        String file = args[0]; // Accepts 1st parameter at command line as input
                               // file
        BufferedReader bro = null;
        String line;
        int percept;
        char action;

        // Safe way to create a new file reader to iterate through input file
        try {
            bro = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("Error declaring BufferedReader.");
            e.printStackTrace();
        }

        // Read the input file line by line, determine action to take based on
        // percept and print output
        try {
            while ((line = bro.readLine()) != null) {
                percept = Integer.parseInt(line);
                action = SimpleReflexAgent(percept);
                printOutput(percept, action);
            }
        } catch (IOException e) {
            System.out.println("Error reading with BufferedReader.");
            e.printStackTrace();
        }

        // Close the buffered reader
        try {
            bro.close();
        } catch (IOException e) {
            System.out.println("Error closing BufferedReader.");
            e.printStackTrace();
        }
    }

    // Method to return an action based on perceived number
    static char SimpleReflexAgent(int percept) {
        if (percept < 0 || percept > 25) {
            return 'N';
        } else if (percept % 5 == 0) {
            return 'G';
        } else {
            return 'N';
        }
    }

    // Method to print appropriate action to perceived number
    static void printOutput(int percept, char action) {
        System.out.println("Percieved: " + percept + "\tAction: " + action);
    }
}
