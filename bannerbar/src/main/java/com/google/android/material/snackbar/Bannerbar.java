package com.google.android.material.snackbar;

import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_CONTROLS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_ICONS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_TEXT;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.hendraanggrian.material.bannerbar.R;

/**
 * Expanded {@link com.google.android.material.snackbar.Snackbar}, useful for displaying
 * full-screen message.
 *
 * @see com.google.android.material.snackbar.Snackbar
 */
public final class Bannerbar extends BaseTransientBottomBar<Bannerbar> {

    @Nullable private final AccessibilityManager accessibilityManager;
    private int actionCount = 0;

    private boolean hasAction() {
        return actionCount > 0;
    }

    private static final int[] BANNERBAR_BUTTON_STYLE_ATTR = new int[]{R.attr.bannerbarButtonStyle};
    private static final int[] BANNERBAR_CONTENT_STYLE_ATTRS =
        new int[]{R.attr.bannerbarButtonStyle, R.attr.bannerbarIconStyle,
            R.attr.bannerbarTitleStyle, R.attr.bannerbarSubtitleStyle};

    /**
     * @see Snackbar.Callback
     */
    public static class Callback extends BaseCallback<Bannerbar> {
        public static final int DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE;
        public static final int DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION;
        public static final int DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT;
        public static final int DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL;
        public static final int DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE;

        @Override
        public void onShown(Bannerbar sb) {
        }

        @Override
        public void onDismissed(Bannerbar transientBottomBar, @DismissEvent int event) {
        }
    }

    @Nullable private BaseCallback<Bannerbar> callback;

    private Bannerbar(
        @NonNull Context context,
        @NonNull ViewGroup parent,
        @NonNull View content,
        @NonNull com.google.android.material.snackbar.ContentViewCallback contentViewCallback
    ) {
        super(context, parent, content, contentViewCallback);
        accessibilityManager =
            (AccessibilityManager) parent.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public boolean isShown() {
        return super.isShown();
    }

    /**
     * Make a Bannerbar to display a message
     * <p>
     * <p>Bannerbar will try and find a parent view to hold Bannerbar's view from the value given to
     * {@code view}. Bannerbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     * <p>
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Bannerbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     * @param view The view to find a parent from.
     * @param title The text to show. Can be formatted text.
     * @param duration How long to display the message. Either {@link #LENGTH_SHORT} or {@link
     *                 #LENGTH_LONG}
     */
    @NonNull
    public static Bannerbar make(@NonNull View view, @NonNull CharSequence title, @Duration int duration) {
        return makeInternal(null, view, title, duration);
    }

    /**
     * Make a Bannerbar to display a message
     * <p>
     * <p>Bannerbar will try and find a parent view to hold Bannerbar's view from the value given to
     * {@code view}. Bannerbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     * <p>
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Bannerbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     * @param context The context to use to create the Bannerbar view.
     * @param view The view to find a parent from.
     * @param title The text to show. Can be formatted text.
     * @param duration How long to display the message. Either {@link #LENGTH_SHORT} or {@link
     *                 #LENGTH_LONG}
     */
    @NonNull
    public static Bannerbar make(
        @NonNull Context context,
        @NonNull View view,
        @NonNull CharSequence title,
        @Duration int duration
    ) {
        return makeInternal(context, view, title, duration);
    }

    @NonNull
    private static Bannerbar makeInternal(
        @Nullable Context context,
        @NonNull View view,
        @NonNull CharSequence text,
        @Duration int duration
    ) {
        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view.");
        }
        if (context == null) {
            context = parent.getContext();
        }
        final LayoutInflater inflater = LayoutInflater.from(context);
        final BannerbarContentLayout content =
            (BannerbarContentLayout)
                inflater.inflate(
                    hasBannerbarContentStyleAttrs(context)
                        ? R.layout.mtrl_layout_bannerbar_include
                        : R.layout.design_layout_bannerbar_include,
                    parent,
                    false);
        final Bannerbar bannerbar = new Bannerbar(context, parent, content, content);
        bannerbar.setTitle(text);
        bannerbar.setDuration(duration);
        return bannerbar;
    }

    @Deprecated
    protected static boolean hasBannerbarButtonStyleAttr(@NonNull Context context) {
        TypedArray a = context.obtainStyledAttributes(BANNERBAR_BUTTON_STYLE_ATTR);
        int snackbarButtonStyleResId = a.getResourceId(0, -1);
        a.recycle();
        return snackbarButtonStyleResId != -1;
    }

    private static boolean hasBannerbarContentStyleAttrs(@NonNull Context context) {
        TypedArray a = context.obtainStyledAttributes(BANNERBAR_CONTENT_STYLE_ATTRS);
        int snackbarButtonStyleResId = a.getResourceId(0, -1);
        int snackbarTextViewStyleResId = a.getResourceId(1, -1);
        a.recycle();
        return snackbarButtonStyleResId != -1 && snackbarTextViewStyleResId != -1;
    }

    /**
     * Make a Bannerbar to display a message
     * <p>
     * <p>Bannerbar will try and find a parent view to hold Bannerbar's view from the value given to
     * {@code view}. Bannerbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     * <p>
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Bannerbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     * @param view The view to find a parent from.
     * @param resId The resource id of the string resource to use. Can be formatted text.
     * @param duration How long to display the message. Either {@link #LENGTH_SHORT} or {@link
     *                 #LENGTH_LONG}
     */
    @NonNull
    public static Bannerbar make(@NonNull View view, @StringRes int resId, @Duration int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    @Nullable
    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                } else {
                    fallback = (ViewGroup) view;
                }
            }
            if (view != null) {
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return fallback;
    }

    @NonNull
    public BannerbarContentLayout getContentLayout() {
        return (BannerbarContentLayout) view.getChildAt(0);
    }

    /**
     * Update the icon in this {@link Bannerbar}.
     *
     * @param icon The new icon for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setIcon(@Nullable Drawable icon) {
        final ImageView view = getContentLayout().getIconView();
        if (icon == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setImageDrawable(icon);
        }
        return this;
    }

    /**
     * Update the icon in this {@link Bannerbar}.
     *
     * @param iconId The new icon for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setIcon(@DrawableRes int iconId) {
        return setIcon(ContextCompat.getDrawable(getContext(), iconId));
    }

    /**
     * Update the title in this {@link Bannerbar}.
     *
     * @param text The new title for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setTitle(@Nullable CharSequence text) {
        getContentLayout().getTitleView().setText(text);
        return this;
    }

    /**
     * Update the title in this {@link Bannerbar}.
     *
     * @param textId The new title for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setTitle(@StringRes int textId) {
        return setTitle(getContext().getText(textId));
    }

    /**
     * Update the subtitle in this {@link Bannerbar}.
     *
     * @param text The new subtitle for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setSubtitle(@Nullable CharSequence text) {
        final TextView view = getContentLayout().getSubtitleView();
        if (TextUtils.isEmpty(text)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
        return this;
    }

    /**
     * Update the subtitle in this {@link Bannerbar}.
     *
     * @param textId The new subtitle for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setSubtitle(@StringRes int textId) {
        return setSubtitle(getContext().getText(textId));
    }

    /**
     * Add the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param text     Text to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public Bannerbar addAction(@NonNull CharSequence text, @Nullable final View.OnClickListener listener) {
        if (actionCount >= 2) {
            throw new UnsupportedOperationException("As explained in https://material.io/components/banners/#anatomy," +
                "Banners can contain up to two text buttons.");
        }
        final TextView view = actionCount++ == 0
            ? getContentLayout().getActionView1()
            : getContentLayout().getActionView2();
        view.setVisibility(View.VISIBLE);
        view.setText(text);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view);
                }
                // Now dismiss the Snackbar
                dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION);
            }
        });
        return this;
    }

    /**
     * Add the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param text Text to display for the action
     */
    @NonNull
    public Bannerbar addAction(@NonNull CharSequence text) {
        return addAction(text, null);
    }

    /**
     * Set the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param textId   String resource to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public Bannerbar addAction(@StringRes int textId, @Nullable View.OnClickListener listener) {
        return addAction(getContext().getText(textId), listener);
    }

    /**
     * Set the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param textId String resource to display for the action
     */
    @NonNull
    public Bannerbar addAction(@StringRes int textId) {
        return addAction(getContext().getText(textId), null);
    }

    @Duration
    public int getDuration() {
        int userSetDuration = super.getDuration();
        if (userSetDuration == LENGTH_INDEFINITE) {
            return LENGTH_INDEFINITE;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            int controlsFlag = hasAction() ? FLAG_CONTENT_CONTROLS : 0;
            return accessibilityManager.getRecommendedTimeoutMillis(
                userSetDuration, controlsFlag | FLAG_CONTENT_ICONS | FLAG_CONTENT_TEXT);
        }
        return hasAction() && accessibilityManager.isTouchExplorationEnabled()
            ? LENGTH_INDEFINITE
            : userSetDuration;
    }

    /**
     * Sets the text color of the message specified in {@link #setTitle(CharSequence)} and {@link
     * #setSubtitle(int)}.
     */
    @NonNull
    public Bannerbar setTitleColor(@NonNull ColorStateList colors) {
        getContentLayout().getTitleView().setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the message specified in {@link #setTitle(CharSequence)} and {@link
     * #setSubtitle(int)}.
     */
    @NonNull
    public Bannerbar setTitleColor(@ColorInt int color) {
        getContentLayout().getTitleView().setTextColor(color);
        return this;
    }

    /**
     * Sets the text color of the message specified in {@link #setSubtitle(CharSequence)} and {@link
     * #setSubtitle(int)}.
     */
    @NonNull
    public Bannerbar setSubtitleColor(@NonNull ColorStateList colors) {
        getContentLayout().getSubtitleView().setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the message specified in {@link #setSubtitle(CharSequence)} and {@link
     * #setSubtitle(int)}.
     */
    @NonNull
    public Bannerbar setSubtitleColor(@ColorInt int color) {
        getContentLayout().getSubtitleView().setTextColor(color);
        return this;
    }

    /**
     * Sets the text color of the action specified in {@link #addAction(CharSequence,
     * View.OnClickListener)}.
     */
    @NonNull
    public Bannerbar setActionsTextColor(@NonNull ColorStateList colors) {
        final BannerbarContentLayout layout = getContentLayout();
        layout.getActionView1().setTextColor(colors);
        layout.getActionView2().setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the action specified in {@link #addAction(CharSequence,
     * View.OnClickListener)}.
     */
    @NonNull
    public Bannerbar setActionsTextColor(@ColorInt int color) {
        final BannerbarContentLayout layout = getContentLayout();
        layout.getActionView1().setTextColor(color);
        layout.getActionView2().setTextColor(color);
        return this;
    }

    /**
     * Sets the tint color of the background Drawable.
     */
    @NonNull
    public Bannerbar setBackgroundTint(@ColorInt int color) {
        Drawable background = view.getBackground();
        if (background != null) {
            background = background.mutate();
            // Drawable doesn't implement setTint in API 21 and Snackbar does not yet use
            // MaterialShapeDrawable as its background (i.e. TintAwareDrawable)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                DrawableCompat.setTint(background, color);
            } else {
                background.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        }
        return this;
    }

    /**
     * Sets the tint color state list of the background Drawable.
     */
    @NonNull
    public Bannerbar setBackgroundTintList(@NonNull ColorStateList colorStateList) {
        DrawableCompat.setTintList(view.getBackground().mutate(), colorStateList);
        return this;
    }

    /**
     * Set a callback to be called when this the visibility of this {@link Bannerbar} changes. Note
     * that this method is deprecated and you should use {@link #addCallback(BaseCallback)} to add a
     * callback and {@link #removeCallback(BaseCallback)} to remove a registered callback.
     *
     * @param callback Callback to notify when transient bottom bar events occur.
     * @see Callback
     * @see #addCallback(BaseCallback)
     * @see #removeCallback(BaseCallback)
     * @deprecated Use {@link #addCallback(BaseCallback)}
     */
    @Deprecated
    @NonNull
    public Bannerbar setCallback(@Nullable Callback callback) {
        if (this.callback != null) {
            removeCallback(this.callback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        this.callback = callback;
        return this;
    }
}
