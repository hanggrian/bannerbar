package com.hendraanggrian.material.bannerbar.ktx

import android.view.View
import android.widget.FrameLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.android.material.snackbar.Bannerbar
import com.google.android.material.snackbar.addCallback
import com.google.android.material.snackbar.bannerbar
import com.google.android.material.snackbar.longBannerbar
import com.google.android.material.snackbar.shortBannerbar
import com.hendraanggrian.material.bannerbar.test.AbstractTest
import com.hendraanggrian.material.bannerbar.test.R
import com.hendraanggrian.material.bannerbar.test.TestActivity
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@LargeTest
@RunWith(AndroidJUnit4::class)
class KotlinExtensionsTest : AbstractTest() {
    @Rule @JvmField val rule = ActivityTestRule(TestActivity::class.java)

    @BeforeTest fun title() {
        onView(withId(R.id.toolbar)).perform(setTitle("Kotlin extensions test"))
    }

    @Ignore("Falsely returning Bannerbar.LENGTH_LONG")
    @Test fun short() {
        val title = "Is this a short bannerbar?"
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                assertEquals(Bannerbar.LENGTH_SHORT, view.shortBannerbar(title).duration)
                view.shortBannerbar(title) {
                    addCallback { onShown { assertEquals(Bannerbar.LENGTH_SHORT, duration) } }
                }
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }

    @Test fun long() {
        val title = "Is this a long bannerbar?"
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                assertEquals(Bannerbar.LENGTH_LONG, view.longBannerbar(title).duration)
                view.longBannerbar(title) {
                    addCallback { onShown { assertEquals(Bannerbar.LENGTH_LONG, duration) } }
                }
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }

    @Test fun indefinite() {
        val title = "Is this an indefinite bannerbar?"
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                assertEquals(Bannerbar.LENGTH_INDEFINITE, view.bannerbar(title).duration)
                view.bannerbar(title) {
                    addCallback { onShown { assertEquals(Bannerbar.LENGTH_INDEFINITE, duration) } }
                }
            }
        })
        onView(withId(R.id.progressBar)).perform(delay(4000))
    }
}