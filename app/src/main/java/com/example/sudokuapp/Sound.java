package com.example.sudokuapp;

import android.content.Context;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;

import java.util.Arrays;
import java.util.Locale;

public class Sound {
    private static TextToSpeech tts;
    public static void playSound(Context context, String wordToSpeak)
    {
        System.out.println("SOUND SHOULD PLAY");
        tts = new TextToSpeech(context, status -> {
            if(Sudoku.getTranslationDirection())
                tts.setLanguage(new Locale("es", "ME"));
            else
                tts.setLanguage(new Locale("en", "US"));
            tts.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null, "");
        });
        //System.out.println(Arrays.toString(Locale.getISOCountries()));
        tts.shutdown();
    }

}
