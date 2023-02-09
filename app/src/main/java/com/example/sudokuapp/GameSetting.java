package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);

        // once the settings are chosen, click confirm to launch the game
        setupLaunchButton();

        // difficulty level
        // word bank selection
        // fill in english or spanish
        // type in answer or choose answer

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
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameSetting.class);
    }
}