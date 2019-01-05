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

package com.hendraanggrian.material.errorbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;

import com.google.android.material.snackbar.ContentViewCallback;

/**
 * @see com.google.android.material.snackbar.SnackbarContentLayout
 */
public class ErrorbarContentLayout extends FrameLayout implements ContentViewCallback {
    private ImageView backgroundView;
    private ViewGroup containerView;
    private ImageView imageView;
    private TextView messageView;
    private Button actionView;

    // keep TypedArray a little bit longer because views are binded in onFinishInflate()
    private TypedArray a;

    public ErrorbarContentLayout(Context context) {
        this(context, null);
    }

    public ErrorbarContentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.errorbarStyle);
    }

    @SuppressLint("CustomViewStyleable")
    public ErrorbarContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a = context.obtainStyledAttributes(attrs, R.styleable.ErrorbarLayout,
            defStyleAttr, R.style.Widget_Design_Errorbar);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onFinishInflate() {
        super.onFinishInflate();

        // bind views
        backgroundView = findViewById(R.id.errorbar_background);
        containerView = findViewById(R.id.errorbar_container);
        imageView = findViewById(R.id.errorbar_image);
        messageView = findViewById(R.id.errorbar_message);
        actionView = findViewById(R.id.errorbar_action);

        // setImageDrawable attr and finally recycle
        if (a.hasValue(R.styleable.ErrorbarLayout_android_background)) {
            ViewCompat.setBackground(this, null);
            ViewUtils.setImageDrawable(backgroundView,
                a.getDrawable(R.styleable.ErrorbarLayout_android_background));
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_src)) {
            ViewUtils.setImageDrawable(imageView,
                a.getDrawable(R.styleable.ErrorbarLayout_android_src));
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textAppearance)) {
            TextViewCompat.setTextAppearance(messageView,
                a.getResourceId(R.styleable.ErrorbarLayout_android_textAppearance, 0));
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textColor)) {
            messageView.setTextColor(
                a.getColorStateList(R.styleable.ErrorbarLayout_android_textColor));
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textSize)) {
            messageView.setTextSize(
                a.getDimension(R.styleable.ErrorbarLayout_android_textSize, 0f));
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextAppearance)) {
            TextViewCompat.setTextAppearance(actionView,
                a.getResourceId(R.styleable.ErrorbarLayout_actionTextAppearance, 0));
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextColor)) {
            actionView.setTextColor(
                a.getColorStateList(R.styleable.ErrorbarLayout_actionTextColor));
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextSize)) {
            actionView.setTextSize(
                a.getDimension(R.styleable.ErrorbarLayout_actionTextSize, 0f));
        }
        a.recycle();
    }

    @NonNull
    public ImageView getBackgroundView() {
        return backgroundView;
    }

    @NonNull
    public ViewGroup getContainerView() {
        return containerView;
    }

    @NonNull
    public ImageView getImageView() {
        return imageView;
    }

    @NonNull
    public TextView getMessageView() {
        return messageView;
    }

    @NonNull
    public Button getActionView() {
        return actionView;
    }

    @Override
    public void animateContentIn(int delay, int duration) {
        animateView(backgroundView, delay, duration, true);
        animateView(imageView, delay, duration, true);
        animateView(messageView, delay, duration, true);
        if (actionView.getVisibility() == VISIBLE) {
            animateView(messageView, delay, duration, true);
        }
    }

    @Override
    public void animateContentOut(int delay, int duration) {
        animateView(backgroundView, delay, duration, false);
        animateView(imageView, delay, duration, false);
        animateView(messageView, delay, duration, false);
        if (actionView.getVisibility() == VISIBLE) {
            animateView(messageView, delay, duration, false);
        }
    }

    private static void animateView(
        @NonNull View view,
        int delay,
        int duration,
        boolean animateIn
    ) {
        view.setAlpha(animateIn ? 0f : 1f);
        view.animate()
            .alpha(animateIn ? 1f : 0f)
            .setDuration(duration)
            .setStartDelay(delay)
            .start();
    }
}