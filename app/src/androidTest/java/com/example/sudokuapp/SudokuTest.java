package com.example.sudokuapp;

import static org.junit.Assert.*;
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
    @Test
    public void setRemainingCells() {
        Sudoku.setRemainingCells(5);
        assertEquals(5, Sudoku.getRemainingCells());
        Sudoku.decreaseRemainingCells();
        assertEquals(4, Sudoku.getRemainingCells());
        Sudoku.increaseRemainingCells();
        assertEquals(5, Sudoku.getRemainingCells());
    }
    @Test
    public void setGRID_SIZE() {

    }
}