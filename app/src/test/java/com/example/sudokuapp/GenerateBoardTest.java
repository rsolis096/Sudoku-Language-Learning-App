package com.example.sudokuapp;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GenerateBoardTest {

    GenerateBoard testGBoard;

    @Before
    public void generateBoardSetup(){
        testGBoard = new GenerateBoard(3, 3,0);
        GenerateBoard.mGeneratedBoard = new int[testGBoard.GRID_SIZE][testGBoard.GRID_SIZE];
        assertNotNull(GenerateBoard.mGeneratedBoard);
    }


    @Test
    public void getEmptyCells() {
        assertNotNull(GenerateBoard.mGeneratedBoard);
        assertEquals(testGBoard.getEmptyCells(),0);
        testGBoard.setRemainingCells(10);
        assertEquals(testGBoard.getEmptyCells(),10);
    }

    @Test
    public void createBoard() {
        assertNotNull(GenerateBoard.mGeneratedBoard);
        testGBoard.createBoard();
        // Check that mGeneratedBoard has correct number of zeros
        int zeroCounter = 0;
        for(int i =0; i < testGBoard.GRID_SIZE; i++)
        {
            for(int j =0; j < testGBoard.GRID_SIZE; j++)
            {
                if(GenerateBoard.mGeneratedBoard[i][j] == 0)
                {
                    ++zeroCounter;
                }
                if(zeroCounter == testGBoard.getEmptyCells())
                    break;
            }
            if(zeroCounter == testGBoard.getEmptyCells())
                break;
        }
        assertEquals(zeroCounter,testGBoard.getEmptyCells());

        //Check that mAnswerBoard has no zeros
        for(int i =0; i < testGBoard.GRID_SIZE; i++)
        {
            for(int j =0; j < testGBoard.GRID_SIZE; j++)
            {
                assertNotEquals(GenerateBoard.mAnswerBoard[i][j], 0);
            }
        }

    }

    @Test
    public void validSpot()
    {
        //Find a invalid spot (a given)
        testGBoard.createBoard();
        int rowIndex = 0;
        int colIndex = 0;
        boolean found = false;
        for(int i = 0; i < testGBoard.GRID_SIZE; i++)
        {
            for(int j = 0; j < testGBoard.GRID_SIZE; j++)
            {
                if(GenerateBoard.mGeneratedBoard[i][j] != 0)
                {
                    rowIndex = i;
                    colIndex = j;
                    found = true;
                    break;
                }

            }
            if(found)
                break;
        }
        assertFalse(testGBoard.validSpot(rowIndex, colIndex, 5, GenerateBoard.mGeneratedBoard));
    }

    @Test
    public void solveBoard() {
        testGBoard.createBoard();
        testGBoard.solveBoard(0,0,GenerateBoard.mGeneratedBoard);
        //Sometimes recursion fails, repeat until it doesn't
        while (GenerateBoard.mGeneratedBoard[0][0] == 0 || GenerateBoard.mGeneratedBoard[0][1] == 0) {
            testGBoard.solveBoard(0, 0, GenerateBoard.mGeneratedBoard);
        }
        for(int i = 0; i < testGBoard.GRID_SIZE; i++)
        {
            for(int j = 0; j < testGBoard.GRID_SIZE; j++)
            {
                assertNotEquals(GenerateBoard.mGeneratedBoard[i][j], 0);
            }
        }
    }
}