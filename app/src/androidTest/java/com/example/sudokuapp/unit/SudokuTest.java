package com.example.sudokuapp.unit;

import static org.junit.Assert.*;

import android.util.Pair;

import com.example.sudokuapp.model.Sudoku;

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
        Sudoku.setGRID_SIZE(9);
        assertEquals(9, Sudoku.getGridSize());
        assertEquals(new Pair<>(3,3), Sudoku.getBoxSize());
        Sudoku.setGRID_SIZE(12);
        assertEquals(12, Sudoku.getGridSize());
        assertEquals(new Pair<>(3,4), Sudoku.getBoxSize());
        Sudoku.setGRID_SIZE(6);
        assertEquals(6, Sudoku.getGridSize());
        assertEquals(new Pair<>(2,3), Sudoku.getBoxSize());
    }
}