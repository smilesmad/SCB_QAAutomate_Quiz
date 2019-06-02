package com.sourcey.materiallogindemo;

import android.app.Application;
import android.test.ApplicationTestCase;

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

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBackUnconditionally;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    /*public ApplicationTestCase() {
        super(Application.class);
    }*/

    public static String name;
    public static String address;
    public static String email;
    public static String mobile;
    public static String password;
    public static String repassword;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

   /* @BeforeClass
    public void DeviceSetup(){

    }*/

    @Before
    public void Setup() {
        name = "aaa";
        address = "aaa";
        email = "test@test.com";
        mobile = "0123456789";
        password = "0123456789";
        repassword = "0123456789";
    }

    @Test
    public void signUpAndLogin_Success() {

        signUp_AddInformationAndClickSignUp();

        waitPageLoading();
        validateMainActivity();

        onView(withId(R.id.btn_logout)).perform(click());

        login_Success();
    }

    public void login_Success(){
        loginAction();
        waitPageLoading();
        validateMainActivity();
    }

    @Test
    public void signUp_NameFailed() {
        name = "";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_name)).check(matches(hasErrorText("at least 3 characters")));
    }

    @Test
    public void signUp_EmptyAddress() {
        address = "";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_address)).check(matches(hasErrorText("Enter Valid Address")));
    }

    @Test
    public void signUp_InvalidEmail() {
        email = "";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_email)).check(matches(hasErrorText("enter a valid email address")));
    }

    @Test
    public void signUp_InvalidMobile() {
        mobile = "0001245";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_mobile)).check(matches(hasErrorText("Enter Valid Mobile Number")));
    }

    @Test
    public void signUp_PasswordNotMatch() {
        repassword = "5555";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_reEnterPassword)).check(matches(hasErrorText("Password Do not match")));
    }

    @Test
    public void signUp_Cancel() {
        signUp_AddInformation();
        pressBackUnconditionally();
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void signUp_ClickAlreadyMember() {
        onView(withId(R.id.link_signup)).perform(click());
        onView(withId(R.id.link_login)).perform(closeSoftKeyboard(), scrollTo(), click());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void login_InvalidEmail(){
        email = "test";
        loginAction();
        onView(withId(R.id.input_email)).check(matches(hasErrorText("enter a valid email address")));
    }

    @Test
    public void login_InvalidPasswordMinLength(){
        password = "tes";
        loginAction();
        onView(withId(R.id.input_password)).check(matches(hasErrorText("between 4 and 10 alphanumeric characters")));
    }

    @Test
    public void login_InvalidPasswordMaxLength(){
        password = "01234567891";
        loginAction();
        onView(withId(R.id.input_password)).check(matches(hasErrorText("between 4 and 10 alphanumeric characters")));
    }

    @Test
    public void login_InvalidPassword(){
        password = "1234";
        loginAction();
        onView(withId(R.id.input_password)).check(matches(hasErrorText("enter a valid email address or password")));
    }

    @Test
    public void login_EmptyEmail(){
        email = "";
        loginAction();
        onView(withId(R.id.input_email)).check(matches(hasErrorText("enter a valid email address")));
    }

    @Test
    public void login_EmptyPassword(){
        password = "";
        loginAction();
        onView(withId(R.id.input_password)).check(matches(hasErrorText("between 4 and 10 alphanumeric characters")));
    }

    private static void loginAction(){
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

    private void signUp_AddInformationAndClickSignUp() {
        signUp_AddInformation();
        onView(withId(R.id.btn_signup)).perform(scrollTo(), click());
    }

    private void signUp_AddInformation() {
        onView(withId(R.id.link_signup)).perform(click());
        onView(withId(R.id.input_name)).perform(scrollTo(), typeText(name), closeSoftKeyboard());
        onView(withId(R.id.input_address)).perform(scrollTo(), typeText(address), closeSoftKeyboard());
        onView(withId(R.id.input_email)).perform(scrollTo(), typeText(email), closeSoftKeyboard());
        onView(withId(R.id.input_mobile)).perform(scrollTo(), typeText(mobile), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(scrollTo(), typeText(password), closeSoftKeyboard());
        onView(withId(R.id.input_reEnterPassword)).perform(scrollTo(), typeText(repassword), closeSoftKeyboard());
    }
}