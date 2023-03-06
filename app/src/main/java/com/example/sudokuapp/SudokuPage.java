package com.example.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.Arrays;

public class SudokuPage extends AppCompatActivity {

    private static final String CURRENT_BOARD = "currentBoard";
    private Sudoku myGame;


    public static Intent makeIntent(Context context) {
        return new Intent(context, SudokuPage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_page);

        //If savedInstanceState == null, this is the first time launching the game
        //If savedInstanceState != null, the screen has been rotated during gameplay
        if (savedInstanceState != null)
        {
            myGame = (Sudoku) savedInstanceState.getSerializable("CURRENT_BOARD");
            //Resume timer here
        }
        else {
            //Initial call, before rotation
            myGame = new Sudoku(this);
            Log.i("Before Rotate: ", String.valueOf(myGame.mRemainingCells));

            //Creates a timer on the game page
            Chronometer cmTimer = findViewById(R.id.gameTimerText);
            myGame.startTimer(cmTimer);
        }

        TableLayout tableLayout = findViewById(R.id.sudoku_table);
        for (int rows = 0; rows < 9; rows++) {
            TableRow tableRow = new TableRow(this);
            for (int cols = 0; cols < 9; cols++)
            {
                //This if statement is used to remove child from parent
                ElementButton element = myGame.getElement(rows, cols);
                // remove existing parent of the view before adding it to the table row
                if (element.getParent() != null) {
                    ((ViewGroup) element.getParent()).removeView(element);
                }
                tableRow.addView(myGame.getElement(rows, cols));
                myGame.setCellDesign(rows, cols, myGame.getElement(rows, cols), this);

            }
            //This adds the created row into the table
            tableLayout.addView(tableRow);
        }

        //Solve button Functionality
        Button solveButton = findViewById(R.id.solveButton);
        solveButton.setOnClickListener(view -> {
            myGame.solveGrid();
            //Re draw the grid to set it with the new values
            myGame.updateGame();
            myGame.checkIfCompleted(view);
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the values you need into "outState"
        super.onSaveInstanceState(savedInstanceState);
        //Saves the Sudoku Object myGame to bundle
        savedInstanceState.putSerializable("CURRENT_BOARD", myGame);
    }
}
