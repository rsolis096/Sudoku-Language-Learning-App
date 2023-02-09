package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GameSetting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);


        // difficulty level
        setupDifficulty();
        // word bank selection
        // fill in english or spanish
        // type in answer or choose answer
        setUpModeSwitch();

        // once the settings are chosen, click confirm to launch the game
        setupLaunchButton();
    }
    //set up for the input mode switch
    private void setUpModeSwitch() {
        Switch swtchInputMode = findViewById(R.id.swtchInputMode);
        swtchInputMode.setOnClickListener(view -> {
            //flips the mode when the switch is triggered based on current state
            if (Sudoku.getInputMode())
                Sudoku.setInputMode(false);
             else
                Sudoku.setInputMode(true);
        });
    }
    //set up for the three difficulty buttons
    private void setupDifficulty() {
        ToggleButton btnEasy = findViewById(R.id.tgBtnEasy);
        ToggleButton btnMedium = findViewById(R.id.tgBtnMedium);
        ToggleButton btnHard = findViewById(R.id.tgBtnHard);
        //sets difficulty to 'easy' and unchecks the other buttons
        btnEasy.setOnClickListener(view -> {
            Sudoku.setDifficulty(0);
            btnEasy.setChecked(true);
            btnMedium.setChecked(false);
            btnHard.setChecked(false);
        });
        //sets difficulty to 'medium' and unchecks the other buttons
        btnMedium.setOnClickListener(view -> {
            Sudoku.setDifficulty(1);
            btnEasy.setChecked(false);
            btnMedium.setChecked(true);
            btnHard.setChecked(false);
        });
        //sets difficulty to 'hard' and unchecks the other buttons
        btnHard.setOnClickListener(view -> {
            Sudoku.setDifficulty(2);
            btnEasy.setChecked(false);
            btnMedium.setChecked(false);
            btnHard.setChecked(true);
        });
    }

    // once the settings are chosen, click confirm to launch the game
    private void setupLaunchButton() {
        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(view -> {
            Toast.makeText(GameSetting.this, "Generating game.", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = MainActivity.makeIntent(GameSetting.this);
            startActivity(intent);
        });
        //return to main menu
        Button btnPrevious = findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(view -> finish());
        };

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameSetting.class);
    }
}