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
public class CreateListingActivityTest {
    @Rule
    public ActivityTestRule<createListing_Activity> mCreateListingActivityTestRule= new ActivityTestRule<createListing_Activity>(createListing_Activity.class);

    @Test
    public void clickToCreateListingButton_opensMainUserUI() throws Exception{
        onView(withId(R.id.submit)).perform(click());

        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));
    }

}
