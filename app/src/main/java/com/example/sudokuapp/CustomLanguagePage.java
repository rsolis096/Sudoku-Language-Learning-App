package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class CustomLanguagePage extends AppCompatActivity {

    public static int rowCounter;

    public CustomLanguagePage() {
        rowCounter = 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_language);

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

    /*
    private void setupAddRow(){
        Button btnCustom = findViewById(R.id.btnCustomWordsAdd);
        btnCustom.setOnClickListener(view -> {
            //Increase row amount
            rowCounter++;
            //Get reference to Table Layout
            TableLayout tableLayout = findViewById(R.id.customWordsTable);
            //Create a new table row object
            TableRow tableRow = new TableRow(this);
            //Sets its Content Description for testing
            tableRow.setContentDescription("customWordsTableRow" + rowCounter);

            //Create the content to be placed in the row
            TextView insertTextView = new TextView(this);
            insertTextView.setText("BANANA");
            tableRow.addView(insertTextView);

            //This adds the created row into the table
            tableLayout.addView(tableRow);
        });
    }
     */

    private void setupAddRow(){
        Button btnCustom = findViewById(R.id.btnCustomWordsAdd);
        btnCustom.setOnClickListener(view -> {
            //Increase row amount
            rowCounter++;
            //Get reference to Table Layout
            TableLayout tableLayout = findViewById(R.id.customWordsTable);
            //Create a new table row object
            TableRow tableRow = new TableRow(this);
            //Sets its Content Description for testing
            tableRow.setContentDescription("customWordsTableRow" + rowCounter);

            //Create the content to be placed in the row
            // Create the dialog box this way allows alert.cancel() to be called, otherwise user needs to manually close the dialog every time
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Word Pair:");

            //Set Up Rows to place User input EditTexts
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

            //When the user hits ok
            builder.setPositiveButton("OK", (dialog, which) ->
            {
                //Get the string currently in the EditText object
                String userEnglishInput = englishInput.getText().toString();
                String userSpanishInput = spanishInput.getText().toString();

                //Create the content to be placed in the row
                TextView insertTextView = new TextView(this);
                String displayString = userEnglishInput +", " + userSpanishInput;
                insertTextView.setText(displayString);
                tableRow.addView(insertTextView);
            });

            //Set up popup cancel button
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            //Display the popup
            builder.show();

            //This adds the created row into the table
            tableLayout.addView(tableRow);
        });
    }


    private void readFile() throws IOException {

        String fileName = "myOtherText.txt";
        File file = new File(getFilesDir(), fileName);

        //create the file if it doesn't exist
        if(!file.exists()) {

            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
        }

        FileInputStream inputStream = openFileInput(fileName);
        //Initialize a byte array
        byte[] buffer = new byte[inputStream.available()];

        //Fill byte array with contents of file (file contents are in byte form)
        inputStream.read(buffer);

        //Use built in String constructor to convert byte array into string
        String fileContents = new String(buffer);

        //Close file
        inputStream.close();

        //Write the string to TextView
        TextView myText = findViewById(R.id.displayTextView);
        myText.setText(fileContents);

        //Confirmation message
        Toast toast = Toast.makeText(findViewById(R.id.btnWrite).getContext(), "CONTENTS WRITTEN ON SCREEN",Toast.LENGTH_LONG);
        toast.show();
    }

    private void writeToFile() throws IOException {
        FileOutputStream myFileOut = openFileOutput("myOtherText.txt", MODE_PRIVATE);
        EditText editTextBox = findViewById(R.id.readEditTextView);
        myFileOut.write(editTextBox.getText().toString().getBytes());
        myFileOut.close();

        Toast toast = Toast.makeText(findViewById(R.id.btnWrite).getContext(), "FILE CONTENTS UPDATED",Toast.LENGTH_LONG);
        toast.show();

    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, CustomLanguagePage.class);
    }
}