package com.example.sudokuapp;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.test.filters.SdkSuppress;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@SdkSuppress(minSdkVersion = 18)
public class CustomWordsTest {

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
    public void customWordTest() throws InterruptedException{

        //Test startButton
        UiObject2 startButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnStart"));
        assertTrue("Start Button is not enabled", startButton.isEnabled());
        assertTrue("Start Button is not clickable", startButton.isClickable());

        //Test optionsButton
        UiObject2 optionsButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnOptions"));
        assertTrue("Start Button is not enabled", startButton.isEnabled());
        assertTrue("Start Button is not clickable", startButton.isClickable());

        //Check options page
        optionsButton.click();
        //Give App time to catchup
        Thread.sleep(500);



        // go back
        UiObject2 backButton = mDevice.findObject(By.res("com.example.sudokuapp:id/btnCustomWordsBack"));
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