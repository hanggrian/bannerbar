package com.hendraanggrian.errorview.test

import android.os.Build
import android.os.CountDownTimer
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.registerIdlingResources
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import com.hendraanggrian.widget.ErrorView2
import org.hamcrest.Matcher
import org.jetbrains.anko.toast
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class InstrumentedTest {

    @Rule @JvmField var rule = ActivityTestRule(InstrumentedActivity::class.java)

    fun test1_xml() {}

    @Test
    fun test2_simple() {
        onView(withId(R.id.toolbar))
                .perform(setTitle("Here's an errorview"))
        onView(withId(R.id.frameLayout))
                .perform(object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return isAssignableFrom(FrameLayout::class.java)
                    }

                    override fun getDescription(): String {
                        return FrameLayout::class.java.name
                    }

                    override fun perform(uiController: UiController, view: View) {
                        ErrorView2.make(view as FrameLayout, "No internet connection.", ErrorView2.LENGTH_LONG)
                                .setBackdropDrawable(R.drawable.errorview_bg_cloud)
                                .setLogoDrawable(R.drawable.errorview_ic_cloud)
                                .setAction("Retry", { v ->
                                    v.context.toast("Clicked!")
                                    true
                                })
                                .show()
                    }
                }, delay(5000))
    }

    @Test
    fun test3_custom() {
        onView(withId(R.id.toolbar))
                .perform(setTitle("Here's another"))
        onView(withId(R.id.frameLayout))
                .perform(object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return isAssignableFrom(FrameLayout::class.java)
                    }

                    override fun getDescription(): String {
                        return FrameLayout::class.java.name
                    }

                    override fun perform(uiController: UiController, view: View) {
                        ErrorView2.make(view as FrameLayout, "No internet connection.", ErrorView2.LENGTH_INDEFINITE)
                                .setBackdropDrawable(R.drawable.bg_empty)
                                .setLogoBitmap(null)
                                .setAction(null, {
                                    view.setVisibility(View.GONE)
                                    true
                                })
                                .show()
                        registerIdlingResources(ViewVisibilityIdlingResource(view, View.GONE))
                    }
                })
        onView(withId(R.id.frameLayout))
                .perform(object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return isAssignableFrom(FrameLayout::class.java)
                    }

                    override fun getDescription(): String {
                        return FrameLayout::class.java.name
                    }

                    override fun perform(uiController: UiController, view: View) {}
                })
    }

    private fun setTitle(title: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(Toolbar::class.java)
            }

            override fun getDescription(): String {
                return "title to " + title
            }

            override fun perform(uiController: UiController, view: View) {
                (view as Toolbar).title = title
            }
        }
    }

    private fun delay(millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun getDescription(): String {
                return "delay for " + millis
            }

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
}