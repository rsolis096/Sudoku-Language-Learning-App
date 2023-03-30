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
    boolean isWrong;
    boolean isLocked;

    //Getter methods
    public int getValue() {
        return mValue;
    }
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

    public boolean getLocked() {
        return isLocked;
    }

    //Setter methods
    public void setValue(int value) {
        mValue = value;
    }
    public void setEnglish(String english) {
        mEnglish = english;
    }
    public void setTranslation(String translation) {
        if(Sudoku.getTranslationDirection())
        {
            mTranslation = translation;
        }
        else {
            mEnglish = translation;
        }
    }
    public void setIndex(int i, int j)
    {
        index1 = i;
        index2 = j;
    }
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public ElementButton(int v, String e, String t, @NonNull Context context, boolean locked, int i1, int i2) {

        super(context);

        isWrong = true;
        index1 = i1;
        index2 = i2;
        mValue = v;
        mEnglish = e;
        mTranslation = t;
        //this.setClickable(!locked);
        isLocked = locked;

        //Sets the text of the element button depending on if its audio mode, a given, or an empty cell
        if(mValue == 0)
        {
            this.setText(" ");
        }
        else if(!DataModel.getAudioMode())
        {
            //translationDirection = true -> english to spanish
            if(Sudoku.getTranslationDirection())
                this.setText(mEnglish);
            else
                this.setText(mTranslation);

        }
        else {
            this.setText(String.valueOf(this.getValue()));
        }
        this.setTextColor(Color.BLACK);
    }

    public ElementButton(Context context)
    {
        super(context);
        mValue = 0;
        mEnglish = "";
        mTranslation ="";
        isLocked = true;
        //this.setClickable(false);
    }
}