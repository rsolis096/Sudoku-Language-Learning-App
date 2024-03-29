package com.example.sudokuapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.example.sudokuapp.R;

import java.util.Objects;

public class GameOptions extends AppCompatActivity {


    Switch switcher;
    boolean nightMode;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setupOptionsButton();
        setupBackToMain();
        changeTheme();
    }

private void changeTheme(){
        switcher = findViewById(R.id.switchTheme);
        //used to save mode in case game is exited and revisited
        MainMenu.sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = MainMenu.sharedPreferences.getBoolean("night", false); //light mode selection by default

        if(nightMode){
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switcher.setOnClickListener(view -> {

            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = MainMenu.sharedPreferences.edit();
                editor.putBoolean("night", false);
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = MainMenu.sharedPreferences.edit();
                editor.putBoolean("night", true);
            }
            editor.apply();
        });
}
private void setupBackToMain() {
        Button btn = findViewById(R.id.backButton);
        btn.setOnClickListener(view -> finish());
    }

private void setupOptionsButton() {
        Button btnCustom_Bank = findViewById(R.id.buttonCustomBank);
        btnCustom_Bank.setOnClickListener(view -> {
            Intent intent = CustomWordsPage.makeIntent(GameOptions.this);
            startActivity(intent);
        });
}

public static Intent makeIntent(Context context) {
        return new Intent(context, GameOptions.class);
    }
}