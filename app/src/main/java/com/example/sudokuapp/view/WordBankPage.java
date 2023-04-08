package com.example.sudokuapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sudokuapp.FileIO;
import com.example.sudokuapp.R;
import com.example.sudokuapp.WordBank;

import java.io.IOException;
import java.util.Objects;

public class WordBankPage extends AppCompatActivity {

    public static ToggleButton[] toggleButtons;

    public static Intent makeIntent(Context gameSetting) {
        return new Intent(gameSetting, WordBankPage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.word_bank);

        //Get number of words in file
        if(WordBank.getCustomWordsLength() == 0)
        {
            try {
                String fileContents = FileIO.readFile(this);
                String [] fileContentsLines = fileContents.split("\\n");
                WordBank.setCustomWordsLength(fileContentsLines.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        setupWordBank();
        // back button close activity
        setupClose();
    }

    private void setupClose() {
        Button btnBack = findViewById(R.id.btnback);
        btnBack.setOnClickListener(view -> finish());
    }

    public static void resetWordBank() {
        WordBank.setCheckedCategory(0);
    }

    private void setupWordBank() {

        toggleButtons = new ToggleButton[6];
        toggleButtons[0] = findViewById(R.id.btnNumbers);
        toggleButtons[1] = findViewById(R.id.btnfamily);
        toggleButtons[2] = findViewById(R.id.btnGreeting);
        toggleButtons[3] = findViewById(R.id.btnFood);
        toggleButtons[4] = findViewById(R.id.btnDirection);
        toggleButtons[5] = findViewById(R.id.btnCustomWordBank);

        toggleButtons[WordBank.getCheckedCategory()].setChecked(true);

        toggleButtons[0].setOnClickListener(view-> {
            WordBank.setCategoryIndex(0);
            toggleButtons[WordBank.getCheckedCategory()].setChecked(false);
            toggleButtons[0].setChecked(true);
            WordBank.setCheckedCategory(0);

        });
        toggleButtons[1].setOnClickListener(view -> {
            WordBank.setCategoryIndex(7);
            toggleButtons[WordBank.getCheckedCategory()].setChecked(false);
            toggleButtons[1].setChecked(true);
            WordBank.setCheckedCategory(1);
        });
        toggleButtons[2].setOnClickListener(view -> {
            WordBank.setCategoryIndex(1);
            toggleButtons[WordBank.getCheckedCategory()].setChecked(false);
            toggleButtons[2].setChecked(true);
            WordBank.setCheckedCategory(2);
        });
        toggleButtons[3].setOnClickListener(view -> {
            WordBank.setCategoryIndex(10);
            toggleButtons[WordBank.getCheckedCategory()].setChecked(false);
            toggleButtons[3].setChecked(true);
            WordBank.setCheckedCategory(3);

        });
        toggleButtons[4].setOnClickListener(view -> {
            WordBank.setCategoryIndex(4);
            toggleButtons[WordBank.getCheckedCategory()].setChecked(false);
            toggleButtons[4].setChecked(true);
            WordBank.setCheckedCategory(4);
        });
        toggleButtons[5].setOnClickListener(view -> {
            WordBank.setCategoryIndex(13);
            toggleButtons[WordBank.getCheckedCategory()].setChecked(false);
            toggleButtons[5].setChecked(true);
            WordBank.setCheckedCategory(5);
        });
    }
}