package com.example.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class SudokuPage extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, SudokuPage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_page);

        //Create Sudoku object, probably should change the way the board is initialized
        Sudoku myGame = new Sudoku(this);

        TableLayout tableLayout = findViewById(R.id.sudoku_table);

        int lightGray = Color.rgb(	128, 128, 128);
        int darkGray = Color.rgb(90, 90, 90);

        for(int rows = 0; rows < 9; rows++)
        {
            TableRow tableRow = new TableRow(this);
            for(int cols = 0; cols < 9; cols++)
            {
                //This adds the created ElementButton into the row
                tableRow.addView(myGame.mSudokuBoard[rows][cols]);
            }
            //This adds the created row into the table
            tableLayout.addView(tableRow);
        }
        //This shrinks all columns to fit the screen
        //tableLayout.setShrinkAllColumns(true);


        //Solve button Functionality
        Button solveButton = findViewById(R.id.solveButton);
        solveButton.setOnClickListener(view -> {
            myGame.solveGrid();
            //Re draw the grid to set it with the new values
            myGame.updateGame();
            myGame.checkIfCompleted(view);
        });
    }
}
