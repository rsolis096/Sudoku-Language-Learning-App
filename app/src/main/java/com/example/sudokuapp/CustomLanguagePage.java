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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CustomLanguagePage extends AppCompatActivity {

    public static int rowCounter;

    public CustomLanguagePage() {
        rowCounter = 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_language);

        //Fill page with file contents
        try {
            //Read the file and update the table layout
            readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Setup back button
        setupBackToMain();

        //Setup Add Row Button
        setupAddRow();

        //Setup Clear Button
        try {
            setupClear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // close this activity
    private void setupBackToMain() {
        Button btn = findViewById(R.id.btnCustomWordsBack);
        btn.setOnClickListener(view -> finish());
    }

    private void setupAddRow(){
        Button btnCustom = findViewById(R.id.btnCustomWordsAdd);
        btnCustom.setOnClickListener(view -> {
            //Increase row amount
            rowCounter++;
            //Get reference to Table Layout
            TableLayout tableLayout = findViewById(R.id.customWordsTable);

            //Create a new table row object (this holds the new content the user enters after pressing ok)
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(10,10,10,10);

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
                    String displayString = userEnglishInput +", " + userSpanishInput + "\n";

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


                    tableRow.addView(tableRowText);
                    tableRow.addView(deleteRowButton);
                    tableLayout.addView(tableRow);

                    try {
                        writeToFile(displayString);
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

    public void clearFile()
    {
        //Remove all TableRows from TableLayout
        TableLayout tableLayout = findViewById(R.id.customWordsTable);
        tableLayout.removeAllViews();

        //Open file
        FileOutputStream myFileOut = null;
        try {
            myFileOut = openFileOutput("myOtherText.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Write fileContents to the opened file
        try {
            myFileOut.write("".getBytes());
            myFileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFile() throws IOException {

        String fileName = "myOtherText.txt";
        File file = new File(getFilesDir(), fileName);

        //create the file if it doesn't exist
        if(!file.exists()) {
            FileOutputStream outputStream = openFileOutput(fileName, MODE_PRIVATE);
        }

        FileInputStream inputStream = openFileInput(fileName);
        //Initialize a byte array
        byte[] buffer = new byte[inputStream.available()];

        //Fill byte array with contents of file (file contents are in byte form)
        inputStream.read(buffer);

        //Use built in String constructor to convert byte array into string
        String fileContents = new String(buffer);
        //Write the string to TableLayout to individual rows
        String[] fileContentsLines = fileContents.split("\\n");

        //Close file
        inputStream.close();

        //Get a reference to the Table Layout
        TableLayout tableLayout = findViewById(R.id.customWordsTable);


        //fileContentsLines appears to have one element even if fileContents is of length zero. Only update table layout if
        //fileContents is greater than zero
        if(fileContents.length() > 0)
        {
            for (String fileContentsLine : fileContentsLines) {
                //Create a row to add to the table layout
                TableRow tableRow = new TableRow(this);
                tableRow.setPadding(10, 10, 10, 10);
                tableLayout.addView(tableRow);

                //Create a text view to add to the row
                TextView fileLine = new TextView(this);

                //Create the delete button to delete the current row
                Button deleteRowButton = new Button(this);
                deleteRowButton.setText("X");
                //Button Functionality
                deleteRowButton.setOnClickListener(new deleteRowButtonListener());

                //Create new GradientDrawable for the textview styling
                GradientDrawable border = new GradientDrawable();
                border.setStroke(2, Color.BLACK);

                //Some styling for the text view
                fileLine.setText(fileContentsLine);
                fileLine.setTextSize(20);
                fileLine.setTypeface(null, Typeface.BOLD);
                fileLine.setBackground(border);
                fileLine.setPadding(10, 10, 10, 10);

                //Add to table row
                tableRow.addView(fileLine);
                tableRow.addView(deleteRowButton);
            }

        }
        //Confirmation message
        Toast toast = Toast.makeText(this, "CONTENTS WRITTEN ON SCREEN",Toast.LENGTH_SHORT);
        toast.show();
    }

    private void writeToFile(String fileContents) throws IOException {
        //Open file
        FileOutputStream myFileOut = openFileOutput("myOtherText.txt", MODE_PRIVATE | MODE_APPEND);

        //Write fileContents to the opened file
        myFileOut.write(fileContents.getBytes());

        //Close file
        myFileOut.close();

        //Confirmation toast
        Toast toast = Toast.makeText(this, "FILE CONTENTS UPDATED",Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setupClear() throws FileNotFoundException
    {
        Button btn = findViewById(R.id.btnCustomWordsClear);
        btn.setOnClickListener(view -> {

            clearFile();
            rowCounter = 0;
            //Confirmation toast
            Toast toast = Toast.makeText(this, "FILE CONTENTS UPDATED",Toast.LENGTH_SHORT);
            toast.show();

        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, CustomLanguagePage.class);
    }

    //This writes the file contents to console for viewing purposes
    //Not intended to be part of working code, call to display text file contents
    public void displayFileContents() throws IOException {
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
        System.out.println("File Contents" +fileContents);

    }

    public static class deleteRowButtonListener extends AppCompatActivity implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            //Get Reference to TableRow which is the parent of button and textview
            TableRow tableRow = (TableRow) v.getParent();

            //Get Reference to TableLayout which is the parent of tableRow
            TableLayout tableLayout = (TableLayout) tableRow.getParent();

            //Delete the textview and button from tableRow
            tableRow.removeAllViews();
            //Delete tableRow from tableLayout
            tableLayout.removeView(tableRow);

            //Re-Write the file
            clearFile(v.getContext());
            int numberOfRows = tableLayout.getChildCount();
            for(int i =0; i < numberOfRows; i++)
            {
                //Get access to the children of tableLayout
                TableRow row = (TableRow) tableLayout.getChildAt(i);
                TextView rowText = (TextView) row.getChildAt(0);
                try {
                    writeToFile(v.getContext(), rowText.getText().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public static void clearFile(Context context)
        {
            //Open file
            FileOutputStream myFileOut = null;
            try {
                myFileOut = context.openFileOutput("myOtherText.txt", MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            //Write fileContents to the opened file
            try {
                myFileOut.write("".getBytes());
                myFileOut.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static void writeToFile(Context context, String fileContents) throws IOException {

            //Open file
            fileContents += "\n";
            FileOutputStream myFileOut = context.openFileOutput("myOtherText.txt", MODE_PRIVATE | MODE_APPEND);

            //Write fileContents to the opened file
            myFileOut.write(fileContents.getBytes());

            //Close file
            myFileOut.close();
        }
    }
}