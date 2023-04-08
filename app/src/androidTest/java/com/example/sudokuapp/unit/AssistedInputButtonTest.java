package com.example.sudokuapp.unit;

import static org.junit.Assert.*;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.sudokuapp.model.AssistedInputButton;
import com.example.sudokuapp.model.ElementButton;

import org.junit.BeforeClass;
import org.junit.Test;

public class AssistedInputButtonTest {
    private static Context context;
    private static AssistedInputButton testAssistedInputButton;

    @BeforeClass
    public static void setupTest(){
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        testAssistedInputButton = new AssistedInputButton(context);
        Looper.prepare();
    }

    @Test
    public void setCallingButton() {
        ElementButton tempElementButton = new ElementButton(context);
        tempElementButton.setEnglish("Hello");
        tempElementButton.setTranslation("Hola");
        testAssistedInputButton.setCallingButton(tempElementButton);
        assertEquals(testAssistedInputButton.getCallingButton(), tempElementButton);
    }

    @Test
    public void setAssociatedAlertDialog() {
        AlertDialog.Builder testBuilder = new AlertDialog.Builder(context);
        testBuilder.setTitle("Title");
        testBuilder.setMessage("Message");
        AlertDialog testDialog = testBuilder.create();
        testAssistedInputButton.setAssociatedAlertDialog(testDialog);
        assertEquals(testAssistedInputButton.getAssociatedAlertDialog(), testDialog);
    }

    @Test
    public void setIndex() {
        testAssistedInputButton.setIndex(5);
        assertEquals(testAssistedInputButton.getIndex(), 5);
    }

    @Test
    public void getCallingButton() {
        ElementButton tempElementButton = new ElementButton(context);
        tempElementButton.setEnglish("Hello");
        tempElementButton.setTranslation("Hola");
        testAssistedInputButton.setCallingButton(tempElementButton);
        assertEquals(testAssistedInputButton.getCallingButton(), tempElementButton);
    }

    @Test
    public void getAssociatedAlertDialog() {
        AlertDialog.Builder testBuilder = new AlertDialog.Builder(context);
        testBuilder.setTitle("Title");
        testBuilder.setMessage("Message");
        AlertDialog testDialog = testBuilder.create();
        testAssistedInputButton.setAssociatedAlertDialog(testDialog);
        assertEquals(testAssistedInputButton.getAssociatedAlertDialog(), testDialog);
    }

    @Test
    public void getIndex() {
        testAssistedInputButton.setIndex(5);
        assertEquals(testAssistedInputButton.getIndex(), 5);
    }
}