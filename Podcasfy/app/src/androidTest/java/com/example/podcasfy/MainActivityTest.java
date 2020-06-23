package com.example.podcasfy;


import android.content.pm.ActivityInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    public void rotateScreenLandscape(){
        mainActivityActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Test
    public void mainFragmentIsShown(){
        onView(withId(R.id.main_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void bottomNavigationViewIsShown(){
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
    }

    @Test
    public void mainFragmentIsShown_AfterRotation(){
        rotateScreenLandscape();
        mainFragmentIsShown();
    }

    @Test
    public void bottomNavigationViewIsShown_AfterRotation(){
        rotateScreenLandscape();
        bottomNavigationViewIsShown();
    }
}
