package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Tutorialpage1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorialpage1);

        // close this activity
        setupBackToMain();

    }

    // close this activity
    private void setupBackToMain() {
        Button btn = findViewById(R.id.backToMenu);
        btn.setOnClickListener(view -> finish());
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Tutorialpage1.class);
    }
}