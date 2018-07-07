/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("DEPRECATION")

package com.hendraanggrian.errorbar

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ScrollView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.widget.NestedScrollView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.hendraanggrian.errorbar.internal.ErrorbarContentLayout
import com.hendraanggrian.errorbar.internal.invoke
import kotlin.annotation.AnnotationRetention.SOURCE

/**
 * A larger Snackbar to display error and empty state.
 *
 * @see com.google.android.material.snackbar.Snackbar
 */
class Errorbar private constructor(
    parent: ViewGroup,
    content: View,
    contentViewCallback: ContentViewCallback
) : BaseTransientBottomBar<Errorbar>(parent, content, contentViewCallback) {

    @IntDef(LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG)
    @IntRange(from = 1)
    @Retention(SOURCE)
    annotation class Duration

    companion object {
        const val LENGTH_INDEFINITE = BaseTransientBottomBar.LENGTH_INDEFINITE
        const val LENGTH_SHORT = BaseTransientBottomBar.LENGTH_SHORT
        const val LENGTH_LONG = BaseTransientBottomBar.LENGTH_LONG

        /**
         * Make an [Errorbar] to display a message.
         *
         * @param view the view to find a parent from.
         * @param text the text to show. Can be formatted text.
         * @param duration how long to display the message.
         * @see Snackbar.make
         */
        fun make(view: View, text: CharSequence, @Duration duration: Int): Errorbar {
            val parent = view.findSuitableParent()
                ?: throw IllegalStateException("No suitable parent")
            val context = parent.context
            val content = LayoutInflater.from(context).inflate(
                R.layout.design_layout_errorbar_include, parent, false) as ErrorbarContentLayout
            return Errorbar(parent, content, content).also {
                it.setText(text)
                it.duration = duration
                // hack Snackbar's view container
                it.view.setPadding(0, 0, 0, 0)
                it.view.setBackgroundColor(context.theme.getColor(android.R.attr.windowBackground))
                it.view.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        }

        /**
         * Make an [Errorbar] to display a message.
         *
         * @param view the view to find a parent from.
         * @param text the resource id of the string resource to use. Can be formatted text.
         * @param duration how long to display the message.
         */
        @Suppress("NOTHING_TO_INLINE")
        inline fun make(view: View, @StringRes text: Int, @Duration duration: Int): Errorbar =
            make(view, view.resources.getText(text), duration)

        /**
         * While [Errorbar] prioritizes [CollapsingToolbarLayout] to be its parent,
         * [Errorbar] accepts any parent capable of holding more than one child.
         */
        private fun View?.findSuitableParent(): ViewGroup? {
            var view = this
            do {
                if (view is ViewGroup) {
                    // ScrollView can only accept one child, therefore not qualified to be an errorbar's parent
                    if (view !is ScrollView && view !is NestedScrollView) {
                        return view
                    }
                }
                if (view != null) {
                    // loop to get parents
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)
            return null
        }

        @ColorInt
        private fun Resources.Theme.getColor(@AttrRes attr: Int): Int {
            val a = obtainStyledAttributes(null, intArrayOf(attr), 0, 0)
            if (!a.hasValue(0)) throw Resources.NotFoundException()
            val value = a.getColor(0, 0)
            a.recycle()
            return value
        }
    }

    val layout get() = view.getChildAt(0) as ErrorbarContentLayout

    /** Clear background. */
    fun noBackground(): Errorbar = apply {
        layout.backgroundView.setImageDrawable(null)
        layout.backgroundView.visibility = GONE
    }

    /** Set backdrop from drawable resource. */
    fun setBackground(@DrawableRes resource: Int): Errorbar = apply {
        layout.backgroundView { setImageResource(resource) }
    }

    /** Set backdrop from uri. */
    fun setBackground(uri: Uri): Errorbar = apply {
        layout.backgroundView { setImageURI(uri) }
    }

    /** Set backdrop from drawable. */
    fun setBackground(drawable: Drawable): Errorbar = apply {
        layout.backgroundView { setImageDrawable(drawable) }
    }

    /** Set backdrop from icon. */
    @RequiresApi(23)
    fun setBackground(icon: Icon): Errorbar = apply {
        layout.backgroundView { setImage(icon) }
    }

    /** Set backdrop from tint. */
    @RequiresApi(21)
    fun setBackground(tint: ColorStateList): Errorbar = apply {
        layout.backgroundView { setImage(tint) }
    }

    /** Set a backdrop from bitmap. */
    fun setBackground(bitmap: Bitmap): Errorbar = apply {
        layout.backgroundView { setImageBitmap(bitmap) }
    }

    /** Set a backdrop from color. */
    fun setBackgroundColor(@ColorInt color: Int): Errorbar = apply {
        layout.backgroundView { setBackgroundColor(color) }
    }

    /** Set content margin each side. */
    fun setContentMargin(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int): Errorbar =
        apply {
            (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams)
                .setMargins(left, top, right, bottom)
        }

    /** Set content left margin. */
    fun setContentMarginLeft(@Px left: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = left
    }

    /** Set content top margin. */
    fun setContentMarginTop(@Px top: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams).topMargin = top
    }

    /** Set content right margin. */
    fun setContentMarginRight(@Px right: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = right
    }

    /** Set content bottom margin. */
    fun setContentMarginBottom(@Px bottom: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = bottom
    }

    /** Clear image. */
    fun noImage(): Errorbar = apply {
        layout.imageView.setImageDrawable(null)
        layout.imageView.visibility = GONE
    }

    /** Set image from drawable resource. */
    fun setImage(@DrawableRes resource: Int): Errorbar = apply {
        layout.imageView { setImageResource(resource) }
    }

    /** Set image from uri. */
    fun setImage(uri: Uri): Errorbar = apply {
        layout.imageView { setImageURI(uri) }
    }

    /** Set image from drawable. */
    fun setImage(drawable: Drawable): Errorbar = apply {
        layout.imageView { setImageDrawable(drawable) }
    }

    /** Set image from icon. */
    @RequiresApi(23)
    fun setImage(icon: Icon): Errorbar = apply {
        layout.imageView { setImage(icon) }
    }

    /** Set image from tint. */
    @RequiresApi(21)
    fun setImage(tint: ColorStateList): Errorbar = apply {
        layout.imageView { setImage(tint) }
    }

    /** Set image from bitmap. */
    fun setImage(bitmap: Bitmap): Errorbar = apply {
        layout.backgroundView { setImageBitmap(bitmap) }
    }

    /** Set text to this Errorbar. */
    fun setText(text: CharSequence): Errorbar = apply {
        if (text.isNotEmpty()) layout.textView { setText(text) }
    }

    /** Set text from string resource. */
    fun setText(@StringRes text: Int): Errorbar = setText(layout.resources.getText(text))

    /** Set button text and its click listener. */
    fun setAction(text: CharSequence?, action: ((View) -> Unit)? = null): Errorbar = apply {
        if (!text.isNullOrEmpty()) layout.actionView {
            setText(text)
            setOnClickListener {
                action?.invoke(it)
                dispatchDismiss(Callback.DISMISS_EVENT_ACTION)
            }
        }
    }

    /** Set button text from string resource and its click listener. */
    fun setAction(@StringRes text: Int, action: ((View) -> Unit)? = null): Errorbar =
        setAction(context.resources.getText(text), action)

    /** Sets the text color of the action specified in [Errorbar.setAction]. */
    fun setActionTextColor(@ColorInt color: Int): Errorbar = apply {
        layout.textView.setTextColor(color)
    }

    /** Sets the text color of the action specified in [Errorbar.setAction]. */
    fun setActionTextColor(colors: ColorStateList): Errorbar = apply {
        layout.textView.setTextColor(colors)
    }

    /**
     * Callback class for [Errorbar] instances.
     */
    open class Callback : BaseCallback<Errorbar>() {

        companion object {
            /** Indicates that the Errorbar was dismissed via a swipe.*/
            const val DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE
            /** Indicates that the Errorbar was dismissed via an action click.*/
            const val DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION
            /** Indicates that the Errorbar was dismissed via a timeout.*/
            const val DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT
            /** Indicates that the Errorbar was dismissed via a call to [Errorbar.dismiss].*/
            const val DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL
            /** Indicates that the Errorbar was dismissed from a new Snackbar being shown.*/
            const val DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE
        }

        override fun onShown(errorbar: Errorbar) {}

        override fun onDismissed(errorbar: Errorbar, @DismissEvent event: Int) {}
    }
}