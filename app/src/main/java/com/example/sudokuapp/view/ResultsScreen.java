package com.example.sudokuapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.sudokuapp.R;
import com.example.sudokuapp.Sudoku;
import com.example.sudokuapp.WordBank;

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

        //reset all options
        Sudoku.setDifficulty(0);
        WordBank.setCategoryIndex(0);
        Sudoku.setInputMode(false);
        Sudoku.setTranslationDirection(true);
        Sudoku.setGRID_SIZE(9);
        WordBankPage.resetWordBank();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultsScreen.this, GameSetting.class);
        startActivity(intent);
    }


    // close this activity
    private void setupBackToMain() {
        Button btn = findViewById(R.id.btnEndGameReturn);
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(ResultsScreen.this, GameSetting.class);
            startActivity(intent);
        });
    }
}