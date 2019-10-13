package com.hendraanggrian.material.errorbar

import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hendraanggrian.material.errorbar.test.R
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class SimpleTest : BaseTest() {

    @Rule @JvmField val rule = ActivityTestRule(InstrumentedActivity::class.java)

    @Test
    fun test() {
        onView(withId(R.id.toolbar)).perform(setTitle("Here's an errorbar"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Errorbar.make(view, "No internet connection.", Errorbar.LENGTH_LONG)
                    .setAction("Retry") { v ->
                        Toast.makeText(v.context, "Clicked!", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
        onView(withId(R.id.toolbar)).perform(setTitle("Here's one with a backdrop"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Errorbar.make(view, "You have no new emails", Errorbar.LENGTH_LONG)
                    .setBackground(R.drawable.bg_empty)
                    .noImage()
                    .setContentMarginBottom(150)
                    .show()
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }
}