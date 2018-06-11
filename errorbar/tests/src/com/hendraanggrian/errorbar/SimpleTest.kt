package com.hendraanggrian.errorbar

import android.support.design.widget.longErrorbar
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.toast
import com.hendraanggrian.errorbar.activity.InstrumentedActivity
import com.hendraanggrian.errorbar.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleTest : BaseTest() {

    @Rule @JvmField val rule = ActivityTestRule(InstrumentedActivity::class.java)

    @Test
    fun test() {
        onView(withId(R.id.toolbar)).perform(setTitle("Here's an errorbar"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                (view as FrameLayout).longErrorbar("No internet connection.")
                    .setAction("Retry", { v ->
                        v.context.toast("Clicked!")
                    })
                    .setBackdropResource(R.drawable.errorbar_bg_cloud)
                    .setLogoResource(R.drawable.errorbar_ic_cloud)
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
        onView(withId(R.id.toolbar)).perform(setTitle("Here's one with a backdrop"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                (view as FrameLayout).longErrorbar("You have no new emails")
                    .setBackdropResource(R.drawable.bg_empty)
                    .setContentMarginBottom(100)
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }
}