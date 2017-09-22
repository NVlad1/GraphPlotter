package com.nvlad.mathapp.Activity;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;

import com.nvlad.mathapp.CustomRules.FunctionLabSetupActivityRule;
import com.nvlad.mathapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by Naboka Vladislav on 18.09.2017.
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> testRule = new FunctionLabSetupActivityRule<>(MainActivity.class);
    @Test
    public void MultigraphActivityLaunch(){
        onView(withId(R.id.btn_func)).perform(click());
        onView(withId(R.id.Fedit0)).check(matches(allOf(isDescendantOfA(withId(R.id.layout0)), isDisplayed())));
        onView(withId(R.id.btn0)).check(matches(allOf(isDescendantOfA(withId(R.id.layout0)), isDisplayed())));
        onView(withId(R.id.text0)).check(matches(allOf(isDescendantOfA(withId(R.id.layout0)), isDisplayed())));
        onView(withId(R.id.text0)).check(matches(withText("f(x)=")));
        onView(withId(R.id.Fedit0)).check(matches(withText("x")));
        onView(withId(R.id.Fedit1)).check(matches(withText("x+2")));
        onView(withId(R.id.Fedit2)).check(matches(withText("(x-6)^3")));
    }

    @Test
    public void MultigraphActivityLaunch2(){
        try {
            onView(withText(testRule.getActivity().getString(R.string.add_remove_function))).perform(click());
        }
        catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText(testRule.getActivity().getString(R.string.add_remove_function))).perform(click());
        }
        onView(withId(R.id.Fedit0)).check(matches(allOf(isDescendantOfA(withId(R.id.layout0)), isDisplayed())));
        onView(withId(R.id.btn0)).check(matches(allOf(isDescendantOfA(withId(R.id.layout0)), isDisplayed())));
        onView(withId(R.id.text0)).check(matches(allOf(isDescendantOfA(withId(R.id.layout0)), isDisplayed())));
        onView(withId(R.id.text0)).check(matches(withText("f(x)=")));
        onView(withId(R.id.Fedit0)).check(matches(withText("x")));
        onView(withId(R.id.Fedit1)).check(matches(withText("x+2")));
        onView(withId(R.id.Fedit2)).check(matches(withText("(x-6)^3")));
    }

    @Test
    public void ParametersActivityLaunch(){
        try {
            onView(withText(testRule.getActivity().getString(R.string.params))).perform(click());
        }
        catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText(testRule.getActivity().getString(R.string.params))).perform(click());
        }
        onView(withId(R.id.params_a)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_params_a)), isDisplayed())));
        onView(withId(R.id.params_b)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_params_b)), isDisplayed())));
        onView(withId(R.id.params_c)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_params_c)), isDisplayed())));
        onView(withId(R.id.params_d)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_params_d)), isDisplayed())));

        onView(withId(R.id.params_a)).check(matches(withText("2.0")));
        onView(withId(R.id.params_b)).check(matches(withText("-1.0")));
        onView(withId(R.id.params_c)).check(matches(withText("0.0")));
        onView(withId(R.id.params_d)).check(matches(withText("0.0")));
    }

    @Test
    public void HelpActivityLaunch(){
        try {
            onView(withText(testRule.getActivity().getString(R.string.help))).perform(click());
        }
        catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText(testRule.getActivity().getString(R.string.help))).perform(click());
        }
        onView(withText(R.string.help1)).check(matches(allOf(isDescendantOfA(withId(R.id.texts)), isDisplayed())));
        onView(withText(R.string.help2)).check(matches(allOf(isDescendantOfA(withId(R.id.texts)), isDisplayed())));
        onView(withText(R.string.help3)).check(matches(allOf(isDescendantOfA(withId(R.id.texts)), isDisplayed())));
        onView(withText(R.string.help4)).check(matches(allOf(isDescendantOfA(withId(R.id.texts)), isDisplayed())));
    }

    @Test
    public void OptionsActivityLaunch(){
        try {
            onView(withText(testRule.getActivity().getString(R.string.options))).perform(click());
        }
        catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText(testRule.getActivity().getString(R.string.options))).perform(click());
        }
        onView(withId(R.id.xmin_str)).check(matches(allOf(isDescendantOfA(withId(R.id.options_layout)), isDisplayed())));
        onView(withId(R.id.xmax_str)).check(matches(allOf(isDescendantOfA(withId(R.id.options_layout)), isDisplayed())));
        onView(withId(R.id.ymin_str)).check(matches(allOf(isDescendantOfA(withId(R.id.options_layout)), isDisplayed())));
        onView(withId(R.id.ymax_str)).check(matches(allOf(isDescendantOfA(withId(R.id.options_layout)), isDisplayed())));

        onView(withText(R.string.xmin)).check(matches(allOf(isDescendantOfA(withId(R.id.options_layout)), isDisplayed())));
        onView(withText(R.string.xmax)).check(matches(allOf(isDescendantOfA(withId(R.id.options_layout)), isDisplayed())));
        onView(withText(R.string.ymin)).check(matches(allOf(isDescendantOfA(withId(R.id.options_layout)), isDisplayed())));
        onView(withText(R.string.ymax)).check(matches(allOf(isDescendantOfA(withId(R.id.options_layout)), isDisplayed())));

        onView(withId(R.id.xmin_str)).check(matches(withText("-10.0")));
        onView(withId(R.id.xmax_str)).check(matches(withText("10.0")));
        onView(withId(R.id.ymin_str)).check(matches(withText("-10.0")));
        onView(withId(R.id.ymax_str)).check(matches(withText("10.0")));
    }

}