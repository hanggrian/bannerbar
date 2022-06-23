package com.hendraanggrian.material.bannerbar.test;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

public class AbstractClass {
    public ViewAction setTitle(final String title) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(Toolbar.class);
            }

            @Override
            public String getDescription() {
                return String.format("setTitle(%s)", title);
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((Toolbar) view).setTitle(title);
            }
        };
    }
}
