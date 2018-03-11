@file:Suppress("NOTHING_TO_INLINE")

package android.support.design.widget

import android.support.annotation.StringRes
import android.view.View

/**
 * Display an Errorbar with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun errorbar(view: View, @StringRes message: Int) = Errorbar
    .make(view, message, Errorbar.LENGTH_INDEFINITE)
    .apply { show() }

/**
 * Display an Errorbar with [Errorbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun longErrorbar(view: View, @StringRes message: Int) = Errorbar
    .make(view, message, Errorbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display an Errorbar with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun errorbar(view: View, message: String) = Errorbar
    .make(view, message, Errorbar.LENGTH_INDEFINITE)
    .apply { show() }

/**
 * Display an Errorbar with [Errorbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun longErrorbar(view: View, message: String) = Errorbar
    .make(view, message, Errorbar.LENGTH_LONG)
    .apply { show() }