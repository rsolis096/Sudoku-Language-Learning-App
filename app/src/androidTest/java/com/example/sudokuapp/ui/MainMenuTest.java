package com.example.sudokuapp.ui;

import androidx.test.filters.SdkSuppress;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
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

@RunWith(AndroidJUnit4ClassRunner.class)
@SdkSuppress(minSdkVersion = 18)
public class MainMenuTest {

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
    public void mainMenuTest() throws InterruptedException{

        //Test startButton
        UiObject2 startButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        assertTrue("Start Button is not enabled", startButton.isEnabled());
        assertTrue("Start Button is not clickable", startButton.isClickable());

        //Test optionsButton
        UiObject2 optionsButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnOptions"));
        assertTrue("Start Button is not enabled", startButton.isEnabled());
        assertTrue("Start Button is not clickable", startButton.isClickable());

        //Test tutorialButton
        UiObject2 tutorialButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnTut"));
        assertTrue("Start Button is not enabled", startButton.isEnabled());
        assertTrue("Start Button is not clickable", startButton.isClickable());

        //Check tutorial page
        tutorialButton.click();


        UiObject2 textView = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/textView5")), 3000);
        textView.isEnabled();
        mDevice.findObject(By.res("com.example.sudokuapp:id/imageView")).isEnabled();
        UiObject2 backButton = mDevice.findObject(By.res("com.example.sudokuapp:id/backToMenu"));
        backButton.isEnabled();
        backButton.isClickable();
        backButton.click();

        //Give App time to catchup
        Thread.sleep(500);

        //Check options page
        optionsButton.click();

        //Check Dark Mode Button
        UiObject2 switchTheme = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/switchTheme")),5000);
        assertTrue(switchTheme.isCheckable());
        assertTrue(switchTheme.isClickable());
        assertTrue(switchTheme.isEnabled());

        //Test open custom word bank activity button
        UiObject2 buttonCustomBankButton = mDevice.findObject(By.res("com.example.sudokuapp:id/buttonCustomBank"));
        assertTrue("buttonCustomBank is not enabled", buttonCustomBankButton.isEnabled());
        assertTrue("buttonCustomBank is not clickable", buttonCustomBankButton.isClickable());
        buttonCustomBankButton.click();
        Thread.sleep(500);

        //Give App time to catchup
        Thread.sleep(500);
        backButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnCustomWordsBack"));
        backButton.isEnabled();
        backButton.isClickable();
        backButton.click();

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

}