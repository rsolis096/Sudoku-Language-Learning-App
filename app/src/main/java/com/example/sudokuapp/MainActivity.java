package com.example.sudokuapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Initialize 2d array for table
    Element[][] mBoard = new Element[9][9];

    //This grid stores a pre-made valid 9x9 sudoku grid
    private final int[][] demoTable =

            {
                {0, 0, 0, 2, 6, 0, 7, 0, 1},
                {6, 8, 0, 0, 7, 0, 0, 9, 0},
                {1, 9, 0, 0, 0, 4, 5, 0, 0},
                {8, 2, 0, 1, 0, 0, 0, 4, 0},
                {0, 0, 4, 6, 0, 2, 9, 0, 5},
                {0, 5, 0, 0, 0, 3, 0, 2, 8},
                {0, 0, 9, 3, 0, 0, 0, 7, 4},
                {0, 4, 0, 0, 5, 0, 0, 3, 6},
                {7, 0, 3, 0, 1, 8, 0, 0, 0}
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create Sudoku object, probably should change the way the board is initialized
        Sudoku myGame = new Sudoku(mBoard, this);

        //Initialize Resources, access English.xml and Spanish.xml
        Resources res = getResources();
        String[] english = res.getStringArray(R.array.numbers_english);
        String[] spanish = res.getStringArray(R.array.numbers_spanish);

        //The following code both initializes mBoard and places on screen in the form of a table
        //using table layout

        //Change this table to directly set the sudoku table board
        TableLayout tableLayout = new TableLayout(this);
        for(int rows = 0; rows < 9; rows++)
        {

            TableRow tableRow = new TableRow(this);
            for(int cols = 0; cols < 9; cols++)
            {
                //set values for every Element in mBoard
                if(demoTable[rows][cols] != 0)
                {
                    myGame.mSudokuBoard[rows][cols] = new Element(demoTable[rows][cols], english[demoTable[rows][cols] - 1], spanish[demoTable[rows][cols] - 1], this);
                }
                else
                {
                    myGame.mSudokuBoard[rows][cols] = new Element(0, "", "", this);
                }
                //This adds the created element into the row
                tableRow.addView(myGame.mSudokuBoard[rows][cols].mButton);
            }
            //This adds the created row into the table
            tableLayout.addView(tableRow);
        }


        //Adds the button to the layout as another row
        tableLayout.addView(myGame.solveButton);
        //These shrinks all columns to fit the screen
        tableLayout.setShrinkAllColumns(true);
        //Display the table and button
        setContentView(tableLayout);


        //Button Functionality
        myGame.solveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                myGame.solveGrid(0,0);
                Log.i("[0,0]", String.valueOf(myGame.mSudokuBoard[0][0].mValue));

                //Re draw the grid to set it with the new values
                myGame.updateGame();
            }
        });
    }




}