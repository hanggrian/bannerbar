@file:JvmMultifileClass
@file:JvmName("BannerbarKt")
@file:Suppress("NOTHING_TO_INLINE", "SpellCheckingInspection")

package com.hendraanggrian.material.bannerbar

import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.StringRes

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 */
inline fun View.backdrop(@NonNull text: CharSequence): Bannerbar =
    Bannerbar.make(this, text, Bannerbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 */
inline fun View.backdrop(@StringRes text: Int): Bannerbar =
    Bannerbar.make(this, text, Bannerbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.backdrop(
    @NonNull text: CharSequence,
    @NonNull actionText: CharSequence,
    noinline action: (View) -> Unit
): Bannerbar = Bannerbar.make(this, text, Bannerbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.backdrop(
    @StringRes text: Int,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Bannerbar = Bannerbar.make(this, text, Bannerbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.backdrop(
    @NonNull text: CharSequence,
    @StringRes actionText: Int,
    noinline action: (View) -> Unit
): Bannerbar = Bannerbar.make(this, text, Bannerbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_SHORT] duration.
 *
 * @param text the text message.
 * @param actionText the action text message.
 * @param action action click listener.
 */
inline fun View.backdrop(
    @StringRes text: Int,
    @NonNull actionText: CharSequence,
    noinline action: (View) -> Unit
): Bannerbar = Bannerbar.make(this, text, Bannerbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }
