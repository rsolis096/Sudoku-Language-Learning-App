package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GameSetting extends AppCompatActivity {
    int mDifficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);


        // difficulty level
        setupDifficulty();
        // word bank selection
        // fill in english or spanish
        // type in answer or choose answer

        // once the settings are chosen, click confirm to launch the game
        setupLaunchButton();
    }

    private void setupDifficulty() {
        ToggleButton btnEasy = findViewById(R.id.tgBtnEasy);
        ToggleButton btnMedium = findViewById(R.id.tgBtnMedium);
        ToggleButton btnHard = findViewById(R.id.tgBtnHard);

        btnEasy.setOnClickListener(view -> {
            mDifficulty = 0;
            btnEasy.setChecked(true);
            btnMedium.setChecked(false);
            btnHard.setChecked(false);
        });

        btnMedium.setOnClickListener(view -> {
            mDifficulty = 1;
            btnEasy.setChecked(false);
            btnMedium.setChecked(true);
            btnHard.setChecked(false);
        });

        btnHard.setOnClickListener(view -> {
            mDifficulty = 2;
            btnEasy.setChecked(false);
            btnMedium.setChecked(false);
            btnHard.setChecked(true);
        });
    }

    // once the settings are chosen, click confirm to launch the game
    private void setupLaunchButton() {
        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(view -> {
            Toast.makeText(GameSetting.this, String.valueOf(mDifficulty), Toast.LENGTH_SHORT)
                    .show();
            Intent intent = MainActivity.makeIntent(GameSetting.this);
            startActivity(intent);
        });

        Button btnPrevious = findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(view -> finish());
        };

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameSetting.class);
    }
}