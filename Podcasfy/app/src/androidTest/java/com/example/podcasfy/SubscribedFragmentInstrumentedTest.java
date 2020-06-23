package com.example.podcasfy;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.podcasfy.ui.SubscribedFragment;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class SubscribedFragmentInstrumentedTest {

    @Before
    public void launchSubscriptionFragment(){
        FragmentScenario.launchInContainer(SubscribedFragment.class);
    }

    @Test
    public void labelTextIsShown(){
        onView(withId(R.id.subscribedLabel)).check(matches(isDisplayed()));
    }

    @Test
    public void labelTextShowsCorrectText(){
        onView(withId(R.id.subscribedLabel)).check(matches(withText(R.string.subscriptions_label_text)));
    }

    @Test
    public void subscribedRecyclerViewIsShown(){
        onView(withId(R.id.subscribedRecyclerView)).check(matches(isDisplayed()));
    }
}
