package com.example.sudokuapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;


public class Sudoku extends AppCompatActivity implements Serializable
{
    private static WordBank bank = new WordBank();
    private static int wordBank;
    private static int GRID_SIZE;
    private static ElementButton[][] mSudokuBoard;
    private static Pair<Integer, Integer> boxSize;
    private final ElementButton[][] mSudokuAnswerBoard;
    private final Context context;
    private transient final Chronometer mTimer;
    private static int difficulty;

    private static boolean manual;
    private static boolean translationDirection = true;
    private static int mRemainingCells;
    private static long minutes, seconds;
    public LinkedList<ElementButton> userInputButtons;

    //setters for game settings
    public static void setDifficulty(int d) {difficulty = d;}
    public static void setWordBank(int index) {wordBank = index;}
    public static void setInputMode(boolean m) {manual = m;}
    public static void setTranslationDirection(boolean t) {translationDirection = t;}
    public static void decreaseRemainingCells() {--mRemainingCells;}
    public static void increaseRemainingCells() {++mRemainingCells;}
    public static void setGRID_SIZE(int size) {
        GRID_SIZE = size;

        Pair<Integer, Integer> temp = new Pair<>(9, 9);
        if (Math.sqrt(size) % 1 == 0) {
            temp = new Pair<>((int) Math.sqrt(size), (int) Math.sqrt(size));
        } else if (size == 12) {
            temp = new Pair<>(3,4);
        } else if (size == 6) {
            temp = new Pair<>(2,3);
        }
        boxSize = temp;
    }
    public static void setRemainingCells(int remainingCells) {mRemainingCells = remainingCells;}

    public static Pair<Integer, Integer> getBoxSize() {return boxSize;}
    public static ElementButton getElement(int rows, int cols) {return mSudokuBoard[rows][cols];}
    public static WordBank getBank() {return bank;}
    public static int getWordBank() {return wordBank;}
    public static int getDifficulty() {return difficulty;}
    public static boolean getInputMode() {return manual;}
    public static boolean getTranslationDirection() {return translationDirection;}
    //returns time in H:MM:SS or MM:SS format
    public static String getElapsedTime() {
        String sec;
        int hours = 0;
        if(minutes >= 60) {
            hours = (int) Math.floor(minutes / 60.0);
            minutes -= (hours * 60L);
        }
        //if seconds less than 10, add a 0 before for proper display
        if(seconds < 10) {sec = "0" + seconds;}
        //otherwise just convert to string
        else {sec = seconds+""; }
        //check if an hour has passed, otherwise don't display hours
        if(minutes < 60) {return minutes + ":" + sec;}
        else {return hours + ":" + minutes + ":" + sec;}
    }
    public static int getRemainingCells() {
        return mRemainingCells;
    }
    public static int getGridSize() {
        return GRID_SIZE;
    }
    public Chronometer getTimer() {
        return mTimer;
    }

    Sudoku(Context THIS, Chronometer t){
        //Default GRID_SIZE
        setGRID_SIZE(getGridSize());
        if(getGridSize() == 0)
        {
            GRID_SIZE = 9;
            setGRID_SIZE(9);
        }
        userInputButtons = new LinkedList<>();
        context = THIS;
        mTimer = t;

        mTimer.start();
        mTimer.setOnChronometerTickListener(chronometer -> {
            //convert base time to readable minutes and seconds
            minutes = ((SystemClock.elapsedRealtime() - t.getBase())/1000) / 60;
            seconds = ((SystemClock.elapsedRealtime() - t.getBase())/1000) % 60;
        });
        //creates a word bank object that contains english and spanish arrays of the word bank
        bank.generateWordBank(GRID_SIZE, difficulty, wordBank, context);

        //Builds a valid integer board
        //GenerateBoard class has member 2d arrays:
        //int[][] mGeneratedBoard; This board is the partially filled array of integers
        //int[][] mAnswerBoard;    This board is the completed board used to reference for answer checking
        GenerateBoard generatedBoard = new GenerateBoard(getBoxSize().first, getBoxSize().second, getDifficulty());
        generatedBoard.createBoard();
        //Initialize number of unfilled cells
        mRemainingCells = generatedBoard.getEmptyCells();
        Log.i("Status:","Board Generated");
        //mSudokuBoard is the play board of ElementButtons (what the player interacts with)
        mSudokuBoard = new ElementButton[GRID_SIZE][GRID_SIZE];
        //mSudokuAnswerBoard is a complete board of ElementButtons used to reference for input checking
        mSudokuAnswerBoard = new ElementButton[GRID_SIZE][GRID_SIZE];

        //Copy mGeneratedBoard into mSudokuBoard, skip empty cells which == 0
        for(int rows = 0; rows < GRID_SIZE; rows++)
        {
            for(int cols = 0; cols < GRID_SIZE; cols++)
            {
                //This initializes ElementButtons that correspond to given cells
                if(GenerateBoard.mGeneratedBoard[rows][cols] != 0)
                {

                    mSudokuBoard[rows][cols] =
                            new ElementButton(
                                GenerateBoard.mGeneratedBoard[rows][cols],
                                bank.getEnglish()[GenerateBoard.mGeneratedBoard[rows][cols] - 1],
                                bank.getSpanish()[GenerateBoard.mGeneratedBoard[rows][cols] - 1],
                                context,
                                true,
                                rows,
                                cols
                            );
                }
                //This initializes ElementButtons that correspond to empty Cells
                else
                {
                    mSudokuBoard[rows][cols] = new ElementButton(0, "", "", context, false, rows, cols);
                    //Set listener only for buttons that can change
                    mSudokuBoard[rows][cols].setOnClickListener(new ElementButtonListener());
                }
                //This initializes the answer board of ElementButtons used to be matched with mSudokuBoard for error checking
                mSudokuAnswerBoard[rows][cols] =
                        new ElementButton(
                                GenerateBoard.mAnswerBoard[rows][cols],
                                bank.getEnglish()[GenerateBoard.mAnswerBoard[rows][cols] - 1],
                                bank.getSpanish()[GenerateBoard.mAnswerBoard[rows][cols] - 1],
                                context,
                                true,
                                rows,
                                cols
                        );


            }
        }
    }
    public void checkIfCompleted(View view) {

        if(getRemainingCells() == 0) {
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

    //pairs each cell to a proper border drawable to make the board look like sudoku
    public void setCellDesign(ElementButton cell) {
        int rowCoordinate = cell.getIndex1();
        int colCoordinate = cell.getIndex2();

        if((colCoordinate + 1) % boxSize.first == 0) {
            if ((rowCoordinate + 1) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom_right));
            }
            else if((rowCoordinate) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_top_right));
            }
            else {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_right));
            }
        }
        else if((colCoordinate) % boxSize.first == 0) {
            if ((rowCoordinate + 1) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom_left));
            }
            else if((rowCoordinate) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_top_left));
            }
            else {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_left));
            }
        }
        else {
            if ((rowCoordinate + 1) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom));
            }
            else if((rowCoordinate) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_top));
            }
            else {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border));
            }
        }
    }



    private class ElementButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Log.i("Remaining Cells", String.valueOf(getRemainingCells()));
            //Save the calling object as to a variable for easier to understand use.
            ElementButton buttonPressed = (ElementButton) view;
            //Only allow unlocked cells to be changed (givens cannot be changed)
            if (buttonPressed.isClickable()) {

                //**************************************//
                //          MANUAL INPUT                //
                //**************************************//

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if (manual) {
                    builder.setTitle("Enter Word:");
                    //Set up the input type (manual text input)
                    EditText input = new EditText(view.getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    //Display popup
                    builder.setView(input);
                    //When the user hits ok
                    builder.setPositiveButton("OK", (dialog, which) ->
                    {
                        //This index tracks the position userInput is in the english or spanish array
                        int index = 0;
                        //Get the string currently in the EditText object
                        String userInput = input.getText().toString();

                        //If nothing is entered, close and do nothing
                        if(userInput.length() != 0)
                        {
                            //Boolean to store whether or not the userInput string is part of the words in play
                            boolean validUserInput = false;

                            //Check if userInput string is part of the words in play
                            for (int i = 0; i < GRID_SIZE; i++)
                            {
                                //User input in Spanish
                                if (translationDirection) {
                                    //Check to see if the userInput is a valid word in play
                                    if (Objects.equals(bank.getSpanish()[i].toLowerCase(), userInput.toLowerCase())) {
                                        index = i;
                                        validUserInput = true;
                                        break;
                                    }
                                }
                                //User input in English
                                else {
                                    if (Objects.equals(bank.getEnglish()[i].toLowerCase(), userInput.toLowerCase())) {
                                        index = i;
                                        validUserInput = true;
                                        break;
                                    }
                                }
                            }
                            // If userInput string is a valid word in play, and is placed in a valid position by sudoku rules
                            if (SudokuFunctionality.validSpot(buttonPressed, userInput) && validUserInput) {
                                buttonPressed.setValue(index + 1);
                                buttonPressed.setText(userInput);
                                buttonPressed.setTextColor(Color.rgb(0, 138, 216));
                                userInputButtons.add(buttonPressed);
                                if(buttonPressed.isWrong)
                                {
                                    decreaseRemainingCells();
                                }
                                buttonPressed.isWrong = false;
                                //Check if the game is complete.
                                checkIfCompleted(view);
                            }
                            // If userInput string is a valid word in play, but is not placed in a valid position by sudoku rules
                            else if (!SudokuFunctionality.validSpot(buttonPressed, userInput) && validUserInput) {
                                buttonPressed.setValue(index + 1);
                                buttonPressed.setText(userInput);
                                buttonPressed.setTextColor(Color.rgb(255, 114, 118));
                                if(!buttonPressed.isWrong)
                                {
                                    increaseRemainingCells();
                                }
                                buttonPressed.isWrong = true;
                                userInputButtons.add(buttonPressed);
                            }
                            // If userInput string is not a valid word in play
                            else {
                                Toast t = Toast.makeText(context, "You cant place that there!", Toast.LENGTH_LONG);
                                t.show();
                            }
                            System.out.println("Get value: "+ buttonPressed.getValue());
                        }
                    });
                    //Set up popup clear button, used to remove value in current cell
                    builder.setNeutralButton("Clear Answer", (dialog, which) -> {
                        //reverts cell to default state
                        buttonPressed.setText("");
                        buttonPressed.setValue(0);
                        userInputButtons.remove(buttonPressed);
                        //rgb value doesn't matter, no text is visible
                        //If the cleared cell was in a valid position, set it to currently false (isWrong = true) and increase the number of remaining cells
                        if(!buttonPressed.isWrong)
                        {
                            increaseRemainingCells();
                            buttonPressed.isWrong = true;
                        }
                        setCellDesign(buttonPressed);
                    });
                    //Set up popup cancel button
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                    //Display the popup
                    builder.show();
                }
                //Below uses buttons to assist user with word input
                else
                {
                    //**************************************//
                    //          ASSISTED INPUT              //
                    //**************************************//

                    // Create the dialog box this way allows alert.cancel() to be called, otherwise user needs to manually close the dialog every time
                    AlertDialog alert = builder.create();
                    alert.setTitle("Enter Word:");
                    Context dialogContext = builder.getContext();
                    //Set the clear answer button in the popup
                    builder.setNeutralButton("Clear Answer", (dialog, which) -> {
                        buttonPressed.setText("");
                        buttonPressed.setValue(0);
                        userInputButtons.remove(buttonPressed);
                        buttonPressed.setBackgroundResource(android.R.drawable.btn_default);
                        if(!buttonPressed.isWrong)
                        {
                            increaseRemainingCells();
                            buttonPressed.isWrong = true;
                        }
                        setCellDesign(buttonPressed);
                    });
                    // Exit the popup with no changes made
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                    // Builds the pre defined inputs the user may choose from


                    TableLayout input = new TableLayout(dialogContext);
                    input.setContentDescription("assistDialogLayout");

                    //Set tag counter for assistButtons
                    int assistButtonTagCounter = 0;
                    for (int rows = 0; rows < boxSize.second; rows++)
                    {
                        TableRow tableRow = new TableRow(dialogContext);
                        //Set tag for each table row to be used in testing
                        tableRow.setTag("assistTableRowTag" + (rows));

                        for (int cols = 0; cols < boxSize.first; cols++)
                        {
                            //These buttons represents the 1 of 9 buttons user can choose words from
                            AssistedInputButton wordButton = new AssistedInputButton(dialogContext);
                            //Set tag each AssistedInputButton for testing
                            wordButton.setTag("assistButtonTag" + (assistButtonTagCounter));
                            assistButtonTagCounter++;

                            //If true, the user should be given the choice of words in spanish
                            if(translationDirection)
                                wordButton.setText(bank.getSpanish()[(rows* boxSize.first) + cols]);
                            else
                                wordButton.setText(bank.getEnglish()[(rows* boxSize.first) + cols]);

                            //Button holds its index of where it is in subgrid
                            wordButton.setIndex(rows*boxSize.first + cols);
                            //Button stores a reference to the AlertDialog so it can close it in onclicklistener
                            wordButton.setAssociatedAlertDialog(alert);
                            //Stores a reference to the ElementButton that called it when it was pressed
                            wordButton.setCallingButton(buttonPressed);
                            //Button Functionality
                            wordButton.setOnClickListener(new AssistedInputButtonListener());
                            tableRow.setId(View.generateViewId());
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

            if(SudokuFunctionality.validSpot(wordButtonPressed.callingButton, wordButtonPressed.getText().toString()))
            {
                wordButtonPressed.callingButton.setValue(wordButtonPressed.index + 1);
                wordButtonPressed.callingButton.setText(wordButtonPressed.getText().toString());
                wordButtonPressed.callingButton.setTextColor(Color.rgb(	0,138,216));
                userInputButtons.add(wordButtonPressed.callingButton);
                if(wordButtonPressed.callingButton.isWrong)
                {
                    decreaseRemainingCells();
                }
                wordButtonPressed.callingButton.isWrong = false;
            }
            else
            {
                wordButtonPressed.callingButton.setValue(wordButtonPressed.index + 1);
                wordButtonPressed.callingButton.setText(wordButtonPressed.getText().toString());
                wordButtonPressed.callingButton.setTextColor(Color.rgb(255,114,118));
                if(!wordButtonPressed.callingButton.isWrong)
                {
                    increaseRemainingCells();
                }
                wordButtonPressed.callingButton.isWrong = true;

                userInputButtons.add(wordButtonPressed.callingButton);
            }
            //update cells and check completion
            checkIfCompleted(view);
            //closes dialog box after a button is pressed
            wordButtonPressed.AssociatedAlertDialog.cancel();
        }

    }

}


