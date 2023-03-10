package com.example.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.io.Serializable;
import java.util.Arrays;

public class SudokuPage extends AppCompatActivity implements Serializable {

    private Sudoku myGame;
    private Bundle mBundle;

    public static Intent makeIntent(Context context) {
        return new Intent(context, SudokuPage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_page);
        mBundle = savedInstanceState;
        //If savedInstanceState == null, this is the first time launching the game
        //If savedInstanceState != null, the screen has been rotated during gameplay
        if (savedInstanceState != null)
        {
            myGame = (Sudoku) savedInstanceState.getSerializable("CURRENT_BOARD");
            //Resume timer here

            //Access timer in activity
            Chronometer cmTimer = findViewById(R.id.gameTimerText);
            //Get the time saved at the moment the screen was rotated, key: "timer"
            long elapsedTime = savedInstanceState.getLong("timer");
            //Set timer to start at elapsed time then start to resume it
            cmTimer.setBase(SystemClock.elapsedRealtime() + elapsedTime);
            cmTimer.start();
        }
        else
        {
            //Creates a timer on the game page
            Chronometer cmTimer = findViewById(R.id.gameTimerText);
            //changed the sudoku constructor to pass the timer so it could be assigned as a member variable, not sure if there's a cleaner way to implement this
            myGame = new Sudoku(this, cmTimer);
            myGame.startTimer(cmTimer);
        }

        TableLayout tableLayout = findViewById(R.id.sudoku_table);
        for (int rows = 0; rows < Sudoku.getGridSize(); rows++) {
            TableRow tableRow = new TableRow(this);
            for (int cols = 0; cols < Sudoku.getGridSize(); cols++)
            {
                //This if statement is used to remove child from parent
                ElementButton element = myGame.getElement(rows, cols);
                // remove existing parent of the view before adding it to the table row
                if (element.getParent() != null) {
                    ((ViewGroup) element.getParent()).removeView(element);
                }
                tableRow.addView(myGame.getElement(rows, cols));
                myGame.setCellDesign(myGame.getElement(rows, cols));

            }
            //This adds the created row into the table
            tableLayout.addView(tableRow);
            //Update error cells, putting this specifically here fixes cell colors/errors not carrying over on screen rotate
            myGame.updateCells();
        }

        //Solve button Functionality
        Button solveButton = findViewById(R.id.solveButton);
        solveButton.setOnClickListener(view -> {
            myGame.solveGrid();
            //Re draw the grid to set it with the new values
            myGame.updateCells();
            myGame.checkIfCompleted(view);
        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        // Save the values you need into "outState"
        super.onSaveInstanceState(savedInstanceState);
        //Saves the Sudoku Object myGame to bundle


        //THIS LINE

        savedInstanceState.putSerializable("CURRENT_BOARD", myGame);


        //Save timer to bundle
        Chronometer cmTimer = findViewById(R.id.gameTimerText);
        savedInstanceState.putLong("timer", cmTimer.getBase() - SystemClock.elapsedRealtime());
    }
}
