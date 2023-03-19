package com.example.sudokuapp;


import android.graphics.Color;
import android.util.Log;

// Utility class to provide general Functionality for Sudoku Board
public class SudokuFunctionality {

    //When the user places a value in an empty cell, we check to see if its valid according to sudoku rules
    //This does not guarantee that the user has placed the correct value into the empty cell
    static public boolean validSpotHelper(int row, int col, int num) {

        //Cannot place value where a given is
        if(Sudoku.getElement(row,col).isClickable())
        {
            //Check rows and cols
            for (int i = 0; i < Sudoku.getGridSize(); i++) {
                if (Sudoku.getElement(row, i).getValue() == num || Sudoku.getElement(i, col).getValue() == num) {
                    return false;
                }
            }
            //Check box
            int box_start_row = ((int) (row / Sudoku.getBoxSize().first) * Sudoku.getBoxSize().first);
            int box_start_col = ((int) (col / Sudoku.getBoxSize().second) * Sudoku.getBoxSize().second);
            for (int i = 0; i < (int) Sudoku.getBoxSize().first; i++) {
                for (int j = 0; j < (int) Sudoku.getBoxSize().second; j++) {
                    if (Sudoku.getElement(i + box_start_row, j + box_start_col).getValue() == num) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    //checks a given element for any conflicts with other cell values
    static public boolean validSpot(ElementButton cell, String givenInput)
    {
        int checkNum = 0;
        if(cell.isClickable()) {
            if (!Sudoku.getTranslationDirection()) {
                for (int i = 0; i < Sudoku.getGridSize(); i++) {
                    if (Sudoku.english[i].equalsIgnoreCase(givenInput)) {
                        checkNum = i + 1;
                    }
                }
            } else {
                for (int i = 0; i < Sudoku.getGridSize(); i++) {
                    if (Sudoku.spanish[i].equalsIgnoreCase(givenInput)) {
                        checkNum = i + 1;
                    }
                }
            }
            if (SudokuFunctionality.validSpotHelper(cell.getIndex1(), cell.getIndex2(), checkNum)) {
                Log.i("Status", "Valid spot, follows game rules.");
                return true;
            }
        }
        Log.i("Status", "Not a valid spot, does not follow game rules.");
        return false;
    }

    static public void updateGame()
    {
        //Updates the text of buttons
        for(int i = 0; i < Sudoku.getGridSize(); i++) {
            for (int j = 0; j < Sudoku.getGridSize(); j++) {
                if (Sudoku.getElement(i,j).isClickable()) {
                    Sudoku.getElement(i,j).setText(Sudoku.getElement(i,j).getTranslation(Sudoku.getTranslationDirection()));
                    Sudoku.getElement(i,j).setTextColor(Color.rgb(0,0,0));
                    Sudoku.getElement(i,j).setClickable(false);
                }
            }
        }
    }

    static public void solveGrid()
    {
        Sudoku.setRemainingCells(0);
        for(int i = 0; i < Sudoku.getGridSize(); i++)
        {
            for(int j = 0; j < Sudoku.getGridSize(); j++)
            {
                Sudoku.getElement(i,j).setValue(GenerateBoard.mAnswerBoard[i][j]);

                //If true, english is board language, spanish is the language the user should input
                if(Sudoku.getTranslationDirection())
                {
                    Sudoku.getElement(i,j).setEnglish(Sudoku.english[GenerateBoard.mAnswerBoard[i][j]-1]);
                    Sudoku.getElement(i,j).setTranslation(Sudoku.spanish[GenerateBoard.mAnswerBoard[i][j]-1]);
                    System.out.println("English to spanish");
                }
                else
                {
                    Sudoku.getElement(i,j).setEnglish(Sudoku.spanish[GenerateBoard.mAnswerBoard[i][j]-1]);
                    Sudoku.getElement(i,j).setTranslation(Sudoku.english[GenerateBoard.mAnswerBoard[i][j]-1]);
                    System.out.println("Spanish to english");
                    System.out.println(Sudoku.getElement(i,j).getEnglish() +", "+Sudoku.getElement(i,j).getTranslation(Sudoku.getTranslationDirection()));
                }
            }
        }
        SudokuFunctionality.updateGame();
    }
}
