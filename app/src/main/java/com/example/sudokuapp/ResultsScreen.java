package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);

        setTitle("Congratulations!");
        // goto MainMenu
        //display
        TextView txt = findViewById(R.id.resultTime);
        txt.setText(Sudoku.getElapsedTime());
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