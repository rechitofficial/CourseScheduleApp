package com.dicoding.courseschedule.ui.home


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.courseschedule.R
import org.junit.Before

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun homeActivityTest() {
        onView(withId(R.id.action_add)).perform(click())
        onView(withId(R.id.add_course_name)).check(matches(isDisplayed()))
        onView(withId(R.id.dropdown_day)).check(matches(isDisplayed()))
        onView(withId(R.id.add_start_time_picker)).check(matches(isDisplayed()))
        onView(withId(R.id.add_end_time_picker)).check(matches(isDisplayed()))
        onView(withId(R.id.add_lecturer_name)).check(matches(isDisplayed()))
        onView(withId(R.id.add_note)).check(matches(isDisplayed()))

    }
}
