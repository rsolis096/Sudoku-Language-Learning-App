package com.example.sudokuapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.KeyEvent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainMenuTest {

    @Rule
    public ActivityScenarioRule<MainMenu> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainMenu.class);

    @Test
    public void mainMenuTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.btnStart), withText("START"),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btnOptions), withText("OPTIONS"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.btnTut), withText("TUTORIAL"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));
    }
}
