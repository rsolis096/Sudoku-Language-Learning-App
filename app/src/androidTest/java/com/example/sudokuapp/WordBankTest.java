package com.example.sudokuapp;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.io.IOException;


public class WordBankTest {

    @Test
    public void generateWordBank() throws IOException {
        WordBank bank = new WordBank();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        DataModel.setCategoryIndex(0);
        bank.generateWordBank(9,0,context);


        String[] inputString = context.getResources().getStringArray(R.array.numbers);

        String[] eng = new String[9];
        String[] span = new String[9];
        //From word pair, words are split by commas. Separate and place in corresponding array. Eg. inputString[0] = "english,spanish"
        for(int i = 0; i < 9; i++)
        {
            String [] wordPair = inputString[i].split(",");
            eng[i] = wordPair[0];
            span[i] = wordPair[1];
        }

        assertArrayEquals(eng, bank.getEnglish());
        assertArrayEquals(span, bank.getSpanish());
        assertEquals(DataModel.getCategoryIndex(), 9);
    }
    @Test
    public void setValue() {
        DataModel.setCategoryIndex(4);
        assertEquals(DataModel.getCategoryIndex(), 4);
    }
    @Test
    public void getValue() {
        DataModel.setCategoryIndex(10);
        assertEquals(DataModel.getCategoryIndex(), 10);
    }
}