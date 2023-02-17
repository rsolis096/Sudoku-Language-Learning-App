package com.example.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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

    }
}