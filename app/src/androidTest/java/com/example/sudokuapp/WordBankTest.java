package com.example.sudokuapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WordBankTest {

    @Rule
    public ActivityScenarioRule<MainMenu> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainMenu.class);

    @Test
    public void wordBankTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnStart), withText("Start"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnWB), withText("Categories"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.btnback), withText("BACK"),
                        withParent(allOf(withId(R.id.settings),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction toggleButton = onView(
                allOf(withId(R.id.btnNumbers), withText("NUMBERS"),
                        withParent(allOf(withId(R.id.settings),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        toggleButton.check(matches(isDisplayed()));
        toggleButton.check(matches(isChecked()));

        ViewInteraction toggleButton2 = onView(
                allOf(withId(R.id.btnfamily), withText("FAMILY"),
                        withParent(allOf(withId(R.id.settings),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        toggleButton2.check(matches(isDisplayed()));

        ViewInteraction toggleButton3 = onView(
                allOf(withId(R.id.btnGreeting), withText("GREETINGS"),
                        withParent(allOf(withId(R.id.settings),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        toggleButton3.check(matches(isDisplayed()));

        ViewInteraction toggleButton4 = onView(
                allOf(withId(R.id.btnFood), withText("FOOD AND DRINKS"),
                        withParent(allOf(withId(R.id.settings),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        toggleButton4.check(matches(isDisplayed()));

        ViewInteraction toggleButton5 = onView(
                allOf(withId(R.id.btnDirection), withText("DIRECTION"),
                        withParent(allOf(withId(R.id.settings),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        toggleButton5.check(matches(isDisplayed()));

        //clicking a button should check it
        toggleButton2.perform(click());
        toggleButton2.check(matches(isChecked()));
        toggleButton3.perform(click());
        toggleButton3.check(matches(isChecked()));
        toggleButton4.perform(click());
        toggleButton4.check(matches(isChecked()));
        toggleButton5.perform(click());
        toggleButton5.check(matches(isChecked()));
        //Go back to settings and come back to categories the last clicked should still be selected
        button.perform(click());
        materialButton2.perform(click());
        toggleButton5.check(matches(isChecked()));

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnback), withText("Back"),
                        childAtPosition(
                                allOf(withId(R.id.settings),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.backToMenu), withText("BACK"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView2), withText("Choose a Difficulty:"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Choose a Difficulty:")));

        ViewInteraction toggleButton7 = onView(
                allOf(withId(R.id.tgBtnEasy), withText("EASY"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        toggleButton7.check(matches(isDisplayed()));

        ViewInteraction toggleButton8 = onView(
                allOf(withId(R.id.tgBtnMedium), withText("MEDIUM"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        toggleButton8.check(matches(isDisplayed()));

        ViewInteraction toggleButton9 = onView(
                allOf(withId(R.id.tgBtnHard), withText("HARD"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        toggleButton9.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView3), withText("Choose a Learning Method:"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("Choose a Learning Method:")));

        ViewInteraction toggleButton10 = onView(
                allOf(withId(R.id.tgBtnEngToSpan), withText("ENGLISH TO SPANISH TRANSLATION"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        toggleButton10.check(matches(isDisplayed()));

        ViewInteraction toggleButton11 = onView(
                allOf(withId(R.id.tgBtnSpanToEng), withText("SPANISH TO ENGLISH TRANSLATION"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        toggleButton11.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.btnWB), withText("Categories"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction toggleButton12 = onView(
                allOf(withId(R.id.tgBtn9), withText("9X9"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        toggleButton12.check(matches(isDisplayed()));

        ViewInteraction toggleButton13 = onView(
                allOf(withId(R.id.tgBtn16), withText("16X16"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        toggleButton13.check(matches(isDisplayed()));

        ViewInteraction toggleButton14 = onView(
                allOf(withId(R.id.tgBtn4), withText("4X4"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        toggleButton14.check(matches(isDisplayed()));

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.switchInputMode), withText("Manual Input"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        switch_.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.btnConfirm), withText("CONFIRM"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.backToMenu), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction button6 = onView(
                allOf(withId(R.id.btnStart), withText("START"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button6.check(matches(isDisplayed()));

        ViewInteraction button7 = onView(
                allOf(withId(R.id.btnOptions), withText("OPTIONS"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button7.check(matches(isDisplayed()));

        ViewInteraction button8 = onView(
                allOf(withId(R.id.btnTut), withText("TUTORIAL"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button8.check(matches(isDisplayed()));

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
