package com.example.sudokuapp.ui;

import androidx.test.filters.SdkSuppress;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;

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
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;
import static org.junit.Assert.*;

import com.example.sudokuapp.model.Sudoku;

@RunWith(AndroidJUnit4ClassRunner.class)
@SdkSuppress(minSdkVersion = 18)
public class SudokuPageTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.example.sudokuapp";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;

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
    public void assistModeTest() throws InterruptedException, UiObjectNotFoundException {
        //Tests only assist mode
        assistModeCheck(4);
        assistModeCheck(6);
        assistModeCheck(9);
        assistModeCheck(12);
    }
    @Test
    public void manualModeTest() throws InterruptedException, UiObjectNotFoundException {
        //Tests only manual mode
        manualModeCheck(4);
        manualModeCheck(6);
        manualModeCheck(9);
        manualModeCheck(12);
    }
    @Test
    public void combinationModeTest() throws InterruptedException, UiObjectNotFoundException {
        //Tests a mix of manual and assist mode
        combinationCheck(4);
        combinationCheck(6);
        combinationCheck(9);
        combinationCheck(12);
    }

    public void combinationCheck(int testSize) throws InterruptedException {
        startMainActivityFromHomeScreen();
        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        start.click();

        //Make sure 12x12 toggle button exists
        UiObject2 toggleButton= mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/tgBtn"+testSize)),3000);
        toggleButton.click();
        assertTrue("togglebutton button is not checked", toggleButton.isChecked());

        //manual mode to check if the correct category of words was used
        UiObject2 manualSwitch = mDevice.findObject(By.res("com.example.sudokuapp:id/switchInputMode"));
        manualSwitch.click();
        assertTrue("manual switch is not checked", manualSwitch.isChecked());

        //change difficulty
        UiObject2 mediumTglBtn = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtnMedium"));
        mediumTglBtn.click();

        //Change category to direction
        UiObject2 categoryButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnWB"));
        categoryButton.click();
        UiObject2 backBtnCtg = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnback")),3000);
        UiObject2 directionButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnDirection"));
        directionButton.click();
        Thread.sleep(1000);
        backBtnCtg.click();

        // Press the confirm button
        UiObject2 confirm = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnConfirm")),3000);
        confirm.click();

        Thread.sleep(500);

        //get empty cell
        UiObject2 emptyCell = mDevice.wait(Until.findObject(By.desc("emptyCell")),3000);
        emptyCell.click();

        //check a valid input
        UiObject2 editText = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),3000);
        UiObject2 okB = mDevice.findObject(By.res("com.example.sudokuapp:id/manualInputConfirmBtn"));
        assertTrue("edit text field should be clickable.", editText.isClickable());
        editText.setText(Sudoku.bank.getSpanish()[0].toLowerCase());
        okB.click();
        Thread.sleep(500);
        //confirm the change
        String prevAnswer = emptyCell.getText();
        assertEquals("previously empty cell should be displaying the answer.", Sudoku.bank.getSpanish()[0].toLowerCase(), prevAnswer.toLowerCase());

        //check an invalid input from same category different difficulty
        emptyCell.click();
        editText = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),3000);
        editText.setText("Izquierda");
        okB.click();
        Thread.sleep(500);
        //confirm no change
        assertEquals("invalid input should not change the text of the cell.", prevAnswer, emptyCell.getText());

        //check an invalid input from different category same difficulty
        emptyCell.click();
        editText = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),3000);
        editText.setText("Pollo");
        okB.click();
        Thread.sleep(500);
        //confirm no change
        assertEquals("invalid input should not change the text of the cell.", prevAnswer, emptyCell.getText());

        //check an invalid input of wrong language
        emptyCell.click();
        editText = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),3000);
        editText.setText("Outside");
        okB.click();
        Thread.sleep(500);
        //confirm no change
        assertEquals("invalid input should not change the text of the cell.", prevAnswer, emptyCell.getText());

        //solve and check categories are reset
        UiObject2 resources = mDevice.findObject(By.res("com.example.sudokuapp:id/solveButton"));
        resources.click();
        //check pop up for game completion
        UiObject2 textV = mDevice.wait(Until.findObject(By.res("android:id/alertTitle")),3000);
        assertEquals("Game finished should be displayed.", "Game Finished!", textV.getText());
        resources = mDevice.findObject(By.res("android:id/button1"));
        resources.click();

        //check result screen
        textV = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/textView4")),3000);
        assertEquals("Game finished should be displayed.", "Congratulations, you completed the puzzle!", textV.getText());
        resources = mDevice.findObject(By.res("com.example.sudokuapp:id/btnEndGameReturn"));
        resources.click();

        // check category button states are restored
        categoryButton = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnWB")),3000);
        assertTrue(categoryButton.isEnabled());
        assertTrue(categoryButton.isClickable());
        assertEquals(categoryButton.getText().toLowerCase(), "categories");
        categoryButton.click();

        UiObject2 numbersButton = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/btnNumbers")),3000);
        assertTrue(numbersButton.isChecked());

        UiObject2 familyButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnfamily"));
        assertFalse(familyButton.isChecked());

        UiObject2 greetingsButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnGreeting"));
        assertFalse(greetingsButton.isChecked());

        UiObject2 foodButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnFood"));
        assertFalse(foodButton.isChecked());

        directionButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnDirection"));
        assertFalse(directionButton.isChecked());

    }

    public void manualModeCheck(int testSize) throws InterruptedException{

        startMainActivityFromHomeScreen();

        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        start.click();

        //Make sure testSize toggle button exists
        UiObject2 toggleButton = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/tgBtn"+testSize)),3000);
        assertTrue("Toggle button is not enabled", toggleButton.isEnabled());
        assertTrue("Toggle button is not checkable", toggleButton.isCheckable());
        toggleButton.click();
        assertTrue("12x12 button is not checked", toggleButton.isChecked());

        UiObject2 manualSwitch = mDevice.findObject(By.res("com.example.sudokuapp:id/switchInputMode"));
        assertTrue("manual switch is not enabled", manualSwitch.isEnabled());
        assertTrue("manual switch is not checkable", manualSwitch.isCheckable());
        assertFalse("manual switch should be not checked as default.", manualSwitch.isChecked());
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
        Thread.sleep(2000);

        //Check the table rows in the main game board (last 2 out of screen)
        int x = testSize;

        //Some tablerows are off the screen in 12x12
        if(testSize == 12)
        {
            x = 10;
        }

        for(int i =0; i < x; i++)
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
        Thread.sleep(500);


        String[] validWords = Sudoku.getBank().getSpanish();
        //Reference to ok button for manual input
        UiObject2 manualInputConfirmBtn= mDevice.findObject(By.res("com.example.sudokuapp:id/manualInputConfirmBtn"));
        //Check text edit function
        UiObject2 editText = mDevice.findObject(By.clazz("android.widget.EditText"));

        assertTrue("edit text field should be clickable.", editText.isClickable());
        editText.setText(validWords[0]);
        manualInputConfirmBtn.click();
        //confirm the change
        assertEquals("previously empty cell should be displaying the answer.", validWords[0].toLowerCase(), emptyCell.getText().toLowerCase());

        //Check new valid input replace old input
        emptyCell.click();
        assertTrue("edit text field should be clickable.", editText.isClickable());
        editText.setText(validWords[1]);
        manualInputConfirmBtn.click();
        //confirm the change
        assertEquals("cell should display new valid input.", validWords[1].toLowerCase(), emptyCell.getText().toLowerCase());

        //try invalid input, result should be the same as before the input.
        emptyCell.click();
        editText.setText(" dos ");
        manualInputConfirmBtn.click();
        //confirm no change
        assertNotEquals("invalid input should not change the text of the cell.", " dos ", emptyCell.getText().toLowerCase());


        //check solve button
        UiObject2 solveButton = mDevice.findObject(By.res("com.example.sudokuapp:id/solveButton"));
        assertTrue("solve button is not enabled", solveButton .isEnabled());
        assertTrue("solve button is not clickable", solveButton .isClickable());
        solveButton .click();

        //check pop up for game completion
        UiObject2 textV = mDevice.wait(Until.findObject(By.res("android:id/alertTitle")),3000);
        assertEquals("Game finished should be displayed.", "Game Finished!", textV.getText());
        UiObject2 button1 = mDevice.findObject(By.res("android:id/button1"));
        assertTrue("continue button is not enabled", button1.isEnabled());
        assertTrue("continue button is not clickable", button1.isClickable());
        button1.click();

        //check result screen
        textV = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/textView4")),3000);
        assertEquals("Game finished should be displayed.", "Congratulations, you completed the puzzle!", textV.getText());
        textV = mDevice.findObject(By.res("com.example.sudokuapp:id/resultTime"));
        assertTrue("result time should be shown.", textV.isEnabled());
        UiObject2 btnEndGameReturn = mDevice.findObject(By.res("com.example.sudokuapp:id/btnEndGameReturn"));
        assertTrue("home button is not enabled", btnEndGameReturn.isEnabled());
        assertTrue("home button is not clickable", btnEndGameReturn.isClickable());
        btnEndGameReturn.click();
    }

    public void assistModeCheck(int testSize) throws UiObjectNotFoundException, InterruptedException {
        //Ensure app is starting from beginning
        startMainActivityFromHomeScreen();

        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        assertNotNull("Start button is null in test " + testSize, start);
        start.click();

        //Make sure 12x12 toggle button exists
        UiObject2 toggleButton= mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/tgBtn"+testSize)),8000);
        assertTrue("Toggle button is not enabled", toggleButton.isEnabled());
        assertTrue("Toggle button is not checkable", toggleButton.isCheckable());
        toggleButton.click();
        assertTrue(testSize + "x"+testSize+ "button is not checked", toggleButton.isChecked());

        // Timers to slow down test, fails otherwise.
        Thread.sleep(1000);

        //Make sure in assist mode
        UiObject2 manual = mDevice.findObject(By.res("com.example.sudokuapp:id/switchInputMode"));
        assertFalse(manual.isChecked());
        if(manual.isChecked())
            manual.click();

        // Press the confirm button
        UiObject2 confirm = mDevice.findObject(By.res("com.example.sudokuapp:id/btnConfirm"));
        assertTrue("Confirm button is not enabled", confirm.isEnabled());
        assertTrue("Confirm button is not clickable", confirm.isClickable());
        confirm.click();

        //Check Timer (wait for it to appear on screen, going to fast will cause fail)
        UiObject2 cTimer = mDevice.wait(Until.findObject(By.clazz(Chronometer.class)),2000);
        assertTrue("Timer is not enabled", cTimer.isEnabled());
        //Get text to compare for later to make sure it is counting up
        String timerText = cTimer.getText();

        //Set scroll direction
        UiScrollable scrollView = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        scrollView.setAsHorizontalList();

        // Get a reference to the sudokutable (TableLayout)
        UiObject2 tableLayout = mDevice.findObject(By.res("com.example.sudokuapp:id/sudoku_table"));

        //Check all visible ElementButtons
        checkElementButtons(tableLayout);

        //We need to scroll if the board size is big enough to be off screen
        if(testSize >= 9)
        {
            //Scroll to right of screen (UPPER RIGHT)
            scrollView.scrollForward(5);
            // Timers so app can catch up
            Thread.sleep(2000);
            //Check all visible ElementButtons (UPPER RIGHT)
            checkElementButtons(tableLayout);

            //SCROLL BACK TO BEGINNING (UPPER LEFT)
            scrollView.scrollBackward(5);
            // Timers so app can catch up

        }

        Thread.sleep(2000);
        //check timer again to see if it is counting up
        assertTrue("Timer is not enabled", cTimer.isEnabled());
        assertNotEquals("timer is not counting up.", timerText, cTimer.getText());

        //Check single empty cell for functionality of assisted mode
        UiObject2 emptyCell = mDevice.findObject(By.desc("emptyCell"));
        while(emptyCell == null)
        {
            scrollView.scrollForward(5);
            emptyCell = mDevice.findObject(By.desc("emptyCell"));
        }

        assertTrue("Empty Cell is not clickable", emptyCell.isClickable());
        emptyCell.click();

        //Check all the table rows that pop up in assist mode
        tableLayout = mDevice.wait(Until.findObject(By.desc("assistDialogLayout")),9000);
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

        //Make sure the emptyCell was updated
        //Comparing with a string variable because assistButtonToSelect is off screen
        assertEquals(emptyCell.getText(),assistButtonSelectedText);
    }


    /*
     * Ignore this method but don't delete it
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
                assertTrue("Empty Cell is not clickable", singleButton.isClickable());
                assertTrue("Button is not enabled", singleButton.isEnabled());
            }
        }
    }

}