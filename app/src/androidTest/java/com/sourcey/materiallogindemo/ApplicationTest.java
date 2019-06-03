package com.sourcey.materiallogindemo;

import android.app.Application;
import android.support.test.espresso.NoMatchingViewException;
import android.test.ApplicationTestCase;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBackUnconditionally;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void setup() {
        name = "name";
        address = "address";
        email = "test@email.com";
        mobile = "0123456789";
        password = "0123456789";
        repassword = password;
    }

    @After
    public void tearDown(){

    }

    @Test
    public void TC101_signUp_Success() {
        signUp_AddInformationAndClickSignUp();
        waitPageLoading();
        validateMainActivity();
        onView(withId(R.id.btn_logout)).perform(click());
    }

    @Test
    public void TC201_login_Success(){
        loginAction();
        waitPageLoading();
        validateMainActivity();
    }

    @Test
    public void TC301_logout_Success(){
        TC201_login_Success();
        onView(withId(R.id.btn_logout)).perform(click());
        onView(withId(R.id.input_email)).check(matches(isDisplayed())).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void TC102_signUp_NameEmpty() {
        name = "";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_name)).perform(scrollTo()).check(matches(hasErrorText("at least 3 characters")));
    }

    @Test
    public void TC103_signUp_NameLessThan3Char() {
        name = "12";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_name)).perform(scrollTo()).check(matches(hasErrorText("at least 3 characters")));
    }

    @Test
    public void TC104_signUp_AddressEmpty() {
        address = "";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_address)).perform(scrollTo()).check(matches(hasErrorText("Enter Valid Address")));
    }

    @Test
    public void TC105_signUp_EmailEmpty() {
        email = "";
        signUp_AddInformationAndClickSignUp();
        validateEmailInvalid();
    }

    @Test
    public void TC106_signUp_EmailInvalidFormat() {
        email = "test@.";
        signUp_AddInformationAndClickSignUp();
        validateEmailInvalid();
    }

    @Test
    public void TC107_signUp_MobileInvalid() {
        mobile = "01234567891";
        signUp_AddInformationAndClickSignUp();
        validateMobileError();
    }

    @Test
    public void TC108_signUp_MobileEmpty() {
        mobile = "";
        signUp_AddInformationAndClickSignUp();
        validateMobileError();
    }

    @Test
    public void TC109_signUp_MobileLessThan10Char() {
        mobile = "1025";
        signUp_AddInformationAndClickSignUp();
        validateMobileError();
    }

    @Test
    public void TC109_signUp_MobileMoreThan10Char() {
        mobile = "01234567891";
        signUp_AddInformationAndClickSignUp();
        validateMobileError();
    }

    @Test
    public void TC110_signUp_PasswordEmpty() {
        password = "";
        repassword = password;
        signUp_AddInformationAndClickSignUp();
        validatePasswordError();
    }

    @Test
    public void TC111_signUp_PasswordLessThan3Char() {
        password = "55";
        repassword = password;
        signUp_AddInformationAndClickSignUp();
        validatePasswordError();
    }

    @Test
    public void TC112_signUp_PasswordMoreThan10Char() {
        password = "123456789012";
        repassword = password;
        signUp_AddInformationAndClickSignUp();
        validatePasswordError();
    }

    @Test
    public void TC113_signUp_RePasswordNotMatch() {
        repassword = "5555";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_reEnterPassword)).check(matches(hasErrorText("Password Do not match")));
    }

    @Test
    public void TC114_signUp_Cancel() {
        clickSignUpLink();
        pressBackUnconditionally();
        waitPageLoading();
        onView(withId(R.id.input_email)).check(matches(isDisplayed())).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void TC115_signUp_ClickAlreadyMember() {
        clickSignUpLink();
        onView(withId(R.id.input_name)).perform(closeSoftKeyboard());
        onView(withId(R.id.link_login)).perform(scrollTo(), click());
        onView(withId(R.id.input_email)).check(matches(isDisplayed())).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void TC116_signUp_DuplicateData() {
        signUp_AddInformationAndClickSignUp();
        waitPageLoading();
        onView(withId(R.id.input_name)).check(matches(isDisplayed()));
    }

    @Test
    public void TC202_login_EmailInvalidFormat(){
        email = "test1@..";
        loginAction();
        validateEmailInvalid();
    }

    @Test
    public void TC203_login_EmailEmpty(){
        email = "";
        loginAction();
        validateEmailInvalid();
    }

    @Test
    public void TC204_login_PasswordMinLength(){
        password = "tes";
        loginAction();
        validatePasswordError();
    }

    @Test
    public void TC205_login_PasswordMaxLength(){
        password = "123456789012";
        loginAction();
        validatePasswordError();
    }

    @Test
    public void TC206_login_PasswordInvalid(){
        password = "1234";
        loginAction();
        onView(withId(R.id.input_password)).check(matches(hasErrorText("enter a valid email address or password")));
    }

    @Test
    public void TC207_login_PasswordEmpty(){
        password = "";
        loginAction();
        validatePasswordError();
    }

    private static void loginAction(){
        onView(withId(R.id.input_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
    }

    private static void clickSignUpLink(){
        onView(withId(R.id.input_email)).check(matches(isDisplayed())).perform(closeSoftKeyboard());
        onView(withId(R.id.link_signup)).perform(scrollTo(), click());
    }

    private static void validateEmailInvalid(){
        onView(withId(R.id.input_email)).check(matches(hasErrorText("enter a valid email address")));
    }

    private static void validatePasswordError(){
        onView(withId(R.id.input_password)).check(matches(hasErrorText("between 4 and 10 alphanumeric characters")));
    }

    private static void validateMobileError(){
        onView(withId(R.id.input_mobile)).check(matches(hasErrorText("Enter Valid Mobile Number")));
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
        clickSignUpLink();
        onView(withId(R.id.input_name)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.input_address)).perform(typeText(address), closeSoftKeyboard());
        onView(withId(R.id.input_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.input_mobile)).perform(typeText(mobile), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.input_reEnterPassword)).perform(typeText(repassword), closeSoftKeyboard());
    }
}