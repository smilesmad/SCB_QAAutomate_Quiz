package com.sourcey.materiallogindemo;

import android.app.Application;
//import android.support.test.espresso.NoMatchingViewException;
//import android.support.test.espresso.matcher.BoundedMatcher;
import android.test.ApplicationTestCase;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import android.view.View;
//import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
//import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

//import java.util.regex.Matcher;

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
//import static android.support.test.espresso.matcher.BoundedMatcher.*;

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
        //Verify sign up successfully

        //Singup
        signUp_AddInformationAndClickSignUp();

        //Wait main page load
        waitPageLoading();

        //validate main page loaded successfully
        validateMainActivity();

        //Click logout
        onView(withId(R.id.btn_logout)).perform(click());
    }

    @Test
    public void TC201_login_Success(){
        // Verify valid user can login successfully.

        //Login
        loginAction();

        //Wait main page load
        waitPageLoading();

        //validate main page loaded successfully
        validateMainActivity();
    }

    @Test
    public void TC301_logout_Success(){
        // Verify logout button works correctly

        //Login
        TC201_login_Success();

        // 4.Click Logout button
        onView(withId(R.id.btn_logout)).perform(click());

        //Login page displays
        onView(withId(R.id.input_email)).check(matches(isDisplayed())).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void TC102_signUp_NameEmpty() {
        // Verify name field cannot be blank

        //Setup name = ""
        name = "";

        //Sign up
        signUp_AddInformationAndClickSignUp();

        //Check error
        onView(withId(R.id.input_name)).perform(scrollTo()).check(matches(hasErrorText("at least 3 characters")));
    }

    @Test
    public void TC103_signUp_NameLessThan3Char() {
        //Verify minimum of name's length is 3 characters

        // set name
        name = "12";

        //Sign up
        signUp_AddInformationAndClickSignUp();

        //Check error
        onView(withId(R.id.input_name)).perform(scrollTo()).check(matches(hasErrorText("at least 3 characters")));
    }

    @Test
    public void TC104_signUp_AddressEmpty() {
        //Verify address field cannot be blank

        //set name
        name = "nam";
        //clear address
        address = "";
        //set password and repassword
        password = "1234";
        password = repassword;

        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_address)).perform(scrollTo()).check(matches(hasErrorText("Enter Valid Address")));
        //onView(withId(R.id.input_name)).check(matches(hasNoErrorText()));
        //onView(withId(R.id.input_password)).check(matches(hasNoErrorText()));
    }

    @Test
    public void TC105_signUp_EmailEmpty() {
        //Verify email field cannot be blank

        //clear email
        email = "";
        signUp_AddInformationAndClickSignUp();
        validateEmailInvalid();
    }

    @Test
    public void TC106_signUp_EmailInvalidFormat() {
        //Verify email's format should be correct.

        //set new email
        email = "test@.";
        signUp_AddInformationAndClickSignUp();
        validateEmailInvalid();
    }

    @Test
    public void TC107_signUp_MobileEmpty() {
        //Verify mobile field cannot be blank

        //clear mobile
        mobile = "";
        signUp_AddInformationAndClickSignUp();
        validateMobileError();
    }

    @Test
    public void TC108_signUp_MobileLessThan10Char() {
        //Verify mobile number must have 10 digits

        //set mobile
        mobile = "0123";
        signUp_AddInformationAndClickSignUp();
        validateMobileError();
    }

    @Test
    public void TC109_signUp_MobileMoreThan10Char() {
        //Verify mobile number must have 10 digits

        //set mobile
        mobile = "01234567891";
        signUp_AddInformationAndClickSignUp();
        validateMobileError();
    }

    @Test
    public void TC110_signUp_PasswordEmpty() {
        //Verify password field cannot be blank

        //clear password
        password = "";
        repassword = password;
        signUp_AddInformationAndClickSignUp();
        validatePasswordError();
    }

    @Test
    public void TC111_signUp_PasswordLessThan4Char() {
        //Verify minimum of password's length is 4 characters

        //Set new password
        password = "555";
        repassword = password;
        signUp_AddInformationAndClickSignUp();
        validatePasswordError();
    }

    @Test
    public void TC112_signUp_PasswordMoreThan10Char() {
        //Verify maximum of password's length is 10 characters

        //Set new password
        password = "123456789012";
        repassword = password;
        signUp_AddInformationAndClickSignUp();
        validatePasswordError();
    }

    @Test
    public void TC113_signUp_RePasswordNotMatch() {
        //Verify repassword must be match with password

        //Set new repassword
        repassword = "5555";
        signUp_AddInformationAndClickSignUp();
        onView(withId(R.id.input_reEnterPassword)).check(matches(hasErrorText("Password Do not match")));
    }

    @Test
    public void TC114_signUp_Cancel() {
        //Press back on signup page must redirect to login page
        clickSignUpLink();

        //Close keyboard
        onView(withId(R.id.input_name)).perform(closeSoftKeyboard());

        //press back
        pressBackUnconditionally();

        //Wait page
        waitPageLoading();

        //Validate login page
        onView(withId(R.id.input_email)).check(matches(isDisplayed())).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void TC115_signUp_ClickAlreadyMember() {
        //Verify 'Already member' link work properly
        clickSignUpLink();
        onView(withId(R.id.input_name)).perform(closeSoftKeyboard());
        onView(withId(R.id.link_login)).perform(scrollTo(), click());
        onView(withId(R.id.input_email)).check(matches(isDisplayed())).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void TC116_signUp_DuplicateData() {
        //Verify when sign up the same information must be error

        signUp_AddInformationAndClickSignUp();
        waitPageLoading();

        //After click signup, it should stay with SignUp page
        onView(withId(R.id.input_name)).check(matches(isDisplayed()));
    }

    @Test
    public void TC202_login_EmailInvalidFormat(){
        //Verify invalid email format must show error

        //set email
        email = "test1@..";
        loginAction();
        validateEmailInvalid();
    }

    @Test
    public void TC203_login_EmailEmpty(){
        //Verify email field cannot be blank

        //clear email
        email = "";
        loginAction();
        validateEmailInvalid();
    }

    @Test
    public void TC204_login_PasswordMinLength(){
        //Verify minimum of password's length is 4 characters

        //set password
        password = "tes";
        loginAction();
        validatePasswordError();
    }

    @Test
    public void TC205_login_PasswordMaxLength(){
        //Verify maximum of password's length is 10 characters

        //set password
        password = "123456789012";
        loginAction();
        validatePasswordError();
    }

    @Test
    public void TC206_login_PasswordInvalid(){
        //Verify invalid password cannot access to system

        //set password
        password = "1234";
        loginAction();
        onView(withId(R.id.input_password)).check(matches(hasErrorText("enter a valid email address or password")));
    }

    @Test
    public void TC207_login_PasswordEmpty(){
        //Verify password field cannot be blank

        //clear password
        password = "";
        loginAction();
        validatePasswordError();
    }

    private static void loginAction(){
        // 1.Enter valid registered email
        // 2.Enter valid password
        // 3.Click Login button

        onView(withId(R.id.input_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
    }

    private static void clickSignUpLink(){
        // Click Signup link
        onView(withId(R.id.input_email)).check(matches(isDisplayed())).perform(closeSoftKeyboard());
        onView(withId(R.id.link_signup)).perform(scrollTo(), click());
    }

    private static void validateEmailInvalid(){
        //Validate error, enter a valid email address
        onView(withId(R.id.input_email)).check(matches(hasErrorText("enter a valid email address")));
    }

    private static void validatePasswordError(){
        //Validate error, between 4 and 10 alphanumeric characters
        onView(withId(R.id.input_password)).check(matches(hasErrorText("between 4 and 10 alphanumeric characters")));
    }

    private static void validateMobileError(){
        //Validate error Enter Valid Mobile Number
        onView(withId(R.id.input_mobile)).check(matches(hasErrorText("Enter Valid Mobile Number")));
    }

    private static void validateMainActivity(){
        //On Main page should have 'Hello World' and logout button
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

        // 3.Click 'Sign up'
        onView(withId(R.id.btn_signup)).perform(scrollTo(), click());
    }

    private void signUp_AddInformation() {
        // 1.Click 'Signup' link
        // 2.Add informaion
        clickSignUpLink();
        onView(withId(R.id.input_name)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.input_address)).perform(typeText(address), closeSoftKeyboard());
        onView(withId(R.id.input_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.input_mobile)).perform(typeText(mobile), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.input_reEnterPassword)).perform(typeText(repassword), closeSoftKeyboard());
    }

/*    public static Matcher<View> hasNoErrorText() {
        return new BoundedMatcher<View, EditText>(EditText.class) {

            @Override
            public void describeTo(Description description) {
               // description.appendText("has no error text");
            }

            @Override
            protected boolean matchesSafely(EditText view) {
                return view.getError() == null;
            }
        };
    }*/
}