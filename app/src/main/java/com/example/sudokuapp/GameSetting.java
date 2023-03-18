package com.example.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GameSetting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);


        // difficulty level
        setupDifficulty();
        // setup grid size
        setupGridSize();
        // fill in english or spanish
        setupLanguageMode();
        // type in answer or choose answer
        setUpModeSwitch();
        // Set up buttons: back, confirm, word bank
        setupLaunchButton();
    }

    private void setupLanguageMode() {
        Sudoku.setTranslationDirection(true);
        ToggleButton btnEngToSpan = findViewById(R.id.tgBtnEngToSpan);
        ToggleButton btnSpanToEng = findViewById(R.id.tgBtnSpanToEng);
        //sets which direction the sudoku translates
        //true for spanish inputs, false for english inputs
        btnEngToSpan.setOnClickListener(view -> {
            Sudoku.setTranslationDirection(true);
            btnEngToSpan.setChecked(true);
            btnSpanToEng.setChecked(false);

        });
        btnSpanToEng.setOnClickListener(view -> {
            Sudoku.setTranslationDirection(false);
            btnEngToSpan.setChecked(false);
            btnSpanToEng.setChecked(true);
        });

    }

    //set up for the input mode switch
    private void setUpModeSwitch() {
        SwitchCompat switchInputMode = findViewById(R.id.switchInputMode);
        switchInputMode.setOnClickListener(view -> {
            //flips the mode when the switch is triggered based on current state
            Sudoku.setInputMode(!Sudoku.getInputMode());
        });
    }
    //set up for the three difficulty buttons
    private void setupDifficulty() {
        Sudoku.setDifficulty(0);
        ToggleButton btnEasy = findViewById(R.id.tgBtnEasy);
        ToggleButton btnMedium = findViewById(R.id.tgBtnMedium);
        ToggleButton btnHard = findViewById(R.id.tgBtnHard);
        //sets difficulty to 'easy' and unchecks the other buttons
        btnEasy.setOnClickListener(view -> {
            Log.i("Difficulty ", "Called at launch");
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

    private void setupGridSize() {
        Sudoku.setGRID_SIZE(9);
        ToggleButton nineXNine = findViewById(R.id.tgBtn9);
        //ToggleButton sixteenXSixteen = findViewById(R.id.tgBtn16);
        ToggleButton fourXFour = findViewById(R.id.tgBtn4);
        ToggleButton twelveXTwelve = findViewById(R.id.tgBtn12);
        ToggleButton sixXSix = findViewById(R.id.tgBtn6);

        //sets difficulty to 'easy' and unchecks the other buttons
        nineXNine.setOnClickListener(view -> {
            Sudoku.setGRID_SIZE(9);
            nineXNine.setChecked(true);
            //sixteenXSixteen.setChecked(false);
            fourXFour.setChecked(false);
            sixXSix.setChecked(false);
            twelveXTwelve.setChecked(false);
        });
        //sets difficulty to 'medium' and unchecks the other buttons
        /*
        sixteenXSixteen.setOnClickListener(view -> {
            Sudoku.setGRID_SIZE(16);
            nineXNine.setChecked(false);
            //sixteenXSixteen.setChecked(true);
            fourXFour.setChecked(false);
            sixXSix.setChecked(false);
            twelveXTwelve.setChecked(false);
        });
        */

        //sets difficulty to 'hard' and unchecks the other buttons
        fourXFour.setOnClickListener(view -> {
            Sudoku.setGRID_SIZE(4);
            nineXNine.setChecked(false);
            //sixteenXSixteen.setChecked(false);
            fourXFour.setChecked(false);
            sixXSix.setChecked(false);
            twelveXTwelve.setChecked(false);
        });
        twelveXTwelve.setOnClickListener(view -> {
            Sudoku.setGRID_SIZE(12);
            nineXNine.setChecked(false);
            //sixteenXSixteen.setChecked(false);
            fourXFour.setChecked(false);
            sixXSix.setChecked(false);
            twelveXTwelve.setChecked(true);
        });
        sixXSix.setOnClickListener(view -> {
            Sudoku.setGRID_SIZE(6);
            nineXNine.setChecked(false);
            //sixteenXSixteen.setChecked(false);
            fourXFour.setChecked(false);
            sixXSix.setChecked(true);
            twelveXTwelve.setChecked(false);
        });
    }

    // once the settings are chosen, click confirm to launch the game
    private void setupLaunchButton() {
        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(view -> {
            Toast.makeText(GameSetting.this, "Generating game.", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = SudokuPage.makeIntent(GameSetting.this);
            startActivity(intent);
        });
        Button btnWB = findViewById(R.id.btnWB);
        btnWB.setOnClickListener(view -> {
            Intent intent = WordBank.makeIntent(GameSetting.this);
            startActivity(intent);
        });
        //return to main menu
        Button btn = findViewById(R.id.backToMenu);
        btn.setOnClickListener(view ->
        {
            Intent intent = new Intent(GameSetting.this, MainMenu.class);
            startActivity(intent);
        });
     }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameSetting.this, MainMenu.class);
        startActivity(intent);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameSetting.class);
    }
}