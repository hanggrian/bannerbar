/*
 * Copyright (C) 2015 The Android Open Source Project
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

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;

import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_CONTROLS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_ICONS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_TEXT;

/**
 * @see com.google.android.material.snackbar.Snackbar
 */
public final class Errorbar extends BaseTransientBottomBar<Errorbar> {

    @Nullable private final AccessibilityManager accessibilityManager;
    private boolean hasAction;

    /**
     * Callback class for {@link Errorbar} instances.
     * <p>
     * <p>Note: this class is here to provide backwards-compatible way for apps written before the
     * existence of the base {@link BaseTransientBottomBar} class.
     *
     * @see BaseTransientBottomBar#addCallback(BaseCallback)
     */
    public static class Callback extends BaseCallback<Errorbar> {
        /**
         * Indicates that the Errorbar was dismissed via a swipe.
         */
        public static final int DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE;
        /**
         * Indicates that the Errorbar was dismissed via an action click.
         */
        public static final int DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION;
        /**
         * Indicates that the Errorbar was dismissed via a timeout.
         */
        public static final int DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT;
        /**
         * Indicates that the Errorbar was dismissed via a call to {@link #dismiss()}.
         */
        public static final int DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL;
        /**
         * Indicates that the Errorbar was dismissed from a new Errorbar being shown.
         */
        public static final int DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE;

        @Override
        public void onShown(Errorbar sb) {
            // Stub implementation to make API check happy.
        }

        @Override
        public void onDismissed(Errorbar transientBottomBar, @DismissEvent int event) {
            // Stub implementation to make API check happy.
        }
    }

    @Nullable private BaseCallback<Errorbar> callback;

    private Errorbar(
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
     * Make an Errorbar to display a message
     * <p>
     * <p>Errorbar will try and find a parent view to hold Errorbar's view from the value given to
     * {@code view}. Errorbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     * <p>
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Errorbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     *
     * @param view     The view to find a parent from.
     * @param text     The text to show. Can be formatted text.
     * @param duration How long to display the message. Either {@link #LENGTH_SHORT} or {@link
     *                 #LENGTH_LONG}
     */
    @NonNull
    public static Errorbar make(
        @NonNull View view, @NonNull CharSequence text, @Duration int duration
    ) {
        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view.");
        }

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ErrorbarContentLayout content =
            (ErrorbarContentLayout)
                inflater.inflate(R.layout.design_layout_errorbar_include, parent, false);
        final Errorbar errorbar = new Errorbar(parent, content, content);
        errorbar.setText(text);
        errorbar.setDuration(duration);
        // hack Snackbar's view container
        errorbar.view.setPadding(0, 0, 0, 0);
        errorbar.view.setBackgroundColor(
            getColorAttr(parent.getContext(), android.R.attr.windowBackground));
        errorbar.view.setLayoutParams(new ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return errorbar;
    }

    @ColorInt
    @SuppressWarnings("SameParameterValue")
    private static int getColorAttr(@NonNull Context context, @AttrRes int resId) {
        final TypedArray a = context.getTheme().obtainStyledAttributes(
            null, new int[]{resId}, 0, 0);
        if (!a.hasValue(0)) {
            throw new Resources.NotFoundException();
        }
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    /**
     * Make an Errorbar to display a message.
     *
     * <p>Errorbar will try and find a parent view to hold Errorbar's view from the value given to
     * {@code view}. Errorbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     *
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Errorbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     *
     * @param view     The view to find a parent from.
     * @param resId    The resource id of the string resource to use. Can be formatted text.
     * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
     *                 #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
     */
    @NonNull
    public static Errorbar make(@NonNull View view, @StringRes int resId, @Duration int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    /**
     * While regular snackbar will try to find {@link com.google.android.material.appbar.CollapsingToolbarLayout}
     * as parent, errorbar accepts any {@link ViewGroup}.
     */
    @Nullable
    private static ViewGroup findSuitableParent(View view) {
        do {
            if (view instanceof ViewGroup) {
                return (ViewGroup) view;
            }
            if (view != null) {
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        throw new IllegalStateException("Can't find suitable parent");
    }

    private ErrorbarContentLayout getContentLayout() {
        return (ErrorbarContentLayout) view.getChildAt(0);
    }

    /**
     * Clear background.
     */
    @NonNull
    public Errorbar noBackground() {
        final ImageView background = getContentLayout().getBackgroundView();
        background.setVisibility(View.GONE);
        return this;
    }

    /**
     * Set background from drawable resource.
     */
    @NonNull
    public Errorbar setBackground(@DrawableRes int resId, @Nullable ImageView.ScaleType scale) {
        final ImageView background = getContentLayout().getBackgroundView();
        background.setVisibility(View.VISIBLE);
        background.setImageResource(resId);
        background.setScaleType(scale != null ? scale : ImageView.ScaleType.CENTER_CROP);
        return this;
    }

    /**
     * Set background from drawable resource.
     */
    @NonNull
    public Errorbar setBackground(@DrawableRes int resId) {
        return setBackground(resId, null);
    }

    /**
     * Set background from uri.
     */
    @NonNull
    public Errorbar setBackground(@NonNull Uri uri, @Nullable ImageView.ScaleType scale) {
        final ImageView background = getContentLayout().getBackgroundView();
        background.setVisibility(View.VISIBLE);
        background.setImageURI(uri);
        background.setScaleType(scale != null ? scale : ImageView.ScaleType.CENTER_CROP);
        return this;
    }

    /**
     * Set background from uri.
     */
    @NonNull
    public Errorbar setBackground(@NonNull Uri uri) {
        return setBackground(uri, null);
    }

    /**
     * Set background from drawable.
     */
    @NonNull
    public Errorbar setBackground(@NonNull Drawable drawable, @Nullable ImageView.ScaleType scale) {
        final ImageView background = getContentLayout().getBackgroundView();
        background.setVisibility(View.VISIBLE);
        background.setImageDrawable(drawable);
        background.setScaleType(scale != null ? scale : ImageView.ScaleType.CENTER_CROP);
        return this;
    }

    /**
     * Set background from drawable.
     */
    @NonNull
    public Errorbar setBackground(@NonNull Drawable drawable) {
        return setBackground(drawable, null);
    }

    /**
     * Set background from icon.
     */
    @NonNull
    @RequiresApi(23)
    public Errorbar setBackground(@NonNull Icon icon, @Nullable ImageView.ScaleType scale) {
        final ImageView background = getContentLayout().getBackgroundView();
        background.setVisibility(View.VISIBLE);
        background.setImageIcon(icon);
        background.setScaleType(scale != null ? scale : ImageView.ScaleType.CENTER_CROP);
        return this;
    }

    /**
     * Set background from icon.
     */
    @NonNull
    @RequiresApi(23)
    public Errorbar setBackground(@NonNull Icon icon) {
        return setBackground(icon, null);
    }

    /**
     * Set a background from bitmap.
     */
    @NonNull
    public Errorbar setBackground(@NonNull Bitmap bitmap, @Nullable ImageView.ScaleType scale) {
        final ImageView background = getContentLayout().getBackgroundView();
        background.setVisibility(View.VISIBLE);
        background.setImageBitmap(bitmap);
        background.setScaleType(scale != null ? scale : ImageView.ScaleType.CENTER_CROP);
        return this;
    }

    /**
     * Set a background from bitmap.
     */
    @NonNull
    public Errorbar setBackground(@NonNull Bitmap bitmap) {
        return setBackground(bitmap, null);
    }

    /**
     * Set background from tint.
     */
    @NonNull
    @RequiresApi(21)
    public Errorbar setBackgroundColor(@NonNull ColorStateList tint) {
        final ImageView background = getContentLayout().getBackgroundView();
        background.setVisibility(View.VISIBLE);
        background.setImageTintList(tint);
        return this;
    }

    /**
     * Set a background from color.
     */
    @NonNull
    public Errorbar setBackgroundColor(@ColorInt int color) {
        final ImageView background = getContentLayout().getBackgroundView();
        background.setVisibility(View.VISIBLE);
        background.setBackgroundColor(color);
        return this;
    }

    /**
     * Set content margin each side.
     */
    @NonNull
    public Errorbar setContentMargin(@Px int left, @Px int top, @Px int right, @Px int bottom) {
        final ViewGroup container = getContentLayout().getContainerView();
        ((ViewGroup.MarginLayoutParams) container.getLayoutParams()).setMargins(
            left, top, right, bottom);
        return this;
    }

    /**
     * Set content left margin.
     */
    @NonNull
    public Errorbar setContentMarginLeft(@Px int left) {
        final ViewGroup container = getContentLayout().getContainerView();
        ((ViewGroup.MarginLayoutParams) container.getLayoutParams()).leftMargin = left;
        return this;
    }

    /**
     * Set content top margin.
     */
    @NonNull
    public Errorbar setContentMarginTop(@Px int top) {
        final ViewGroup container = getContentLayout().getContainerView();
        ((ViewGroup.MarginLayoutParams) container.getLayoutParams()).topMargin = top;
        return this;
    }

    /**
     * Set content right margin.
     */
    @NonNull
    public Errorbar setContentMarginRight(@Px int right) {
        final ViewGroup container = getContentLayout().getContainerView();
        ((ViewGroup.MarginLayoutParams) container.getLayoutParams()).rightMargin = right;
        return this;
    }

    /**
     * Set content bottom margin.
     */
    @NonNull
    public Errorbar setContentMarginBottom(@Px int bottom) {
        final ViewGroup container = getContentLayout().getContainerView();
        ((ViewGroup.MarginLayoutParams) container.getLayoutParams()).bottomMargin = bottom;
        return this;
    }

    /**
     * Clear image.
     */
    @NonNull
    public Errorbar noImage() {
        final ImageView image = getContentLayout().getImageView();
        image.setImageDrawable(null);
        image.setVisibility(View.GONE);
        return this;
    }

    /**
     * Set image from drawable resource.
     */
    @NonNull
    public Errorbar setImage(@DrawableRes int resId) {
        final ImageView image = getContentLayout().getImageView();
        image.setVisibility(View.VISIBLE);
        image.setImageResource(resId);
        return this;
    }

    /**
     * Set image from uri.
     */
    @NonNull
    public Errorbar setImage(@NonNull Uri uri) {
        final ImageView image = getContentLayout().getImageView();
        image.setVisibility(View.VISIBLE);
        image.setImageURI(uri);
        return this;
    }

    /**
     * Set image from drawable.
     */
    @NonNull
    public Errorbar setImage(@NonNull Drawable drawable) {
        final ImageView image = getContentLayout().getImageView();
        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(drawable);
        return this;
    }

    /**
     * Set image from icon.
     */
    @NonNull
    @RequiresApi(23)
    public Errorbar setImage(@NonNull Icon icon) {
        final ImageView image = getContentLayout().getImageView();
        image.setVisibility(View.VISIBLE);
        image.setImageIcon(icon);
        return this;
    }

    /**
     * Set image from bitmap.
     */
    @NonNull
    public Errorbar setImage(@NonNull Bitmap bitmap) {
        final ImageView image = getContentLayout().getImageView();
        image.setVisibility(View.VISIBLE);
        image.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Set text to this Errorbar.
     */
    @NonNull
    public Errorbar setText(@NonNull CharSequence text) {
        final TextView message = getContentLayout().getMessageView();
        message.setText(text);
        return this;
    }

    /**
     * Set text from string resource.
     */
    @NonNull
    public Errorbar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    /**
     * Sets the text color.
     */
    @NonNull
    public Errorbar setTextColor(@ColorInt int color) {
        final TextView message = getContentLayout().getMessageView();
        message.setTextColor(color);
        return this;
    }

    /**
     * Sets the text colors.
     */
    @NonNull
    public Errorbar setTextColor(@NonNull ColorStateList tint) {
        final TextView message = getContentLayout().getMessageView();
        message.setTextColor(tint);
        return this;
    }

    /**
     * Set button text and its click listener.
     */
    @NonNull
    public Errorbar setAction(CharSequence text, final View.OnClickListener listener) {
        final TextView action = getContentLayout().getActionView();
        if (TextUtils.isEmpty(text) || listener == null) {
            action.setVisibility(View.GONE);
            action.setOnClickListener(null);
            hasAction = false;
        } else {
            hasAction = true;
            action.setVisibility(View.VISIBLE);
            action.setText(text);
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                    // Now dismiss the Errorbar
                    dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION);
                }
            });
        }
        return this;
    }

    /**
     * Set button text from string resource and its click listener.
     */
    @NonNull
    public Errorbar setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    @Override
    public int getDuration() {
        int userSetDuration = super.getDuration();
        if (userSetDuration == LENGTH_INDEFINITE) {
            return LENGTH_INDEFINITE;
        }

        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            int controlsFlag = hasAction ? FLAG_CONTENT_CONTROLS : 0;
            return accessibilityManager.getRecommendedTimeoutMillis(
                userSetDuration, controlsFlag | FLAG_CONTENT_ICONS | FLAG_CONTENT_TEXT);
        }

        // If touch exploration is enabled override duration to give people chance to interact.
        return hasAction && accessibilityManager.isTouchExplorationEnabled()
            ? LENGTH_INDEFINITE
            : userSetDuration;
    }

    /**
     * Sets the text color of the action specified in {@link Errorbar#setAction(CharSequence, View.OnClickListener)}.
     */
    @NonNull
    public Errorbar setActionTextColor(@ColorInt int color) {
        final TextView action = getContentLayout().getActionView();
        action.setTextColor(color);
        return this;
    }

    /**
     * Sets the text color of the action specified in {@link Errorbar#setAction(CharSequence, View.OnClickListener)}.
     */
    @NonNull
    public Errorbar setActionTextColor(@NonNull ColorStateList tint) {
        final TextView action = getContentLayout().getActionView();
        action.setTextColor(tint);
        return this;
    }

    /**
     * Set a callback to be called when this the visibility of this {@link Errorbar} changes. Note
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
    public Errorbar setCallback(Callback callback) {
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
}
