package com.example.sudokuapp;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.*;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.widget.Chronometer;


import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

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
        System.out.println(Sudoku.getGridSize() + "GRID SIZE");

        int testNum = 0;
        for(int i = 0; i < Sudoku.getGridSize(); i++)
        {
            for(int j = 0; j < Sudoku.getGridSize(); j++)
            {
                if(Sudoku.getElement(i,j).getValue() != 0)
                {
                    testNum = Sudoku.getElement(i,j).getValue()-1;
                    break;
                }
            }
        }
        //Should be false, cannot place a value in an occupied cell
        assertFalse(SudokuFunctionality.validSpotHelper(Sudoku.getGridSize()-1, Sudoku.getGridSize()-1, testNum));
    }

    @Test
    public void validSpot() {
    }

    @Test
    public void updateGame() {
    }

    @Test
    public void solveGrid() {
    }
}