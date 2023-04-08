package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;


public class CustomLanguagePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_custom_language);

        try {
            //Fill page with file contents
            populateTableLayout();
            //Setup Clear Button
            setupClear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Setup back button
        setupBackToMain();
        //Setup Add Row Button
        setupAddRow();
    }

    // close this activity
    private void setupBackToMain() {
        Button btn = findViewById(R.id.btnCustomWordsBack);
        btn.setOnClickListener(view -> finish());
    }

    //Creates the pop up that the user interacts with to add words
    private void setupAddRow(){
        Button btnCustom = findViewById(R.id.btnCustomWordsAdd);
        btnCustom.setOnClickListener(view -> {
            //Get reference to Table Layout
            TableLayout tableLayout = findViewById(R.id.customWordsTable);

            //Create a new table row object (this holds the new content the user enters after pressing ok)
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(10,10,10,10);

            //Sets its Content Description for testing (needs more thought)
            //tableRow.setContentDescription("customWordsTableRow" + rowCounter);

            //Create the content to be placed in the row
            //Create the dialog box this way allows alert.cancel() to be called, otherwise user needs to manually close the dialog every time
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Word Pair:");

            //Set Up rows in TableLayout pop up to place User input from EditTexts
            TableLayout userInputLayout = new TableLayout(view.getContext());
            builder.setView(userInputLayout);
            TableRow userInputRowOne = new TableRow(view.getContext());
            TableRow userInputRowTwo = new TableRow(view.getContext());
            userInputLayout.addView(userInputRowOne);
            userInputLayout.addView(userInputRowTwo);

            //Set up English EditText
            EditText englishInput = new EditText(view.getContext());
            englishInput.setInputType(InputType.TYPE_CLASS_TEXT);
            englishInput.setWidth(750);
            englishInput.setHint("English");

            //Set up Spanish EditText
            EditText spanishInput = new EditText(view.getContext());
            spanishInput.setInputType(InputType.TYPE_CLASS_TEXT);
            spanishInput.setHint("Spanish");

            //Place the EditText views in the pop up
            userInputRowOne.addView(englishInput);
            userInputRowTwo.addView(spanishInput);

            //When the user hits ok, update the screen, append to the file
            builder.setPositiveButton("OK", (dialog, which) ->
            {
                //Get the string currently in the EditText object
                String userEnglishInput = englishInput.getText().toString();
                String userSpanishInput = spanishInput.getText().toString();

                //Do not add empty lines
                if(englishInput.length() == 0 || spanishInput.length() == 0)
                {
                    //Confirmation message
                    Toast toast = Toast.makeText(this, "INVALID INPUT",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    //Create the content to be placed in the row
                    TextView tableRowText = new TextView(this);
                    String displayString = userEnglishInput +", " + userSpanishInput;

                    //Create the delete button to delete the current row
                    Button deleteRowButton = new Button(this);
                    deleteRowButton.setText("X");
                    deleteRowButton.setOnClickListener(new deleteRowButtonListener());

                    // Create a new GradientDrawable with the desired border color and width
                    GradientDrawable border = new GradientDrawable();
                    border.setStroke(2, Color.BLACK);

                    //Some styling for the words
                    tableRowText.setText(displayString);
                    tableRowText.setTextSize(20);
                    tableRowText.setTypeface(null, Typeface.BOLD);
                    tableRowText.setBackground(border);
                    tableRowText.setPadding(10,10,10,10);

                    //Place the new views on screen
                    tableRow.addView(tableRowText);
                    tableRow.addView(deleteRowButton);
                    tableLayout.addView(tableRow);

                    //Append the file with the updated line
                    try {
                        FileIO.writeToFile(this,displayString);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });

            //Set up popup cancel button
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            //Display the popup
            builder.show();

        });
    }

    //Places the user input words onto the TableLayout
    public void populateTableLayout() throws IOException {

        //Read the contents of the text file and write them to a string
        String fileContents = FileIO.readFile(this);
        //System.out.println("ACTUAL STRING " + fileContents);

        //Only proceed if the string fileContents has contents
        if(fileContents.length() != 0)
        {
            //Place the contents of the string fileContents into an array of string lines
            String [] fileContentsLines = fileContents.split("\\n");

            //Get a reference to the Table Layout
            TableLayout tableLayout = findViewById(R.id.customWordsTable);

            //Populate the table layout with rows and the lines of strings in fileContentsLines
            for (String str : fileContentsLines) {

                //Create a row to add to the table layout
                TableRow tableRow = new TableRow(this);
                tableRow.setPadding(10, 10, 10, 10);
                tableLayout.addView(tableRow);

                //Create a text view to add to the row
                TextView fileLine = new TextView(this);

                //Create the row delete button
                Button deleteRowButton = new Button(this);
                deleteRowButton.setText("X");
                //Button Functionality
                deleteRowButton.setOnClickListener(new deleteRowButtonListener());

                //Create new GradientDrawable for the textview styling
                //Some styling for the text view
                GradientDrawable border = new GradientDrawable();
                border.setStroke(2, Color.BLACK);
                fileLine.setText(str);
                fileLine.setTextSize(20);
                fileLine.setTypeface(null, Typeface.BOLD);
                fileLine.setBackground(border);
                fileLine.setPadding(10, 10, 10, 10);

                //Add to table row
                tableRow.addView(fileLine);
                tableRow.addView(deleteRowButton);
            }
            //Confirmation message
            Toast toast = Toast.makeText(this, "CONTENTS WRITTEN ON SCREEN", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Clears the TableLayout of all its contents and empties the text file
    private void setupClear() throws FileNotFoundException {
        Button btn = findViewById(R.id.btnCustomWordsClear);
        btn.setOnClickListener(view -> {
            //Clear table rows
            TableLayout tableLayout = findViewById(R.id.customWordsTable);
            tableLayout.removeAllViews();

            //Clear the file
            FileIO.clearFile(this);

            //Confirmation toast
            Toast toast = Toast.makeText(this, "FILE CONTENTS UPDATED",Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, CustomLanguagePage.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Update the number of custom words to the data model
        TableLayout tableLayout = findViewById(R.id.customWordsTable);
        DataModel.setCustomWordsLength(tableLayout.getChildCount());
    }

    //Deletes a row from the custom words TableLayout. Re-Writes the entire text file
    public static class deleteRowButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            //Get Reference to TableRow which is the parent of x button and textview
            TableRow tableRow = (TableRow) v.getParent();

            //Get Reference to TableLayout which is the parent of tableRow
            TableLayout tableLayout = (TableLayout) tableRow.getParent();

            //Delete the textview and button from tableRow
            tableRow.removeAllViews();

            //Delete tableRow from tableLayout
            tableLayout.removeView(tableRow);

            //Clear the file so it can be re written
            FileIO.clearFile(v.getContext());

            for(int i =0; i < tableLayout.getChildCount(); i++)
            {
                //Get access to the children of tableLayout (current visible board on screen)
                TableRow row = (TableRow) tableLayout.getChildAt(i);
                TextView rowText = (TextView) row.getChildAt(0);
                try {
                    FileIO.writeToFile(v.getContext(), rowText.getText().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}