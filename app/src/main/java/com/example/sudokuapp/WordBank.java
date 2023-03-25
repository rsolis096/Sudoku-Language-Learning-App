package com.example.sudokuapp;

import android.content.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WordBank {

    private String[] english, spanish;
    private static int value;
    public void generateWordBank(int size, int dif, int val, Context context) {
        int[] categoryArrays = {
                R.array.numbers,//0
                R.array.greetings_easy,//1
                R.array.greetings_medium,//2
                R.array.greetings_hard,//3
                R.array.directions_easy,//4
                R.array.directions_medium,//5
                R.array.directions_hard,//6
                R.array.family_easy,//7
                R.array.family_medium,//8
                R.array.family_hard,//9
                R.array.food_drinks_easy,//10
                R.array.food_drinks_medium,//11
                R.array.food_drinks_hard//12
        };

        //Given a category from categoryArrays, generate a puzzle using that category.
        int selectedArrayId;
        String[] inputString;

        if(val == 0) {
            inputString = context.getResources().getStringArray(0);
        }
        else {
            selectedArrayId = categoryArrays[val + dif];
            //shuffles word bank to give random values at random indices (except for number word bank)
            inputString = context.getResources().getStringArray(selectedArrayId);
            List<String> temp = Arrays.asList(inputString);
            Collections.shuffle(temp);
            temp.toArray(inputString);
        }
        //There is a one to one correspondence between english and spanish. the string at index 0 in spanish is the translation to the string at index 0 in english
        english = new String[size];
        spanish = new String[size];
        //From word pair, words are split by commas. Separate and place in corresponding array. Eg. inputString[0] = "english,spanish"
        for(int i = 0; i < size; i++)
        {
            String [] wordPair = inputString[i].split(",");
            english[i] = wordPair[0];
            spanish[i] = wordPair[1];
        }
    }
    static void setValue(int val) {value = val;}
    int getValue() {return value;}
    String[] getEnglish() {return english;}
    String[] getSpanish() {return spanish;}
}
