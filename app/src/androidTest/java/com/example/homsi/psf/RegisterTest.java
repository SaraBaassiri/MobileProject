package com.example.homsi.psf;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegisterTest {
    @Rule
    public ActivityTestRule<RegisterActivity> createActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);
    String name = "jonnel";
    String pass = "jonnel";
    String email = "jonnel@jonnel";
    String last = "jonnel";

    @Test
    public void testRegisterFields(){
        onView(withId(R.id.password)).perform(typeText("jonnel"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("jonnel@jonnel"));
        closeSoftKeyboard();
        onView(withId(R.id.firstName)).perform(typeText("jonnel"));
        closeSoftKeyboard();
        onView(withId(R.id.lastName)).perform(typeText("jonnel"));
        closeSoftKeyboard();

        onView(withId(R.id.password)).check(matches(withText(pass)));
        onView(withId(R.id.email)).check(matches(withText(email)));
        onView(withId(R.id.firstName)).check(matches(withText(name)));
        onView(withId(R.id.lastName)).check(matches(withText(last)));
    }

    @Test
    public void testRegisterButton(){
        onView(withId(R.id.email_sign_in_button)).perform(click());

    }

    @Test
    public void testSoftKeyboardsForRegister(){
        onView(withId(R.id.password)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.firstName)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.lastName)).perform(click());
        closeSoftKeyboard();
    }



}