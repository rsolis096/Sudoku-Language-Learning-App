package com.example.sudokuapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.sax.Element;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;

public class SudokuPage extends AppCompatActivity implements Serializable {

    private Sudoku myGame;
    public static ElementButton selectedButton = null;

    public static Intent makeIntent(Context context) {
        return new Intent(context, SudokuPage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_page);

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
        ScrollView sView = (ScrollView) tableLayout.getParent();
        //sView.setScaleX(0.5f);
        //sView.setScaleX(0.5f);
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
                myGame.setCellDesign(Sudoku.getElement(rows, cols));

            }
            //This adds the created row into the table
            tableLayout.addView(tableRow);
        }
        //tableLayout.setShrinkAllColumns(true);

        //Sets colours for validButtons (specifically for screen rotation)
        for (ElementButton btn : myGame.userInputButtons) {
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
            myGame.checkIfCompleted(view);
            myGame.getTimer().stop();
        });

        if (Sudoku.manual) {
            //setupManualInput();
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
            DataModel.setCategoryIndex(0);
            DataModel.setAudioMode(false);
            Sudoku.setInputMode(false);
            Sudoku.setTranslationDirection(true);
            Sudoku.setGRID_SIZE(9);
            WordBankPage.resetWordBank();
            Intent intent = new Intent(context, MainMenu.class);
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

    public void setupAssistedInput() {
        //**************************************//
        //          ASSISTED INPUT              //
        //**************************************//

        TableLayout input = findViewById(R.id.lowerHalfTableLayout);
        input.setContentDescription("assistDialogLayout");
        System.out.println("GRID SIZE "+Sudoku.getGridSize());

        //Set tag counter for assistButtons
        int assistButtonTagCounter = 0;

        //Make sure the buttons are on the screen
        input.setStretchAllColumns(true);
        input.setShrinkAllColumns(true);

        for (int rows = 0; rows < Sudoku.getBoxSize().second; rows++) {
            TableRow tableRow = new TableRow(this);
            for (int cols = 0; cols < Sudoku.getBoxSize().first; cols++) {
                //These buttons represents the 1 of 9 buttons user can choose words from
                AssistedInputButton wordButton = new AssistedInputButton(this);
                //Set tag each AssistedInputButton for testing
                wordButton.setTag("assistButtonTag" + (assistButtonTagCounter));
                assistButtonTagCounter++;

                //If true, the user should be given the choice of words in spanish
                if (Sudoku.getTranslationDirection())
                    wordButton.setText(Sudoku.bank.getSpanish()[(rows * Sudoku.getBoxSize().first) + cols]);
                else
                    wordButton.setText(Sudoku.bank.getEnglish()[(rows * Sudoku.getBoxSize().first) + cols]);

                wordButton.setOnClickListener(new WordButtonListener());

                tableRow.setId(View.generateViewId());
                tableRow.addView(wordButton);
            }
            input.addView(tableRow);
        }
    }

    private static class WordButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            AssistedInputButton wordButtonPressed = (AssistedInputButton) view;

            selectedButton.setText(wordButtonPressed.getText());

            //Verifies correct input
            if(SudokuFunctionality.validSpot(selectedButton, wordButtonPressed.getText().toString()))
            {
                selectedButton.setValue(wordButtonPressed.index + 1);
                selectedButton.setText(wordButtonPressed.getText().toString());
                selectedButton.setTextColor(Color.rgb(	0,138,216));
                Sudoku.userInputButtons.add(wordButtonPressed.callingButton);
                if(selectedButton.isWrong)
                {
                    Sudoku.decreaseRemainingCells();
                }
                selectedButton.isWrong = false;
            }
            else
            {
                selectedButton.setValue(wordButtonPressed.index + 1);
                selectedButton.setText(wordButtonPressed.getText().toString());
                selectedButton.setTextColor(Color.rgb(255,114,118));
                if(!selectedButton.isWrong)
                {
                    Sudoku.increaseRemainingCells();
                }
                selectedButton.isWrong = true;

                Sudoku.userInputButtons.add(selectedButton);
            }
            //update cells and check completion
            Sudoku.checkIfCompleted(view);

        }
    }
}
