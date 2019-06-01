package com.sourcey.materiallogindemo;

import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.Root;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
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

    public String name = "aaa";
    public String address = "aaa";
    public String email = "test@test.com";
    public String mobile = "0123456789";
    public String password = "0123456789";

    @Test
    public void signUpActivityTest() {
        onView(withId(R.id.link_signup)).perform(click());
        onView(withId(R.id.input_name)).perform(scrollTo(), typeText(name), closeSoftKeyboard());
        onView(withId(R.id.input_address)).perform(scrollTo(), typeText(address), closeSoftKeyboard());
        onView(withId(R.id.input_email)).perform(scrollTo(), typeText(email), closeSoftKeyboard());
        onView(withId(R.id.input_mobile)).perform(scrollTo(), typeText(mobile), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(scrollTo(), typeText(password), closeSoftKeyboard());
        onView(withId(R.id.input_reEnterPassword)).perform(scrollTo(), typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btn_signup)).perform(scrollTo(), click());

        waitPageLoading();
        validateMainActivity();

        //onView(withId(R.id.btn_logout)).perform(click());
    }

    @Test
    public void login_Success(){
        loginAction(email, password);
        waitPageLoading();

        validateMainActivity();
        //onView(withId(R.id.btn_logout)).perform(click());
    }

    @Test
    public void login_Failure(){
        loginAction("test", "test1234");
        //onView(withText(R.string.)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()))
    }

    private static void loginAction(String email, String password){
        onView(withId(R.id.input_email)).perform(typeText(email));
        onView(withId(R.id.input_password)).perform(typeText(password));
        onView(withId(R.id.btn_login)).perform(click());
    }

    private static void validateMainActivity(){
        onView(withText("Hello world!")).check(matches(isDisplayed()));
        onView(withId(R.id.btn_logout)).check(matches(isDisplayed()));
    }

    private static void waitPageLoading(){
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
