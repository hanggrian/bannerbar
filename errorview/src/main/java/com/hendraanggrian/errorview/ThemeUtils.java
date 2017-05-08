package com.hendraanggrian.errorview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ThemeUtils {

    private ThemeUtils() {
    }

    public static int getColorFromAttrRes(@NonNull Context context, @AttrRes int attrRes, @ColorInt int defaultValue) {
        TypedArray a = context.obtainStyledAttributes(new int[]{attrRes});
        try {
            return a.getColor(0, defaultValue);
        } finally {
            a.recycle();
        }
    }
}