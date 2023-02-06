package com.example.sudokuapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Objects;

public class Sudoku
{
    public Element[][] mSudokuBoard;
    public Button solveButton;
    public Context THIS;
    public HashMap<Integer, Pair<String,String>> wordIndex;
    public HashMap<Pair<String,String>, Integer>  numberIndex;
    private final Element[][] answerTable;
    Sudoku(Context context, Resources res)
    {

        String[] english = res.getStringArray(R.array.numbers_english);
        String[] spanish = res.getStringArray(R.array.numbers_spanish);

        //Initialize a hash map for easier accessing of words
        //Each word is assigned a number [1,9] to make indexing easier
        //Indexing could be done through the array
        wordIndex = new HashMap<>();
        numberIndex = new HashMap<>();
        wordIndex.put(0, new Pair<>("", ""));
        numberIndex.put(new Pair<>("",""), 0);
        for(int i = 0; i < 9; i++)
        {
            //wordIndex; Key = Integer, Value = Pair
            wordIndex.put(i+1, new Pair<>(english[i], spanish[i]));
            //numberIndex; Key = Pair, Value = Integer
            numberIndex.put(new Pair<>(english[i], spanish[i]), i+1);
        }

        //This table is for the purposes of testing, generating algorithm should be used
        int[][] demoTable = {
                {0, 0, 0, 2, 6, 0, 7, 0, 1},
                {6, 8, 0, 0, 7, 0, 0, 9, 0},
                {1, 9, 0, 0, 0, 4, 5, 0, 0},
                {8, 2, 0, 1, 0, 0, 0, 4, 0},
                {0, 0, 4, 6, 0, 2, 9, 0, 5},
                {0, 5, 0, 0, 0, 3, 0, 2, 8},
                {0, 0, 9, 3, 0, 0, 0, 7, 4},
                {0, 4, 0, 0, 5, 0, 0, 3, 6},
                {7, 0, 3, 0, 1, 8, 0, 0, 0}
        };

        mSudokuBoard = new Element[9][9];
        answerTable = new Element[9][9];
        for(int rows = 0; rows < 9; rows++)
        {
            for(int cols = 0; cols < 9; cols++)
            {
                //set values for every Element in mBoard
                //This grid stores a pre-made valid 9x9 sudoku grid

                if(demoTable[rows][cols] != 0)
                {
                    mSudokuBoard[rows][cols] = new Element(demoTable[rows][cols], english[demoTable[rows][cols] - 1], spanish[demoTable[rows][cols] - 1], context);
                    answerTable[rows][cols] = new Element(demoTable[rows][cols], english[demoTable[rows][cols] - 1], spanish[demoTable[rows][cols] - 1], context);
                    mSudokuBoard[rows][cols].setGiven(true);
                    mSudokuBoard[rows][cols].setLock(true);
                    mSudokuBoard[rows][cols].setIndex(rows, cols);
                }
                else
                {
                    mSudokuBoard[rows][cols] = new Element(0, "", "", context);
                    answerTable[rows][cols] = new Element(0, "", "", context);
                    mSudokuBoard[rows][cols].setIndex(rows, cols);

                }
                mSudokuBoard[rows][cols].mButton.setOnClickListener(new ElementButtonListener());
            }
        }

        //Solve the grid for user input error checking
        solveGrid(0,0, answerTable);

        //Create the solve button
        THIS = context;
        solveButton = new Button(THIS);
        solveButton.setText("SOLVE");
        solveButton.setLayoutParams(new LinearLayout.LayoutParams(100, 200));
    }


    public void updateGame() //Only givens can be updated, they will be updated to the language opposite of the givens
    {
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(!mSudokuBoard[i][j].getGiven())
                {
                    mSudokuBoard[i][j].mButton.setText(mSudokuBoard[i][j].mTranslation);
                }
            }
        }
    }


    public boolean checkBox(int row, int col, int num, Element[][] board)
    {
        //This function checks a 3x3 mSudokuBoard area the proposed number is within to verify its not repeated
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if((i + (Math.floorDiv(row, 3) * 3) ) == row && (j + (Math.floorDiv(col, 3) * 3)) == col)
                    continue;
                else if (board[i + (Math.floorDiv(row, 3) * 3) ][j + (Math.floorDiv(col, 3) * 3)].mValue == num)
                {
                    //Log.i("Box Failed:", String.valueOf(board[i + (Math.floorDiv(row, 3) * 3) ][j + (Math.floorDiv(col, 3) * 3)].mValue));
                    return false;

                }
            }
        }
        return true;
    }


    //Return true if num is in the correct position given row and col coordinates
    public boolean validSpot(int row, int col, int num, Element[][] board)
    {
        //TO DO: Throw an error for num outside [0,9]
        for (int i = 0; i < 9; i++)
        {
            //Check row
            if (board[row][i].mValue == num)
            {
                if(col == i)
                    continue;
                else {
                    //Log.i("Row Failed:", String.valueOf(board[row][i].mValue));
                    return false;
                }

            }
            //Check column
            if (board[i][col].mValue == num)
            {
                if(row == i)
                    continue;
                else {
                    //Log.i("Col Failed:", String.valueOf(board[i][col].mValue));
                    return false;
                }
            }
            //Check box
            if (!checkBox(row, col, num, board))
            {
                return false;
            }
        }
        return true;
    }

    public boolean solveGrid(int row, int col, Element[][] board)
    {
        //Stops recursion if all rows are filled
        if (row == 9)
        {
            return true;
        }

        //If the value at the specified row and col coordinate is not zero, continue with this if statement to move onto the next cell
        if (board[row][col].mValue != 0)
        {
            //If at the last column, move to the next row and start column at 0 again
            if (col == 8)
            {
                return solveGrid(row + 1, 0, board);
            }
            //Otherwise, continue in same row, but move onto next column
            else
            {
                return solveGrid(row, col + 1, board);
            }
        }

        //If at a zero position, attempt to fill it with numbers [1,9]
        for (int num = 1; num <= 9; num++)
        {
            //Check the passed parameters coordinates if they are valid
            if (validSpot(row, col, num, board))
            {
                //If so, set num
                //CHANGE THIS TO BE INTERCHANGABLE WITH WORDS
                board[row][col].mValue = num;
                board[row][col].setEnglish(wordIndex.get(num).first);
                board[row][col].setTranslation(wordIndex.get(num).second);

                //If you are at column 8 (last column), move onto the next row
                if (col == 8)
                {
                    //Calling this function will test numbers at the next index
                    //If the numbers are suitable, and passes validSpot() then it will continue until row =9
                    //Otherwise, it will return false therefore this if statement will not return true and the original row and col will have
                    //to be tested again with another value num++
                    //This is the same concept for every other function call in this function
                    if (solveGrid(row + 1, 0, board))
                    {
                        return true;
                    }
                }
                else
                {
                    //Otherwise, Move onto the next column if not at the last column
                    if (solveGrid(row, col + 1, board))
                    {
                        return true;
                    }
                }
                //When backtracking, reset the value to 0 as the presumed solution failed
                //THis needs to reset it to the correct number, english and translation.
                board[row][col].mValue = 0;
                board[row][col].setEnglish(wordIndex.get(0).first);
                board[row][col].setTranslation(wordIndex.get(0).second);
            }
        }
        return false;
    }

    private class ElementButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //Save the calling object as to a variable for easier to understand use.
            ElementButton buttonPressed = (ElementButton) view;

            //Only allow unlocked cells to be changes (givens cannot be changed)
            if(!buttonPressed.isLocked)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Enter Number");

                EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput = input.getText().toString();

                        //Check if userInput is in the hashmap
                        //Iterating like this defeats purpose of hashmap, data structure should be reconsidered
                        boolean validUserInput = false;
                        for(int i = 1; i <= 9; i++)
                        {
                            if(Objects.equals(wordIndex.get(i).second, userInput))
                            {
                                validUserInput = true;
                                mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setValue(i);
                                mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setEnglish(wordIndex.get(i).first);
                                mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setTranslation(wordIndex.get(i).second);
                            }
                        }

                        if ((mSudokuBoard[buttonPressed.index1][buttonPressed.index2].mValue == answerTable[buttonPressed.index1][buttonPressed.index2].mValue) && validUserInput)
                        {
                            //Green if spot is valid
                            buttonPressed.setBackgroundColor(Color.rgb(173, 223, 179));
                            //Lock the button, cannot be changed after correct input
                            buttonPressed.setLocked(true);
                            //Update the cell with the userInput text
                            buttonPressed.setText(userInput);
                        } else
                        {
                            //Red if spot is invalid, button remains locked, text unchanged
                            buttonPressed.setBackgroundColor(Color.rgb(255, 114, 118));
                        }
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            }
        }
    }
}
