package com.example.sudokuapp;

import android.content.Context;

import androidx.annotation.NonNull;

//This class inherits button to add some useful attributes.
public class ElementButton extends androidx.appcompat.widget.AppCompatButton
{
    public int index1;
    public int index2;
    public boolean isLocked;

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setIndex(int i, int j)
    {
        index1 = i;
        index2 = j;
    }


    public ElementButton(@NonNull Context context, int i1, int i2, boolean l) {
        super(context);
        index1 = i1;
        index2 = i2;
        isLocked = l;
    }

    public ElementButton(Context context) {
        super(context);
    }
}
