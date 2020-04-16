package com.google.android.material.snackbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.google.android.material.color.MaterialColors;
import com.hendraanggrian.material.bannerbar.R;

/**
 * @see com.google.android.material.snackbar.SnackbarContentLayout
 */
public class BannerbarContentLayout extends RelativeLayout implements ContentViewCallback {
    private ImageView iconView;
    private TextView titleView;
    private TextView subtitleView;
    private Button actionView1;
    private Button actionView2;

    public BannerbarContentLayout(@NonNull Context context) {
        this(context, null);
    }

    public BannerbarContentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iconView = findViewById(R.id.bannerbar_icon);
        titleView = findViewById(R.id.bannerbar_title);
        subtitleView = findViewById(R.id.bannerbar_subtitle);
        actionView1 = findViewById(R.id.bannerbar_action1);
        actionView2 = findViewById(R.id.bannerbar_action2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int extraHorizontal = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_horizontal);
        final int defaultVertical =
            getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
        final int shortVertical = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);

        int horizontal = 0;
        int bottom = defaultVertical;

        // when an icon is not shown, apply horizontal padding to make it look more like a snackbar
        if (iconView.getDrawable() == null) {
            horizontal = extraHorizontal;
        }

        // when there are buttons, reduce bottom padding
        if (actionView1.getVisibility() == VISIBLE || actionView2.getVisibility() == VISIBLE) {
            bottom = shortVertical;
        }

        if (ViewCompat.isPaddingRelative(this)) {
            ViewCompat.setPaddingRelative(this, horizontal, defaultVertical, horizontal, bottom);
        } else {
            setPadding(horizontal, defaultVertical, horizontal, bottom);
        }
    }

    public ImageView getIconView() {
        return iconView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getSubtitleView() {
        return subtitleView;
    }

    public Button getActionView1() {
        return actionView1;
    }

    public Button getActionView2() {
        return actionView2;
    }

    void updateActionTextColorAlphaIfNeeded(float actionTextColorAlpha) {
        if (actionTextColorAlpha != 1) {
            updateActionTextColorAlpha(actionView1, actionTextColorAlpha);
            updateActionTextColorAlpha(actionView2, actionTextColorAlpha);
        }
    }

    private void updateActionTextColorAlpha(TextView actionView, float actionTextColorAlpha) {
        int originalActionTextColor = actionView.getCurrentTextColor();
        int colorSurface = MaterialColors.getColor(this, R.attr.colorSurface);
        int actionTextColor = MaterialColors.layer(colorSurface, originalActionTextColor, actionTextColorAlpha);
        actionView.setTextColor(actionTextColor);
    }

    @Override
    public void animateContentIn(int delay, int duration) {
        animateIn(subtitleView, delay, duration);
        if (titleView.getVisibility() == VISIBLE) animateIn(titleView, delay, duration);
        if (actionView1.getVisibility() == VISIBLE) animateIn(actionView1, delay, duration);
        if (actionView2.getVisibility() == VISIBLE) animateIn(actionView2, delay, duration);
    }

    @Override
    public void animateContentOut(int delay, int duration) {
        animateOut(subtitleView, delay, duration);
        if (titleView.getVisibility() == VISIBLE) animateOut(titleView, delay, duration);
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