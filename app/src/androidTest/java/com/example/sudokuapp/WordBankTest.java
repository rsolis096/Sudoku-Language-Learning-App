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
        //use this context if resources are needed for your tests
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        WordBank.setCategoryIndex(0);
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

        //Due to randomness of word selection, we may only test size of array
        assertEquals(eng.length, bank.getEnglish().length);
        assertEquals(span.length, bank.getSpanish().length);
        assertEquals(0, WordBank.getCategoryIndex());
    }
    @Test
    public void setValue() {
        WordBank.setCategoryIndex(4);
        assertEquals(WordBank.getCategoryIndex(), 4);
    }
    @Test
    public void getValue() {
        WordBank.setCategoryIndex(10);
        assertEquals(WordBank.getCategoryIndex(), 10);
    }
}