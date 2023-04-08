package com.example.sudokuapp;

import static org.junit.Assert.*;

import android.content.Context;


import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class FileIOTest {

    Context context;

    @Before
    public void setupTest() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();;
    }

    @Test
    public void testFileFunctions() throws IOException {

        //Clear file (To be safe)
        FileIO.clearFile(context);

        //Make sure file is empty
        assertEquals(FileIO.readFile(context), "");

        //Put stuff in the file
        FileIO.writeToFile(context, "Test String");

        //Ensure file had stuff written to it
        assertEquals(FileIO.readFile(context), "Test String\n");

        //Clear file again
        FileIO.clearFile(context);

        //Make sure file is empty
        assertEquals(FileIO.readFile(context), "");
    }

}