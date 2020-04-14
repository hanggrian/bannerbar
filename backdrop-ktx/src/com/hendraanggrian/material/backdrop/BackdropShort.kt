@file:JvmMultifileClass
@file:JvmName("BackdropKt")
@file:Suppress("NOTHING_TO_INLINE", "SpellCheckingInspection")

package com.hendraanggrian.material.backdrop

import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.StringRes

/**
 * Display [Backdrop] with [Backdrop.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 */
inline fun View.backdrop(@NonNull text: CharSequence): Backdrop =
    Backdrop.make(this, text, Backdrop.LENGTH_SHORT)
        .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 */
inline fun View.backdrop(@StringRes text: Int): Backdrop =
    Backdrop.make(this, text, Backdrop.LENGTH_SHORT)
        .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.backdrop(
    @NonNull text: CharSequence,
    @NonNull actionText: CharSequence,
    noinline action: (View) -> Unit
): Backdrop = Backdrop.make(this, text, Backdrop.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.backdrop(
    @StringRes text: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Backdrop = Backdrop.make(this, text, Backdrop.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.backdrop(
    @NonNull text: CharSequence,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Backdrop = Backdrop.make(this, text, Backdrop.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.backdrop(
    @StringRes text: Int,
    @NonNull actionText: CharSequence,
    noinline action: (View) -> Unit
): Backdrop = Backdrop.make(this, text, Backdrop.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }
