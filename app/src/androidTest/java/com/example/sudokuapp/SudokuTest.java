package com.example.sudokuapp;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.Random;


public class SudokuTest extends AppCompatActivity {

    @Test
    public void setDifficulty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Sudoku testSudoku = new Sudoku(context, getResources());
        testSudoku.setDifficulty(1);
        assertEquals(1, testSudoku.getDifficulty());
    }
    @Test
    public void setInputMode() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Sudoku testSudoku = new Sudoku(context, getResources());
        testSudoku.setInputMode(true);
        assertTrue(testSudoku.getInputMode());
    }
    @Test
    public void setTranslationDirection() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Sudoku testSudoku = new Sudoku(context, getResources());
        testSudoku.setTranslationDirection(true);
        assertTrue(testSudoku.getTranslationDirection());
    }
    @Test
    public void getDifficulty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Sudoku testSudoku = new Sudoku(context, getResources());
        testSudoku.setDifficulty(1);
        assertEquals(1, testSudoku.getDifficulty());
    }
    @Test
    public void getInputMode() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Sudoku testSudoku = new Sudoku(context, getResources());
        testSudoku.setInputMode(true);
        assertTrue(testSudoku.getInputMode());
    }
    @Test
    public void getTranslationDirection() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Sudoku testSudoku = new Sudoku(context, getResources());
        testSudoku.setTranslationDirection(true);
        assertTrue(testSudoku.getTranslationDirection());
    }
}