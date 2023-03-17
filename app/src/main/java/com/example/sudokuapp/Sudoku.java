package com.example.sudokuapp;


import android.app.AlertDialog;
import android.content.Context;
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
import java.util.LinkedList;
import java.util.Objects;


public class Sudoku extends AppCompatActivity implements Serializable
{
    private static int GRID_SIZE;
    private static ElementButton[][] mSudokuBoard;
    private static int boxSizeX = 3;
    private static int boxSizeY = 3;
    private final ElementButton[][] mSudokuAnswerBoard;
    private final Context context;
    private transient final Chronometer mTimer;
    private static int difficulty;
    public static String[] english;
    public static String[] spanish;
    private static int wordBank;
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
    public void decreaseRemainingCells() {--mRemainingCells;}
    public void increaseRemainingCells() {++mRemainingCells;}
    public static void setGRID_SIZE(int size) {
        GRID_SIZE = size;
        if(Math.sqrt(size) % 1 == 0) {
            boxSizeX = boxSizeY = (int) Math.sqrt(size);
        }
        else if(size == 12) {
            boxSizeX = 3;
            boxSizeY = 4;
        }
        else if(size == 6) {
            boxSizeX = 2;
            boxSizeY = 3;
        }

    }
    public static void setRemainingCells(int remainingCells) {mRemainingCells = remainingCells;}

    public static int getBoxSizeX() {return boxSizeX;}
    public static int getBoxSizeY() {return boxSizeY;}

    //getters for game settings
    public static ElementButton getElement(int rows, int cols) {return mSudokuBoard[rows][cols];}
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
    public int getRemainingCells() {
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
        if(getGridSize() == 0)
        {
            GRID_SIZE = 9;
        }
        userInputButtons = new LinkedList<>();
        context = THIS;
        mTimer = t;
        //Temporarily hardcoded, another solution should best be found
        int[] categoryArrays = {
                        R.array.numbers,//0
                        R.array.greetings_easy,//1
                        R.array.greetings_medium,//2
                        R.array.greetings_hard,//3
                        R.array.directions_easy,//4
                        R.array.directions_medium,//5
                        R.array.directions_hard,//6
                        R.array.family_easy,//7
                        R.array.family_medium,//7
                        R.array.family_hard,//8
                        R.array.food_drinks_easy,//10
                        R.array.food_drinks_medium,//9
                        R.array.food_drinks_hard//10
                };


        //Given a category from categoryArrays, generate a puzzle using that category.
        int selectedArrayId = categoryArrays[getWordBank() + getDifficulty()];
        if(getWordBank() == 0)
            selectedArrayId = categoryArrays[0];
        String[] inputString = context.getResources().getStringArray(selectedArrayId);

        //There is a one to one correspondence between english and spanish. the string at index 0 in spanish is the translation to the string at index 0 in english
        english = new String[GRID_SIZE];
        spanish = new String[GRID_SIZE];
        //From word pair, words are split by commas. Separate and place in corresponding array. Eg. inputString[0] = "english,spanish"
        for(int i = 0; i < GRID_SIZE; i++)
        {
            String [] wordPair = inputString[i].split(",");
            english[i] = wordPair[0];
            spanish[i] = wordPair[1];
        }

        //english  = new String[] {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty-one", "Twenty-two", "Twenty-three", "Twenty-four", "Twenty-five"};
        //spanish = new String[]{"Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez", "Once", "Doce", "Trece", "Catorce", "Quince", "Dieciséis", "Diecisiete", "Dieciocho", "Diecinueve", "Veinte", "Veintiuno", "Veintidós", "Veintitrés", "Veinticuatro", "Veinticinco"};

        //Builds a valid integer board
        //GenerateBoard class has member 2d arrays:
        //int[][] mGeneratedBoard; This board is the partially filled array of integers
        //int[][] mAnswerBoard;    This board is the completed board used to reference for answer checking
        GenerateBoard generatedBoard = new GenerateBoard(GRID_SIZE, GRID_SIZE, getDifficulty());
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
                                english[GenerateBoard.mGeneratedBoard[rows][cols] - 1],
                                spanish[GenerateBoard.mGeneratedBoard[rows][cols] - 1],
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
                                english[GenerateBoard.mAnswerBoard[rows][cols] - 1],
                                spanish[GenerateBoard.mAnswerBoard[rows][cols] - 1],
                                context,
                                true,
                                rows,
                                cols
                        );


            }
        }
    }

    public void startTimer(Chronometer t) {
        t.start();
        t.setOnChronometerTickListener(chronometer -> {
            //convert base time to readable minutes and seconds
            minutes = ((SystemClock.elapsedRealtime() - t.getBase())/1000) / 60;
            seconds = ((SystemClock.elapsedRealtime() - t.getBase())/1000) % 60;
        });
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

    //looks complicated, just pairs each cell to a proper border drawable to make the board look like sudoku
    public void setCellDesign(ElementButton cell) {
        int rowCoordinate = cell.getIndex1();
        int colCoordinate = cell.getIndex2();

        if((colCoordinate + 1) % boxSizeX == 0) {
            if ((rowCoordinate + 1) % boxSizeY == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom_right));
            }
            else if((rowCoordinate) % boxSizeY == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_top_right));
            }
            else {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_right));
            }
        }
        else if((colCoordinate) % boxSizeX == 0) {
            if ((rowCoordinate + 1) % boxSizeY == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom_left));
            }
            else if((rowCoordinate) % boxSizeY == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_top_left));
            }
            else {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_left));
            }
        }
        else {
            if ((rowCoordinate + 1) % boxSizeY == 0) {
                cell.setBackground(AppCompatResources.getDrawable(context, R.drawable.border_thick_bottom));
            }
            else if((rowCoordinate) % boxSizeY == 0) {
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
                                    if (Objects.equals(spanish[i].toLowerCase(), userInput.toLowerCase())) {
                                        index = i;
                                        validUserInput = true;
                                        break;
                                    }
                                }
                                //User input in English
                                else {
                                    if (Objects.equals(english[i].toLowerCase(), userInput.toLowerCase())) {
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
                    for (int rows = 0; rows < boxSizeY; rows++)
                    {
                        TableRow tableRow = new TableRow(dialogContext);
                        //Set tag for each table row to be used in testing
                        tableRow.setTag("assistTableRowTag" + (rows));

                        for (int cols = 0; cols < boxSizeX; cols++)
                        {
                            //These buttons represents the 1 of 9 buttons user can choose words from
                            AssistedInputButton wordButton = new AssistedInputButton(dialogContext);
                            //Set tag each AssistedInputButton for testing
                            wordButton.setTag("assistButtonTag" + (assistButtonTagCounter));
                            assistButtonTagCounter++;

                            //If true, the user should be given the choice of words in spanish
                            if(translationDirection)
                                wordButton.setText(spanish[(rows* boxSizeX) + cols]);
                            else
                                wordButton.setText(english[(rows* boxSizeX) + cols]);

                            //Button holds its index of where it is in subgrid
                            wordButton.setIndex(rows*boxSizeX + cols);
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


