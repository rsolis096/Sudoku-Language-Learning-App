package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class OptionsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);

        // close this activity
        setupBackToMain();

        // Update Text box with file contents
        try {
            readFile();
        }
        catch (IOException e) {
            throw new RuntimeException("DOESNT EXIST",e);

        }

        //Setup buttons
        try {
            setupCustomWordsButton();
            setupWriteButton();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // theme/colour
        // leaderboard
        // modify word bank
    }

    // close this activity
    private void setupBackToMain() {
        Button btn = findViewById(R.id.optionsBackToMenu);
        btn.setOnClickListener(view -> finish());
    }

    private void setupCustomWordsButton() throws IOException {
        Button btnCustom = findViewById(R.id.btnCustom);
        btnCustom.setOnClickListener(view -> {
            try {
                readFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setupWriteButton() throws IOException {
        Button btnWrite= findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(view -> {
            try {
                writeToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        return new Intent(context, OptionsPage.class);
    }
}