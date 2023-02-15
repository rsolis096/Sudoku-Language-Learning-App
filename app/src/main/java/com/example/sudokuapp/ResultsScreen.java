package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResultsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);

        // goto MainMenu
        setupBackToMain();
    }

    // close this activity
    private void setupBackToMain() {
        Button btn = findViewById(R.id.btnEndGameReturn);
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(ResultsScreen.this, MainMenu.class);
            startActivity(intent);
        });
    }
}