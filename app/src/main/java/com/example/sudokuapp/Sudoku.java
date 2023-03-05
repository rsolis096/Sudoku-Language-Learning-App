package com.example.sudokuapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.TypedArrayUtils;

import org.w3c.dom.DOMStringList;
import org.w3c.dom.Element;

import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Objects;


public class Sudoku extends AppCompatActivity
{
    private final ElementButton[][] mSudokuBoard;
    private final ElementButton[][] mSudokuAnswerBoard;
    private final GenerateBoard generatedBoard;
    private final Context context;
    private static int difficulty;
    private final String[] english;
    private final String[] spanish;
    private static int wordBank;
    private static boolean manual;
    private static boolean translationDirection = true;
    private int mRemainingCells;
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

    Sudoku(Context THIS)
    {
        context = THIS;
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

        mRemainingCells = generatedBoard.getEmptyCells();

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
        t.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //convert base time to readable minutes and seconds
                minutes = ((SystemClock.elapsedRealtime() - t.getBase())/1000) / 60;
                seconds = ((SystemClock.elapsedRealtime() - t.getBase())/1000) % 60;
                //Log.i("time", getElapsedTime());

                if(mRemainingCells == 0) {
                    t.stop();
                }
            }
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
                    mSudokuBoard[i][j].setLock(true);
                }
            }
        }
        mRemainingCells = 0;
    }

    public void checkIfCompleted(View view) {
        if(mRemainingCells == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Game Finished!");
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(view.getContext(), ResultsScreen.class);
                    view.getContext().startActivity(intent);
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    Intent intent = new Intent(view.getContext(), ResultsScreen.class);
                    view.getContext().startActivity(intent);
                }
            });
            builder.show();
        }
    }

    //looks complicated, just pairs each cell to a proper border drawable to make the board look like sudoku
    public void setCellDesign(int rows, int cols, ElementButton cell, Context c) {
        if(cols == 0 || cols == 1 || cols == 4 || cols == 7 || cols == 8) {
            if(rows == 0 || rows == 1 || rows == 4 || rows == 7 || rows == 8) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border));
            }
            else if(rows == 2 || rows == 5) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border_thick_bottom));
            }
            else if(rows == 3 || rows == 6) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border_thick_top));
            }
        }
        else if(cols == 2 || cols == 5) {
            if (rows == 0 || rows == 1 || rows == 4 || rows == 7 || rows == 8) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border_thick_right));
            }
            else if(rows == 2 || rows == 5) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border_thick_bottom_right));
            }
            else if(rows == 3 || rows == 6) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border_thick_top_right));
            }
        }
        else if(cols == 3 || cols == 6) {
            if (rows == 0 || rows == 1 || rows == 4 || rows == 7 || rows == 8) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border_thick_left));
            }
            else if(rows == 2 || rows == 5) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border_thick_bottom_left));
            }
            else if(rows == 3 || rows == 6) {
                cell.setBackground(AppCompatResources.getDrawable(c, R.drawable.border_thick_top_left));
            }
        }
    }

    private class ElementButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            //Save the calling object as to a variable for easier to understand use.
            ElementButton buttonPressed = (ElementButton) view;

            //Only allow unlocked cells to be changed (givens cannot be changed)
            if (!buttonPressed.isLocked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                            //Green if spot is valid
                            buttonPressed.setBackgroundColor(Color.rgb(173, 223, 179));
                            //Lock the button, cannot be changed after correct input
                            buttonPressed.setLock(true);
                            mRemainingCells--;
                            checkIfCompleted(view);
                            //Update the cell with the userInput text
                            buttonPressed.setText(userInput);
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
                //Below if statement uses buttons to assist user with word input
                else
                {
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
            String userInput = wordButtonPressed.getText().toString();
            Log.i("Word Pressed:",userInput);
            Log.i("Correct Word:", mSudokuAnswerBoard[wordButtonPressed.callingButton.getIndex1()][wordButtonPressed.callingButton.getIndex2()].getTranslation(translationDirection));

            //If the answer is correct
            if (userInput.equals(mSudokuAnswerBoard[wordButtonPressed.callingButton.getIndex1()][wordButtonPressed.callingButton.getIndex2()].getTranslation(translationDirection))) {

                //Green if spot is valid
                wordButtonPressed.callingButton.setBackgroundColor(Color.rgb(173, 223, 179));
                //Lock the button, cannot be changed after correct input
                wordButtonPressed.callingButton.setLock(true);
                mRemainingCells--;

                checkIfCompleted(view);
                //Update the cell with the userInput text
                wordButtonPressed.callingButton.setText(userInput);
            }
            //If the answer is incorrect
            else {
                //Red if spot is invalid, button remains unlocked, text unchanged
                wordButtonPressed.callingButton.setBackgroundColor(Color.rgb(255, 114, 118));
                //Update the cell with the userInput text
                wordButtonPressed.callingButton.setText(userInput);
            }
            //closes dialog box after a button is pressed
            wordButtonPressed.AssociatedAlertDialog.cancel();
        }

    }
}


