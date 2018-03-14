package com.lasalle.mdpa.trinder;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.lasalle.mdpa.trinder.view.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class LoginUserIntegrationTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule =
            new ActivityTestRule<>(LoginActivity.class, false, false);


    private static TrinderApplication application;
    private static Scope applicationScope;

    @Before
    public void setup() {
        /*
        application = (TrinderApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
        applicationScope = Toothpick.openScope(application);

        // We need to install the modules and launch the activity manually
        applicationScope.installTestModules(new Module() {{
            bind(UserManager.class).toInstance(userManagerMock);
        }});
        */
        loginActivityRule.launchActivity(null);
    }

    @After
    public void tearDown() {
        /*
        Toothpick.reset(applicationScope);
        application.installToothPickModules(applicationScope);
        */
    }

    @Test
    public void checkUserAndPasswordReceiveAuth() {
        onView(withId(R.id.email))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
    }





}
