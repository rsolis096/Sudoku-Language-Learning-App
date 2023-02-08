package com.example.sudokuapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create Sudoku object, probably should change the way the board is initialized
        Sudoku myGame = new Sudoku(this, getResources());

        LinearLayout linearLayout = findViewById(R.id.game_scroll);
        TableLayout tableLayout = findViewById(R.id.sudoku_table);

        for(int rows = 0; rows < 9; rows++)
        {
//hi
            TableRow tableRow = new TableRow(this);
            for(int cols = 0; cols < 9; cols++)
            {
                //This adds the created element into the row
                tableRow.addView(myGame.mSudokuBoard[rows][cols]);
            }
            //This adds the created row into the table
            tableLayout.addView(tableRow);
        }
        //Adds the button to the layout as another row
        tableLayout.addView(myGame.solveButton);
        //These shrinks all columns to fit the screen
        //tableLayout.setShrinkAllColumns(true);


        //Button Functionality
        myGame.solveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                myGame.solveGrid(0,0, myGame.mSudokuBoard);
                //Re draw the grid to set it with the new values
                myGame.updateGame();
            }
        });

    }

}