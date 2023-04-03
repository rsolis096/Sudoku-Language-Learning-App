package com.example.sudokuapp;

public class DataModel
{
    //These are used to pass data between multiple activities.
    private static boolean audioMode = false;
    private static int categoryIndex = 0;
    private static int checkedCategory = 0;
    private static int customWordsLength = 0;


    //Getters
    public static boolean getAudioMode() {
        return audioMode;
    }

    public static int getCategoryIndex() {
        return categoryIndex;
    }

    public static int getCheckedCategory() {
        return checkedCategory;
    }

    public static int getCustomWordsLength() {
        return customWordsLength;
    }

    //Setters
    public static void setAudioMode(boolean b) {
        audioMode = b;
    }

    public static void setCategoryIndex(int x) {
        categoryIndex = x;
    }

    public static void setCheckedCategory(int x) {
        checkedCategory = x;
    }

    public static void setCustomWordsLength(int x) {
        customWordsLength = x;
    }
}