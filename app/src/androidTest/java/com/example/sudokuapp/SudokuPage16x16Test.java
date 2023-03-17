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
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Until;
import static org.junit.Assert.*;
import java.util.Objects;

@RunWith(AndroidJUnit4ClassRunner.class)
@SdkSuppress(minSdkVersion = 18)
public class SudokuPage16x16Test {

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
    public void assistModeCheck() {
        /*
        This 16x16 was made for 16x16 functionality but was disabled due to inconsistent behaviour.


        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        start.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Make sure 16x16 toggle button exists
        UiObject2 toggleButton16x16 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn16"));
        assertTrue("Toggle button is not enabled", toggleButton16x16.isEnabled());
        assertTrue("Toggle button is not checkable", toggleButton16x16.isCheckable());
        toggleButton16x16.click();
        assertTrue("Toggle button is not checked", toggleButton16x16.isChecked());

        // Timers to slow down test, fails otherwise.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Press the confirm button
        UiObject2 confirm = mDevice.findObject(By.res("com.example.sudokuapp:id/btnConfirm"));
        assertTrue("Confirm button is not enabled", confirm.isEnabled());
        assertTrue("Confirm button is not clickable", confirm.isClickable());
        confirm.click();


        //Check Timer (wait for it to appear on screen, going to fast will cause fail)
        UiObject2 cTimer = mDevice.wait(Until.findObject(By.clazz(Chronometer.class)),500);
        assertTrue("Timer is not enabled", cTimer.isEnabled());
        //Get text to compare for later to make sure it is counting up
        String timerText = cTimer.getText();


        //Set scroll direction
        UiScrollable scrollView = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        scrollView.setAsHorizontalList();

        //Start of Element Button Test//

        // Get a reference to the sudokutable (TableLayout)
        UiObject2 tableLayout = mDevice.findObject(By.res("com.example.sudokuapp:id/sudoku_table"));

        //Start at far right side to improve scrollDescriptionIntoView speed
        System.out.println("scroll forward");
        scrollView.scrollForward(5);
        // Timers so app can catch up
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Check ElementButtons at current location
        checkElementButtons(tableLayout);

        System.out.println("TO TAG 10");
        scrollView.setMaxSearchSwipes(1);
        scrollView.scrollDescriptionIntoView("elementButtonTag10");
        // Timers so app can catch up
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkElementButtons(tableLayout);

        System.out.println("TO TAG 5");
        scrollView.setMaxSearchSwipes(1);
        scrollView.scrollDescriptionIntoView("elementButtonTag5");
        // Timers so app can catch up
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkElementButtons(tableLayout);

        //To far left of screen
        System.out.println("TO TAG 1");
        scrollView.setMaxSearchSwipes(5);
        scrollView.scrollBackward(5);
        // Timers so app can catch up
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Check ElementButtons at current location
        checkElementButtons(tableLayout);

        //Test Lower Element Buttons Test

        //Start at far right side to improve scrollDescriptionIntoView speed
        System.out.println("Scroll to right then downward");
        //Scroll to far right
        scrollView.setAsHorizontalList();
        scrollView.scrollForward(5);
        // Timers so app can catch up
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Scroll downwards
        scrollView.setAsVerticalList();
        scrollView.scrollForward(5);
        // Timers so app can catch up
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scrollView.setAsHorizontalList();
        checkElementButtons(tableLayout);

        System.out.println("TO TAG 246");
        scrollView.setMaxSearchSwipes(1);
        scrollView.scrollDescriptionIntoView("elementButtonTag246");
        // Timers so app can catch up
        try {
            Thread.sleep(2000);
            checkElementButtons(tableLayout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Scroll all the way to bottom left
        scrollView.scrollBackward(5);
        // Timers so app can catch up
        try {
            Thread.sleep(2000);
            checkElementButtons(tableLayout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Scroll back to top, check empty cell
        scrollView.setAsVerticalList();
        scrollView.scrollBackward(5);
        //Wait for app to scroll and catchup
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Check single empty cell for functionality of assisted mode
        UiObject2 emptyCell = mDevice.findObject(By.desc("emptyCell"));
        assertTrue("Empty Cell is not clickable", emptyCell.isClickable());
        emptyCell.click();

        //Wait for app to catch up
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Check all the table rows that pop up in assist mode
        tableLayout = mDevice.findObject(By.desc("assistDialogLayout"));
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

        //Wait so the app can catch up
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Make sure the emptyCell was updated
        //Comparing with a string variable because assistButtonToSelect is off screen
        assertEquals(emptyCell.getText(),assistButtonSelectedText);

        // Hold to ensure app is where its expected to be
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
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