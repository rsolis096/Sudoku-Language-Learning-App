package com.example.sudokuapp;

import androidx.test.filters.SdkSuppress;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
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
public class Sudoku9x9Test {

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
    public void assistModeCheck() throws UiObjectNotFoundException {
        // Press the start button
        UiObject2 start = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        start.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Make sure 9x9 toggle button exists
        UiObject2 toggleButton9x9 = mDevice.findObject(By.res("com.example.sudokuapp:id/tgBtn9"));
        assertTrue("Toggle button is not enabled", toggleButton9x9.isEnabled());
        assertTrue("Toggle button is not checkable", toggleButton9x9.isCheckable());
        assertTrue("Toggle button is not checked", toggleButton9x9.isChecked());
        toggleButton9x9.click();
        assertTrue("Toggle button is not checked", toggleButton9x9.isChecked());

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

        //Wait one second for the game to load before continuing with further actions
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Check the table rows in the main game board
        for(int i =0; i < 9; i++)
        {
            UiObject2 tableRows = mDevice.findObject(By.desc("tableRowTag" + i));
            assertTrue("Table Row " + i + " is not enabled", tableRows.isEnabled());
        }

        //** Test the ElementButtons in the table **//
        // Get a reference to the sudokutable (TableLayout)
        UiObject2 tableLayout = mDevice.findObject(By.res("com.example.sudokuapp:id/sudoku_table"));
        // Get a reference to the ScrollView with horizontal scroll
        UiScrollable scrollView = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        scrollView.setAsHorizontalList();
        //Iterate through tablerows list to test each button in each table row
        checkElementButtons(tableLayout);
        //Scroll to test other buttons not currently visible
        scrollView.scrollToEnd(2);
        //Slow down next code to give time to scroll
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Check the remaining buttons
        checkElementButtons(tableLayout);

        //Check Timer
        //Verify timer is counting up
        assertTrue("Timer is not enabled", cTimer.isEnabled());
        assertNotEquals(cTimer.getText(), timerText);

        //Scroll left to test empty ElementButton functionality
        scrollView.scrollToBeginning(2);
        //Slow down next code to give time to scroll
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Check single empty cell for functionality of assisted mode
        UiObject2 emptyCell = mDevice.findObject(By.desc("emptyCell"));
        assertTrue("Empty Cell is not clickable", emptyCell.isClickable());
        emptyCell.click();

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
                assertTrue("Empty Cell is not clickable", singleButton.isClickable());
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
    }

    @Test
    public void manualModeCheck() {
        /*
        ViewInteraction materialButton1 = onView(
                allOf(withId(R.id.btnStart), withText("Start"),

                        isDisplayed()));
        materialButton1.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction toggleButton1 = onView(
                allOf(withId(R.id.tgBtn9), withText("9X9"),
                        isDisplayed()));
        toggleButton1.check(matches(isDisplayed()));

        ViewInteraction switchCompat = onView(
                allOf(withId(R.id.switchInputMode), withText("Manual Input"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        switchCompat.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnConfirm), withText("Confirm"),
                        isDisplayed()));
        materialButton3.perform(click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction frameLayout1 = onView(
                allOf(withId(android.R.id.content),
                        withParent(allOf(withId(com.google.android.material.R.id.decor_content_parent),
                                withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        frameLayout1.check(matches(isDisplayed()));

        ViewInteraction viewGroup1 = onView(
                allOf(withParent(allOf(withId(android.R.id.content),
                                withParent(withId(com.google.android.material.R.id.decor_content_parent)))),
                        isDisplayed()));
        viewGroup1.check(matches(isDisplayed()));

        ViewInteraction horizontalScrollView1 = onView(
                allOf(withId(R.id.horizontalScrollView),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        horizontalScrollView1.check(matches(isDisplayed()));

        ViewInteraction scrollView1 = onView(
                allOf(withId(R.id.ScrollView),
                        withParent(allOf(withId(R.id.horizontalScrollView),
                                withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        scrollView1.check(matches(isDisplayed()));

        ViewInteraction tableLayout1 = onView(
                allOf(withId(R.id.sudoku_table),
                        withParent(allOf(withId(R.id.ScrollView),
                                withParent(withId(R.id.horizontalScrollView)))),
                        isDisplayed()));
        tableLayout1.check(matches(isDisplayed()));

        //Check the table rows in the main game board
        for(int i =0; i < 9; i++)
        {
            ViewInteraction tableRow1 = onView(withTagValue(is("tableRowTag" + i )));
            tableRow1.check(matches(isEnabled()));
        }

        //Check all of the buttons and rows
        for (int i = 0; i < 80; i++)
        {
            //Checking if it exists
            ViewInteraction button = onView(withTagValue(is("elementButtonTag" + i)));
            button.check(matches(isEnabled()));
        }

        //Verify timer exists
        ViewInteraction chronometer1 = onView(
                allOf(withId(R.id.gameTimerText),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        chronometer1.check(matches(isDisplayed()));

        //Check single empty cell for functionality of manual input
        ViewInteraction elementButton1 = onView(withTagValue(is("emptyCell")));
        elementButton1.perform(scrollTo(), click());

        ViewInteraction frameLayout2 = onView(
                allOf(withId(android.R.id.content),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        frameLayout2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(IsInstanceOf.instanceOf(android.widget.TextView.class), withText("Enter Word:"),
                        withParent(allOf(IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Enter Word:")));

        ViewInteraction editText = onView(
                allOf(withParent(allOf(withId(android.R.id.custom),
                                withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(android.R.id.button3), withText("CLEAR ANSWER"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(android.R.id.button2), withText("CANCEL"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        //Enter invalid input
        ViewInteraction editText2 = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        editText2.perform(replaceText("haaaa"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        //Cell should still be empty
        onView(withTagValue(CoreMatchers.is("emptyCell"))).check(matches(withText(" ")));

        //Check single empty cell for functionality of manual input assist
        ViewInteraction elementButton2 = onView(withTagValue(CoreMatchers.is("emptyCell")));
        elementButton2.perform(scrollTo(), click());

        //Enter valid input
        ViewInteraction editText3 = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        editText3.perform(replaceText("uno"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        //Cell should still be updated
        onView(withTagValue(CoreMatchers.is("emptyCell"))).check(matches(withText("UNO")));


        //Solve the board and return to home
        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.solveButton), withText("Solve"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                        withParent(allOf(withId(android.R.id.content),
                                withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(IsInstanceOf.instanceOf(android.widget.TextView.class), withText("Game Finished!"),
                        withParent(allOf(IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("Game Finished!")));

        ViewInteraction button8 = onView(
                allOf(withId(android.R.id.button1), withText("CONTINUE"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        button8.check(matches(isDisplayed()));

        ViewInteraction materialButton7 = onView(
                allOf(withId(android.R.id.button1), withText("Continue"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(Matchers.is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton7.perform(scrollTo(), click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView4), withText("Congratulations, you completed the puzzle!"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView4.check(matches(withText("Congratulations, you completed the puzzle!")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView7), withText("Here is your time:"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView5.check(matches(withText("Here is your time:")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.resultTime),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction button9 = onView(
                allOf(withId(R.id.btnEndGameReturn), withText("HOME"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button9.check(matches(isDisplayed()));

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.btnEndGameReturn), withText("Home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction button10 = onView(
                allOf(withId(R.id.btnStart), withText("START"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button10.check(matches(isDisplayed()));
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