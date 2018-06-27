package com.hendraanggrian.errorbar

import android.support.design.widget.errorbar
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.FrameLayout
import com.hendraanggrian.errorbar.activity.InstrumentedActivity
import org.jetbrains.anko.toast
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.hendraanggrian.errorbar.test.R

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
                view.errorbar("No internet connection.") {
                    setAction("Retry") { v ->
                        v.context.toast("Clicked!")
                    }
                }
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
        onView(withId(R.id.toolbar)).perform(setTitle("Here's one with a backdrop"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                view.errorbar("You have no new emails") {
                    setBackground(R.drawable.bg_empty)
                    setContentMarginBottom(150)
                }
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }
}