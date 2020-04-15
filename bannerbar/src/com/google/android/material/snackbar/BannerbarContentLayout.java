package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hendraanggrian.material.bannerbar.R;

/**
 * @see com.google.android.material.snackbar.SnackbarContentLayout
 */
public class BannerbarContentLayout extends LinearLayout implements ContentViewCallback {
    private TextView messageView;
    private Button actionView1;
    private Button actionView2;

    public BannerbarContentLayout(@NonNull Context context) {
        this(context, null);
    }

    public BannerbarContentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerbarLayout);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        messageView = findViewById(R.id.bannerbar_text);
        actionView1 = findViewById(R.id.bannerbar_action1);
        actionView2 = findViewById(R.id.bannerbar_action2);
    }

    public TextView getMessageView() {
        return messageView;
    }

    public Button getActionView1() {
        return actionView1;
    }

    public Button getActionView2() {
        return actionView2;
    }

    @Override
    public void animateContentIn(int delay, int duration) {
        animateIn(messageView, delay, duration);
        if (actionView1.getVisibility() == VISIBLE) animateIn(actionView1, delay, duration);
        if (actionView2.getVisibility() == VISIBLE) animateIn(actionView2, delay, duration);
    }

    @Override
    public void animateContentOut(int delay, int duration) {
        animateOut(messageView, delay, duration);
        if (actionView1.getVisibility() == VISIBLE) animateOut(actionView1, delay, duration);
        if (actionView2.getVisibility() == VISIBLE) animateOut(actionView2, delay, duration);
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