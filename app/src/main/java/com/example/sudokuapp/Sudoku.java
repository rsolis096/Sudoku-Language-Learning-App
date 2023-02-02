package com.example.sudokuapp;


import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

public class Sudoku
{
    public Element[][] mSudokuBoard;
    public Button solveButton;
    public Context THIS;
    Sudoku(Element[][] board, Context context)
    {
        mSudokuBoard = board;
        //Create the solve button
        THIS = context;
        solveButton = new Button(THIS);
        solveButton.setText("SOLVE");
        solveButton.setLayoutParams(new LinearLayout.LayoutParams(100, 200));
    }

    public void updateGame()
    {
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                mSudokuBoard[i][j].mButton.setText(String.valueOf(mSudokuBoard[i][j].mValue));
            }
        }
    }


    public boolean checkBox(int row, int col, int num)
    {
        //This function checks a 3x3 mSudokuBoard area the proposed number is within to verify its not repeated
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (mSudokuBoard[i + (row - row%3)][j + (col - col%3)].mValue == num)
                    return false;
            }
        }
        return true;
    }


    //Return true if num is in the correct position given row and col coordinates
    public boolean validSpot(int row, int col, int num)
    {
        //TO DO: Throw an error for numbers outside [0,9]
        for (int i = 0; i < 9; i++)
        {
            //Check row
            if (mSudokuBoard[row][i].mValue == num)
            {
                return false;
            }
            //Check column
            if (mSudokuBoard[i][col].mValue == num)
            {
                return false;
            }
            //Check box
            if (!checkBox(row, col, num))
            {
                return false;
            }
        }
        return true;
    }

    public boolean solveGrid(int row, int col)
    {
        //Stops recursion if all rows are filled
        if (row == 9)
        {
            return true;
        }

        //If the value at the specified row and col coordinate is not zero, continue with this if statement to move onto the next cell
        if (mSudokuBoard[row][col].mValue != 0)
        {
            //If at the last column, move to the next row and start column at 0 again
            if (col == 8)
            {
                return solveGrid(row + 1, 0);
            }
            //Otherwise, continue in same row, but move onto next column
            else
            {
                return solveGrid(row, col + 1);
            }
        }

        //If at a zero position, attempt to fill it with numbers [1,9]
        for (int num = 1; num <= 9; num++)
        {
            //Check the passed parameters coordinates if they are valid
            if (validSpot(row, col, num))
            {
                //If so, set num
                //CHANGE THIS TO BE INTERCHANGABLE WITH WORDS
                mSudokuBoard[row][col].mValue = num;

                //If you are at column 8 (last column), move onto the next row
                if (col == 8)
                {
                    //Calling this function will test numbers at the next index
                    //If the numbers are suitable, and passes validSpot() then it will continue until row =9
                    //Otherwise, it will return false therefore this if statement will not return true and the original row and col will have
                    //to be tested again with another value num++
                    //This is the same concept for every other function call in this function
                    if (solveGrid(row + 1, 0))
                    {
                        return true;
                    }
                }
                else
                {
                    //Otherwise, Move onto the next column if not at the last column
                    if (solveGrid(row, col + 1))
                    {
                        return true;
                    }
                }
                //When backtracking, reset the value to 0 as the presumed solution failed
                //THis needs to reset it to the correct number, english and translation.
                mSudokuBoard[row][col].mValue = 0;
            }
        }
        return false;
    }

}
