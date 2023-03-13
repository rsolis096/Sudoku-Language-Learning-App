package com.example.sudokuapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SudokuPage9x9Test {

    @Rule
    public ActivityScenarioRule<MainMenu> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainMenu.class);

    @Test
    public void sudokuPage9x9Test() {

        //**************************************//
        //          ASSISTED INPUT              //
        //**************************************//

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnStart), withText("Start"),

                        isDisplayed()));
        materialButton.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction toggleButton = onView(
                allOf(withId(R.id.tgBtn9), withText("9X9"),
                        isDisplayed()));
        toggleButton.check(matches(isDisplayed()));

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnConfirm), withText("Confirm"),
                        isDisplayed()));
        materialButton2.perform(click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction frameLayout = onView(
                allOf(withId(android.R.id.content),
                        withParent(allOf(withId(com.google.android.material.R.id.decor_content_parent),
                                withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction viewGroup = onView(
                allOf(withParent(allOf(withId(android.R.id.content),
                                withParent(withId(com.google.android.material.R.id.decor_content_parent)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction horizontalScrollView = onView(
                allOf(withId(R.id.horizontalScrollView),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        horizontalScrollView.check(matches(isDisplayed()));

        ViewInteraction scrollView = onView(
                allOf(withId(R.id.ScrollView),
                        withParent(allOf(withId(R.id.horizontalScrollView),
                                withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        scrollView.check(matches(isDisplayed()));

        ViewInteraction tableLayout = onView(
                allOf(withId(R.id.sudoku_table),
                        withParent(allOf(withId(R.id.ScrollView),
                                withParent(withId(R.id.horizontalScrollView)))),
                        isDisplayed()));
        tableLayout.check(matches(isDisplayed()));

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
        ViewInteraction chronometer = onView(
                allOf(withId(R.id.gameTimerText),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        chronometer.check(matches(isDisplayed()));

        //Check single empty cell for functionality of assisted mode
        ViewInteraction elementButton = onView(withTagValue(is("emptyCell")));
        elementButton.perform(scrollTo(), click());

        //Check all the table rows that pop up in assist mode
        for(int i =0; i < 3; i++)
        {
            ViewInteraction assistedTableRow = onView(withTagValue(is("assistTableRowTag" + i)));
            assistedTableRow.check(matches(isDisplayed()));
        }

        //Check if assist buttons exist
        for(int i =0; i < 9; i++)
        {
            ViewInteraction assistedButton = onView(withTagValue(is("assistButtonTag" + i)));
            assistedButton.check(matches(isEnabled()));
        }

        ViewInteraction textView = onView(
                allOf(IsInstanceOf.instanceOf(android.widget.TextView.class), withText("Enter Word:"),
                        withParent(allOf(IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Enter Word:")));

        //Select a assistedButton
        ViewInteraction selectAssisted = onView(withTagValue(is("assistButtonTag2")));
        selectAssisted.perform(click());

        //Make sure the emptyCell was updated
        onView(withTagValue(is("emptyCell"))).check(matches(withText("Tres")));

        Espresso.pressBack();
        Espresso.pressBack();


        //**************************************//
        //          MANUAL INPUT                //
        //**************************************//
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
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
