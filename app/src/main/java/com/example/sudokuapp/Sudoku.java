package com.example.sudokuapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.io.Serializable;
import java.util.Objects;


public class Sudoku extends AppCompatActivity implements Serializable
{
    private final ElementButton[][] mSudokuBoard;
    private final ElementButton[][] mSudokuAnswerBoard;
    private final GenerateBoard generatedBoard;
    private final Context context;
    private transient final Chronometer mTimer;
    private static int difficulty;
    private final String[] english;
    private final String[] spanish;
    private static int wordBank;
    private static boolean manual;
    private static boolean translationDirection = true;
    private static long minutes, seconds;

    //setters for game settings
    public static void setDifficulty(int d) {difficulty = d;}
    public static void setWordBank(int index) {wordBank = index;}
    public static void setInputMode(boolean m) {manual = m;}
    public static void setTranslationDirection(boolean t) {translationDirection = t;}

    //getters for game settings
    public ElementButton getElement(int rows, int cols) {return mSudokuBoard[rows][cols];}
    public static int getWordBank() {return wordBank;}
    public static int getDifficulty() {return difficulty;}
    public static boolean getInputMode() {return manual;}
    public static boolean getTranslationDirection() {return translationDirection;}
    //returns time in MM:SS format
    public static String getElapsedTime() {
        String sec;
        if(seconds < 10) {sec = "0" + seconds;}
        else {sec = seconds+""; }

        return minutes + ":" + sec;
    }

    Sudoku(Context THIS, Chronometer t)
    {
        context = THIS;
        mTimer = t;
        //Temporarily hardcoded, another solution should best be found
        int[] categoryArrays = {
                        R.array.numbers,//0
                        R.array.greetings_easy,//1
                        R.array.greetings_medium,
                        R.array.greetings_hard,
                        R.array.directions_easy,//4
                        R.array.directions_medium,
                        R.array.directions_hard,
                        R.array.family_easy,//7
                        R.array.family_medium,
                        R.array.family_hard,
                        R.array.food_drinks_easy,//10
                        R.array.food_drinks_medium,
                        R.array.food_drinks_hard
                };

        //Given a category from categoryArrays, generate a puzzle using that category.
        int selectedArrayId = categoryArrays[getWordBank()];
        String[] inputString = context.getResources().getStringArray(selectedArrayId);

        //There is a one to one correspondence between english and spanish. the string at index 0 in spanish is the translation to the string at index 0 in english
        english = new String[9];
        spanish = new String[9];
        //Pairs of words are split by a comma
        for(int i = 0; i < 9; i++)
        {
            String [] wordPair = inputString[i].split(",");
            english[i] = wordPair[0];
            spanish[i] = wordPair[1];
        }

        //Builds a valid integer board
        //GenerateBoard has member arrays of a completed table and a partially completed table
        generatedBoard = new GenerateBoard(9, 9, getDifficulty());
        generatedBoard.createBoard();

        //Converts the integer board into and Element board.

        //mSudokuBoard is the play board
        mSudokuBoard = new ElementButton[9][9];
        //mSudokuAnswerBoard is a complete board with words used to reference for input checking
        mSudokuAnswerBoard = new ElementButton[9][9];
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
                }
                else
                {
                    mSudokuBoard[rows][cols] = new ElementButton(0, "", "", context, false, rows, cols);
                }

                mSudokuAnswerBoard[rows][cols] =
                        new ElementButton(
                                generatedBoard.mAnswerBoard[rows][cols],
                                english[generatedBoard.mAnswerBoard[rows][cols] - 1],
                                spanish[generatedBoard.mAnswerBoard[rows][cols] - 1],
                                context,
                                true,
                                rows,
                                cols
                        );

                mSudokuBoard[rows][cols].setOnClickListener(new ElementButtonListener());
            }
        }
    }

    public void startTimer(Chronometer t) {
        t.start();
        t.setOnChronometerTickListener(chronometer -> {
            //convert base time to readable minutes and seconds
            minutes = ((SystemClock.elapsedRealtime() - t.getBase())/1000) / 60;
            seconds = ((SystemClock.elapsedRealtime() - t.getBase())/1000) % 60;
            //Log.i("time", getElapsedTime());
        });
    }

    public void solveGrid()
    {
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                mSudokuBoard[i][j].setValue(generatedBoard.mAnswerBoard[i][j]);

                //If true, english is board language, spanish is the language the user should input
                if(translationDirection)
                {
                    mSudokuBoard[i][j].setEnglish(english[generatedBoard.mAnswerBoard[i][j]-1]);
                    mSudokuBoard[i][j].setTranslation(spanish[generatedBoard.mAnswerBoard[i][j]-1]);
                }
                else
                {
                    mSudokuBoard[i][j].setEnglish(spanish[generatedBoard.mAnswerBoard[i][j]-1]);
                    mSudokuBoard[i][j].setTranslation(english[generatedBoard.mAnswerBoard[i][j]-1]);
                }
            }
        }
        updateGame();
    }

    public void updateGame()
    {
        //Updates the text of buttons
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!mSudokuBoard[i][j].getLocked()) {
                    mSudokuBoard[i][j].setText(mSudokuBoard[i][j].mTranslation);
                    mSudokuBoard[i][j].setTextColor(Color.rgb(0,0,0));
                    mSudokuBoard[i][j].setLock(true);
                }
            }
        }
        mTimer.stop();
    }
    public void updateCells() {
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //don't check empty cells, their values are technically the same as other empty cells but thats ok
                if(mSudokuBoard[i][j].getValue() != 0) {
                    //check if each cell has a conflict
                    checkElement(mSudokuBoard[i][j]);
                    //if so, set the cell to red
                    if(mSudokuBoard[i][j].getWrong()) {
                        mSudokuBoard[i][j].setBackgroundColor(Color.rgb(255, 114, 118));
                    }
                    //otherwise, reset cell background
                    else {
                        setCellDesign(mSudokuBoard[i][j]);
                    }
                }
            }
        }
    }

    public void checkIfCompleted(View view) {
        boolean isComplete = true;
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mSudokuBoard[i][j].getWrong() || mSudokuBoard[i][j].getValue() == 0) {
                    isComplete = false;
                    break;
                }
            }
        }

        if(isComplete) {
            mTimer.stop();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Game Finished!");
            builder.setPositiveButton("Continue", (dialog, which) -> {
                Intent intent = new Intent(context, ResultsScreen.class);
                context.startActivity(intent);
            });
            builder.setOnDismissListener(dialogInterface -> {
                Intent intent = new Intent(context, ResultsScreen.class);
                context.startActivity(intent);
            });
            builder.show();
        }
    }

    //looks complicated, just pairs each cell to a proper border drawable to make the board look like sudoku
    public void setCellDesign(ElementButton cell) {
        int rows = cell.getIndex1();
        int cols = cell.getIndex2();
        if(cols == 0 || cols == 1 || cols == 4 || cols == 7 || cols == 8) {
            if(rows == 0 || rows == 1 || rows == 4 || rows == 7 || rows == 8) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border));
            }
            else if(rows == 2 || rows == 5) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom));
            }
            else if(rows == 3 || rows == 6) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_top));
            }
        }
        else if(cols == 2 || cols == 5) {
            if (rows == 0 || rows == 1 || rows == 4 || rows == 7 || rows == 8) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_right));
            }
            else if(rows == 2 || rows == 5) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom_right));
            }
            else if(rows == 3 || rows == 6) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_top_right));
            }
        }
        else if(cols == 3 || cols == 6) {
            if (rows == 0 || rows == 1 || rows == 4 || rows == 7 || rows == 8) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_left));
            }
            else if(rows == 2 || rows == 5) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom_left));
            }
            else if(rows == 3 || rows == 6) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_top_left));
            }
        }
    }

    //checks a given element for any conflicts with other cell values
    //currently only designed for use with updateCells(), but can be modified to return a bool if needed (just return flag)
    public void checkElement(ElementButton cell) {
        boolean flag = false;
        int r = cell.getIndex1();
        int c = cell.getIndex2();
        int given = cell.getValue();

        Log.i("given value:", Integer.toString(given));
        Log.i("given row index:", Integer.toString(r));
        Log.i("given col index:", Integer.toString(c));

        //******ROW AND COLUMN CHECKING******
        //check column values, set ALL conflicting values to be wrong
        for(int i = 0; i < 9; i++) {
            //dont check value with itself, will always fail
            if(c != i) {
                //duplicate is found, trip flag and set the element as wrong
                if (mSudokuBoard[r][i].getValue() == given) { //this statement is iterating over COLUMN (horizontal) values in row r from 0 to 8
                    Log.i("f", "checked at (" + r + "," + i + ") " + ", given value " + given + " matched value of " + mSudokuBoard[r][i].getValue());
                    flag = true;
                    mSudokuBoard[r][i].setWrong(true);
                }
            }
        }
        //check row values, set ALL conflicting values to be wrong
        for(int j = 0; j < 9; j++) {
            //dont check value with itself, will always fail
            if(r != j) {
                //duplicate is found, trip flag and set the element as wrong
                if (mSudokuBoard[j][c].getValue() == given) { //this statement is iterating over ROW (vertical) values in column c from 0 to 8
                    Log.i("f", "checked at (" + j + "," + c + ") " + ", given value " + given + " matched value of " + mSudokuBoard[j][c].getValue());
                    flag = true;
                    mSudokuBoard[j][c].setWrong(true);
                }
            }
        }
        //******BOX CHECKING******
        //calculate box coords relative to other boxes (ie: coordinates are 3x3 not 9x9 for an 81 cell board)
        //these values are used to find the top left corner cell of the box, so that iteration can begin akin to reading left to right
        int boxRow = (int)Math.floor(r / 3.0); // either 0 1 or 2
        int boxCol = (int)Math.floor(c / 3.0); // either 0 1 or 2
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++)
                //dont check value with itself, will always fail
                if(!(r == boxRow * 3 + i && c == boxCol * 3 + j)) {
                    if (mSudokuBoard[boxRow * 3 + i][boxCol * 3 + j].getValue() == given) {
                        flag = true;
                        cell.setWrong(true);
                    }
                }
        }
        //if an error is detected, value in this box is wrong
        //otherwise, value does not cause an error
        cell.setWrong(flag);
    }
    private class ElementButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            //Save the calling object as to a variable for easier to understand use.
            ElementButton buttonPressed = (ElementButton) view;

            //Only allow unlocked cells to be changed (givens cannot be changed)
            if (!buttonPressed.isLocked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                buttonPressed.setTextColor(view.getResources().getColor(R.color.user_input_text));
                if (manual) {
                    builder.setTitle("Enter Word:");
                    EditText input = new EditText(view.getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    builder.setPositiveButton("OK", (dialog, which) -> {
                        String userInput = input.getText().toString();
                        //Check if userInput is in the hashmap
                        boolean validUserInput = false;

                        for (int i = 1; i <= 9; i++) {

                            //converting all words (both dictionary and user input) to lowercase makes case sensitivity irrelevant
                            //may cause a bug when accents are included as not sure how exactly toLowerCase() works with them
                            //translationDirection = true -> english to spanish
                            if (translationDirection) {
                                if (Objects.equals(spanish[i-1].toLowerCase(), userInput.toLowerCase())) {
                                    validUserInput = true;
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setValue(i);
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setEnglish(english[i-1]);
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setTranslation(spanish[i-1]);
                                    break;
                                }
                            }
                            else {
                                if (Objects.equals(english[i-1].toLowerCase(), userInput.toLowerCase())) {
                                    validUserInput = true;
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setValue(i);
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setEnglish(spanish[i-1]);
                                    mSudokuBoard[buttonPressed.index1][buttonPressed.index2].setTranslation(english[i-1]);
                                    break;
                                }
                            }
                        }
                        if ((mSudokuBoard[buttonPressed.index1][buttonPressed.index2].mValue == generatedBoard.mAnswerBoard[buttonPressed.index1][buttonPressed.index2]) && validUserInput)
                        {
                            //Update the cell with the userInput text
                            buttonPressed.setText(userInput);
                            //update cells and check for completion
                            updateCells();
                            checkIfCompleted(view);
                        }
                        //If the answer is incorrect
                        else {
                            //Case 1: Invalid input, Toast message displayed button unchanged.
                            if (!validUserInput) {
                                Toast t = Toast.makeText(context, "Invalid Input", Toast.LENGTH_LONG);
                                t.show();
                            }
                            //Case 2: Valid input (in vocab) but incorrect word
                            else {
                                //Update the cell with the userInput text
                                buttonPressed.setText(userInput);
                                //update cells
                                updateCells();
                            }
                        }

                    });
                    builder.setNeutralButton("Clear Answer", (dialog, which) -> {
                        //reverts cell to default state, then updates cells
                        buttonPressed.setText("");
                        buttonPressed.setValue(0);
                        setCellDesign(buttonPressed);
                        updateCells();
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                    builder.show();
                }
                //Below if statement uses buttons to assist user with word input
                else
                {
                    builder.setNeutralButton("Clear Answer", (dialog, which) -> {
                        buttonPressed.setText("");
                        buttonPressed.setValue(0);
                        updateCells();
                        setCellDesign(buttonPressed);
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                    //creating the dialog box this way allows alert.cancel() to be called, otherwise user needs to manually close the dialog every time
                    AlertDialog alert = builder.create();
                    alert.setTitle("Enter Word:");
                    Context dialogContext = builder.getContext();
                    TableLayout input = new TableLayout(dialogContext);


                    //builds grid of vocab words here by iterating over wordIndex
                    for (int rows = 0; rows < 3; rows++)
                    {
                        TableRow tableRow = new TableRow(dialogContext);
                        for (int cols = 0; cols < 3; cols++)
                        {
                            //These buttons represents the 1 of 9 buttons user can choose words from
                            AssistedInputButton wordButton = new AssistedInputButton(dialogContext);

                            //If true, the user should be given the choice of words in spanish
                            if(translationDirection)
                                wordButton.setText(spanish[(rows*3) + cols]);
                            else
                                wordButton.setText(english[(rows*3) + cols]);

                            //Button holds its index of where it is in 3x3 grid
                            wordButton.setIndex((rows*3) + cols);
                            //Button stores a reference to the AlertDialog so close it in onclicklistener
                            wordButton.setAssociatedAlertDialog(alert);
                            //Stores the ElementButton that called it when it was pressed
                            wordButton.setCallingButton(buttonPressed);

                            //Button Functionality
                            wordButton.setOnClickListener(new AssistedInputButtonListener());

                            tableRow.addView(wordButton);
                        }
                        input.addView(tableRow);
                    }

                    alert.setView(input);
                    alert.show();
                }
            }
        }
    }

    //Class used for OnClickListener for userInput buttons when manual input is turned off
    private class AssistedInputButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AssistedInputButton wordButtonPressed = (AssistedInputButton) view;
            Log.i("Word Pressed:", wordButtonPressed.getText().toString());
            Log.i("Correct Word:", mSudokuAnswerBoard[wordButtonPressed.callingButton.getIndex1()][wordButtonPressed.callingButton.getIndex2()].getTranslation(translationDirection));

            //the +1 here converts the index from the wordIndex into a value that compares with elementButton values properly
            wordButtonPressed.callingButton.setValue(wordButtonPressed.index + 1);
            wordButtonPressed.callingButton.setText(wordButtonPressed.getText().toString());
            //update cells and check completion
            updateCells();
            checkIfCompleted(view);

            //closes dialog box after a button is pressed
            wordButtonPressed.AssociatedAlertDialog.cancel();
        }

    }

}


