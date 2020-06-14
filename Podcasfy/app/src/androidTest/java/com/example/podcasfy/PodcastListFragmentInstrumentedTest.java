package com.example.podcasfy;

import android.content.pm.ActivityInfo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.rule.ActivityTestRule;

import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.ui.PodcastListFragment;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class PodcastListFragmentInstrumentedTest {

    @Rule
    public final ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    public void rotateScreenLandscape(){
        mainActivityActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Before
    public void launchPodcastListFragment(){
        FragmentScenario.launchInContainer(PodcastListFragment.class);
    }

    @Test
    public void subscriptionRecyclerViewIsShown(){
        onView(withId(R.id.podcast_list_subscription)).check(matches(isDisplayed()));
    }

    @Test
    public void ivooxRecyclerViewIsShown(){
        onView(withId(R.id.podcast_list_ivoox)).check(matches(isDisplayed()));
    }

    @Test
    public void spotifyRecyclerViewIsShown(){
        onView(withId(R.id.podcast_list_spotify)).check(matches(isDisplayed()));
    }

    @Test
    public void subscriptionRecyclerViewIsShown_AfterRotation(){
        rotateScreenLandscape();
        subscriptionRecyclerViewIsShown();
    }

    @Test
    public void ivooxRecyclerViewIsShown_AfterRotation(){
        rotateScreenLandscape();
        ivooxRecyclerViewIsShown();
    }

    @Test
    public void spotifyRecyclerViewIsShown_AfterRotation(){
        rotateScreenLandscape();
        spotifyRecyclerViewIsShown();
    }
}
