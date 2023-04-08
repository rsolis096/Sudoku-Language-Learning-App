package com.example.sudokuapp.controller;


import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.sudokuapp.AssistedInputButton;
import com.example.sudokuapp.ElementButton;
import com.example.sudokuapp.Sound;
import com.example.sudokuapp.model.Sudoku;

import java.util.Objects;

//Unique functionality for clicking a given cell in audio mode
public class AudioElementButtonListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        Log.i("Remaining Cells", String.valueOf(Sudoku.getRemainingCells()));
        //Save the calling object as to a variable for easier to understand use.
        ElementButton buttonPressed = (ElementButton) view;

        Sound.playSound(buttonPressed.getContext(), buttonPressed.getTranslation(Sudoku.getTranslationDirection()));

        //**************************************//
        //          MANUAL INPUT                //
        //**************************************//

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        if (Sudoku.manual) {
            builder.setTitle("What did you hear?");
            //Set up the input type (manual text input)
            EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            //Display popup
            builder.setView(input);
            //When the user hits ok
            builder.setPositiveButton("OK", (dialog, which) ->
            {
                //This index tracks the position userInput is in the english or spanish array
                //Get the string currently in the EditText object
                String userInput = input.getText().toString();

                //If nothing is entered, close and do nothing
                if(userInput.length() != 0)
                {
                    //Boolean to store whether or not the userInput string is part of the words in play
                    boolean validUserInput = false;

                    //Check if userInput string is part of the words in play
                    for (int i = 0; i < Sudoku.getGridSize(); i++)
                    {
                        //User input in Spanish
                        if (!Sudoku.getTranslationDirection()) {
                            //Check to see if the userInput is a valid word in play
                            if (Objects.equals(Sudoku.bank.getSpanish()[i].toLowerCase(), userInput.toLowerCase())) {
                                validUserInput = true;
                                break;
                            }
                        }
                        //User input in English
                        else {
                            if (Objects.equals(Sudoku.bank.getEnglish()[i].toLowerCase(), userInput.toLowerCase())) {
                                validUserInput = true;
                                break;
                            }
                        }
                    }
                    // If userInput string is a valid word in play, and is placed in a valid position by sudoku rules
                    if (Objects.equals(buttonPressed.getTranslation(!Sudoku.getTranslationDirection()), userInput) &&  validUserInput) {
                        buttonPressed.setText(buttonPressed.getTranslation(!Sudoku.getTranslationDirection()));
                    }
                    // If userInput string is not a valid word in play
                    else {
                        Toast t = Toast.makeText(view.getContext(), "You cant place that there!", Toast.LENGTH_LONG);
                        t.show();
                    }
                }
            });
            //Set up popup cancel button
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            //Display the popup
            builder.show();
        }
        //Below uses buttons to assist user with word input
        else
        {
            //**************************************//
            //          ASSISTED INPUT              //
            //**************************************//

            // Create the dialog box this way allows alert.cancel() to be called, otherwise user needs to manually close the dialog every time
            AlertDialog alert = builder.create();
            alert.setTitle("Enter Word:");
            Context dialogContext = builder.getContext();

            // Exit the popup with no changes made
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            // Builds the pre defined inputs the user may choose from
            TableLayout input = new TableLayout(dialogContext);
            input.setContentDescription("assistDialogLayout");

            //Set tag counter for assistButtons
            int assistButtonTagCounter = 0;
            for (int rows = 0; rows < Sudoku.getBoxSize().second; rows++)
            {
                TableRow tableRow = new TableRow(dialogContext);
                //Set tag for each table row to be used in testing
                tableRow.setTag("assistTableRowTag" + (rows));

                for (int cols = 0; cols < Sudoku.getBoxSize().first; cols++)
                {
                    //These buttons represents the 1 of 9 buttons user can choose words from
                    AssistedInputButton wordButton = new AssistedInputButton(dialogContext);
                    //Set tag each AssistedInputButton for testing
                    wordButton.setTag("assistButtonTag" + (assistButtonTagCounter));
                    assistButtonTagCounter++;

                    //If true, the user should be given the choice of words in spanish
                    if(!Sudoku.getTranslationDirection())
                        wordButton.setText(Sudoku.bank.getSpanish()[(rows* Sudoku.getBoxSize().first) + cols]);
                    else
                        wordButton.setText(Sudoku.bank.getEnglish()[(rows* Sudoku.getBoxSize().first) + cols]);

                    //Button holds its index of where it is in subgrid
                    wordButton.setIndex(rows*Sudoku.getBoxSize().first + cols);
                    //Button stores a reference to the AlertDialog so it can close it in onclicklistener
                    wordButton.setAssociatedAlertDialog(alert);
                    //Stores a reference to the ElementButton that called it when it was pressed
                    wordButton.setCallingButton(buttonPressed);
                    //Button Functionality
                    wordButton.setOnClickListener(new AudioAssistedInputButtonListener());
                    tableRow.setId(View.generateViewId());
                    tableRow.addView(wordButton);
                }
                input.addView(tableRow);
                input.setStretchAllColumns(true);
                input.setShrinkAllColumns(true);
            }
            alert.setView(input);
            alert.show();
        }
    }

    //This deals with the check after selecting an answer that had popped up after a given cell is pressed in audio mode
    private static class AudioAssistedInputButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AssistedInputButton wordButtonPressed = (AssistedInputButton) view;
            if(Objects.equals(wordButtonPressed.getCallingButton().getTranslation(!Sudoku.getTranslationDirection()), wordButtonPressed.getText().toString()))
            {
                wordButtonPressed.getCallingButton().setText(wordButtonPressed.getText().toString());
                wordButtonPressed.getCallingButton().setClickable(false);
            }
            //closes dialog box after a button is pressed
            wordButtonPressed.getAssociatedAlertDialog().cancel();
        }

    }

}

