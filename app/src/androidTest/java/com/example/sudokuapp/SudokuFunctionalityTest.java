package com.example.sudokuapp;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.*;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Chronometer;


import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.Objects;
import java.util.Random;
import org.junit.Before;
public class SudokuFunctionalityTest {

    Sudoku testBoard;
    Chronometer t;

    @Before
    public void setupTest() throws Throwable {

        runOnUiThread(new Runnable() {
            public void run() {
                t = new Chronometer(InstrumentationRegistry.getInstrumentation().getTargetContext());
                testBoard = new Sudoku(InstrumentationRegistry.getInstrumentation().getTargetContext(), t);
            }
        });
    }


    @Test
    public void validSpotHelper() {
        //Check if validSpot handles incorrect input
        assertNotNull(t);
        assertNotNull(testBoard);

        int testNum = 0;
        for(int i = 0; i < Sudoku.getGridSize(); i++)
        {
            for(int j = 0; j < Sudoku.getGridSize(); j++)
            {
                if(Sudoku.getElement(i,j).getValue() != 0)
                {
                    testNum = Sudoku.getElement(i,j).getValue();
                    assertFalse(Sudoku.getElement(i,j).isClickable());
                    break;
                }
            }
        }

        //Should be false, cannot place a value in an occupied cell
        assertFalse(SudokuFunctionality.validSpotHelper(Sudoku.getGridSize()-1, Sudoku.getGridSize()-1, testNum));
        //Should be false, cannot place a zero on the board
        assertFalse(SudokuFunctionality.validSpotHelper(Sudoku.getGridSize()-1, Sudoku.getGridSize()-1, 0));
        ///Difficult to test actual value due to randomness of board\
    }

    @Test
    public void validSpot()
    {
        //Check if validSpot handles incorrect input
        assertNotNull(t);
        assertNotNull(testBoard);

        ElementButton testButton = null;
        for(int i = 0; i < Sudoku.getGridSize(); i++)
        {
            for(int j = 0; j < Sudoku.getGridSize(); j++)
            {
                if(Sudoku.getElement(i,j).getValue() == 1)
                {
                    testButton = Sudoku.getElement(i,j);
                    break;
                }
            }
        }
        //Should be false, cannot place a zero on the board
        assertFalse(SudokuFunctionality.validSpot(testButton, "apple"));
    }

    @Test
    public void solveGrid()
    {
        //Solve Grid Relies on updateGame. Testing the function here with solveGrid
        SudokuFunctionality.solveGrid();

        for(int i = 0; i < Sudoku.getGridSize(); i++)
        {
            for(int j = 0; j < Sudoku.getGridSize(); j++)
            {
                assertNotEquals(Sudoku.getElement(i,j).getValue(), 0);
                assertNotEquals(Sudoku.getElement(i,j).getText(), " ");
            }
        }

    }
}