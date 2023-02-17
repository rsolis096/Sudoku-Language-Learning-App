package com.example.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class WordBank extends AppCompatActivity {

    public int checkedIndex;

    public static Intent makeIntent(Context gameSetting) {
        return new Intent(gameSetting, WordBank.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_bank);

        // back button close activity
        Button btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(view -> finish());
        setupWordBank();
    }

    private void setupWordBank() {

        ToggleButton[] toggleButtons = new ToggleButton[5];
        toggleButtons[0] = findViewById(R.id.btnNumbers);
        toggleButtons[1] = findViewById(R.id.btnfamily);
        toggleButtons[2] = findViewById(R.id.btnGreeting);
        toggleButtons[3] = findViewById(R.id.btnFood);
        toggleButtons[4] = findViewById(R.id.btnDirection);

        toggleButtons[0].setOnClickListener(view-> {
            Sudoku.setWordBank(0);
            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[0].setChecked(true);
            checkedIndex = 0;
        });
        toggleButtons[1].setOnClickListener(view -> {
            Sudoku.setWordBank(7);
            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[1].setChecked(true);
            checkedIndex = 1;
        });
        toggleButtons[2].setOnClickListener(view -> {
            Sudoku.setWordBank(1);
            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[2].setChecked(true);
            checkedIndex = 2;
        });
        toggleButtons[3].setOnClickListener(view -> {
            Sudoku.setWordBank(10);
            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[3].setChecked(true);
            checkedIndex = 3;
        });
        toggleButtons[4].setOnClickListener(view -> {
            Sudoku.setWordBank(4);

            toggleButtons[checkedIndex].setChecked(false);
            toggleButtons[4].setChecked(true);
            checkedIndex = 4;
        });
    }
}