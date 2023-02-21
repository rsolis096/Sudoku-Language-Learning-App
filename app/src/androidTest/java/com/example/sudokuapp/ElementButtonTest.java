package com.example.sudokuapp;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.annotation.NonNull;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.Random;

public class ElementButtonTest {

    @Test
    public void getValue()
    {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);

        assertEquals(value, testButton.getValue());
    }

    @Test
    public void getLocked() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);

        assertTrue(testButton.getLocked());
    }

    @Test
    public void getEnglish() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);

        assertEquals("English", testButton.getEnglish());
    }

    @Test
    public void getTranslation() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);

        assertEquals("Spanish", testButton.getTranslation(true));
    }

    @Test
    public void getIndex1() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);

        assertEquals(index1, testButton.getIndex1());
    }

    @Test
    public void getIndex2() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);

        assertEquals(index2, testButton.getIndex2());
    }

    @Test
    public void setValue() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);
        testButton.setValue(0);


        assertEquals(testButton.getValue(), 0);
    }

    @Test
    public void setLock() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);
        testButton.setLock(false);


        assertFalse(testButton.getLocked());
    }

    @Test
    public void setEnglish() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);
        testButton.setEnglish("English Word");


        assertEquals(testButton.getEnglish(), "English Word");
    }

    @Test
    public void setTranslation() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);
        testButton.setTranslation("Spanish Word");


        assertEquals(testButton.getTranslation(true), "Spanish Word");
    }


    @Test
    public void setIndex() {
        Random random = new Random();
        int min = 0;
        int max = 8;
        int index1 = random.nextInt((max - min) + 1) + min;
        int index2 = random.nextInt((max - min) + 1) + min;
        int value = random.nextInt((max - min) + 1) + min;

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ElementButton testButton = new ElementButton(value, "English", "Spanish", context, true, index1, index2);
        testButton.setIndex(5,1);


        assertEquals(testButton.getIndex1(), 5);
        assertEquals(testButton.getIndex2(), 1);
    }
}