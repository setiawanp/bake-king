package com.setiawanpaiman.bakeking.android.recipelist;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.setiawanpaiman.bakeking.android.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeStepListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void recipeStepListActivityTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.name), withText("Nutella Pie"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("Nutella Pie"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Nutella Pie")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.content), withText("Ingredient List:\n - Graham Cracker crumbs (2 CUP)\n - unsalted butter, melted (6 TBLSP)\n - granulated sugar (0.5 CUP)\n - salt (1.5 TSP)\n - vanilla (5 TBLSP)\n - Nutella or other chocolate-hazelnut spread (1 K)\n - Mascapone Cheese(room temperature) (500 G)\n - heavy cream(cold) (1 CUP)\n - cream cheese(softened) (4 OZ)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.content), withText("0. Recipe Introduction"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        1),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("0. Recipe Introduction")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.content), withText("1. Starting prep"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        2),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("1. Starting prep")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.content), withText("2. Prep the cookie crust."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        3),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("2. Prep the cookie crust.")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.content), withText("3. Press the crust into baking form."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        4),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("3. Press the crust into baking form.")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.content), withText("4. Start filling prep"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        5),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("4. Start filling prep")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.content), withText("5. Finish filling prep"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        6),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("5. Finish filling prep")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.content), withText("6. Finishing Steps"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        7),
                                0),
                        isDisplayed()));
        textView9.check(matches(withText("6. Finishing Steps")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.content), withText("6. Finishing Steps"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipestep_list),
                                        7),
                                0),
                        isDisplayed()));
        textView10.check(matches(withText("6. Finishing Steps")));

    }

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
    }
}
