package com.example.sudokuapp;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;

//This class inherits button to add some useful attributes.
public class ElementButton extends androidx.appcompat.widget.AppCompatButton
{
    int mValue;
    String mTranslation;
    String mEnglish;
    public int index1;
    public int index2;
    public boolean isLocked;

    //Getter methods
    public int getValue() {
        return mValue;
    }
    public boolean getLocked() {return isLocked;}
    public String getEnglish() {
        return mEnglish;
    }
    public String getTranslation() {
        return mTranslation;
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
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
    public void setIndex(int i, int j)
    {
        index1 = i;
        index2 = j;
    }


    public ElementButton(int v, String e, String t, @NonNull Context context, boolean locked, int i1, int i2) {
        super(context);
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
            this.setText(mEnglish);

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
