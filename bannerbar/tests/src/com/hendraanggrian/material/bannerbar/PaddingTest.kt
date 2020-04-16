package com.hendraanggrian.material.bannerbar

import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.google.android.material.snackbar.Bannerbar
import com.hendraanggrian.material.bannerbar.test.R
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@LargeTest
@RunWith(AndroidJUnit4::class)
class PaddingTest : BaseTest() {
    @Rule @JvmField val rule = ActivityTestRule(InstrumentedActivity::class.java)

    @BeforeTest fun yo() {
        onView(withId(R.id.toolbar)).perform(setTitle("Padding Test"))
    }

    @Test fun horizontal() {
        val title = "Checking horizontal padding..."
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Bannerbar.make(view, title, Bannerbar.LENGTH_SHORT)
                    .setSubtitle("Without icon")
                    .addCallback(object : Bannerbar.Callback() {
                        override fun onDismissed(bannerbar: Bannerbar, event: Int) {
                            val layout = bannerbar.contentLayout
                            assertNotEquals(0, ViewCompat.getPaddingStart(layout))
                            assertNotEquals(0, ViewCompat.getPaddingEnd(layout))
                        }
                    })
                    .show()
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Bannerbar.make(view, title, Bannerbar.LENGTH_SHORT)
                    .setIcon(android.R.drawable.ic_delete)
                    .setSubtitle("With icon")
                    .addCallback(object : Bannerbar.Callback() {
                        override fun onDismissed(bannerbar: Bannerbar, event: Int) {
                            val layout = bannerbar.contentLayout
                            assertEquals(0, ViewCompat.getPaddingStart(layout))
                            assertEquals(0, ViewCompat.getPaddingEnd(layout))
                        }
                    })
                    .show()
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }

    @Test fun vertical() {
        val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
        val defaultVertical = resources.getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines)
        val shortVertical = resources.getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical)
        val title = "Checking vertical padding..."
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Bannerbar.make(view, title, Bannerbar.LENGTH_SHORT)
                    .setSubtitle("Without button")
                    .addCallback(object : Bannerbar.Callback() {
                        override fun onDismissed(bannerbar: Bannerbar, event: Int) {
                            val layout = bannerbar.contentLayout
                            assertEquals(defaultVertical, layout.paddingTop)
                            assertEquals(defaultVertical, layout.paddingBottom)
                        }
                    })
                    .show()
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Bannerbar.make(view, title, Bannerbar.LENGTH_SHORT)
                    .setSubtitle("With button")
                    .addAction("Confirm")
                    .addCallback(object : Bannerbar.Callback() {
                        override fun onDismissed(bannerbar: Bannerbar, event: Int) {
                            val layout = bannerbar.contentLayout
                            assertEquals(defaultVertical, layout.paddingTop)
                            assertEquals(shortVertical, layout.paddingBottom)
                        }
                    })
                    .show()
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }
}