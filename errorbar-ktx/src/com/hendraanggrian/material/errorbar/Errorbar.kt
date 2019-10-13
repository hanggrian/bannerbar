@file:JvmMultifileClass
@file:JvmName("ErrorbarKt")
@file:Suppress("NOTHING_TO_INLINE", "SpellCheckingInspection")

package com.hendraanggrian.material.errorbar

import android.view.View
import androidx.annotation.StringRes

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 */
inline fun View.errorbar(text: CharSequence): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 */
inline fun View.errorbar(@StringRes text: Int): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text message.
 */
inline fun View.longErrorbar(text: CharSequence): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text message.
 */
inline fun View.longErrorbar(@StringRes text: Int): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 */
inline fun View.indefiniteErrorbar(text: CharSequence): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 */
inline fun View.indefiniteErrorbar(@StringRes text: Int): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.errorbar(
    text: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.errorbar(
    @StringRes text: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.longErrorbar(
    text: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_LONG)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.longErrorbar(
    @StringRes text: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_LONG)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.indefiniteErrorbar(
    text: CharSequence,
    actionText: CharSequence,
    noinline action: (View) -> Unit
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.indefiniteErrorbar(
    @StringRes text: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }
