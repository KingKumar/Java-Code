// The purpose of this class is to provide a template
// for the various optimization algorithms that this 
// program will implement. 

import java.util.Random;

public abstract class Solver {
    protected int[][] initialState;
    protected int[] numberBank = new int[4];
    protected Sudoku temp;
    protected int errors;

    // This function creates a random ordering of integers
    // from one through four.
    protected void populateNumberBank() {
        Random generator = new Random();
        int element;

        for (int i = 0; i < 4; i++) {
            this.numberBank[i] = 0;
        }

        for (int i = 0; i < 4; i++) {
            do {
                element = generator.nextInt(4) + 1;
            } while (this.existsInArray(this.numberBank, element));

            this.numberBank[i] = element;
        }

    }

    protected void setInitialState(Sudoku solution) {
        this.initialState = solution.getInitialState();
    }

    protected boolean existsInArray(int[] array, int element) {
        for (int i = 0; i < 4; i++) {
            if (array[i] == element) {
                return true;
            }
        }
        return false;
    }

    protected void initializeState(int[][] solution) {
        this.populateNumberBank();
        // Goal: If the current value of numberBank[] is
        // nowhere else in the initialState[i] row and if there exists an
        // element in
        // initialState[i] that equals zero, then assign a random number from
        // numberBank.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    switch (solution[i][j]) {
                        case 0:
                            if (!this.existsInArray(solution[i],
                                    this.numberBank[k])) {
                                solution[i][j] = this.numberBank[k];
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public abstract int[][] solve(Sudoku puzzle);
}
