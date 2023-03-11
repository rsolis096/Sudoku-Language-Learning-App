package com.example.sudokuapp;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;

import java.io.Serializable;

//This class inherits button to add some useful attributes.
public class ElementButton extends androidx.appcompat.widget.AppCompatButton implements Serializable
{
    private int mValue;
    private String mTranslation;
    private String mEnglish;
    private int  index1;
    private int index2;
    private boolean isLocked;
    boolean isWrong;

    //Getter methods
    public int getValue() {
        return mValue;
    }
    public boolean getLocked() {return isLocked;}
    public String getEnglish() {
        return mEnglish;
    }
    public String getTranslation(boolean direction)
    {
        if(direction)
        {
            return mTranslation;
        }
        else {
            return mEnglish;
        }
    }
    public int getIndex1() {
        return index1;
    }
    public int getIndex2() {
        return index2;
    }

    //Setter methods
    public void setValue(int value) {
        mValue = value;
    }
    public void setLock(boolean val) {
        isLocked = val;
    }
    public void setEnglish(String english) {
        mEnglish = english;
    }
    public void setTranslation(String translation) {
        mTranslation = translation;
    }
    public void setIndex(int i, int j)
    {
        index1 = i;
        index2 = j;
    }
    public void setIndex1(int index1) {
        this.index1 = index1;
    }
    public void setIndex2(int index2) {
        this.index2 = index2;
    }


    public ElementButton(int v, String e, String t, @NonNull Context context, boolean locked, int i1, int i2) {

        super(context);
        if((v > Sudoku.getGridSize() || v < 0) || (i1 > Sudoku.getGridSize() || i1 < 0) || (i2 > Sudoku.getGridSize() || i2 < 0))
        {
            throw new IllegalArgumentException("Index or value out of range!");
        }

        isWrong = true;
        index1 = i1;
        index2 = i2;
        mValue = v;
        mEnglish = e;
        mTranslation = t;
        isLocked = locked;

        if(mValue == 0)
        {
            this.setText(" ");
        }
        else
        {
            //translationDirection = true -> english to spanish
            if(Sudoku.getTranslationDirection())
                this.setText(mEnglish);
            else
                this.setText(mTranslation);

        }
        this.setTextColor(Color.BLACK);
    }

    public ElementButton(Context context)
    {
        super(context);
        mValue = 0;
        mEnglish = "";
        mTranslation ="";
        isLocked = false;
    }

    public ElementButton(Context context, int num)
    {
        super(context);
        mValue = num;
        mEnglish = "";
        mTranslation ="";
        isLocked = false;
    }
}