package com.example.sudokuapp;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
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

import java.util.List;

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

        //open options page
        optionsButton.click();

        //Test open custom word bank activity button
        UiObject2 buttonCustomBankButton = mDevice.findObject(By.res("com.example.sudokuapp:id/buttonCustomBank"));
        assertTrue("buttonCustomBank is not enabled", buttonCustomBankButton.isEnabled());
        assertTrue("buttonCustomBank is not clickable", buttonCustomBankButton.isClickable());
        buttonCustomBankButton.click();
        Thread.sleep(500);

        //check views and buttons
        UiObject2 text = mDevice.wait(Until.findObject(By.res("com.example.sudokuapp:id/customWordsHeader")),3000);
        assertEquals(text.getText(),"Your Custom Words");
        UiObject2 scroll = mDevice.findObject((By.res("com.example.sudokuapp:id/customWordsScrollView")));
        assertTrue(scroll.isEnabled());
        UiObject2 add = mDevice.findObject((By.res("com.example.sudokuapp:id/btnCustomWordsAdd")));
        assertTrue(add.isEnabled());
        assertTrue(add.isClickable());
        UiObject2 clear = mDevice.findObject((By.res("com.example.sudokuapp:id/btnCustomWordsClear")));
        assertTrue(clear.isEnabled());
        assertTrue(clear.isClickable());
        //clear word bank
        clear.click();
        Thread.sleep(1000);
        assertEquals(1,scroll.getChildCount());


        //add words
        add.click();

        //check pop-up views and buttons
        text = mDevice.wait(Until.findObject(By.res("android:id/alertTitle")),3000);
        assertEquals(text.getText(),"Enter Word Pair:");
        UiObject2 enterEng = mDevice.findObject(By.text("English"));
        UiObject2 enterSpa = mDevice.findObject(By.text("Spanish"));
        UiObject2 cancel = mDevice.findObject(By.res("android:id/button2"));
        UiObject2 ok = mDevice.findObject(By.res("android:id/button1"));
        assertTrue(enterEng.isEnabled());
        assertTrue(enterEng.isClickable());
        assertTrue(enterSpa.isEnabled());
        assertTrue(enterSpa.isClickable());
        assertTrue(cancel.isEnabled());
        assertTrue(cancel.isClickable());
        assertTrue(ok.isEnabled());
        assertTrue(ok.isClickable());

        //words to be put in
        String[] engWord = {"one", "two","three","four"};
        String[] spaWord = {"uno", "dos","tres","cuatro"};
        //valid input 1
        enterEng.setText("one");
        enterSpa.setText("uno");
        ok.click();

        //valid input 2-4
        for(int i = 1;i<4;i++){
            add = mDevice.wait(Until.findObject((By.res("com.example.sudokuapp:id/btnCustomWordsAdd"))),3000);
            add.click();
            Thread.sleep(1000);
            enterEng = mDevice.findObject(By.text("English"));
            enterSpa = mDevice.findObject(By.text("Spanish"));
            ok = mDevice.findObject(By.res("android:id/button1"));
            enterEng.setText(engWord[i]);
            enterSpa.setText(spaWord[i]);
            ok.click();
        }

        Thread.sleep(500);

        //Check the table rows in the scroll view
        scroll = mDevice.findObject((By.res("com.example.sudokuapp:id/customWordsTable")));
        List<UiObject2> tableRows = scroll.getChildren();

        String[] wordpair = {"one, uno", "two, dos", "three, tres", "four, cuatro"};
        for(int i = 0;i<4;i++) {
            tableRows.get(i).findObject(By.text(wordpair[i]));
        }

        //clear again and check
        clear = mDevice.findObject((By.res("com.example.sudokuapp:id/btnCustomWordsClear")));
        //clear word bank
        clear.click();
        Thread.sleep(1000);
        assertEquals(0,scroll.getChildCount());


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