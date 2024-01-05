package com.example.sudokuapp.model;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Sound {
    private static boolean audioMode = false;
    private static TextToSpeech tts;
    public static int[] playSound(Context context, String wordToSpeak)
    {
        //These are used for unit testing, to verify sound is played and language is set
        AtomicInteger language = new AtomicInteger();
        AtomicInteger spoken = new AtomicInteger();
        if(tts != null)
        {
            if (Sudoku.getTranslationDirection()) {
                language.set(tts.setLanguage(new Locale("es", "ME")));
            } else {
                language.set(tts.setLanguage(new Locale("en", "US")));
            }
        }
        else {
            tts = new TextToSpeech(context, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    Log.e("TTS", "TextToSpeech initialized successfully with status: " + status);
                    if (Sudoku.getTranslationDirection()) {
                        tts.setLanguage(new Locale("es", "ME"));
                    } else {
                        tts.setLanguage(new Locale("en", "US"));
                    }
                }
                else{
                    Log.e("TTS", "TextToSpeech initialization failed with status: " + status);
                }
            });
        }
        if(tts != null)
            spoken.set(tts.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null, ""));

       return new int[]{language.get(), spoken.get()};
    }

    public static boolean getAudioMode() {
        return audioMode;
    }
    public static void setAudioMode(boolean b) {
        audioMode = b;
    }
}
