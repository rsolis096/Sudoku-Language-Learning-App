package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);

        // close this activity
        setupBackToMain();

        // theme/colour
        // leaderboard
        // modify word bank
    }

    // close this activity
    private void setupBackToMain() {
        Button btn = findViewById(R.id.optionsBackToMenu);
        btn.setOnClickListener(view -> finish());
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionsPage.class);
    }
}