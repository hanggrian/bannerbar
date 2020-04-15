package com.hendraanggrian.material.bannerbar;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

class Utils {

    /**
     * @see com.google.android.material.color.MaterialColors#getColor(View, int)
     */
    @ColorInt
    static int getColor(@NonNull View view, @AttrRes int colorAttributeResId) {
        return resolveOrThrow(view, colorAttributeResId);
    }

    /**
     * @see com.google.android.material.resources.MaterialAttributes#resolveOrThrow(View, int)
     */
    private static int resolveOrThrow(@NonNull View componentView, @AttrRes int attributeResId) {
        return resolveOrThrow(componentView.getContext(), attributeResId, componentView.getClass().getCanonicalName());
    }

    /**
     * @see com.google.android.material.resources.MaterialAttributes#resolveOrThrow(Context, int, String)
     */
    private static int resolveOrThrow(
            @NonNull Context context, @AttrRes int attributeResId, @NonNull String errorMessageComponent) {
        TypedValue typedValue = resolve(context, attributeResId);
        if (typedValue == null) {
            String errorMessage = "%1$s requires a value for the %2$s attribute to be set in your app theme. "
                    + "You can either set the attribute in your theme or "
                    + "update your theme to inherit from Theme.MaterialComponents (or a descendant).";
            throw new IllegalArgumentException(String.format(
                    errorMessage,
                    errorMessageComponent,
                    context.getResources().getResourceName(attributeResId)));
        }
        return typedValue.data;
    }

    /**
     * @see com.google.android.material.resources.MaterialAttributes#resolve(Context, int)
     */
    @Nullable
    private static TypedValue resolve(@NonNull Context context, @AttrRes int attributeResId) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attributeResId, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    /**
     * @see com.google.android.material.color.MaterialColors#layer(int, int, float)
     */
    @ColorInt
    static int layer(
            @ColorInt int backgroundColor,
            @ColorInt int overlayColor,
            @FloatRange(from = 0.0, to = 1.0) float overlayAlpha) {
        int computedAlpha = Math.round(Color.alpha(overlayColor) * overlayAlpha);
        int computedOverlayColor = ColorUtils.setAlphaComponent(overlayColor, computedAlpha);
        return layer(backgroundColor, computedOverlayColor);
    }


    /**
     * @see com.google.android.material.color.MaterialColors#layer(int, int)
     */
    @ColorInt
    private static int layer(@ColorInt int backgroundColor, @ColorInt int overlayColor) {
        return ColorUtils.compositeColors(overlayColor, backgroundColor);
    }
}
