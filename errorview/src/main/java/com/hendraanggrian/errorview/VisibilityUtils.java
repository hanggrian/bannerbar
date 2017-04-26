package com.hendraanggrian.errorview;

import android.support.annotation.NonNull;
import android.view.View;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class VisibilityUtils {

    public static boolean setVisible(@NonNull View view, boolean visible) {
        if (visible && view.getVisibility() != VISIBLE)
            view.setVisibility(VISIBLE);
        else if (view.getVisibility() != GONE)
            view.setVisibility(GONE);
        return visible;
    }
}