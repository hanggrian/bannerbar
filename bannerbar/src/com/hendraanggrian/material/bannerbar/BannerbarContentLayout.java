/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hendraanggrian.material.bannerbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.ContentViewCallback;

/**
 * @see com.google.android.material.snackbar.SnackbarContentLayout
 */
public class BannerbarContentLayout extends RelativeLayout implements ContentViewCallback {
    private TextView messageView;
    private Button neutralButton;
    private Button negativeButton;
    private Button positiveButton;

    private Drawable icon;

    public BannerbarContentLayout(@NonNull Context context) {
        this(context, null);
    }

    public BannerbarContentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerbarLayout);
        icon = a.getDrawable(R.styleable.BannerbarLayout_icon);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        messageView = findViewById(R.id.bannerbar_text);
        neutralButton = findViewById(R.id.bannerbar_action_neutral);
        negativeButton = findViewById(R.id.bannerbar_action_negative);
        positiveButton = findViewById(R.id.bannerbar_action_positive);
    }

    public TextView getMessageView() {
        return messageView;
    }

    public Button getNeutralButton() {
        return neutralButton;
    }

    public Button getNegativeButton() {
        return negativeButton;
    }

    public Button getPositiveButton() {
        return positiveButton;
    }

    void updateActionTextColorAlphaIfNeeded(float actionTextColorAlpha) {
        if (actionTextColorAlpha != 1) {
            updateActionTextColorAlpha(neutralButton, actionTextColorAlpha);
            updateActionTextColorAlpha(negativeButton, actionTextColorAlpha);
            updateActionTextColorAlpha(positiveButton, actionTextColorAlpha);
        }
    }

    private void updateActionTextColorAlpha(Button actionView, float actionTextColorAlpha) {
        int originalActionTextColor = actionView.getCurrentTextColor();
        int colorSurface = Utils.getColor(this, R.attr.colorSurface);
        int actionTextColor = Utils.layer(colorSurface, originalActionTextColor, actionTextColorAlpha);
        actionView.setTextColor(actionTextColor);
    }

    @Override
    public void animateContentIn(int delay, int duration) {
        animateIn(messageView, delay, duration);
        if (neutralButton.getVisibility() == VISIBLE) animateIn(neutralButton, delay, duration);
        if (negativeButton.getVisibility() == VISIBLE) animateIn(negativeButton, delay, duration);
        if (positiveButton.getVisibility() == VISIBLE) animateIn(positiveButton, delay, duration);
    }

    @Override
    public void animateContentOut(int delay, int duration) {
        animateOut(messageView, delay, duration);
        if (neutralButton.getVisibility() == VISIBLE) animateOut(neutralButton, delay, duration);
        if (negativeButton.getVisibility() == VISIBLE) animateOut(negativeButton, delay, duration);
        if (positiveButton.getVisibility() == VISIBLE) animateOut(positiveButton, delay, duration);
    }

    private static void animateIn(View view, int delay, int duration) {
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(duration).setStartDelay(delay).start();
    }

    private static void animateOut(View view, int delay, int duration) {
        view.setAlpha(1f);
        view.animate().alpha(0f).setDuration(duration).setStartDelay(delay).start();
    }
}