package com.example.sudokuapp;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;

import com.example.sudokuapp.view.SudokuPage;

//Sets the ElementButton as the selected button for user input
public class ElementButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Log.i("Remaining Cells", String.valueOf(Sudoku.getRemainingCells()));
        //Save the calling object as to a variable for easier to understand use.
        ElementButton buttonPressed = (ElementButton) view;
        if(SudokuPage.selectedButton != buttonPressed && SudokuPage.selectedButton!= null)
        {
            //If another button is selected, return the previous button to original state
            //setCellDesign(SudokuPage.selectedButton);
            SudokuFunctionality.colorBoxColumnRow(SudokuPage.selectedButton.getIndex1(), SudokuPage.selectedButton.getIndex2(), false);
        }

        SudokuPage.selectedButton = buttonPressed;
        //Update the new currently selected button
        SudokuFunctionality.colorBoxColumnRow(buttonPressed.getIndex1(), buttonPressed.getIndex2(), true);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.rgb(187,222,251)); // set the fill color
        gd.setStroke(2, Color.rgb(0,0,0)); // set the border color and width
        buttonPressed.setBackground(gd);
    }
}