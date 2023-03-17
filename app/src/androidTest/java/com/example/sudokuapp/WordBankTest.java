package com.example.sudokuapp;

import androidx.test.filters.SdkSuppress;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Chronometer;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Until;
import static org.junit.Assert.*;
import java.util.Objects;

@RunWith(AndroidJUnit4ClassRunner.class)
@SdkSuppress(minSdkVersion = 18)
public class WordBankTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.example.sudokuapp";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertNotNull(launcherPackage);
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);

    }

    @Test
    public void checkPreconditions() {
        assertNotNull(mDevice);
    }

    @Test
    public void wordBankTest() throws InterruptedException {
        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        assertTrue(start.isEnabled());
        assertTrue(start.isClickable());
        start.click();

        Thread.sleep(1000);

        // Press the word bank/category Button
        UiObject2 categoryButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnWB"));
        assertTrue(categoryButton.isEnabled());
        assertTrue(categoryButton.isClickable());
        assertEquals(categoryButton.getText(), "CATEGORIES");
        categoryButton.click();

        Thread.sleep(1000);

        //Check back button
        UiObject2 backBtnCtg = mDevice.findObject(By.res("com.example.sudokuapp:id/btnback"));
        assertTrue(backBtnCtg.isEnabled());
        assertTrue(backBtnCtg.isClickable());
        assertEquals(backBtnCtg.getText(), "BACK");

        //Check all category buttons exist and are clickable
        UiObject2 numbersButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnNumbers"));
        assertTrue(numbersButton.isClickable());
        assertTrue(numbersButton.isEnabled());

        UiObject2 familyButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnfamily"));
        assertTrue(familyButton.isEnabled());
        assertTrue(familyButton.isClickable());

        UiObject2 greetingsButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnGreeting"));
        assertTrue(greetingsButton.isEnabled());
        assertTrue(greetingsButton.isClickable());

        UiObject2 foodButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnFood"));
        assertTrue(foodButton.isEnabled());
        assertTrue(foodButton.isClickable());

        UiObject2 directionButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnDirection"));
        assertTrue(directionButton.isEnabled());
        assertTrue(directionButton.isClickable());

        //Return to Game Settings
        backBtnCtg.click();

        //Wait for app to catch up
        Thread.sleep(1000);

        //Check Game Settings View Objects
        UiObject2 difficultyText = mDevice.findObject(By.res("com.example.sudokuapp:id/textView2"));
        assertTrue(difficultyText.isEnabled());
        System.out.println(difficultyText.getText());
        assertEquals(difficultyText.getText(), "Choose a Difficulty:");

        //Check difficulty toggle buttons
        UiObject2 easyTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnEasy"));
        assertTrue(easyTglBtn.isEnabled());
        assertTrue(easyTglBtn.isClickable());

        UiObject2 mediumTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnMedium"));
        assertTrue(mediumTglBtn.isClickable());
        assertTrue(mediumTglBtn.isEnabled());

        UiObject2 hardTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnHard"));
        assertTrue(hardTglBtn.isClickable());
        assertTrue(hardTglBtn.isEnabled());

        //Check language text view
        UiObject2 learningMethodText= mDevice.findObject(By.res("com.example.sudokuapp:id/textView3"));
        assertTrue(learningMethodText.isEnabled());
        assertEquals(learningMethodText.getText(),"Choose a Learning Method:");

        //Language toggle 1
        UiObject2 engToSpanTgl = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnEngToSpan"));
        assertTrue(engToSpanTgl.isClickable());
        assertTrue(engToSpanTgl.isEnabled());
        assertEquals(engToSpanTgl.getText(), "ENGLISH TO SPANISH TRANSLATION");

        //Language toggle 2
        UiObject2 spanToEngTgl = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnSpanToEng"));
        assertTrue(spanToEngTgl.isClickable());
        assertTrue(spanToEngTgl.isEnabled());
        assertEquals(spanToEngTgl.getText(), "SPANISH TO ENGLISH TRANSLATION");

        //Check Grid Size toggle buttons
        UiObject2 tglBtn4 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn4"));
        assertTrue(tglBtn4.isClickable());
        assertTrue(tglBtn4.isEnabled());
        assertTrue(tglBtn4.isCheckable());

        UiObject2 tglBtn6 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn6"));
        assertTrue(tglBtn6.isClickable());
        assertTrue(tglBtn6.isCheckable());
        assertTrue(tglBtn6.isEnabled());

        UiObject2 tglBtn9 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn9"));
        assertTrue(tglBtn9.isClickable());
        assertTrue(tglBtn9.isCheckable());
        assertTrue(tglBtn9.isEnabled());
        assertTrue(tglBtn9.isChecked());

        UiObject2 tglBtn12 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn12"));
        assertTrue(tglBtn12.isClickable());
        assertTrue(tglBtn12.isEnabled());
        assertTrue(tglBtn12.isCheckable());

        /*
        UiObject2 tglBtn16 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn16"));
        assertTrue(tglBtn16.isClickable());
        assertTrue(tglBtn16.isEnabled());
        assertTrue(tglBtn16.isCheckable());*/

        UiObject2 manualAndAssistToggle = mDevice.findObject(By.res("com.example.sudokuapp:id/switchInputMode"));
        assertTrue(manualAndAssistToggle.isClickable());
        assertTrue(manualAndAssistToggle.isEnabled());

        UiObject2 confirm = mDevice.findObject(By.res("com.example.sudokuapp:id/btnConfirm"));
        assertTrue(confirm.isEnabled());
        assertTrue(confirm.isClickable());

    }



    /*
     * Ignore this function but don't delete it
     * From BasicSample linked in https://developer.android.com/training/testing/other-components/ui-automator
     * BasicSample: https://github.com/android/testing-samples/tree/main/ui/uiautomator/BasicSample
     * Uses package manager to find the package name of the Device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
