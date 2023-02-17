package com.example.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class WordBank extends AppCompatActivity {

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
        ToggleButton btnNumbers = findViewById(R.id.btnNumbers);
        ToggleButton btnFamily = findViewById(R.id.btnfamily);
        ToggleButton btnGreetings = findViewById(R.id.btnGreeting);
        ToggleButton btnFood = findViewById(R.id.btnFood);
        ToggleButton btnDirection = findViewById(R.id.btnDirection);
        btnNumbers.setOnClickListener(view -> {
            Sudoku.setWordBank(0);
            btnNumbers.setChecked(!btnNumbers.isChecked());
        });
        btnFamily.setOnClickListener(view -> {
            Sudoku.setWordBank(1);
            btnFamily.setChecked(!btnFamily.isChecked());
        });
        btnGreetings.setOnClickListener(view -> {
            Sudoku.setWordBank(2);
            btnGreetings.setChecked(!btnGreetings.isChecked());
        });
        btnFood.setOnClickListener(view -> {
            Sudoku.setWordBank(3);
            btnFood.setChecked(!btnFood.isChecked());
        });
        btnDirection.setOnClickListener(view -> {
            Sudoku.setWordBank(4);
            btnDirection.setChecked(!btnDirection.isChecked());
        });
    }
}