package com.example.sudokuapp.model;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIO {

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

    public static void writeToFile(Context context, String fileContents) throws IOException {

        //Open file
        FileOutputStream myFileOut = context.openFileOutput("myOtherText.txt", MODE_PRIVATE | Context.MODE_APPEND);

        fileContents += "\n";
        //Write fileContents to the opened file
        myFileOut.write(fileContents.getBytes());

        //Close file
        myFileOut.close();
    }

    //Returns a string the contains the contents of the text file
    public static String readFile(Context context) throws IOException {
        File file = new File(context.getFilesDir(), "myOtherText.txt");

        //create the file if it doesn't exist
        if(!file.exists()) {
            FileOutputStream outputStream = context.openFileOutput("myOtherText.txt", MODE_PRIVATE);
        }

        FileInputStream inputStream = context.openFileInput("myOtherText.txt");
        //Initialize a byte array
        byte[] buffer = new byte[inputStream.available()];

        //Fill byte array with contents of file (file contents are in byte form)
        inputStream.read(buffer);

        //Close file
        inputStream.close();

        return new String(buffer);
    }

}
