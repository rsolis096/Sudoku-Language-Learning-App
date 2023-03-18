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
public class SudokuPage4x4Test {

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
    public void assistModeCheck() throws InterruptedException{
        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        start.click();

        //Make sure 4x4 toggle button exists
        UiObject2 toggleButton4x4 = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/tgBtn4")),3000);
        assertTrue("Toggle button is not enabled", toggleButton4x4.isEnabled());
        assertTrue("Toggle button is not checkable", toggleButton4x4.isCheckable());
        toggleButton4x4.click();
        assertTrue("4x4 button is not checked", toggleButton4x4.isChecked());

        // Timers to slow down test, fails otherwise.
        Thread.sleep(1000);

        // Press the confirm button
        UiObject2 confirm = mDevice.findObject(By.res("com.example.sudokuapp:id/btnConfirm"));
        assertTrue("Confirm button is not enabled", confirm.isEnabled());
        assertTrue("Confirm button is not clickable", confirm.isClickable());
        confirm.click();

        //Check Timer (wait for it to appear on screen, going to fast will cause fail)
        UiObject2 cTimer = mDevice.wait(Until.findObject(By.clazz(Chronometer.class)),3000);
        assertTrue("Timer is not enabled", cTimer.isEnabled());
        //Get text to compare for later to make sure it is counting up
        String timerText = cTimer.getText();

        //Wait one second for the game to load before continuing with further actions
        Thread.sleep(1000);

        //Check the table rows in the main game board
        for(int i =0; i < 4; i++)
        {
            UiObject2 tableRows = mDevice.findObject(By.desc("tableRowTag" + i));
            assertTrue("Table Row " + i + " is not enabled", tableRows.isEnabled());
        }

        //** Test the ElementButtons in the table **//
        // Get a reference to the sudokutable (TableLayout)
        UiObject2 tableLayout = mDevice.findObject(By.res("com.example.sudokuapp:id/sudoku_table"));
        checkElementButtons(tableLayout);

        //Slow down next code to give time to scroll
        Thread.sleep(1000);

        //Verify timer is counting up
        assertTrue("Timer is not enabled", cTimer.isEnabled());
        assertNotEquals(cTimer.getText(), timerText);

        //Check single empty cell for functionality of assisted mode
        UiObject2 emptyCell = mDevice.findObject(By.desc("emptyCell"));
        assertTrue("Empty Cell is not clickable", emptyCell.isClickable());
        emptyCell.click();

        //Check all the table rows that pop up in assist mode
        tableLayout = mDevice.wait(Until.findObject(By.desc("assistDialogLayout")),3000);
        UiObject2 assistButtonToSelect = null;
        for(UiObject2 individualButton : tableLayout.getChildren())
        {
            for(UiObject2 singleButton : individualButton.getChildren())
            {
                assertTrue("Button is not clickable", singleButton.isClickable());
                assertTrue("Button is not enabled", singleButton.isEnabled());
                assistButtonToSelect = singleButton;
            }
        }

        //Select an assistedButton
        assert assistButtonToSelect != null;
        String assistButtonSelectedText = assistButtonToSelect.getText();
        assistButtonToSelect.click();

        Thread.sleep(500);

        //Comparing with a string variable because assistButtonToSelect is off screen
        assertEquals(emptyCell.getText(),assistButtonSelectedText);

        // Hold to ensure app is where its expected to be
        Thread.sleep(1000);
    }

    @Test
    public void manualModeCheck() throws InterruptedException {

        //**************************************//
        //          MANUAL INPUT                //
        //**************************************//

        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        start.click();

        //Make sure 4x4 toggle button exists
        UiObject2 toggleButton4x4 = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/tgBtn4")),3000);
        assertTrue("Toggle button is not enabled", toggleButton4x4.isEnabled());
        assertTrue("Toggle button is not checkable", toggleButton4x4.isCheckable());
        toggleButton4x4.click();
        assertTrue("Toggle button is not checked", toggleButton4x4.isChecked());

        UiObject2 manualSwitch = mDevice.findObject(By.res("com.example.sudokuapp:id/switchInputMode"));
        assertTrue("manual switch is not enabled", manualSwitch.isEnabled());
        assertTrue("manual switch is not checkable", manualSwitch.isCheckable());
        manualSwitch.click();
        assertTrue("manual switch is not checked", manualSwitch.isChecked());

        // Timers to slow down test, fails otherwise.
        Thread.sleep(1000);

        // Press the confirm button
        UiObject2 confirm = mDevice.findObject(By.res("com.example.sudokuapp:id/btnConfirm"));
        assertTrue("Confirm button is not enabled", confirm.isEnabled());
        assertTrue("Confirm button is not clickable", confirm.isClickable());
        confirm.click();

        //Check Timer (wait for it to appear on screen, going to fast will cause fail)
        UiObject2 cTimer = mDevice.wait(Until.findObject(By.clazz(Chronometer.class)),3000);
        assertTrue("Timer is not enabled", cTimer.isEnabled());
        //Get text to compare for later to make sure it is counting up
        String timerText = cTimer.getText();

        //Wait one second for the game to load before continuing with further actions
        Thread.sleep(1000);

        //Check the table rows in the main game board
        for(int i =0; i < 4; i++)
        {
            UiObject2 tableRows = mDevice.findObject(By.desc("tableRowTag" + i));
            assertTrue("Table Row " + i + " is not enabled", tableRows.isEnabled());
        }

        //** Test the ElementButtons in the table **//
        // Get a reference to the sudokutable (TableLayout)
        UiObject2 tableLayout = mDevice.findObject(By.res("com.example.sudokuapp:id/sudoku_table"));
        checkElementButtons(tableLayout);

        Thread.sleep(1000);

        //Verify timer is counting up
        assertTrue("Timer is not enabled", cTimer.isEnabled());
        assertNotEquals(cTimer.getText(), timerText);

        //Check single empty cell for functionality of manual input
        UiObject2 emptyCell = mDevice.findObject(By.desc("emptyCell"));
        assertTrue("Empty Cell is not clickable", emptyCell.isClickable());
        emptyCell.click();

        //Check all resources in manual input pop up
        UiObject2 resources = mDevice.wait(Until.findObject(By.res("android:id/alertTitle")),3000);
        assertEquals("Enter Word should be shown.", "Enter Word:", resources.getText());
        resources = mDevice.findObject(By.res("android:id/button3"));
        assertTrue("clear answer is not enabled", resources.isEnabled());
        assertTrue("clear answer is not clickable", resources.isClickable());
        resources = mDevice.findObject(By.res("android:id/button2"));
        assertTrue("cancel button is not enabled", resources.isEnabled());
        assertTrue("cancel button is not clickable", resources.isClickable());
        resources = mDevice.findObject(By.res("android:id/button1"));
        assertTrue("ok button is not enabled", resources.isEnabled());
        assertTrue("ok button is not clickable", resources.isClickable());

        //Check text edit function
        UiObject2 editText = mDevice.findObject(By.clazz("android.widget.EditText"));
        assertTrue("edit text field should be clickable.", editText.isClickable());
        editText.setText("Uno");
        resources.click();
        Thread.sleep(500);
        //confirm the change
        String prevAnswer = emptyCell.getText();
        assertEquals("previously empty cell should be displaying the answer.", "UNO", prevAnswer);

        //Check new valid input replace old input
        emptyCell.click();
        Thread.sleep(500);
        editText = mDevice.findObject(By.clazz("android.widget.EditText"));
        assertTrue("edit text field should be clickable.", editText.isClickable());
        editText.setText("dos");
        resources = mDevice.findObject(By.res("android:id/button1"));
        resources.click();
        Thread.sleep(500);
        //confirm the change
        prevAnswer = emptyCell.getText();
        assertEquals("cell should display new valid input.", "DOS", prevAnswer);

        //try invalid input, result should be the same as before the input.
        emptyCell.click();
        editText = mDevice.findObject(By.clazz("android.widget.EditText"));
        editText.setText("Un");
        resources = mDevice.findObject(By.res("android:id/button1"));
        resources.click();
        Thread.sleep(500);
        //confirm no change
        assertEquals("invalid input should not change the text of the cell.", prevAnswer, emptyCell.getText());

        //try cancel button
        emptyCell.click();
        resources = mDevice.findObject(By.res("android:id/button2"));
        resources.click();
        Thread.sleep(500);
        //confirm no change
        assertEquals("invalid input should not change the text of the cell.", prevAnswer, emptyCell.getText());

        //try clear answer
        emptyCell.click();
        resources = mDevice.findObject(By.res("android:id/button3"));
        resources.click();
        Thread.sleep(500);
        //is cell null after clearing?
        assertNull("cell should be empty.", emptyCell.getText());

        //check solve button
        resources = mDevice.findObject(By.res("com.example.sudokuapp:id/solveButton"));
        assertTrue("solve button is not enabled", resources.isEnabled());
        assertTrue("solve button is not clickable", resources.isClickable());
        resources.click();
        //check pop up for game completion
        UiObject2 textV = mDevice.wait(Until.findObject(By.res("android:id/alertTitle")),3000);
        assertEquals("Game finished should be displayed.", "Game Finished!", textV.getText());
        resources = mDevice.findObject(By.res("android:id/button1"));
        assertTrue("continue button is not enabled", resources.isEnabled());
        assertTrue("continue button is not clickable", resources.isClickable());
        resources.click();

        //check result screen
        textV = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/textView4")),3000);
        assertEquals("Game finished should be displayed.", "Congratulations, you completed the puzzle!", textV.getText());
        textV = mDevice.findObject(By.res("com.example.sudokuapp:id/resultTime"));
        assertTrue("result time should be shown.", textV.isEnabled());
        resources = mDevice.findObject(By.res("com.example.sudokuapp:id/btnEndGameReturn"));
        assertTrue("home button is not enabled", resources.isEnabled());
        assertTrue("home button is not clickable", resources.isClickable());
        resources.click();



        // Hold to ensure app is where its expected to be
        Thread.sleep(1000);
    }

    @Test
    public void combinationCheck() throws InterruptedException {
        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        start.click();

        //Make sure 4x4 toggle button exists
        UiObject2 toggleButton4x4 = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/tgBtn4")),3000);
        toggleButton4x4.click();
        assertTrue("4x4 button is not checked", toggleButton4x4.isChecked());

        //manual mode to check if the correct category of words was used
        UiObject2 manualSwitch = mDevice.findObject(By.res("com.example.sudokuapp:id/switchInputMode"));
        manualSwitch.click();
        assertTrue("manual switch is not checked", manualSwitch.isChecked());

        //change translation direction
        UiObject2 spanToEngTgl = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnSpanToEng"));
        spanToEngTgl.click();

        //change difficulty
        UiObject2 mediumTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnMedium"));
        mediumTglBtn.click();

        //Change category to family
        UiObject2 categoryButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnWB"));
        categoryButton.click();
        UiObject2 backBtnCtg = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnback")),3000);
        UiObject2 familyButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnfamily"));
        familyButton.click();
        Thread.sleep(500);
        backBtnCtg.click();

        // Press the confirm button
        UiObject2 confirm = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnConfirm")),3000);
        confirm.click();

        //get empty cell
        UiObject2 emptyCell = mDevice.wait(Until.findObject(By.desc("emptyCell")),3000);
        emptyCell.click();

        //check a valid input
        UiObject2 editText = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),3000);
        UiObject2 okB = mDevice.findObject(By.res("android:id/button1"));
        assertTrue("edit text field should be clickable.", editText.isClickable());
        editText.setText("Stepdaughter");
        okB.click();
        Thread.sleep(500);
        //confirm the change
        String prevAnswer = emptyCell.getText();
        assertEquals("previously empty cell should be displaying the answer.", "STEPDAUGHTER", prevAnswer);

        //check an invalid input from same category different difficulty
        emptyCell.click();
        editText = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),3000);
        editText.setText("Mother");
        okB = mDevice.findObject(By.res("android:id/button1"));
        okB.click();
        Thread.sleep(500);
        //confirm no change
        assertEquals("invalid input should not change the text of the cell.", prevAnswer, emptyCell.getText());

        //check an invalid input from different category same difficulty
        emptyCell.click();
        editText = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),3000);
        editText.setText("Breakfast");
        okB = mDevice.findObject(By.res("android:id/button1"));
        okB.click();
        Thread.sleep(500);
        //confirm no change
        assertEquals("invalid input should not change the text of the cell.", prevAnswer, emptyCell.getText());

        //check an invalid input of wrong language
        emptyCell.click();
        editText = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),3000);
        editText.setText("Sobrino");
        okB = mDevice.findObject(By.res("android:id/button1"));
        okB.click();
        Thread.sleep(500);
        //confirm no change
        assertEquals("invalid input should not change the text of the cell.", prevAnswer, emptyCell.getText());

        //quit and check the button states are reset
        mDevice.pressBack();
        UiObject2 textV = mDevice.wait(Until.findObject(By.res("android:id/alertTitle")),3000);
        UiObject2 noB = mDevice.findObject(By.res("android:id/button2"));
        UiObject2 yesB = mDevice.findObject(By.res("android:id/button1"));
        assertEquals("Are you sure you want to quit?", textV.getText());
        assertTrue(noB.isEnabled());
        assertTrue(noB.isClickable());
        assertTrue(yesB.isEnabled());
        assertTrue(yesB.isClickable());
        yesB.click();

        start = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnStart")),3000);
        start.click();

        // check category button states are restored
        categoryButton = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnWB")),3000);
        assertTrue(categoryButton.isEnabled());
        assertTrue(categoryButton.isClickable());
        assertEquals(categoryButton.getText(), "CATEGORIES");
        categoryButton.click();

        UiObject2 numbersButton = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnNumbers")),3000);
        assertTrue(numbersButton.isChecked());

        familyButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnfamily"));
        assertFalse(familyButton.isChecked());

        UiObject2 greetingsButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnGreeting"));
        assertFalse(greetingsButton.isChecked());

        UiObject2 foodButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnFood"));
        assertFalse(foodButton.isChecked());

        UiObject2 directionButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnDirection"));
        assertFalse(directionButton.isChecked());

        // Hold to ensure app is where its expected to be
        Thread.sleep(1000);
    }



    /*
     * Ignore this function but don't delete it
     * From BasicSample linked in https://developer.android.com/training/testing/other-components/ui-automator
     * BasicSample: https://github.com/android/testing-samples/tree/main/ui/uiautomator/BasicSample
     * Uses package manager to find the package name of the device launcher. Usually this package
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

    // Tests all Element Buttons within a table layout
    // Scrolling required before running this code
    private void checkElementButtons(UiObject2 tableLayout)
    {
        for(UiObject2 individualButton : tableLayout.getChildren())
        {
            for(UiObject2 singleButton : individualButton.getChildren())
            {
                System.out.println(singleButton.getContentDescription());
                if(Objects.equals(singleButton.getText(), " ")){
                    assertTrue("Empty Cell is not clickable", singleButton.isClickable());
                }
                else {
                    assertFalse("Given Cell is clickable", singleButton.isClickable());
                }
                assertTrue("Button is not enabled", singleButton.isEnabled());
            }
        }
    }

}