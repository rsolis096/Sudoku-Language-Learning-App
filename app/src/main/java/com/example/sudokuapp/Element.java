package com.example.sudokuapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;


public class Element {
    int mValue;
    ElementButton mButton;



    String mEnglish;

    Boolean mGiven;


    public Boolean getGiven() {
        return mGiven;
    }

    //Setter methods
    public void setValue(int value) {
        mValue = value;
    }

    public void setGiven(Boolean given) {
        mGiven = given;
    }

    public void setLock(boolean val) {
        mButton.setLocked(val);
    }

    public void setIndex(int i1, int i2)
    {
        mButton.setIndex(i1, i2);
    }


    public void setEnglish(String english) {
        mEnglish = english;
    }

    public void setTranslation(String translation) {
        mTranslation = translation;
    }

    String mTranslation;

    //Getter methods
    public int getValue() {
        return mValue;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public String getTranslation() {
        return mTranslation;
    }

    public Element(int v, String e, String t, Context context)
    {
        mValue = v;
        mEnglish = e;
        mTranslation = t;
        mButton = new ElementButton(context);
        mGiven = false;
        if(mValue == 0)
        {
            mButton.setText(" ");
        }
        else
        {
            mButton.setText(mEnglish);

        }
        mButton.setTextColor(Color.BLACK);


    }

    public Element()
    {
        mValue = 0;
        mEnglish = "";
        mTranslation ="";
        mButton = null;
    }




}
