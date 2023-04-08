package com.example.sudokuapp;

import androidx.test.filters.SdkSuppress;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Until;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4ClassRunner.class)
@SdkSuppress(minSdkVersion = 18)
public class WordBankAndSettingTest {

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
    public void wordBankAndSettingTest() throws InterruptedException {
        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        assertTrue(start.isEnabled());
        assertTrue(start.isClickable());
        start.click();

        // Press the word bank/category Button
        UiObject2 categoryButton = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnWB")),3000);
        assertTrue(categoryButton.isEnabled());
        assertTrue(categoryButton.isClickable());
        assertEquals(categoryButton.getText().toLowerCase(), "categories");
        categoryButton.click();

        //Check back button
        UiObject2 backBtnCtg = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnback")),3000);
        assertTrue(backBtnCtg.isEnabled());
        assertTrue(backBtnCtg.isClickable());
        assertEquals(backBtnCtg.getText().toLowerCase(), "back");

        //Check all category buttons exist and are clickable
        UiObject2 numbersButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnNumbers"));
        assertTrue(numbersButton.isClickable());
        assertTrue(numbersButton.isEnabled());
        assertTrue(numbersButton.isChecked());

        UiObject2 familyButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnfamily"));
        assertTrue(familyButton.isEnabled());
        assertTrue(familyButton.isClickable());
        assertFalse(familyButton.isChecked());

        UiObject2 greetingsButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnGreeting"));
        assertTrue(greetingsButton.isEnabled());
        assertTrue(greetingsButton.isClickable());
        assertFalse(greetingsButton.isChecked());

        UiObject2 foodButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnFood"));
        assertTrue(foodButton.isEnabled());
        assertTrue(foodButton.isClickable());
        assertFalse(foodButton.isChecked());

        UiObject2 directionButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnDirection"));
        assertTrue(directionButton.isEnabled());
        assertTrue(directionButton.isClickable());
        assertFalse(directionButton.isChecked());

        //Click buttons and check state
        directionButton.click();
        Thread.sleep(500);
        assertTrue(directionButton.isChecked());
        assertFalse(numbersButton.isChecked());
        assertFalse(familyButton.isChecked());
        assertFalse(greetingsButton.isChecked());
        assertFalse(foodButton.isChecked());
        foodButton.click();
        Thread.sleep(500);
        assertFalse(directionButton.isChecked());
        assertFalse(numbersButton.isChecked());
        assertFalse(familyButton.isChecked());
        assertFalse(greetingsButton.isChecked());
        assertTrue(foodButton.isChecked());
        greetingsButton.click();
        Thread.sleep(500);
        assertFalse(directionButton.isChecked());
        assertFalse(numbersButton.isChecked());
        assertFalse(familyButton.isChecked());
        assertTrue(greetingsButton.isChecked());
        assertFalse(foodButton.isChecked());
        familyButton.click();
        Thread.sleep(500);
        assertFalse(directionButton.isChecked());
        assertFalse(numbersButton.isChecked());
        assertTrue(familyButton.isChecked());
        assertFalse(greetingsButton.isChecked());
        assertFalse(foodButton.isChecked());
        numbersButton.click();
        Thread.sleep(500);
        assertFalse(directionButton.isChecked());
        assertTrue(numbersButton.isChecked());
        assertFalse(familyButton.isChecked());
        assertFalse(greetingsButton.isChecked());
        assertFalse(foodButton.isChecked());
        //set button clicked to be not the default
        directionButton.click();

        //Return to Game Settings
        backBtnCtg.click();

        //Check Game Settings View Objects
        UiObject2 difficultyText = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/textView2")),3000);
        assertTrue(difficultyText.isEnabled());
        System.out.println(difficultyText.getText());
        assertEquals(difficultyText.getText(), "Choose a Difficulty:");

        //Check difficulty toggle buttons and change difficulty
        UiObject2 easyTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnEasy"));
        assertTrue(easyTglBtn.isEnabled());
        assertTrue(easyTglBtn.isClickable());
        assertTrue(easyTglBtn.isChecked());

        UiObject2 mediumTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnMedium"));
        assertTrue(mediumTglBtn.isClickable());
        assertTrue(mediumTglBtn.isEnabled());
        assertFalse(mediumTglBtn.isChecked());
        mediumTglBtn.click();

        UiObject2 hardTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnHard"));
        assertTrue(hardTglBtn.isClickable());
        assertTrue(hardTglBtn.isEnabled());
        assertFalse(hardTglBtn.isChecked());

        //Check language text view and change translation direction
        UiObject2 learningMethodText= mDevice.findObject(By.res("com.example.sudokuapp:id/textView3"));
        assertTrue(learningMethodText.isEnabled());
        assertEquals(learningMethodText.getText(),"Choose a Learning Method:");

        //Language toggle 1
        UiObject2 engToSpanTgl = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnEngToSpan"));
        assertTrue(engToSpanTgl.isClickable());
        assertTrue(engToSpanTgl.isEnabled());
        assertEquals(engToSpanTgl.getText(), "ENGLISH TO SPANISH TRANSLATION");
        assertTrue(engToSpanTgl.isChecked());

        //Language toggle 2
        UiObject2 spanToEngTgl = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnSpanToEng"));
        assertTrue(spanToEngTgl.isClickable());
        assertTrue(spanToEngTgl.isEnabled());
        assertEquals(spanToEngTgl.getText(), "SPANISH TO ENGLISH TRANSLATION");
        assertFalse(spanToEngTgl.isChecked());
        spanToEngTgl.click();

        //Check Grid Size toggle buttons and change grid size
        UiObject2 tglBtn4 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn4"));
        assertTrue(tglBtn4.isClickable());
        assertTrue(tglBtn4.isEnabled());
        assertTrue(tglBtn4.isCheckable());
        assertFalse(tglBtn4.isChecked());

        UiObject2 tglBtn6 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn6"));
        assertTrue(tglBtn6.isClickable());
        assertTrue(tglBtn6.isCheckable());
        assertTrue(tglBtn6.isEnabled());
        assertFalse(tglBtn6.isChecked());

        UiObject2 tglBtn9 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn9"));
        assertTrue(tglBtn9.isClickable());
        assertTrue(tglBtn9.isCheckable());
        assertTrue(tglBtn9.isEnabled());
        assertTrue(tglBtn9.isChecked());

        UiObject2 tglBtn12 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn12"));
        assertTrue(tglBtn12.isClickable());
        assertTrue(tglBtn12.isEnabled());
        assertTrue(tglBtn12.isCheckable());
        assertFalse(tglBtn12.isChecked());
        tglBtn6.click();

        UiObject2 manualAndAssistToggle = mDevice.findObject(By.res("com.example.sudokuapp:id/switchInputMode"));
        assertTrue(manualAndAssistToggle.isClickable());
        assertTrue(manualAndAssistToggle.isEnabled());
        assertFalse(manualAndAssistToggle.isChecked());
        manualAndAssistToggle.click();

        //Check all button states
        assertTrue(mediumTglBtn.isChecked());
        assertFalse(easyTglBtn.isChecked());
        assertFalse(hardTglBtn.isChecked());
        assertTrue(spanToEngTgl.isChecked());
        assertFalse(engToSpanTgl.isChecked());
        assertFalse(tglBtn4.isChecked());
        assertTrue(tglBtn6.isChecked());
        assertFalse(tglBtn9.isChecked());
        assertFalse(tglBtn12.isChecked());

        //back to category to make sure word category is still selected
        categoryButton.click();
        backBtnCtg = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnback")),3000);
        numbersButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnNumbers"));
        familyButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnfamily"));
        greetingsButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnGreeting"));
        foodButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnFood"));
        directionButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnDirection"));
        assertTrue(directionButton.isChecked());
        assertFalse(numbersButton.isChecked());
        assertFalse(familyButton.isChecked());
        assertFalse(greetingsButton.isChecked());
        assertFalse(foodButton.isChecked());

        //back to setting to make sure other settings stayed changed
        backBtnCtg.click();
        easyTglBtn = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/tgBtnEasy")),3000);
        mediumTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnMedium"));
        hardTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnHard"));
        engToSpanTgl = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnEngToSpan"));
        spanToEngTgl = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnSpanToEng"));
        tglBtn4 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn4"));
        tglBtn6 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn6"));
        tglBtn9 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn9"));
        tglBtn12 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn12"));
        manualAndAssistToggle = mDevice.findObject(By.res("com.example.sudokuapp:id/switchInputMode"));

        assertTrue(mediumTglBtn.isChecked());
        assertFalse(easyTglBtn.isChecked());
        assertFalse(hardTglBtn.isChecked());
        assertTrue(spanToEngTgl.isChecked());
        assertFalse(engToSpanTgl.isChecked());
        assertFalse(tglBtn4.isChecked());
        assertTrue(tglBtn6.isChecked());
        assertFalse(tglBtn9.isChecked());
        assertFalse(tglBtn12.isChecked());
        assertTrue(manualAndAssistToggle.isChecked());


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
