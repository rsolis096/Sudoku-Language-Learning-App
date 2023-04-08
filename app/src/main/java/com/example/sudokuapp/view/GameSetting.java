package com.example.sudokuapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.sudokuapp.R;
import com.example.sudokuapp.Sound;
import com.example.sudokuapp.Sudoku;
import com.example.sudokuapp.WordBank;

import java.util.Objects;

public class GameSetting extends AppCompatActivity {
    private boolean togglesDisabled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_game_setting);

        // difficulty level
        setupDifficulty();
        // setup grid size
        setupGridSize();
        // fill in english or spanish
        setupLanguageMode();
        // type in answer or choose answer
        setUpSwitch();
        // Set up buttons: back, confirm, word bank
        setupLaunchButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resumed");

        //Disable toggle buttons when there arent enough custom words to accomdate a board size
        if(WordBank.getCheckedCategory() == 5)
        {
            togglesDisabled = true;
            DisableToggles();
        }
        //Enable when switch to another grid or when you have enough words
        else if (togglesDisabled)
        {
            togglesDisabled = false;
            EnableToggles();
        }
    }

    public void DisableToggles()
    {
        ToggleButton nineXNine = findViewById(R.id.tgBtn9);
        ToggleButton fourXFour = findViewById(R.id.tgBtn4);
        ToggleButton twelveXTwelve = findViewById(R.id.tgBtn12);
        ToggleButton sixXSix = findViewById(R.id.tgBtn6);

        //If custom word bank is selected
        if(WordBank.getCheckedCategory() == 5)
        {
            System.out.println(WordBank.getCustomWordsLength() + "sdadsadasda");
            if(WordBank.getCustomWordsLength() <= 3)
            {
                fourXFour.setChecked(false);
                nineXNine.setChecked(false);
                sixXSix.setChecked(false);
                twelveXTwelve.setChecked(false);
                fourXFour.setEnabled(false);
                nineXNine.setEnabled(false);
                sixXSix.setEnabled(false);
                twelveXTwelve.setEnabled(false);
                findViewById(R.id.btnConfirm).setClickable(false);
            }
            else if (WordBank.getCustomWordsLength() <= 4) {
                fourXFour.setChecked(true);
                nineXNine.setChecked(false);
                sixXSix.setChecked(false);
                twelveXTwelve.setChecked(false);

                fourXFour.setEnabled(true);
                nineXNine.setEnabled(false);
                sixXSix.setEnabled(false);
                twelveXTwelve.setEnabled(false);
                Sudoku.setGRID_SIZE(4);
            } else if (WordBank.getCustomWordsLength() <= 6) {
                fourXFour.setChecked(false);
                sixXSix.setChecked(true);
                nineXNine.setChecked(false);
                twelveXTwelve.setChecked(false);

                fourXFour.setEnabled(true);
                nineXNine.setEnabled(false);
                sixXSix.setEnabled(true);
                twelveXTwelve.setEnabled(false);
                Sudoku.setGRID_SIZE(6);
            } else if (WordBank.getCustomWordsLength() <= 9) {
                fourXFour.setChecked(false);
                nineXNine.setChecked(true);
                sixXSix.setChecked(false);
                twelveXTwelve.setChecked(false);

                fourXFour.setEnabled(true);
                nineXNine.setEnabled(true);
                sixXSix.setEnabled(true);
                twelveXTwelve.setEnabled(false);
                Sudoku.setGRID_SIZE(9);
            } else if (WordBank.getCustomWordsLength() <= 12) {
                fourXFour.setChecked(false);
                nineXNine.setChecked(true);
                sixXSix.setChecked(false);
                twelveXTwelve.setChecked(false);

                fourXFour.setEnabled(true);
                nineXNine.setEnabled(true);
                sixXSix.setEnabled(true);
                twelveXTwelve.setEnabled(true);
            } else {
                fourXFour.setChecked(false);
                nineXNine.setChecked(true);
                sixXSix.setChecked(false);
                twelveXTwelve.setChecked(false);

                fourXFour.setEnabled(true);
                nineXNine.setEnabled(true);
                sixXSix.setEnabled(true);
                twelveXTwelve.setEnabled(true);
            }
        }
    }

    public void EnableToggles()
    {
        ToggleButton nineXNine = findViewById(R.id.tgBtn9);
        ToggleButton fourXFour = findViewById(R.id.tgBtn4);
        ToggleButton twelveXTwelve = findViewById(R.id.tgBtn12);
        ToggleButton sixXSix = findViewById(R.id.tgBtn6);

        fourXFour.setChecked(false);
        sixXSix.setChecked(false);
        nineXNine.setChecked(true);
        twelveXTwelve.setChecked(false);

        fourXFour.setEnabled(true);
        nineXNine.setEnabled(true);
        sixXSix.setEnabled(true);
        twelveXTwelve.setEnabled(true);
        findViewById(R.id.btnConfirm).setClickable(true);
    }


    private void setupLanguageMode() {
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
    private void setUpSwitch() {
        SwitchCompat switchInputMode = findViewById(R.id.switchInputMode);
        switchInputMode.setChecked(false);
        switchInputMode.setOnClickListener(view -> {
            //flips the mode when the switch is triggered based on current state
            Sudoku.setInputMode(!Sudoku.getInputMode());
        });

        SwitchCompat switchAudioMode = findViewById(R.id.switchAudio);
        switchAudioMode.setChecked(false);
        switchAudioMode.setOnClickListener(view -> {
            //flips the mode when the switch is triggered based on current state
            Sound.setAudioMode(true);
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
        //sets difficulty to 'hard' and unchecks the other buttons
        fourXFour.setOnClickListener(view -> {
            Sudoku.setGRID_SIZE(4);
            nineXNine.setChecked(false);
            //sixteenXSixteen.setChecked(false);
            fourXFour.setChecked(true);
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
            Intent intent = WordBankPage.makeIntent(GameSetting.this);
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