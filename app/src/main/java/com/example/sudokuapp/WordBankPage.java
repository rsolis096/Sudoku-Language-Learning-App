package com.example.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class WordBankPage extends AppCompatActivity {

    public static int checkedIndex = 0;
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
        Button btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(view -> finish());
    }

    public static void resetWordBank() {
        checkedIndex = 0;
    }

    private void setupWordBank() {

        toggleButtons = new ToggleButton[5];
        toggleButtons[0] = findViewById(R.id.btnNumbers);
        toggleButtons[1] = findViewById(R.id.btnfamily);
        toggleButtons[2] = findViewById(R.id.btnGreeting);
        toggleButtons[3] = findViewById(R.id.btnFood);
        toggleButtons[4] = findViewById(R.id.btnDirection);

        toggleButtons[checkedIndex].setChecked(true);

        toggleButtons[0].setOnClickListener(view-> {
            WordBank.setValue(0);
            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[0].setChecked(true);
            checkedIndex = 0;
        });
        toggleButtons[1].setOnClickListener(view -> {
            WordBank.setValue(7);
            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[1].setChecked(true);
            checkedIndex = 1;
        });
        toggleButtons[2].setOnClickListener(view -> {
            WordBank.setValue(1);
            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[2].setChecked(true);
            checkedIndex = 2;
        });
        toggleButtons[3].setOnClickListener(view -> {
            WordBank.setValue(10);
            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[3].setChecked(true);
            checkedIndex = 3;
        });
        toggleButtons[4].setOnClickListener(view -> {
            WordBank.setValue(4);

            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[4].setChecked(true);
            checkedIndex = 4;
        });
    }
}