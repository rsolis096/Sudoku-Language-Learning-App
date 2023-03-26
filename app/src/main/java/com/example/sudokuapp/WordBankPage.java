package com.example.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class WordBankPage extends AppCompatActivity {

    public static ToggleButton[] toggleButtons;

    public static Intent makeIntent(Context gameSetting) {
        return new Intent(gameSetting, WordBankPage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_bank);
        setupWordBank();
        // back button close activity
        setupClose();
    }

    private void setupClose() {
        Button btnBack = findViewById(R.id.btnback);
        btnBack.setOnClickListener(view -> finish());
    }

    public static void resetWordBank() {
        DataModel.setCheckedCategory(0);
    }

    private void setupWordBank() {

        toggleButtons = new ToggleButton[6];
        toggleButtons[0] = findViewById(R.id.btnNumbers);
        toggleButtons[1] = findViewById(R.id.btnfamily);
        toggleButtons[2] = findViewById(R.id.btnGreeting);
        toggleButtons[3] = findViewById(R.id.btnFood);
        toggleButtons[4] = findViewById(R.id.btnDirection);
        toggleButtons[5] = findViewById(R.id.btnCustomWordBank);

        toggleButtons[DataModel.getCheckedCategory()].setChecked(true);

        toggleButtons[0].setOnClickListener(view-> {
            DataModel.setCategoryIndex(0);
            toggleButtons[DataModel.getCheckedCategory()].setChecked(false);
            toggleButtons[0].setChecked(true);
            DataModel.setCheckedCategory(0);

        });
        toggleButtons[1].setOnClickListener(view -> {
            DataModel.setCategoryIndex(7);
            toggleButtons[DataModel.getCheckedCategory()].setChecked(false);
            toggleButtons[1].setChecked(true);
            DataModel.setCheckedCategory(1);
        });
        toggleButtons[2].setOnClickListener(view -> {
            DataModel.setCategoryIndex(1);
            toggleButtons[DataModel.getCheckedCategory()].setChecked(false);
            toggleButtons[2].setChecked(true);
            DataModel.setCheckedCategory(2);
        });
        toggleButtons[3].setOnClickListener(view -> {
            DataModel.setCategoryIndex(10);
            toggleButtons[DataModel.getCheckedCategory()].setChecked(false);
            toggleButtons[3].setChecked(true);
            DataModel.setCheckedCategory(3);

        });
        toggleButtons[4].setOnClickListener(view -> {
            DataModel.setCategoryIndex(4);
            toggleButtons[DataModel.getCheckedCategory()].setChecked(false);
            toggleButtons[4].setChecked(true);
            DataModel.setCheckedCategory(4);
        });
        toggleButtons[5].setOnClickListener(view -> {
            DataModel.setCategoryIndex(13);
            toggleButtons[DataModel.getCheckedCategory()].setChecked(false);
            toggleButtons[5].setChecked(true);
            DataModel.setCheckedCategory(5);
        });
    }
}