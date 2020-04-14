@file:JvmMultifileClass
@file:JvmName("BackdropKt")
@file:Suppress("NOTHING_TO_INLINE", "SpellCheckingInspection")

package com.hendraanggrian.material.backdrop

import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.StringRes

/**
 * Display [Backdrop] with [Backdrop.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 */
inline fun View.indefiniteBackdrop(@NonNull text: CharSequence): Backdrop =
    Backdrop.make(this, text, Backdrop.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 */
inline fun View.indefiniteBackdrop(@StringRes text: Int): Backdrop =
    Backdrop.make(this, text, Backdrop.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.indefiniteBackdrop(
    @NonNull text: CharSequence,
    @NonNull actionText: CharSequence,
    noinline action: (View) -> Unit
): Backdrop = Backdrop.make(this, text, Backdrop.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.indefiniteBackdrop(
    @StringRes text: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Backdrop = Backdrop.make(this, text, Backdrop.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.indefiniteBackdrop(
    @NonNull text: CharSequence,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Backdrop = Backdrop.make(this, text, Backdrop.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Backdrop] with [Backdrop.LENGTH_INDEFINITE] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.indefiniteBackdrop(
    @StringRes text: Int,
    @NonNull actionText: CharSequence,
    noinline action: (View) -> Unit
): Backdrop = Backdrop.make(this, text, Backdrop.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }
