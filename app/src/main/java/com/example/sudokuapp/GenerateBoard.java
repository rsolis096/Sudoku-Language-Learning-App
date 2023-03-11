package com.example.sudokuapp;

import android.util.Log;

import java.io.Serializable;
import java.util.Random;

public class GenerateBoard implements Serializable  {
    public int[][] mGeneratedBoard;
    public int[][] mAnswerBoard;
    public int rows, cols;
    public int dif, remainingCells;

    public int getEmptyCells() {
        return remainingCells;
    }

    GenerateBoard(int r, int c, int d) {
        rows = r;
        cols = c;
        dif = d;
        mGeneratedBoard = null;
    }

    public void createBoard() {

        mGeneratedBoard = new int[rows][cols];
        Random random = new Random();
        int min = 0;
        int max = Sudoku.getGridSize() - 1;
        Log.i("Grid Size: ", String.valueOf(Sudoku.getGridSize()));
        Log.i("Grid Size: ", String.valueOf(Sudoku.getGridSize()));
        int row = random.nextInt((max - min) + 1) + min;
        int col = random.nextInt((max - min) + 1) + min;
        int num = random.nextInt((max - min) + 1) + min;
        //Place a random number anywhere on the table, this acts as a seed.
        mGeneratedBoard[row][col] = num + 1;
        //Solve the grid based off that seed.
        solveBoard(0, 0, mGeneratedBoard);
        //solveBoard failed for some reason. Not sure why, re do it
        while (mGeneratedBoard[0][0] == 0 || mGeneratedBoard[0][1] == 0) {
            Log.i("Status", "Recursion Failed, attempting again...");
            solveBoard(0, 0, mGeneratedBoard);
        }
        //mGeneratedBoard is now a complete board, copy it to another member array
        for (int i = 0; i < Sudoku.getGridSize(); i++) {
            for (int j = 0; j < Sudoku.getGridSize(); j++) {
                System.out.print(mGeneratedBoard[i][j] + " ");

            }
            System.out.print("\n");
            //System.arraycopy(mGeneratedBoard[i], 0, mAnswerBoard[i], 0, Sudoku.getGridSize());
        }
        mAnswerBoard = new int[rows][cols];
        for (int i = 0; i < Sudoku.getGridSize(); i++) {
            System.arraycopy(mGeneratedBoard[i], 0, mAnswerBoard[i], 0, Sudoku.getGridSize());
        }
        int hiddenCounter = 0;
        //Adjusting hiddenMax can be used to set a difficulty
        //If hiddenMax = 70, there are 70 hidden cells, and 11 given cells.
        //dif multiplier alters number of hidden cells by 10, easy = 40 given, medium = 30 given, hard = 20 given
        //TODO: add random variance to hidden cell generation (hard:19-26, med:27-36, easy:37-40)
        Log.i("Get Difficulty:", String.valueOf(Sudoku.getDifficulty()));
        int hiddenMax = (int) ((Sudoku.getGridSize() * Sudoku.getGridSize()) * 0.6);

        remainingCells = hiddenMax;
        while (hiddenCounter < hiddenMax) {
            row = random.nextInt((max - min) + 1) + min;
            col = random.nextInt((max - min) + 1) + min;
            //Log.i("Setting Zero: ",String.valueOf(row) +", " + String.valueOf(col) + "Value of corresponding element in board: " + String.valueOf(mGeneratedBoard[row][col]));
            if (mGeneratedBoard[row][col] != 0) {
                mGeneratedBoard[row][col] = 0;
                hiddenCounter++;
                //Log.i("Status:", "In Loop");
            }
        }
        Log.i("Status:", "Given Cells placed");
    }


    //Return true if num is in the correct position given row and col coordinates
    public boolean validSpot(int row, int col, int num, int[][] board) {
        //Check rows and cols
        for (int i = 0; i < Sudoku.getGridSize(); i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        //Check box
        int box_start_row = (row / (int) Math.sqrt(Sudoku.getGridSize())) * (int) Math.sqrt(Sudoku.getGridSize());
        int box_start_col = (col / (int) Math.sqrt(Sudoku.getGridSize())) * (int) Math.sqrt(Sudoku.getGridSize());
        for (int i = 0; i < (int) Math.sqrt(Sudoku.getGridSize()); i++) {
            for (int j = 0; j < (int) Math.sqrt(Sudoku.getGridSize()); j++) {
                if (board[i + box_start_row][j + box_start_col] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solveBoard(int row, int col, int[][] board) {
        //Log.i("SolveBoard:", "In recursion");
        //Stops recursion if all rows are filled
        if (row == Sudoku.getGridSize()) {
            return true;
        }

        //If the value at the specified row and col coordinate is not zero, continue with this if statement to move onto the next cell
        if (board[row][col] != 0) {
            //If at the last column, move to the next row and start column at 0 again
            if (col == Sudoku.getGridSize() - 1) {
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
        int max = Sudoku.getGridSize();
        int checkNum = random.nextInt((max - min) + 1) + min;
        int counter = 0;
        //If at a zero position, attempt to fill it with numbers [1,9]

        //Must iterate enough times for row + 1 and col+1 to reach 8
        while (counter < Sudoku.getGridSize()) {
            //Check the passed parameters coordinates if they are valid
            if (validSpot(row, col, checkNum, board)) {
                //If so, set to checkNum
                board[row][col] = checkNum;
                //System.out.println("Value placed into 2d array: " + mGeneratedBoard[row][col]);

                //If you are at column 8 (last column), move onto the next row
                if (col == Sudoku.getGridSize() - 1) {
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

