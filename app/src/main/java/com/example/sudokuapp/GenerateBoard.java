package com.example.sudokuapp;

import java.util.Random;

public class GenerateBoard {
    int[][] mGeneratedBoard;
    int rows, cols;

    GenerateBoard(int r, int c)
    {
        rows = r;
        cols = c;
        mGeneratedBoard = new int[r][c];
        for(int[] i:mGeneratedBoard)
        {
            for(int j: i)
            {
                j = 0;
            }
        }
    }

    public void createBoard() {

        Random random = new Random();
        int min = 0;
        int max = 8;
        int row = random.nextInt((max - min) + 1) + min;
        int col = random.nextInt((max - min) + 1) + min;
        int num = random.nextInt((max - min) + 1) + min;
        //Place a random number anywhere on the table, this acts as a seed.
        mGeneratedBoard[row][col] = num+1;
        //Solve the grid based off that seed.
        solveBoard(0,0,mGeneratedBoard);
        //demoTableGenerated and generatedTable are the same. Remove some givens from demoTableGenerated
        int givenCounter = 0;
        int givenMax = 40;
        while (givenCounter < givenMax)
        {
            row = random.nextInt((max - min) + 1) + min;
            col = random.nextInt((max - min) + 1) + min;
            mGeneratedBoard[row][col] = 0;
            givenCounter++;
        }
    }

    public boolean checkBox(int row, int col, int num, int[][] board)
    {
        //This function checks a 3x3 mSudokuBoard area the proposed number is within to verify its not repeated
        int box_start_row = (row / 3) * 3;
        int box_start_col = (col / 3) * 3;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i + box_start_row][j + box_start_col] == num)
                {
                    return false;
                }
            }
        }
        return true;
    }

    //Return true if num is in the correct position given row and col coordinates
    public boolean validSpot(int row, int col, int num, int[][] board)
    {
        //TO DO: Throw an error for num outside [0,9]
        for (int i = 0; i < 9; i++)
        {
            if (board[row][i] == num || board[i][col]== num)
            {
                return false;
            }
        }
        //Check box
        return checkBox(row, col, num, board);
    }

    public boolean solveBoard(int row, int col, int[][] board)
    {
        //Stops recursion if all rows are filled
        if (row == 9)
        {
            return true;
        }

        //If the value at the specified row and col coordinate is not zero, continue with this if statement to move onto the next cell
        if (board[row][col] != 0)
        {
            //If at the last column, move to the next row and start column at 0 again
            if (col == 8)
            {
                return solveBoard(row + 1, 0, board);
            }
            //Otherwise, continue in same row, but move onto next column
            else
            {
                return solveBoard(row, col + 1, board);
            }
        }

        //If at a zero position, attempt to fill it with numbers [1,9]
        for (int num = 1; num <= 9; num++)
        {
            //Check the passed parameters coordinates if they are valid
            if (validSpot(row, col, num, board))
            {
                //If so, set num
                board[row][col] = num;

                //If you are at column 8 (last column), move onto the next row
                if (col == 8)
                {
                    //Calling this function will test numbers at the next index
                    //If the numbers are suitable, and passes validSpot() then it will continue until row =9
                    //Otherwise, it will return false therefore this if statement will not return true and the original row and col will have
                    //to be tested again with another value num++
                    //This is the same concept for every other function call in this function
                    if (solveBoard(row + 1, 0, board))
                    {
                        return true;
                    }
                }
                else
                {
                    //Otherwise, Move onto the next column if not at the last column
                    if (solveBoard(row, col + 1, board))
                    {
                        return true;
                    }
                }
                //When backtracking, reset the value to 0 as the presumed solution failed
                //THis needs to reset it to the correct number, english and translation.
                board[row][col] = 0;
            }
        }
        return false;
    }

}
