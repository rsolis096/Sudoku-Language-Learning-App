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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create Sudoku object, probably should change the way the board is initialized
        Sudoku myGame = new Sudoku(this);

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
                if(myGame.mSudokuBoard[rows][cols].mValue != 0)
                {
                    myGame.mSudokuBoard[rows][cols].setEnglish(english[myGame.mSudokuBoard[rows][cols].mValue - 1]);
                    myGame.mSudokuBoard[rows][cols].setTranslation(spanish[myGame.mSudokuBoard[rows][cols].mValue - 1]);
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
                myGame.solveGrid(0,0, myGame.mSudokuBoard);
                Log.i("[0,0]", String.valueOf(myGame.mSudokuBoard[0][0].mValue));

                //Re draw the grid to set it with the new values
                myGame.updateGame();
            }
        });
    }




}