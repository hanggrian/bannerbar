package com.google.android.material.snackbar

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.StringRes

/**
 * Receiver class of [Bannerbar] builders with custom configuration.
 */
class BannerbarScope @PublishedApi internal constructor(private val bannerbar: Bannerbar) {
    private companion object {
        const val NO_GETTER: String = "Property does not have a getter."

        /** Some mutable backing fields are only used to set value. */
        fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)
    }

    /**
     * @see Bannerbar.setDuration
     */
    var duration: Int
        @BaseTransientBottomBar.Duration get() = bannerbar.duration
        set(@BaseTransientBottomBar.Duration value) {
            bannerbar.duration = value
        }

    /**
     * @see Bannerbar.setIcon
     */
    var icon: Drawable
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@NonNull value) {
            bannerbar.setIcon(value)
        }

    /**
     * @see Bannerbar.setIcon
     */
    var iconId: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@DrawableRes value) {
            bannerbar.setIcon(value)
        }

    /**
     * @see Bannerbar.setTitle
     */
    var title: CharSequence
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@NonNull value) {
            bannerbar.setTitle(value)
        }

    /**
     * @see Bannerbar.setTitle
     */
    var titleId: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@StringRes value) {
            bannerbar.setTitle(value)
        }

    /**
     * @see Bannerbar.setSubtitle
     */
    var subtitle: CharSequence
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@NonNull value) {
            bannerbar.setSubtitle(value)
        }

    /**
     * @see Bannerbar.setSubtitle
     */
    var subtitleId: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@StringRes value) {
            bannerbar.setSubtitle(value)
        }

    /**
     * @see Bannerbar.addAction
     */
    fun addAction(@NonNull text: CharSequence, @Nullable action: View.OnClickListener? = null) {
        bannerbar.addAction(text, action)
    }

    /**
     * @see Bannerbar.addAction
     */
    fun addAction(@StringRes textId: Int, @Nullable action: View.OnClickListener? = null) {
        bannerbar.addAction(textId, action)
    }

    /**
     * @see addAction
     */
    inline operator fun CharSequence.invoke(crossinline action: (View) -> Unit) {
        addAction(this, View.OnClickListener { action(it) })
    }

    /**
     * @see addAction
     */
    inline operator fun @receiver:StringRes Int.invoke(crossinline action: (View) -> Unit) {
        addAction(this, View.OnClickListener { action(it) })
    }

    /**
     * @see Bannerbar.setTitleColor
     */
    var titleColors: ColorStateList
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@NonNull value) {
            bannerbar.setTitleColor(value)
        }

    /**
     * @see Bannerbar.setTitleColor
     */
    var titleColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@ColorInt value) {
            bannerbar.setTitleColor(value)
        }

    /**
     * @see Bannerbar.setSubtitleColor
     */
    var subtitleColors: ColorStateList
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@NonNull value) {
            bannerbar.setTitleColor(value)
        }

    /**
     * @see Bannerbar.setSubtitleColor
     */
    var subtitleColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@ColorInt value) {
            bannerbar.setSubtitleColor(value)
        }

    /**
     * @see Bannerbar.setActionsTextColor
     */
    var actionsTextColors: ColorStateList
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@NonNull value) {
            bannerbar.setActionsTextColor(value)
        }

    /**
     * @see Bannerbar.setActionsTextColor
     */
    var actionsTextColor: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(@ColorInt value) {
            bannerbar.setActionsTextColor(value)
        }
}
