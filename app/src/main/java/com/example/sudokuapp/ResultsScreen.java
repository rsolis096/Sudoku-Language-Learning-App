package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class ResultsScreen extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);
        //savedInstanceState.remove("CURRENT_BOARD");
        setTitle("Congratulations!");
        // goto MainMenu
        //display
        TextView txt = findViewById(R.id.resultTime);
        txt.setText(Sudoku.getElapsedTime());
        setupBackToMain();
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(ResultsScreen.this,"You can't return to a completed game, press Home to return to home screen!",Toast.LENGTH_LONG).show();
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