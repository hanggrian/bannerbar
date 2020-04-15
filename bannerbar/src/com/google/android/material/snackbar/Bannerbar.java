package com.google.android.material.snackbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import com.hendraanggrian.material.bannerbar.R;

import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_CONTROLS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_ICONS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_TEXT;

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

    /**
     * Callback class for {@link Bannerbar} instances.
     * <p>
     * <p>Note: this class is here to provide backwards-compatible way for apps written before the
     * existence of the base {@link BaseTransientBottomBar} class.
     *
     * @see BaseTransientBottomBar#addCallback(BaseCallback)
     */
    public static class Callback extends BaseCallback<Bannerbar> {
        /**
         * Indicates that the Bannerbar was dismissed via a swipe.
         */
        public static final int DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE;
        /**
         * Indicates that the Bannerbar was dismissed via an action click.
         */
        public static final int DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION;
        /**
         * Indicates that the Bannerbar was dismissed via a timeout.
         */
        public static final int DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT;
        /**
         * Indicates that the Bannerbar was dismissed via a call to {@link #dismiss()}.
         */
        public static final int DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL;
        /**
         * Indicates that the Bannerbar was dismissed from a new Bannerbar being shown.
         */
        public static final int DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE;

        @Override
        public void onShown(Bannerbar sb) {
            // Stub implementation to make API check happy.
        }

        @Override
        public void onDismissed(Bannerbar transientBottomBar, @DismissEvent int event) {
            // Stub implementation to make API check happy.
        }
    }

    @Nullable private BaseCallback<Bannerbar> callback;

    private Bannerbar(
        @NonNull ViewGroup parent,
        @NonNull View content,
        @NonNull com.google.android.material.snackbar.ContentViewCallback contentViewCallback
    ) {
        super(parent, content, contentViewCallback);
        accessibilityManager =
            (AccessibilityManager) parent.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize BaseTransientBottomBar hierarchy).
    @Override
    public void show() {
        super.show();
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize BaseTransientBottomBar hierarchy).
    @Override
    public void dismiss() {
        super.dismiss();
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize BaseTransientBottomBar hierarchy).
    @Override
    public boolean isShown() {
        return super.isShown();
    }

    /**
     * Make an Bannerbar to display a message
     * <p>
     * <p>Bannerbar will try and find a parent view to hold Bannerbar's view from the value given to
     * {@code view}. Bannerbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     * <p>
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Bannerbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     *
     * @param view     The view to find a parent from.
     * @param text     The text to show. Can be formatted text.
     * @param duration How long to display the message. Either {@link #LENGTH_SHORT} or {@link
     *                 #LENGTH_LONG}
     */
    @NonNull
    public static Bannerbar make(@NonNull View view, @NonNull CharSequence text, @Duration int duration) {
        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view.");
        }

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final BannerbarContentLayout content =
            (BannerbarContentLayout)
                inflater.inflate(
                    hasSnackbarButtonStyleAttr(parent.getContext())
                        ? R.layout.mtrl_layout_bannerbar_include
                        : R.layout.design_layout_bannerbar_include,
                    parent,
                    false);
        final Bannerbar bannerbar = new Bannerbar(parent, content, content);
        bannerbar.setText(text);
        bannerbar.setDuration(duration);
        return bannerbar;
    }

    /**
     * {@link Bannerbar}s should still work with AppCompat themes, which don't specify a {@code
     * bannerbarButtonStyle}. This method helps to check if a valid {@code bannerbarButtonStyle} is set
     * within the current context, so that we know whether we can use the attribute.
     */
    protected static boolean hasSnackbarButtonStyleAttr(@NonNull Context context) {
        TypedArray a = context.obtainStyledAttributes(BANNERBAR_BUTTON_STYLE_ATTR);
        int snackbarButtonStyleResId = a.getResourceId(0, -1);
        a.recycle();
        return snackbarButtonStyleResId != -1;
    }

    /**
     * Make an Bannerbar to display a message.
     *
     * <p>Bannerbar will try and find a parent view to hold Bannerbar's view from the value given to
     * {@code view}. Bannerbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     *
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Bannerbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     *
     * @param view     The view to find a parent from.
     * @param textId   The resource id of the string resource to use. Can be formatted text.
     * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
     *                 #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
     */
    @NonNull
    public static Bannerbar make(@NonNull View view, @StringRes int textId, @Duration int duration) {
        return make(view, view.getResources().getText(textId), duration);
    }

    @Nullable
    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof CoordinatorLayout) {
                // We've found a CoordinatorLayout, use it
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
    }

    /**
     * Update the icon in this {@link Bannerbar}.
     *
     * @param icon The new icon for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setIcon(@Nullable Drawable icon) {
        final BannerbarContentLayout layout = (BannerbarContentLayout) view.getChildAt(0);
        final TextView view = layout.getMessageView();
        view.setCompoundDrawables(icon, null, null, null);
        view.setCompoundDrawablePadding(icon != null
            ? getContext().getResources().getDimensionPixelSize(R.dimen.design_bannerbar_padding_horizontal)
            : 0);
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
     * Update the text in this {@link Bannerbar}.
     *
     * @param message The new text for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setText(@NonNull CharSequence message) {
        final BannerbarContentLayout layout = (BannerbarContentLayout) view.getChildAt(0);
        final TextView view = layout.getMessageView();
        view.setText(message);
        return this;
    }

    /**
     * Update the text in this {@link Bannerbar}.
     *
     * @param textId The new text for this {@link BaseTransientBottomBar}.
     */
    @NonNull
    public Bannerbar setText(@StringRes int textId) {
        return setText(getContext().getText(textId));
    }

    /**
     * Add the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param text     Text to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public Bannerbar addAction(@Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
        if (actionCount >= 2) {
            throw new UnsupportedOperationException("As explained in https://material.io/components/banners/#anatomy," +
                "Banners can contain up to two text buttons.");
        }
        final BannerbarContentLayout layout = (BannerbarContentLayout) view.getChildAt(0);
        final TextView view = actionCount == 0 ? layout.getActionView1() : layout.getActionView2();
        if (TextUtils.isEmpty(text) || listener == null) {
            view.setVisibility(View.GONE);
            view.setOnClickListener(null);
            actionCount++;
        } else {
            actionCount--;
            view.setVisibility(View.VISIBLE);
            view.setText(text);
            view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(view);
                        // Now dismiss the Snackbar
                        dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION);
                    }
                });
        }
        return this;
    }

    /**
     * Set the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param textId   String resource to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public Bannerbar addAction(@StringRes int textId, View.OnClickListener listener) {
        return addAction(getContext().getText(textId), listener);
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

        // If touch exploration is enabled override duration to give people chance to interact.
        return hasAction() && accessibilityManager.isTouchExplorationEnabled()
            ? LENGTH_INDEFINITE
            : userSetDuration;
    }

    /**
     * Sets the text color of the message specified in {@link #setText(CharSequence)} and {@link
     * #setText(int)}.
     */
    @NonNull
    public Bannerbar setTextColor(ColorStateList colors) {
        final BannerbarContentLayout layout = (BannerbarContentLayout) view.getChildAt(0);
        final TextView view = layout.getMessageView();
        view.setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the message specified in {@link #setText(CharSequence)} and {@link
     * #setText(int)}.
     */
    @NonNull
    public Bannerbar setTextColor(@ColorInt int color) {
        final BannerbarContentLayout layout = (BannerbarContentLayout) view.getChildAt(0);
        final TextView view = layout.getMessageView();
        view.setTextColor(color);
        return this;
    }

    /**
     * Sets the text color of the action specified in {@link #addAction(CharSequence,
     * View.OnClickListener)}.
     */
    @NonNull
    public Bannerbar setActionTextColors(ColorStateList colors) {
        final BannerbarContentLayout layout = (BannerbarContentLayout) view.getChildAt(0);
        layout.getActionView1().setTextColor(colors);
        layout.getActionView2().setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the action specified in {@link #addAction(CharSequence,
     * View.OnClickListener)}.
     */
    @NonNull
    public Bannerbar setActionTextColors(@ColorInt int color) {
        final BannerbarContentLayout layout = (BannerbarContentLayout) view.getChildAt(0);
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
    public Bannerbar setBackgroundTintList(ColorStateList colorStateList) {
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
    public Bannerbar setCallback(Callback callback) {
        // The logic in this method emulates what we had before support for multiple
        // registered callbacks.
        if (this.callback != null) {
            removeCallback(this.callback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        // Update the deprecated field so that we can remove the passed callback the next
        // time we're called
        this.callback = callback;
        return this;
    }

    /**
     * @see com.google.android.material.snackbar.Snackbar.SnackbarLayout
     */
    @SuppressLint("RestrictedApi")
    public static final class BannerbarLayout extends SnackbarBaseLayout {
        public BannerbarLayout(Context context) {
            super(context);
        }

        public BannerbarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);

            // Recalibrate to R.styleable.BannerbarLayout
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerbarLayout);
            if (a.hasValue(R.styleable.BannerbarLayout_elevation)) {
                ViewCompat.setElevation(this, a.getDimensionPixelSize(R.styleable.BannerbarLayout_elevation, 0));
            }
            setAnimationMode(a.getInt(R.styleable.BannerbarLayout_animationMode, ANIMATION_MODE_SLIDE));
            a.recycle();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            // Work around our backwards-compatible refactoring of Snackbar and inner content
            // being inflated against snackbar's parent (instead of against the snackbar itself).
            // Every child that is width=MATCH_PARENT is remeasured again and given the full width
            // minus the paddings.
            int childCount = getChildCount();
            int availableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    child.measure(
                        MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY));
                }
            }
        }
    }
}
