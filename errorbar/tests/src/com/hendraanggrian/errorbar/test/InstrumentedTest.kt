package com.hendraanggrian.errorbar.test

import android.os.Build
import android.os.CountDownTimer
import android.support.design.widget.Errorbar
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import com.hendraanggrian.kota.content.toPx
import org.jetbrains.anko.toast
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

    @Test
    fun test() {
        onView(withId(R.id.toolbar)).perform(setTitle("Here's an errorbar"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Errorbar.make(view as FrameLayout, "No internet connection.", Errorbar.LENGTH_INDEFINITE)
                        .setBackdropDrawable(R.drawable.errorbar_bg_cloud)
                        .setLogoDrawable(R.drawable.errorbar_ic_cloud)
                        .setAction("Retry", View.OnClickListener { v ->
                            v.context.toast("Clicked!")
                        })
                        .show()
            }
        }, delay(4000))
        onView(withId(R.id.toolbar)).perform(setTitle("Here's one with a backdrop"))
        onView(withId(R.id.frameLayout)).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(FrameLayout::class.java)
            override fun getDescription() = FrameLayout::class.java.name
            override fun perform(uiController: UiController, view: View) {
                Errorbar.make(view as FrameLayout, "You have no new emails", Errorbar.LENGTH_LONG)
                        .setBackdropDrawable(R.drawable.bg_empty)
                        .setContentMarginBottom(64.toPx())
                        .show()
            }
        }, delay(4000))
    }

    private fun setTitle(title: String) = object : ViewAction {
        override fun getConstraints() = isAssignableFrom(Toolbar::class.java)
        override fun getDescription() = "title to $title"
        override fun perform(uiController: UiController, view: View) {
            (view as Toolbar).title = title
        }
    }

    private fun delay(millis: Long) = object : ViewAction {
        override fun getConstraints() = isDisplayed()
        override fun getDescription() = "delay for $millis"
        override fun perform(uiController: UiController, view: View) {
            val progressBar = rule.activity.progressBar!!
            progressBar.visibility = View.VISIBLE
            progressBar.progress = 100
            object : CountDownTimer(millis, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progressBar.setProgress((progressBar.max * millisUntilFinished / millis).toInt(), true)
                    } else {
                        progressBar.progress = (progressBar.max * millisUntilFinished / millis).toInt()
                    }
                }

                override fun onFinish() {
                    progressBar.visibility = View.GONE
                }
            }.start()
            uiController.loopMainThreadForAtLeast(millis)
        }
    }
}