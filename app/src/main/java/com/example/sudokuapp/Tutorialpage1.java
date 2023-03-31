package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class Tutorialpage1 extends AppCompatActivity {

    int page = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_tutorialpage1);

        // close this activity
        setupBackToMain();
        nextPage();

    }

    private void nextPage() {
        Button next = findViewById(R.id.button2);
        next.setOnClickListener(this::onClick);
    }

    // close this activity
    private void setupBackToMain() {
        Button btn = findViewById(R.id.backToMenu);
        btn.setOnClickListener(view -> finish());
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Tutorialpage1.class);
    }

    private void onClick(View view) {
        if (page == 0) {
            TextView text = findViewById(R.id.textView5);
            text.setText(R.string.tutorial_2);
            ImageView image = findViewById(R.id.imageView);
            image.setImageResource(R.drawable.example1);
            page = 1;
        } else if (page == 1) {
            TextView text = findViewById(R.id.textView5);
            text.setText(R.string.tutorial_3);
            ImageView image = findViewById(R.id.imageView);
            image.setImageResource(R.drawable.example3);
            page = 2;
        } else if (page == 2) {
            TextView text = findViewById(R.id.textView5);
            text.setText(R.string.tutorial_text);
            ImageView image = findViewById(R.id.imageView);
            image.setImageResource(R.drawable.example2);
            page = 0;
        }
    }
}