@file:JvmMultifileClass
@file:JvmName("BannerbarKt")

package com.google.android.material.snackbar

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.StringRes

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_LONG] duration.
 *
 * @param text the text message.
 */
inline fun View.longBannerbar(@NonNull text: CharSequence, configuration: Bannerbar.() -> Unit): Bannerbar =
    Bannerbar.make(this, text, Bannerbar.LENGTH_LONG)
        .apply {
            configuration()
            show()
        }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_LONG] duration.
 *
 * @param textId the text message.
 */
inline fun View.longBannerbar(@StringRes textId: Int, configuration: Bannerbar.() -> Unit): Bannerbar =
    Bannerbar.make(this, textId, Bannerbar.LENGTH_LONG)
        .apply {
            configuration()
            show()
        }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_LONG] duration.
 *
 * @param icon option on the left side of message.
 * @param text the text message.
 */
inline fun View.longBannerbar(
    @NonNull icon: Drawable,
    @NonNull text: CharSequence,
    configuration: Bannerbar.() -> Unit
): Bannerbar = Bannerbar.make(this, text, Bannerbar.LENGTH_LONG)
    .setIcon(icon)
    .apply {
        configuration()
        show()
    }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_LONG] duration.
 *
 * @param icon option on the left side of message.
 * @param textId the text message.
 */
inline fun View.longBannerbar(
    @NonNull icon: Drawable,
    @StringRes textId: Int,
    configuration: Bannerbar.() -> Unit
): Bannerbar = Bannerbar.make(this, textId, Bannerbar.LENGTH_LONG)
    .setIcon(icon)
    .apply {
        configuration()
        show()
    }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_LONG] duration.
 *
 * @param iconId option on the left side of message.
 * @param text the text message.
 */
inline fun View.longBannerbar(
    @DrawableRes iconId: Int,
    @NonNull text: CharSequence,
    configuration: Bannerbar.() -> Unit
): Bannerbar = Bannerbar.make(this, text, Bannerbar.LENGTH_LONG)
    .setIcon(iconId)
    .apply {
        configuration()
        show()
    }

/**
 * Display [Bannerbar] with [Bannerbar.LENGTH_LONG] duration.
 *
 * @param iconId option on the left side of message.
 * @param textId the text message.
 */
inline fun View.longBannerbar(
    @DrawableRes iconId: Int,
    @StringRes textId: Int,
    configuration: Bannerbar.() -> Unit
): Bannerbar = Bannerbar.make(this, textId, Bannerbar.LENGTH_LONG)
    .setIcon(iconId)
    .apply {
        configuration()
        show()
    }
