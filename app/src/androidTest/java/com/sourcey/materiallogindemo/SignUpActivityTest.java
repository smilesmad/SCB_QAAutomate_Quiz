package com.sourcey.materiallogindemo;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void signUpActivityTest() {
        onView(withId(R.id.link_signup)).perform(click());
        onView(withId(R.id.input_name)).perform(scrollTo(), typeText("aaa"), closeSoftKeyboard());
        onView(withId(R.id.input_address)).perform(scrollTo(), replaceText("aaa"), closeSoftKeyboard());
        onView(withId(R.id.input_email)).perform(scrollTo(), replaceText("test@test.com"), closeSoftKeyboard());
        onView(withId(R.id.input_mobile)).perform(scrollTo(), replaceText("0123456789"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(scrollTo(), replaceText("test1234"), closeSoftKeyboard());
        onView(withId(R.id.input_reEnterPassword)).perform(scrollTo(), replaceText("test1234"), closeSoftKeyboard());
        onView(withId(R.id.btn_signup)).perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Hello world!")).check(matches(withText("Hello world!")));
        onView(withId(R.id.btn_logout)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_logout)).perform(click());

        onView(withId(R.id.input_email)).perform(typeText("test@test.com"));
        onView(withId(R.id.input_password)).perform(typeText("test1234"));
        onView(withId(R.id.btn_login)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Hello world!")).check(matches(withText("Hello world!")));
    }
/*
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }*/
}
