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
public class LoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> createActivityTestRule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void testEmailPasswordLogin(){
        onView(withId(R.id.password)).perform(typeText("123456789"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("rambo@yourmom.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).check(matches(withText("123456789")));
    }

    @Test
    public void testLoginButton(){
        onView(withId(R.id.email_sign_in_button)).perform(click());
    }

    @Test
    public void testLoginSoftKeyboards(){
        onView(withId(R.id.email)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(click());
        closeSoftKeyboard();

    }

}