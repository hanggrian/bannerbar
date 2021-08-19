package com.hendraanggrian.material.bannerbar.test

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom

abstract class AbstractTest {

    fun setTitle(title: String) = object : ViewAction {
        override fun getConstraints() = isAssignableFrom(Toolbar::class.java)
        override fun getDescription() = "setTitle($title)"
        override fun perform(uiController: UiController, view: View) {
            (view as Toolbar).title = title
        }
    }
}