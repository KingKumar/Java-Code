// Population.java
// Manages a population of candidate sudokus
// 

public class Population extends Solver {
    int[][] currentState = new int[4][4];

    // Holds population of sudokus
    Sudoku[] sudokus;

    // Construct a population
    public void initializePopulation(int populationSize, Sudoku puzzle) {
        // Make a copy of puzzle to work with.
        this.temp = new Sudoku(puzzle.getInitialState());
        // Copy the initial state of the puzzle to the Hill
        // climbing class.
        this.setInitialState(this.temp);
        this.sudokus = new Sudoku[populationSize];
        // Loop and create individuals
        for (int i = 0; i < this.populationSize(); i++) {
            // Copy initial state into current state.
            for (int i2 = 0; i2 < 4; i2++) {
                for (int j = 0; j < 4; j++) {
                    this.currentState[i2][j] = this.initialState[i2][j];
                }
            }
            this.initializeState(this.currentState);
            this.temp = new Sudoku(this.currentState);
            this.saveSudoku(i, this.temp);
        }
    }

    // Saves a sudoku
    public void saveSudoku(int index, Sudoku sudoku) {
        this.sudokus[index] = sudoku;
    }

    // Gets a sudoku from population
    public Sudoku getSudoku(int index) {
        return this.sudokus[index];
    }

    // Gets the best sudoku in the population
    public Sudoku getFittest() {
        Sudoku fittest = this.sudokus[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < this.populationSize(); i++) {
            if (fittest.verify() >= this.getSudoku(i).verify()) {
                fittest = this.getSudoku(i);
            }
        }
        return fittest;
    }

    // Gets population size
    public int populationSize() {
        return this.sudokus.length;
    }

    @Override
    public int[][] solve(Sudoku puzzle) {
        // not used in context of solution
        return null;
    }
}