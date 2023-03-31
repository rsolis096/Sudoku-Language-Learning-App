package com.example.sudokuapp;


import java.io.Serializable;
import java.util.Random;

public class GenerateBoard implements Serializable  {
    public static int[][] mGeneratedBoard;
    public static int[][] mAnswerBoard;
    public int rows, cols, GRID_SIZE;
    public int dif;
    public int remainingCells;

    public void setRemainingCells(int remainingCells) {
        this.remainingCells = remainingCells;
    }
    public int getEmptyCells() {
        return remainingCells;
    }

    GenerateBoard(int r, int c, int d) {
        rows = r;
        cols = c;
        GRID_SIZE = (int) (rows * cols);
        dif = d;
        mGeneratedBoard = null;
        remainingCells = 0;
    }

    public void createBoard() {

        mGeneratedBoard = new int[GRID_SIZE][GRID_SIZE];

        Random random = new Random();
        int min = 0;
        int max = GRID_SIZE - 1;
        int row = random.nextInt((max - min) + 1) + min;
        int col = random.nextInt((max - min) + 1) + min;
        int num = random.nextInt((max - min) + 1) + min;
        //Place a random number anywhere on the table, this acts as a seed.
        mGeneratedBoard[row][col] = num + 1;
        //Solve the grid based off that seed. (follow sudoku rules)
        solveBoard(0, 0, mGeneratedBoard);
        //Sometimes recursion fails, repeat until it doesn't
        while (mGeneratedBoard[0][0] == 0 || mGeneratedBoard[0][1] == 0) {
            solveBoard(0, 0, mGeneratedBoard);
        }

        //mGeneratedBoard is now a complete board, copy it to another member array
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(mGeneratedBoard[i][j] + " ");
            }
            System.out.print("\n");
        }
        mAnswerBoard = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            System.arraycopy(mGeneratedBoard[i], 0, mAnswerBoard[i], 0, GRID_SIZE);
        }


        //Adjusting hiddenMax can be used to set a difficulty
        //If hiddenMax = 70, there are 70 hidden cells, and 11 given cells.
        //dif multiplier alters number of hidden cells by 10, easy = 40 given, medium = 30 given, hard = 20 given
        int hiddenMax = (int) ((GRID_SIZE * GRID_SIZE) * 0.6);
        int hiddenCounter = 0;
        setRemainingCells(hiddenMax);
        while (hiddenCounter < hiddenMax) {
            row = random.nextInt((max - min) + 1) + min;
            col = random.nextInt((max - min) + 1) + min;
            if (mGeneratedBoard[row][col] != 0) {
                mGeneratedBoard[row][col] = 0;
                hiddenCounter++;
            }
        }

    }

    //Return true if num is in the correct position given row and col coordinates
    //Different than SudokuFunctionality because this is called before ElementButtons are made
    public boolean validSpot(int row, int col, int num, int[][] board) {
        if(board[row][col] == 0) {
            //Check rows and cols
            for (int i = 0; i < GRID_SIZE; i++) {
                if (board[row][i] == num || board[i][col] == num) {
                    return false;
                }
            }

            //Check box
            int box_start_col = ((int) (col / Sudoku.getBoxSize().first) * Sudoku.getBoxSize().first);
            int box_start_row = ((int) (row / Sudoku.getBoxSize().second) * Sudoku.getBoxSize().second);
            for (int i = 0; i < Sudoku.getBoxSize().first; i++) {
                for (int j = 0; j < Sudoku.getBoxSize().second; j++) {
                    if (board[j + box_start_row][i + box_start_col] == num) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean solveBoard(int row, int col, int[][] board) {
        //Stops recursion if all rows are filled
        if (row == GRID_SIZE) {
            return true;
        }

        //If the value at the specified row and col coordinate is not zero, continue with this if statement to move onto the next cell
        if (board[row][col] != 0) {
            //If at the last column, move to the next row and start column at 0 again
            if (col == GRID_SIZE - 1) {
                return solveBoard(row + 1, 0, board);
            }
            //Otherwise, continue in same row, but move onto next column
            else {
                return solveBoard(row, col + 1, board);
            }
        }

        //Random number prevents first row and box from containing values 1 through 9 in that order
        Random random = new Random();
        int min = 1;
        int max = GRID_SIZE;
        int checkNum = random.nextInt((max - min) + 1) + min;
        int counter = 0;
        //If at a zero position, attempt to fill it with numbers [1,9]

        //Must iterate enough times for row + 1 and col+1 to reach 8
        while (counter < GRID_SIZE) {
            //Check the passed parameters coordinates if they are valid
            if (validSpot(row, col, checkNum, board)) {
                //If so, set to checkNum
                board[row][col] = checkNum;
                //System.out.println("Value placed into 2d array: " + mGeneratedBoard[row][col]);

                //If you are at column 8 (last column), move onto the next row
                if (col == GRID_SIZE - 1) {
                    //Calling this function will test numbers at the next index
                    //If the numbers are suitable, and passes validSpot() then it will continue until row =9
                    //Otherwise, it will return false therefore this if statement will not return true and the original row and col will have
                    //to be tested again with another value num++
                    //This is the same concept for every other function call in this function
                    if (solveBoard(row + 1, 0, board)) {
                        return true;
                    }
                } else {
                    //Otherwise, Move onto the next column if not at the last column
                    if (solveBoard(row, col + 1, board)) {
                        return true;
                    }
                }

            }
            //When backtracking, reset the value to 0 as the presumed solution failed
            board[row][col] = 0;
            counter++;
        }
        return false;
    }

}

