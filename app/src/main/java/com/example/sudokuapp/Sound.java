package com.example.sudokuapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Sound {
    private static TextToSpeech tts;
    private static boolean audioMode = false;
    public static int[] playSound(Context context, String wordToSpeak)
    {
        //These are used for unit testing, to verify sound is played and language is set
        AtomicInteger language = new AtomicInteger();
        AtomicInteger spoken = new AtomicInteger();

        tts = new TextToSpeech(context, status -> {
            if(Sudoku.getTranslationDirection())
                language.set(tts.setLanguage(new Locale("es", "ME")));
            else
                language.set(tts.setLanguage(new Locale("en", "US")));
            spoken.set(tts.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null, ""));
        });
        //System.out.println(Arrays.toString(Locale.getISOCountries()));
        tts.shutdown();
       return new int[]{language.get(), spoken.get()};
    }

    public static boolean getAudioMode() {
        return audioMode;
    }
    public static void setAudioMode(boolean b) {
        audioMode = b;
    }
}
