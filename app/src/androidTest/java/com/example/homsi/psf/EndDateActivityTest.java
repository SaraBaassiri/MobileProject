package com.example.homsi.psf;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Sinjin on 11/8/2017.
 */
@RunWith(JUnit4.class)
public class EndDateActivityTest {
    @Rule
    public ActivityTestRule<EndDate> mEndDateActivityTestRule= new ActivityTestRule<EndDate>(EndDate.class);

    @Test
    public void clickToCreateListingButton_opensCreateListingUI() throws Exception{
        onView(withId(R.id.toTime2Button)).perform(click());

        onView(withId(R.id.address)).check(matches(isDisplayed()));
    }

}
