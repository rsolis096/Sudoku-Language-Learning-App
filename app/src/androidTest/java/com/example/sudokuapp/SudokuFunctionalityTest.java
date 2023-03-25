package com.example.sudokuapp;

import static org.junit.Assert.*;
import android.widget.Chronometer;
import androidx.test.internal.runner.junit4.statement.UiThreadStatement;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.Before;
public class SudokuFunctionalityTest {

    Sudoku testBoard;
    Chronometer t;

    @Before
    public void setupTest() throws Throwable {

        UiThreadStatement.runOnUiThread(() -> {
            t = new Chronometer(InstrumentationRegistry.getInstrumentation().getTargetContext());
            testBoard = new Sudoku(InstrumentationRegistry.getInstrumentation().getTargetContext(), t);
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
        boolean stopLoop = false;
        for(int i = 0; i < Sudoku.getGridSize(); i++)
        {
            for(int j = 0; j < Sudoku.getGridSize(); j++)
            {
                if(Sudoku.getElement(i,j).getValue() == 1)
                {
                    testButton = Sudoku.getElement(i,j);
                    stopLoop = true;
                    break;
                }
            }
            //Break not working as expected. Ensures loop ends
            if(stopLoop)
                break;
        }
        //Should be false, cannot place a zero on the board, apple not a valid word
        assert testButton != null;
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