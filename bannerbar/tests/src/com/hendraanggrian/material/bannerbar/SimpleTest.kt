package com.hendraanggrian.material.bannerbar

import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.android.material.snackbar.Bannerbar
import com.hendraanggrian.material.bannerbar.test.R
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@LargeTest
@RunWith(AndroidJUnit4::class)
class SimpleTest : BaseTest() {
    @Rule @JvmField val rule = ActivityTestRule(InstrumentedActivity::class.java)

    @Test fun test() {
        onView(withId(R.id.toolbar)).perform(setTitle("Here's one"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Bannerbar.make(view, "No internet connection.", Bannerbar.LENGTH_LONG)
                    .show()
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
        onView(withId(R.id.toolbar)).perform(setTitle("Here's one with a button"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Bannerbar.make(view, "No internet connection.", Bannerbar.LENGTH_LONG)
                    .addAction("Retry") { v -> Toast.makeText(v.context, "Clicked!", Toast.LENGTH_SHORT).show() }
                    .show()
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }
}