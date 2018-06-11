@file:Suppress("NOTHING_TO_INLINE")
@file:SuppressLint("Range")

package android.support.design.widget

import android.annotation.SuppressLint
import android.support.annotation.StringRes
import android.view.View

/**
 * Display an [Errorbar] with specified duration duration.
 *
 * @param message the message text resource.
 */
inline fun View.errorbar(
    @StringRes message: Int,
    @BaseTransientBottomBar.Duration duration: Int = Errorbar.LENGTH_SHORT
) = Errorbar.make(this, message, duration).apply { show() }

/**
 * Display an [Errorbar] with specified duration duration.
 *
 * @param message the message text.
 */
inline fun View.errorbar(
    message: String,
    @BaseTransientBottomBar.Duration duration: Int = Errorbar.LENGTH_SHORT
) = Errorbar.make(this, message, duration).apply { show() }