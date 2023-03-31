package com.example.sudokuapp;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;

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
            int box_start_col = ((int) (col / Sudoku.getBoxSize().first) * Sudoku.getBoxSize().first);
            int box_start_row = ((int) (row / Sudoku.getBoxSize().second) * Sudoku.getBoxSize().second);
            for (int i = 0; i < Sudoku.getBoxSize().first; i++) {
                for (int j = 0; j < (int) Sudoku.getBoxSize().second; j++) {
                    if (Sudoku.getElement(j + box_start_row, i + box_start_col).getValue() == num) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static void checkIfCompleted(View view) {

        if(Sudoku.getRemainingCells() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Game Finished!");
            builder.setPositiveButton("Continue", (dialog, which) -> {
                Intent intent = new Intent(view.getContext(), ResultsScreen.class);
                view.getContext().startActivity(intent);
            });
            builder.setOnDismissListener(dialogInterface -> {
                Intent intent = new Intent(view.getContext(), ResultsScreen.class);
                view.getContext().startActivity(intent);
            });
            builder.show();
        }
    }

    //Sets colors of relevant row column and box when an free cell is selected
    static public void colorBoxColumnRow(int row, int col, boolean selected)
    {
        //Check rows and cols

        //Check box
        int box_start_col = ((int) (col / Sudoku.getBoxSize().first) * Sudoku.getBoxSize().first);
        int box_start_row = ((int) (row / Sudoku.getBoxSize().second) * Sudoku.getBoxSize().second);
        Log.i("box top left x", Integer.toString(box_start_col));
        Log.i("box top left y", Integer.toString(box_start_row));
        if(selected)
        {
            //fill in entire corresponding line and column that cell is contained in
            for (int i = 0; i < Sudoku.getGridSize(); i++) {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(Color.rgb(226,235,243)); // set the fill color
                drawable.setStroke(1, Color.rgb(0,0,0)); // set the border color and width

                Sudoku.getElement(i, col).setBackground(drawable);
                Sudoku.getElement(row, i).setBackground(drawable);
            }
            //fill in box that cell is contained in
            for (int i = 0; i < Sudoku.getBoxSize().first; i++) {
                for (int j = 0; j < Sudoku.getBoxSize().second; j++) {

                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setColor(Color.rgb(226,235,243)); // set the fill color
                    drawable.setStroke(1, Color.rgb(0,0,0)); // set the border color and width

                    Sudoku.getElement(j + box_start_row, i + box_start_col).setBackground(drawable);
                }
            }
        }
        else {
            //set corresponding line and column that cell is contained in to default design
            for (int i = 0; i < Sudoku.getGridSize(); i++) {
                Sudoku.setCellDesign(Sudoku.getElement(row, i));
                Sudoku.setCellDesign(Sudoku.getElement(i, col));
            }
            //set box that cell is contained in to default design
            for (int i = 0; i < (int) Sudoku.getBoxSize().first; i++) {
                for (int j = 0; j < (int) Sudoku.getBoxSize().second; j++) {
                    Sudoku.setCellDesign(Sudoku.getElement(j + box_start_row, i + box_start_col));
                }
            }

        }

    }


    //checks a given element for any conflicts with other cell values
    static public boolean validSpot(ElementButton cell, String givenInput)
    {
        int checkNum = 0;
        if(cell.isClickable()) {
            if (!Sudoku.getTranslationDirection()) {
                for (int i = 0; i < Sudoku.getGridSize(); i++) {
                    if (Sudoku.getBank().getEnglish()[i].equalsIgnoreCase(givenInput)) {
                        checkNum = i + 1;
                    }
                }
            } else {
                for (int i = 0; i < Sudoku.getGridSize(); i++) {
                    if (Sudoku.getBank().getSpanish()[i].equalsIgnoreCase(givenInput)) {
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

    //Updates the text of buttons
    static public void updateGame()
    {

        for(int i = 0; i < Sudoku.getGridSize(); i++) {
            for (int j = 0; j < Sudoku.getGridSize(); j++) {
                if (Sudoku.getElement(i,j).isClickable() && !Sudoku.getElement(i,j).getLocked()) {
                    Sudoku.getElement(i,j).setText(Sudoku.getElement(i,j).getTranslation(Sudoku.getTranslationDirection()));
                }
                else if(Sudoku.getElement(i,j).getLocked())
                {
                    Sudoku.getElement(i,j).setText(Sudoku.getElement(i,j).getTranslation(!Sudoku.getTranslationDirection()));
                }
                Sudoku.getElement(i,j).setTextColor(Color.rgb(0,0,0));
                Sudoku.getElement(i,j).setClickable(false);
            }
        }
    }

    //Solves the grid
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
                    Sudoku.getElement(i,j).setEnglish(Sudoku.getBank().getEnglish()[GenerateBoard.mAnswerBoard[i][j]-1]);
                    Sudoku.getElement(i,j).setTranslation(Sudoku.getBank().getSpanish()[GenerateBoard.mAnswerBoard[i][j]-1]);
                    System.out.println("English to spanish");
                }
                else
                {
                    Sudoku.getElement(i,j).setEnglish(Sudoku.getBank().getSpanish()[GenerateBoard.mAnswerBoard[i][j]-1]);
                    Sudoku.getElement(i,j).setTranslation(Sudoku.getBank().getEnglish()[GenerateBoard.mAnswerBoard[i][j]-1]);
                    System.out.println("Spanish to english");
                    System.out.println(Sudoku.getElement(i,j).getEnglish() +", "+Sudoku.getElement(i,j).getTranslation(Sudoku.getTranslationDirection()));
                }
            }
        }
        SudokuFunctionality.updateGame();
    }
}
