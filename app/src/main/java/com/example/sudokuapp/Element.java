package com.example.sudokuapp;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

public class Element {
    int mValue;
    Button mButton;

    String mEnglish;
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
        mButton = new Button(context);
        if(mValue == 0)
        {
            mButton.setText(" ");
        }
        else
        {
            mButton.setText(String.valueOf(mValue));

        }
        mButton.setTextColor((Color.BLACK));
    }

    public Element()
    {
        mValue = 0;
        mEnglish = "";
        mTranslation ="";
        mButton = null;
    }


}
