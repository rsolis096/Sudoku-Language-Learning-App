package com.example.sudokuapp;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;


public class SudokuTest {

    @Test
    public void setDifficulty() {
        Sudoku.setDifficulty(1);
        assertEquals(1, Sudoku.getDifficulty());
    }
    @Test
    public void setInputMode() {
        Sudoku.setInputMode(true);
        assertTrue(Sudoku.getInputMode());
    }
    @Test
    public void setTranslationDirection() {
        Sudoku.setTranslationDirection(true);
        assertTrue(Sudoku.getTranslationDirection());
    }
    @Test
    public void getDifficulty() {
        Sudoku.setDifficulty(1);
        assertEquals(1, Sudoku.getDifficulty());
    }
    @Test
    public void getInputMode() {
        Sudoku.setInputMode(true);
        assertTrue(Sudoku.getInputMode());
    }
    @Test
    public void getTranslationDirection() {
        Sudoku.setTranslationDirection(true);
        assertTrue(Sudoku.getTranslationDirection());
    }
    @Test
    public void setWordBank() {
        Sudoku.setWordBank(2);
        assertEquals(2, Sudoku.getWordBank());
    }
    @Test
    public void getWordBank() {
        Sudoku.setWordBank(2);
        assertEquals(2, Sudoku.getWordBank());
    }
}