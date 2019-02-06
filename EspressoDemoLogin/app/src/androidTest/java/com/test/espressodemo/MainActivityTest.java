package com.test.espressodemo;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.test.espressodemo.MainActivity;
import com.test.espressodemo.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private String mStringToBetyped, mValidPass, mInValidPass;


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Espresso";
        mValidPass = "Espresso";
        mInValidPass = "Invalid";

    }


    @Test
    public void validLogin() {
        onView(withId(R.id.edtUserId))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.edtPass))
                .perform(typeText(mValidPass), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void failedLogin() {
        onView(withId(R.id.edtUserId))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.edtPass))
                .perform(typeText(mInValidPass), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
    }

    @Test
    public void checkFailedUserNameValidation() {
        onView(withId(R.id.edtPass))
                .perform(typeText(mInValidPass), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
    }

    @Test
    public void checkFailedPasswordValidation() {
        onView(withId(R.id.edtUserId))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
    }


}
