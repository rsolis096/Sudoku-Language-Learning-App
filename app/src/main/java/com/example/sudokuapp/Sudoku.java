package com.example.sudokuapp;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;


public class Sudoku extends AppCompatActivity implements Serializable
{
    public static final WordBank bank = new WordBank();
    private static int GRID_SIZE;
    private static ElementButton[][] mSudokuBoard;
    private static Pair<Integer, Integer> boxSize;
    private final ElementButton[][] mSudokuAnswerBoard;
    private final transient Chronometer mTimer;
    private static int difficulty;
    public static boolean manual;
    private static boolean translationDirection = true;
    private static int mRemainingCells;
    private static long minutes, seconds;
    //This keeps track of the cells filled by the user. Used to retain game board fater screen rotation
    public static LinkedList<ElementButton> userInputButtons;

    //setters for game settings
    public static void setDifficulty(int d) {difficulty = d;}
    public static void setInputMode(boolean m) {manual = m;}
    public static void setTranslationDirection(boolean t) {translationDirection = t;}
    public static void decreaseRemainingCells() {--mRemainingCells;}
    public static void increaseRemainingCells() {++mRemainingCells;}
    public static void setGRID_SIZE(int size) {
        GRID_SIZE = size;
        Pair<Integer, Integer> temp = new Pair<>(9, 9);
        if (Math.sqrt(size) % 1 == 0) {
            temp = new Pair<>((int) Math.sqrt(size), (int) Math.sqrt(size));
        } else if (size == 12) {
            temp = new Pair<>(3,4);
        } else if (size == 6) {
            temp = new Pair<>(2,3);
        }
        boxSize = temp;
    }
    public static void setRemainingCells(int remainingCells) {mRemainingCells = remainingCells;}
    public static Pair<Integer, Integer> getBoxSize() {return boxSize;}
    public static ElementButton getElement(int rows, int cols) {return mSudokuBoard[rows][cols];}
    public static WordBank getBank() {return bank;}
    public static int getDifficulty() {return difficulty;}
    public static boolean getInputMode() {return manual;}
    public static boolean getTranslationDirection() {return translationDirection;}
    public static String getElapsedTime() {
        //returns time in H:MM:SS or MM:SS format
        String sec;
        int hours = 0;
        if(minutes >= 60) {
            hours = (int) Math.floor(minutes / 60.0);
            minutes -= (hours * 60L);
        }
        //if seconds less than 10, add a 0 before for proper display
        if(seconds < 10) {sec = "0" + seconds;}
        //otherwise just convert to string
        else {sec = seconds+""; }
        //check if an hour has passed, otherwise don't display hours
        if(minutes < 60) {return minutes + ":" + sec;}
        else {return hours + ":" + minutes + ":" + sec;}
    }
    public static int getRemainingCells() {
        return mRemainingCells;
    }
    public static int getGridSize() {
        return GRID_SIZE;
    }
    public Chronometer getTimer() {
        return mTimer;
    }

    public Sudoku(Context THIS, Chronometer t) throws IOException {

        //Default GRID_SIZE
        setGRID_SIZE(getGridSize());
        if(getGridSize() == 0)
        {
            setGRID_SIZE(9);
        }
        userInputButtons = new LinkedList<>();
        mTimer = t;

        mTimer.start();
        mTimer.setOnChronometerTickListener(chronometer -> {
            //convert base time to readable minutes and seconds
            minutes = ((SystemClock.elapsedRealtime() - t.getBase())/1000) / 60;
            seconds = ((SystemClock.elapsedRealtime() - t.getBase())/1000) % 60;
        });
        //creates a word bank object that contains english and spanish arrays of the word bank
        bank.generateWordBank(GRID_SIZE, difficulty, THIS);

        //Builds a valid integer board
        //GenerateBoard class has member 2d arrays:
        GenerateBoard generatedBoard = new GenerateBoard(getBoxSize().first, getBoxSize().second, getDifficulty());
        generatedBoard.createBoard();

        //Initialize number of unfilled cells
        setRemainingCells(generatedBoard.getEmptyCells());

        //mSudokuBoard is the play board of ElementButtons (what the player interacts with)
        mSudokuBoard = new ElementButton[getGridSize()][getGridSize()];
        //mSudokuAnswerBoard is a complete board of ElementButtons used to reference for input checking
        mSudokuAnswerBoard = new ElementButton[getGridSize()][getGridSize()];

        //Copy mGeneratedBoard into mSudokuBoard, skip empty cells which == 0
        for(int rows = 0; rows < getGridSize(); rows++)
        {
            for(int cols = 0; cols < getGridSize(); cols++)
            {
                //This initializes ElementButtons that correspond to given cells
                if(GenerateBoard.mGeneratedBoard[rows][cols] != 0)
                {

                    mSudokuBoard[rows][cols] =
                            new ElementButton(
                                GenerateBoard.mGeneratedBoard[rows][cols],
                                bank.getEnglish()[GenerateBoard.mGeneratedBoard[rows][cols] - 1],
                                bank.getSpanish()[GenerateBoard.mGeneratedBoard[rows][cols] - 1],
                                THIS,
                                true,
                                rows,
                                cols
                            );
                    //OnClickListener only made for given cells if in audio mode
                    if(Sound.getAudioMode())
                        mSudokuBoard[rows][cols].setOnClickListener(new AudioElementButtonListener());
                }
                //This initializes ElementButtons that correspond to empty Cells
                else
                {
                    mSudokuBoard[rows][cols] = new ElementButton(0, "", "", THIS, false, rows, cols);
                    //Set listener only for buttons that can change
                    mSudokuBoard[rows][cols].setOnClickListener(new ElementButtonListener());
                }
                //This initializes the answer board of ElementButtons used to be matched with mSudokuBoard for error checking
                mSudokuAnswerBoard[rows][cols] =
                        new ElementButton(
                                GenerateBoard.mAnswerBoard[rows][cols],
                                bank.getEnglish()[GenerateBoard.mAnswerBoard[rows][cols] - 1],
                                bank.getSpanish()[GenerateBoard.mAnswerBoard[rows][cols] - 1],
                                THIS,
                                true,
                                rows,
                                cols);
            }
        }
    }
    //pairs each cell to a proper border drawable to make the board look like sudoku
    public static void setCellDesign(ElementButton cell) {
        int rowCoordinate = cell.getIndex1();
        int colCoordinate = cell.getIndex2();
        Context tempContext = cell.getContext();

        if ((colCoordinate + 1) % boxSize.first == 0) {
            if ((rowCoordinate + 1) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border_thick_bottom_right));
            } else if ((rowCoordinate) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border_thick_top_right));
            } else {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border_thick_right));
            }
        } else if ((colCoordinate) % boxSize.first == 0) {
            if ((rowCoordinate + 1) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border_thick_bottom_left));
            } else if ((rowCoordinate) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border_thick_top_left));
            } else {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border_thick_left));
            }
        } else {
            if ((rowCoordinate + 1) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border_thick_bottom));
            } else if ((rowCoordinate) % boxSize.second == 0) {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border_thick_top));
            } else {
                cell.setBackground(AppCompatResources.getDrawable(tempContext, R.drawable.border));
            }
        }
    }
}


