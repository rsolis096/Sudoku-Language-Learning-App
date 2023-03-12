package com.example.sudokuapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
