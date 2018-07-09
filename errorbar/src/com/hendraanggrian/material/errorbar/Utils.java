package com.hendraanggrian.material.errorbar;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import static android.os.Build.VERSION.SDK_INT;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

final class Utils {

    /**
     * No instance.
     */
    private Utils() {
    }

    static void setTextAppearance(@NonNull TextView view, int resId) {
        if (SDK_INT >= 23) {
            view.setTextAppearance(resId);
        } else {
            view.setTextAppearance(view.getContext(), resId);
        }
    }

    static void clearImage(@NonNull ImageView view) {
        view.setImageDrawable(null);
        view.setVisibility(GONE);
    }

    static void setImageDrawable(@NonNull ImageView view, @NonNull Drawable drawable) {
        view.setVisibility(VISIBLE);
        view.setImageDrawable(drawable);
    }

    static void setImageResource(@NonNull ImageView view, @DrawableRes int resId) {
        view.setVisibility(VISIBLE);
        view.setImageResource(resId);
    }

    static void setImageURI(@NonNull ImageView view, @NonNull Uri uri) {
        view.setVisibility(VISIBLE);
        view.setImageURI(uri);
    }

    @TargetApi(23)
    static void setImageIcon(@NonNull ImageView view, @NonNull Icon icon) {
        view.setVisibility(VISIBLE);
        view.setImageIcon(icon);
    }

    @TargetApi(21)
    static void setImageTintList(@NonNull ImageView view, @NonNull ColorStateList tint) {
        view.setVisibility(VISIBLE);
        view.setImageTintList(tint);
    }

    static void setImageBitmap(@NonNull ImageView view, @NonNull Bitmap bitmap) {
        view.setVisibility(VISIBLE);
        view.setImageBitmap(bitmap);
    }

    static void setBackgroundColor(@NonNull ImageView view, @ColorInt int color) {
        view.setVisibility(VISIBLE);
        view.setBackgroundColor(color);
    }
}