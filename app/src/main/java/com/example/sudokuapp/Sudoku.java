package com.example.sudokuapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Objects;


public class Sudoku
{
    public ElementButton[][] mSudokuBoard;
    public Button solveButton;
    private final Context THIS;
    public HashMap<Integer, Pair<String,String>> wordIndex;
    public HashMap<Pair<String,String>, Integer>  numberIndex;
    private final ElementButton[][] answerTable;
    public static int difficulty;
    public static boolean manual;
    //setters for game settings
    public static void setDifficulty(int d) {difficulty = d;}
    public static void setInputMode(boolean m) {manual = m;}
    //getters for game settings
    public static int getDifficulty() {return difficulty;}
    public static boolean getInputMode() {return manual;}
    Sudoku(Context context, Resources res)
    {
        //Saves getResources from MainActivity to be used in this class
        //Get words
        String[] english = res.getStringArray(R.array.numbers_english);
        String[] spanish = res.getStringArray(R.array.numbers_spanish);

        //Hashmap is used for indexing of words TODO: make efficient or switch data structure
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

        //Builds a valid integer board
        GenerateBoard generatedBoard = new GenerateBoard(9, 9, difficulty);
        generatedBoard.createBoard();

        //Converts the integer board into and Element board.
        mSudokuBoard = new ElementButton[9][9];
        answerTable = new ElementButton[9][9];
        for(int rows = 0; rows < 9; rows++)
        {
            for(int cols = 0; cols < 9; cols++)
            {
                //Iterated through the generatedBoard
                if(generatedBoard.mGeneratedBoard[rows][cols] != 0)
                {

                    //Initialize each ElementButton (cell in table)
                    mSudokuBoard[rows][cols] =
                            new ElementButton(
                                generatedBoard.mGeneratedBoard[rows][cols],
                                english[generatedBoard.mGeneratedBoard[rows][cols] - 1],
                                spanish[generatedBoard.mGeneratedBoard[rows][cols] - 1],
                                context,
                                true,
                                rows,
                                cols
                            );
                    mSudokuBoard[rows][cols].setLock(true);
                    mSudokuBoard[rows][cols].setIndex(rows, cols);

                    answerTable[rows][cols] =
                            new ElementButton(
                                generatedBoard.mGeneratedBoard[rows][cols],
                                english[generatedBoard.mGeneratedBoard[rows][cols] - 1],
                                spanish[generatedBoard.mGeneratedBoard[rows][cols] - 1],
                                context,
                                true,
                                rows,
                                cols
                            );

                }
                else
                {
                    mSudokuBoard[rows][cols] = new ElementButton(0, "", "", context, false, 0, 0);
                    answerTable[rows][cols] = new ElementButton(0, "", "", context, false, 0, 0);
                    mSudokuBoard[rows][cols].setIndex(rows, cols);

                }
                mSudokuBoard[rows][cols].setOnClickListener(new ElementButtonListener());
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


    public void updateGame()
    {
        //Updates the text of buttons
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(!mSudokuBoard[i][j].getLocked())
                {
                    mSudokuBoard[i][j].setText(mSudokuBoard[i][j].mTranslation);
                }
            }
        }
    }

    public boolean checkBox(int row, int col, int num, ElementButton[][] board)
    {
        //This function checks a 3x3 mSudokuBoard area the proposed number is within to verify its not repeated
        int box_start_row = (row / 3) * 3;
        int box_start_col = (col / 3) * 3;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {

                if (board[i + box_start_row][j + box_start_col].getValue() == num)
                {
                    return false;
                }
            }
        }
        return true;
    }

    //Return true if num is in the correct position given row and col coordinates
    public boolean validSpot(int row, int col, int num, ElementButton[][] board)
    {
        //TODO: Throw an error for num outside [0,9]
        for (int i = 0; i < 9; i++)
        {
            if (board[row][i].getValue() == num || board[i][col].getValue()== num)
            {
                    return false;
            }
        }
        //Check box
        return checkBox(row, col, num, board);
    }

    public boolean solveGrid(int row, int col, ElementButton[][] board)
    {
        //Stops recursion if all rows are filled
        if (row == 9)
        {
            return true;
        }

        //If the value at the specified row and col coordinate is not zero, continue with this if statement to move onto the next cell
        if (board[row][col].getValue() != 0)
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
                board[row][col].setValue(num);
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
                board[row][col].setValue(0);
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
            if (!buttonPressed.isLocked) {
                if (manual) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Enter Number:");

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
                            for (int i = 1; i <= 9; i++) {
                                if (Objects.equals(wordIndex.get(i).second, userInput)) {
                                    validUserInput = true;
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setValue(i);
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setEnglish(wordIndex.get(i).first);
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setTranslation(wordIndex.get(i).second);
                                }
                            }

                            //If the answer is correct
                            if ((mSudokuBoard[buttonPressed.index1][buttonPressed.index2].mValue == answerTable[buttonPressed.index1][buttonPressed.index2].mValue) && validUserInput) {

                                //Green if spot is valid
                                buttonPressed.setBackgroundColor(Color.rgb(173, 223, 179));
                                //Lock the button, cannot be changed after correct input
                                buttonPressed.setLocked(true);
                                //Update the cell with the userInput text
                                buttonPressed.setText(userInput);

                            }
                            //If the answer is incorrect
                            else {
                                //Case 1: Invalid input, Toast message displayed button unchanged.
                                if (!validUserInput) {
                                    Toast t = Toast.makeText(THIS, "Invalid Input", Toast.LENGTH_LONG);
                                    t.show();
                                }
                                //Case 2: Valid input (in vocab) but incorrect word
                                else {
                                    //Red if spot is invalid, button remains locked, text unchanged
                                    buttonPressed.setBackgroundColor(Color.rgb(255, 114, 118));
                                    //Update the cell with the userInput text
                                    buttonPressed.setText(userInput);
                                }

                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Enter Number:");
                    int place = 1;
                    Context dialogContext = builder.getContext();

                    TableLayout input = new TableLayout(dialogContext);
                    int rows, cols;

                    for (rows = 1; rows < 4; rows++) {

                        TableRow tableRow = new TableRow(dialogContext);
                        for (cols = 1; cols < 4; cols++) {
                            Button newButton = new Button(dialogContext);
                            newButton.setText(wordIndex.get(place).second);
                            tableRow.addView(newButton);
                            place++;
                        }
                        input.addView(tableRow);
                    }
                    builder.setView(input);
                    int finalPlace = place;
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String userInput = wordIndex.get(finalPlace).second;

                            mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setValue(finalPlace);
                            mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setEnglish(wordIndex.get(finalPlace).first);
                            mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setTranslation(wordIndex.get(finalPlace).second);


                            //If the answer is correct
                            if ((finalPlace == answerTable[buttonPressed.index1][buttonPressed.index2].mValue)) {

                                //Green if spot is valid
                                buttonPressed.setBackgroundColor(Color.rgb(173, 223, 179));
                                //Lock the button, cannot be changed after correct input
                                buttonPressed.setLocked(true);
                                //Update the cell with the userInput text
                                buttonPressed.setText(userInput);

                            }
                            //If the answer is incorrect
                            else {
                                //Red if spot is invalid, button remains locked, text unchanged
                                buttonPressed.setBackgroundColor(Color.rgb(255, 114, 118));
                                //Update the cell with the userInput text
                                buttonPressed.setText(userInput);
                            }

                        }

                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                    builder.show();
                }
            }
        }
    }
}


