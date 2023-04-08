package com.example.sudokuapp;

import android.app.AlertDialog;
import android.content.Context;

public class AssistedInputButton extends androidx.appcompat.widget.AppCompatButton {
    public int index;
    public boolean translationDirection;
    private ElementButton callingButton;
    private AlertDialog AssociatedAlertDialog;

    //Setters
    public void setCallingButton(ElementButton callingButton) {
        this.callingButton = callingButton;
    }

    public void setAssociatedAlertDialog(AlertDialog setAssociatedAlertDialog) {
        this.AssociatedAlertDialog = setAssociatedAlertDialog;
    }

    public void setIndex(int i){index = i;}


    //Getters
    public ElementButton getCallingButton() {
        return callingButton;
    }

    public AlertDialog getAssociatedAlertDialog() {
        return AssociatedAlertDialog;
    }

    public int getIndex(){return index;}

    public AssistedInputButton(Context context)
    {
        super(context);
        index = 0;
        translationDirection = false;
        AssociatedAlertDialog = null;
        callingButton = null;
    }

}
