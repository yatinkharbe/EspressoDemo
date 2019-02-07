package com.test.espressodemo;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.test.espressodemo.MainActivity;
import com.test.espressodemo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;


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

        // Enter View User Id with Espresso word and close soft keyboard
        onView(withId(R.id.edtUserId))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());

        // Enter View User Id with Espresso word and close soft keyboard
        onView(withId(R.id.edtPass))
                .perform(typeText(mValidPass), closeSoftKeyboard());

        // Click on Login Button
        onView(withId(R.id.loginBtn)).perform(click());


        /* Once Clicked on Login we have set condition if Login text Content matches Password Content then The Login is a success */

        // Fetch Content of Password Edit Text using Matcher
        String textFromPasswordFld = getText(withId(R.id.edtPass));

        //Check User Id Content with Content Retreived from Password Edit Text Field
        onView(withId(R.id.edtUserId)).check(matches(isEditTextValueEqualTo(R.id.edtUserId, textFromPasswordFld)));

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



        String textFromPasswordFld = getText(withId(R.id.edtPass));
        onView(withId(R.id.edtUserId)).check(matches(not(isEditTextValueEqualTo(R.id.edtUserId, textFromPasswordFld))));


    }

    @Test
    public void checkFailedUserNameValidation() {
        onView(withId(R.id.edtPass))
                .perform(typeText(mInValidPass), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());


        String textFromUserFld = getText(withId(R.id.edtUserId)); // Content Retreived would be blank
        onView(withId(R.id.edtUserId)).check(matches(isEditTextValueEqualTo(R.id.edtUserId, textFromUserFld)));

    }

    @Test
    public void checkFailedPasswordValidation() {
        onView(withId(R.id.edtUserId))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());


        String textFromPasswordFld = getText(withId(R.id.edtPass)); // Content Retreived would be blank
        onView(withId(R.id.edtPass)).check(matches(isEditTextValueEqualTo(R.id.edtPass, textFromPasswordFld)));


    }





    // To Match EditText value with content

    Matcher<View> isEditTextValueEqualTo(final int viewId, final String content) {

        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Match Edit Text Value with View ID Value : :  " + content);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (view != null) {
                    String editTextValue = ((EditText) view.findViewById(viewId)).getText().toString();

                    if (editTextValue.equalsIgnoreCase(content)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }



    // To get EditText text String

    String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }






}
