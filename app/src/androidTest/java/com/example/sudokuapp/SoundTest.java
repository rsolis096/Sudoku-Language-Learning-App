package com.example.sudokuapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import static org.junit.Assert.*;


public class SoundTest {

    private static final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void setAudioMode() {
        Sound.setAudioMode(true);
        assertTrue(Sound.getAudioMode());
    }

    @Test
    public void getAudioMode() {
        assertFalse(Sound.getAudioMode());
        Sound.setAudioMode(true);
        assertTrue(Sound.getAudioMode());
    }

    @Test
    public void playSound() {
        int[] testArr = Sound.playSound(context, "Hola");
        assertEquals("setting locale returned an error",testArr[0], TextToSpeech.LANG_AVAILABLE);
        assertEquals("playing sound returned an error ",testArr[1], TextToSpeech.SUCCESS);

    }
}