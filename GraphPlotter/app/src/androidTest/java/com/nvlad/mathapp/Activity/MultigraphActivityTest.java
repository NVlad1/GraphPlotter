package com.nvlad.mathapp.Activity;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.EditText;

import com.nvlad.mathapp.CustomRules.FunctionLabSetupActivityRule;
import com.nvlad.mathapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Naboka Vladislav on 16.09.2017.
 */

public class MultigraphActivityTest {

    @Rule
    public ActivityTestRule<MultigraphActivity> rule  = new FunctionLabSetupActivityRule<>(MultigraphActivity.class);

    @Test
    public void ensureEdittextDefined() throws Exception {
        MultigraphActivity activity = rule.getActivity();
        View edit0 = activity.findViewById(R.id.Fedit0);
        View edit6 = activity.findViewById(R.id.Fedit6);
        assertThat(edit0,notNullValue());
        assertThat(edit6,notNullValue());
        assertThat(edit0, instanceOf(EditText.class));
        assertThat(edit6, instanceOf(EditText.class));
    }

    @Test
    public void ensureEdittextCorrectVisibility() throws Exception {
        onView(withId(R.id.Fedit0)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.Fedit1)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.Fedit2)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.Fedit3)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.Fedit4)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.Fedit5)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.Fedit6)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }



}
