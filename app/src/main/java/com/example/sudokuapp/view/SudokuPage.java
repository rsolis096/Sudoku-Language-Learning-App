package com.example.sudokuapp.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sudokuapp.model.AssistedInputButton;
import com.example.sudokuapp.model.ElementButton;
import com.example.sudokuapp.R;
import com.example.sudokuapp.model.Sound;
import com.example.sudokuapp.model.Sudoku;
import com.example.sudokuapp.model.SudokuFunctionality;
import com.example.sudokuapp.model.WordBank;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class SudokuPage extends AppCompatActivity implements Serializable {

    private Sudoku myGame;
    public static ElementButton selectedButton = null;

    public static Intent makeIntent(Context context) {
        return new Intent(context, SudokuPage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if(!Sudoku.manual)
        {
            setContentView(R.layout.activity_sudoku_page_assisted);
        }
        else{
            setContentView(R.layout.activity_sudoku_page_manual);
        }

        setupTutorialButton();
        setupClearButton();
        //If savedInstanceState == null, this is the first time launching the game
        //If savedInstanceState != null, the screen has been rotated during gameplay
        if (savedInstanceState != null) {
            myGame = (Sudoku) savedInstanceState.getSerializable("CURRENT_BOARD");
            //Access timer in activity
            Chronometer cmTimer = findViewById(R.id.gameTimerText);
            //Get the time saved at the moment the screen was rotated, key: "timer"
            long elapsedTime = savedInstanceState.getLong("timer");
            //Set timer to start at elapsed time then start to resume it
            cmTimer.setBase(SystemClock.elapsedRealtime() + elapsedTime);
            cmTimer.start();
        } else {
            //Creates a timer on the game page
            Chronometer cmTimer = findViewById(R.id.gameTimerText);
            //changed the sudoku constructor to pass the timer so it could be assigned as a member variable, not sure if there's a cleaner way to implement this
            try {
                myGame = new Sudoku(this, cmTimer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int elementButtonCounterForTag = 0;
        boolean foundEmptyCell = false;
        TableLayout tableLayout = findViewById(R.id.sudoku_table);

        for (int rows = 0; rows < Sudoku.getGridSize(); rows++) {
            TableRow tableRow = new TableRow(this);
            //Sets a content description for each tableRow to be used with UI testing
            tableRow.setContentDescription("tableRowTag" + rows);

            for (int cols = 0; cols < Sudoku.getGridSize(); cols++) {
                //This if statement is used to remove child from parent
                ElementButton element = Sudoku.getElement(rows, cols);

                //Remove existing parent of the view before adding it to the table row
                if (element.getParent() != null) {
                    ((ViewGroup) element.getParent()).removeView(element);
                }
                //Sets a tag for each elementButton for easier testing
                Sudoku.getElement(rows, cols).setContentDescription("elementButtonTag" + (elementButtonCounterForTag));
                ++elementButtonCounterForTag;

                //Set a tag for an empty cell for testing
                if (Sudoku.getElement(rows, cols).getValue() == 0 && !foundEmptyCell) {
                    Sudoku.getElement(rows, cols).setContentDescription("emptyCell");
                    foundEmptyCell = true;
                    elementButtonCounterForTag--;
                }
                //Display the tables
                tableRow.addView(Sudoku.getElement(rows, cols));
                Sudoku.setCellDesign(Sudoku.getElement(rows, cols));

            }
            //This adds the created row into the table
            tableLayout.addView(tableRow);
        }
        //tableLayout.setShrinkAllColumns(true);

        //Sets colours for validButtons (specifically for screen rotation)
        for (ElementButton btn : Sudoku.userInputButtons) {
            if (btn.isWrong) {
                btn.setTextColor(Color.rgb(255, 114, 118));
            } else {
                btn.setTextColor(Color.rgb(0, 138, 216));

            }
        }

        //Solve button Functionality
        Button solveButton = findViewById(R.id.solveButton);
        solveButton.setOnClickListener(view -> {
            SudokuFunctionality.solveGrid();
            SudokuFunctionality.checkIfCompleted(view);
            myGame.getTimer().stop();
        });

        if (Sudoku.manual) {
            setupManualInput();
        } else {
            setupAssistedInput();
        }

    }

    @Override
    public void onBackPressed() {
        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to quit?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //reset all options
            Sudoku.setDifficulty(0);
            WordBank.setCategoryIndex(0);
            Sound.setAudioMode(false);
            Sudoku.setInputMode(false);
            Sudoku.setTranslationDirection(true);
            Sudoku.setGRID_SIZE(9);
            WordBankPage.resetWordBank();
            Intent intent = new Intent(context, GameSetting.class);
            selectedButton = null;
            context.startActivity(intent);
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        // Save the values you need into "outState"
        super.onSaveInstanceState(savedInstanceState);
        //Saves the Sudoku Object myGame to bundle
        savedInstanceState.putSerializable("CURRENT_BOARD", myGame);
        //Save timer to bundle
        Chronometer cmTimer = findViewById(R.id.gameTimerText);
        savedInstanceState.putLong("timer", cmTimer.getBase() - SystemClock.elapsedRealtime());
    }

    //Sets up the assisted input functionality
    @SuppressLint("ResourceType")
    public void setupAssistedInput() {

        String[] selectionArray;
        //If true, the user should be given the choice of words in spanish
        if (Sudoku.getTranslationDirection())
            selectionArray = Arrays.copyOf(Sudoku.bank.getSpanish(), Sudoku.getGridSize());
        else
            selectionArray = Arrays.copyOf(Sudoku.bank.getEnglish(), Sudoku.getGridSize());

        TableLayout lowerHalfTableLayout = findViewById(R.id.lowerHalfTableLayout);
        lowerHalfTableLayout.setContentDescription("assistDialogLayout");

        //Set tag counter for assistButtons
        int assistButtonTagCounter = 0;

        //Make sure the buttons are on the screen
        lowerHalfTableLayout.setStretchAllColumns(true);
        lowerHalfTableLayout.setShrinkAllColumns(true);

        for (int rows = 0; rows < Sudoku.getBoxSize().second; rows++) {
            TableRow tableRow = new TableRow(this);
            for (int cols = 0; cols < Sudoku.getBoxSize().first; cols++) {
                //These buttons represents the 1 of 9 buttons user can choose words from
                AssistedInputButton chosenAssistInputButton = new AssistedInputButton(this);
                chosenAssistInputButton.setIndex(assistButtonTagCounter);
                //Set tag each AssistedInputButton for testing
                chosenAssistInputButton.setTag("assistButtonTag" + (assistButtonTagCounter));
                assistButtonTagCounter++;

                //Set text values of assist buttons
                chosenAssistInputButton.setText(selectionArray[(rows * Sudoku.getBoxSize().first) + cols]);

                /*
                if (Sudoku.getTranslationDirection())
                    chosenAssistInputButton.setText(Sudoku.bank.getSpanish()[(rows * Sudoku.getBoxSize().first) + cols]);
                else
                    chosenAssistInputButton.setText(Sudoku.bank.getEnglish()[(rows * Sudoku.getBoxSize().first) + cols]);
                */

                //Set assist button functionality
                chosenAssistInputButton.setOnClickListener(new AssistedInputButtonListener());

                tableRow.setId(View.generateViewId());
                tableRow.addView(chosenAssistInputButton);
            }
            lowerHalfTableLayout.addView(tableRow);
        }
    }

    //Sets up the manual input functionality
    public void setupManualInput()
    {
        Button manualInputConfirmBtn = findViewById(R.id.manualInputConfirmBtn);
        manualInputConfirmBtn.setOnClickListener(view -> {
            //Get Reference to edit text
            EditText manualInputEditText = findViewById(R.id.manualInputEditText);

            //This index tracks the position userInput is in the english or spanish array
            int index = 0;
            String userInput = manualInputEditText.getText().toString();

            //If nothing is entered, close and do nothing
            if(userInput.length() != 0 && selectedButton != null) {
                //Boolean to store whether or not the userInput string is part of the words in play
                boolean validUserInput = false;
                //Check if userInput string is part of the words in play
                for (int i = 0; i < Sudoku.getGridSize(); i++) {
                    //User input in Spanish
                    if (Sudoku.getTranslationDirection()) {
                        //Check to see if the userInput is a valid word in play
                        if (Objects.equals(Sudoku.bank.getSpanish()[i].toLowerCase(), userInput.toLowerCase())) {
                            index = i;
                            validUserInput = true;
                            break;
                        }
                    }
                    //User input in English
                    else {
                        if (Objects.equals(Sudoku.bank.getEnglish()[i].toLowerCase(), userInput.toLowerCase())) {
                            index = i;
                            validUserInput = true;
                            break;
                        }
                    }
                }

                // If userInput string is a valid word in play, and is placed in a valid position by sudoku rules
                if (SudokuFunctionality.validSpot(selectedButton, userInput) && validUserInput) {
                    selectedButton.setValue(index + 1);
                    selectedButton.setText(userInput);
                    selectedButton.setTextColor(Color.rgb(0, 138, 216));
                    Sudoku.userInputButtons.add(selectedButton);
                    if(selectedButton.isWrong)
                    {
                        Sudoku.decreaseRemainingCells();
                    }
                    selectedButton.isWrong = false;
                    //Check if the game is complete.
                    SudokuFunctionality.checkIfCompleted(view);
                }
                // If userInput string is a valid word in play, but is not placed in a valid position by sudoku rules
                else if (!SudokuFunctionality.validSpot(selectedButton, userInput) && validUserInput) {
                    selectedButton.setValue(index + 1);
                    selectedButton.setText(userInput);
                    selectedButton.setTextColor(Color.rgb(255, 114, 118));
                    if(!selectedButton.isWrong)
                    {
                        Sudoku.increaseRemainingCells();
                    }
                    selectedButton.isWrong = true;
                    Sudoku.userInputButtons.add(selectedButton);
                }
            }
            // If userInput string is not a valid word in play
            else {
                Toast t = Toast.makeText(view.getContext(), "You cant place that there!", Toast.LENGTH_LONG);
                t.show();
            }

        });
    }

    private void setupTutorialButton() {
        Button btnTut = findViewById(R.id.button);
        btnTut.setOnClickListener(view -> {
            Intent intent = Tutorialpage1.makeIntent(SudokuPage.this);
            startActivity(intent);
        });
    }

    private void setupClearButton()
    {
        Button clrBtn = findViewById(R.id.clearSelectedCell);
        clrBtn.setOnClickListener(view -> {
            if(selectedButton!=null)
            {
                selectedButton.setValue(0);
                selectedButton.setText(" ");
            }
        });
    }


    //When an pre given selection is pressed in assist mode
    private static class AssistedInputButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            AssistedInputButton assistButtonPressed = (AssistedInputButton) view;
            if(selectedButton != null) {
                assistButtonPressed.setCallingButton(selectedButton);
                selectedButton.setText(assistButtonPressed.getText());
                selectedButton.setValue(0);

                //Verifies correct input
                if (SudokuFunctionality.validSpot(selectedButton, assistButtonPressed.getText().toString())) {
                    selectedButton.setValue(assistButtonPressed.index + 1);
                    selectedButton.setText(assistButtonPressed.getText().toString());
                    selectedButton.setTextColor(Color.rgb(0, 138, 216));
                    Sudoku.userInputButtons.add(assistButtonPressed.getCallingButton());
                    if (selectedButton.isWrong) {
                        Sudoku.decreaseRemainingCells();
                    }
                    selectedButton.isWrong = false;
                } else {
                    selectedButton.setValue(assistButtonPressed.index + 1);
                    selectedButton.setText(assistButtonPressed.getText().toString());
                    selectedButton.setTextColor(Color.rgb(255, 114, 118));
                    if (!selectedButton.isWrong) {
                        Sudoku.increaseRemainingCells();
                    }
                    selectedButton.isWrong = true;

                    Sudoku.userInputButtons.add(selectedButton);
                }
                //update cells and check completion
                SudokuFunctionality.checkIfCompleted(view);
            }
        }
    }
}
