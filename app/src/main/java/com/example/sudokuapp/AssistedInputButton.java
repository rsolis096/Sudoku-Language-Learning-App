package com.example.sudokuapp;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Button;

public class AssistedInputButton extends androidx.appcompat.widget.AppCompatButton {
    public int index;
    public int value;
    public boolean translationDirection;

    public void setCallingButton(ElementButton callingButton) {
        this.callingButton = callingButton;
    }

    public ElementButton callingButton;

    public AlertDialog AssociatedAlertDialog;


    public void setAssociatedAlertDialog(AlertDialog setAssociatedAlertDialog) {
        this.AssociatedAlertDialog = setAssociatedAlertDialog;
    }

    public void setIndex(int i){index = i;}
    public void setTranslationDirection(boolean val) {translationDirection = val;}

    AssistedInputButton(Context context)
    {
        super(context);
        index = 0;
        translationDirection = false;
        AssociatedAlertDialog = null;
        callingButton = null;
    }


}
