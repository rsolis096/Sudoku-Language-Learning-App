package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // set up the two buttons on main menu to switch to the correct activities
        setupStartButton();
        setupOptionsButton();
        setuptutButton();
    }

    // click options button, go into options page
    private void setupOptionsButton() {
        Button btnOptions = findViewById(R.id.btnOptions);
        btnOptions.setOnClickListener(view -> {
            Intent intent = OptionsPage.makeIntent(MainMenu.this);
            startActivity(intent);
        });
    }
    private void setuptutButton() {
        Button btnTut = findViewById(R.id.btnTut);
        btnTut.setOnClickListener(view -> {
            Intent intent = Tutorialpage1.makeIntent(MainMenu.this);
            startActivity(intent);
        });
    }

    // click start button, go into game settings page
    private void setupStartButton() {
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(view -> {
            Toast.makeText(MainMenu.this, "Please choose your game setting.", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = GameSetting.makeIntent(MainMenu.this);
            startActivity(intent);
        });
    }
}