import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// This is the test driver for the Sudoku class.

public class Driver {
    public static void printSudoku(int[][] puzzle) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(puzzle[i][j]);
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int[][] inputSud = new int[4][4];
        HillClimbing hillClimber = new HillClimbing();
        GeneticAlgorithm GA = new GeneticAlgorithm();

        inputSud = inputToAdjM(args[0]);
        // // This is an unsolved sudoku for test
        // int[][] unsolvedPuzzle = { { 0, 1, 3, 0 }, { 2, 0, 0, 0 },
        // { 0, 0, 0, 3 }, { 0, 2, 1, 0 } };
        //
        // // the same sudoku solved.
        // int[][] solvedPuzzle = { { 4, 1, 3, 2 }, { 2, 3, 4, 1 },
        // { 1, 4, 2, 3 }, { 3, 2, 1, 4 } };
        //
        // // This is an uninitialized puzzle
        // // to use as a temporary variable.
        // int[][] tempPuzzle;
        //
        // // The following creates three instances
        // // of the object Sudoku for each situation
        // // the class will find itself dealing with:
        // // a completely blank sudoku, an unsolved
        // // sudoku, and a solved sudoku.
        // Sudoku blankSudoku = new Sudoku();
        // Sudoku unsolvedSudoku = new Sudoku(unsolvedPuzzle);
        // Sudoku solvedSudoku = new Sudoku(solvedPuzzle);
        // Sudoku inputSudoku = new Sudoku(inputSud);
        //
        // // Testing printCurrentState...
        // System.out.println("Testing printCurrentState...");
        // blankSudoku.printCurrentState();
        // System.out.println();
        // unsolvedSudoku.printCurrentState();
        // System.out.println();
        // solvedSudoku.printCurrentState();
        // System.out.println();
        // // Testing parse of input file (inputToAdjM)...
        // System.out.println("Testing inputToAdjM...");
        // inputSudoku.printCurrentState();
        // System.out.println();
        // System.out.println("-------");
        //
        // // Testing printInitialState...
        // System.out.println("Testing printInitialState...");
        // blankSudoku.printInitialState();
        // System.out.println();
        // unsolvedSudoku.printInitialState();
        // System.out.println();
        // solvedSudoku.printInitialState();
        // System.out.println();
        // inputSudoku.printInitialState();
        // System.out.println();
        // System.out.println("-------");
        //
        // // Testing getCurrentState...
        // System.out.println("Testing getCurrentState...");
        // tempPuzzle = blankSudoku.getCurrentState();
        // printSudoku(tempPuzzle);
        // System.out.println();
        // tempPuzzle = unsolvedSudoku.getCurrentState();
        // printSudoku(tempPuzzle);
        // System.out.println();
        // tempPuzzle = solvedSudoku.getCurrentState();
        // printSudoku(tempPuzzle);
        // System.out.println();
        // tempPuzzle = inputSudoku.getCurrentState();
        // printSudoku(tempPuzzle);
        // System.out.println();
        // System.out.println("-------");
        //
        // // Testing getInitialState...
        // System.out.println("Testing getInitialState...");
        // tempPuzzle = blankSudoku.getInitialState();
        // tempPuzzle[1][1] = 4;
        // printSudoku(tempPuzzle);
        // System.out.println();
        // tempPuzzle = unsolvedSudoku.getInitialState();
        // printSudoku(tempPuzzle);
        // System.out.println();
        // tempPuzzle = solvedSudoku.getInitialState();
        // printSudoku(tempPuzzle);
        // System.out.println();
        // tempPuzzle = inputSudoku.getInitialState();
        // printSudoku(tempPuzzle);
        // System.out.println();
        // System.out.println("-------");
        //
        // // Testing setCurrentState...
        // System.out.println("Testing setCurrentState...");
        // System.out.println("Original unsolvedSudoku.currentState...");
        // unsolvedSudoku.printCurrentState();
        // tempPuzzle = unsolvedSudoku.getCurrentState();
        // for (int i = 0; i < 4; i++) {
        // tempPuzzle[i][0] = 4;
        // }
        // unsolvedSudoku.setCurrentState(tempPuzzle);
        // System.out.println();
        // System.out.println("Modified unsolvedSudoku.currentState...");
        // unsolvedSudoku.printCurrentState();
        // System.out.println();
        // System.out.println("-------");
        //
        // // Testing verify...
        // System.out.println("Number of errors in blank sudoku: "
        // + blankSudoku.verify());
        // System.out.println();
        // System.out.println("Number of errors in unsolved sudoku: "
        // + unsolvedSudoku.verify());
        // System.out.println();
        // System.out.println("Number of errors in solved sudoku: "
        // + solvedSudoku.verify());
        // System.out.println();

        // Testing HillClimbing...
        System.out.println();
        System.out.println("----Testing Hill Climbing----");
        Sudoku tempSudoku = new Sudoku(inputSud);
        System.out.println("Initial state: ");
        tempSudoku.printCurrentState();
        int[][] tempSolution;
        tempSolution = hillClimber.solve(tempSudoku);
        tempSudoku.setCurrentState(tempSolution);
        System.out.println("Solution----");
        tempSudoku.printCurrentState();

        // Testing Genetic Algorithm...
        System.out.println();
        System.out.println("----Testing Genetic Algorithm----");
        // Initialize population
        Population pop = new Population();
        tempSudoku = new Sudoku(inputSud);
        pop.initializePopulation(50, tempSudoku);
        System.out.println("Initial state: ");
        tempSudoku.printCurrentState();
        System.out.println();

        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop, tempSudoku);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop, tempSudoku);
            // System.out.println("(" + i + ")evolving population ... ");
        }
        tempSudoku = pop.getFittest();
        // Print final results
        System.out.println();
        System.out.println("Solution----");
        tempSudoku.printCurrentState();

    }

    // Method to convert input to mutable representation of Sudoku board for
    // program
    protected static int[][] inputToAdjM(String input)
            throws FileNotFoundException {
        int count = 0;
        int[][] inS = new int[4][4];

        BufferedReader bro = null;
        String line;

        // Safe way to create a new file reader to iterate through input file
        try {
            bro = new BufferedReader(new FileReader(input));
        } catch (FileNotFoundException e) {
            System.out.println("Error declaring BufferedReader.");
            e.printStackTrace();
        }

        // Read the input file line by line, create Adj Matrices
        count = 0;
        try {
            while ((line = bro.readLine()) != null && count < 6) {
                for (int i = 0; i < 4; i++) {
                    if (count > 0) {
                        if (line.charAt(i) == '*') {
                            inS[count - 1][i] = 0;
                        } else {
                            inS[count - 1][i] = Character.getNumericValue(line
                                    .charAt(i));
                        }
                    }
                }
                count++;
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
        return inS;
    }
}
