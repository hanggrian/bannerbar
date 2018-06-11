@file:Suppress("NOTHING_TO_INLINE")

package android.support.design.widget

import android.support.annotation.StringRes
import android.view.View

/**
 * Display an [Errorbar] with specified duration duration.
 *
 * @param message the message text resource.
 */
inline fun View.errorbar(
    @StringRes message: Int,
    @Errorbar.Duration duration: Int = Errorbar.LENGTH_SHORT
) = Errorbar.make(this, message, duration).apply { show() }

/**
 * Display an [Errorbar] with specified duration duration.
 *
 * @param message the message text.
 */
inline fun View.errorbar(
    message: String,
    @Errorbar.Duration duration: Int = Errorbar.LENGTH_SHORT
) = Errorbar.make(this, message, duration).apply { show() }