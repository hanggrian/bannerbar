package com.hendraanggrian.errorview;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class VisibilityUtils {

    public static void setImage(@NonNull ImageView imageView, @DrawableRes int drawable) {
        if (setVisible(imageView, drawable != -1))
            imageView.setImageResource(drawable);
    }

    public static void setText(@NonNull TextView textView, @Nullable CharSequence text) {
        if (setVisible(textView, !TextUtils.isEmpty(text)))
            textView.setText(text);
    }

    public static boolean setVisible(@NonNull View view, boolean visible) {
        if (visible && view.getVisibility() != VISIBLE)
            view.setVisibility(VISIBLE);
        else if (view.getVisibility() != GONE)
            view.setVisibility(GONE);
        return visible;
    }
}