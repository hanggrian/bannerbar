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

package android.support.design.widget

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntDef
import android.support.annotation.IntRange
import android.support.annotation.StringRes
import android.support.design.internal.ErrorbarContentLayout
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ScrollView
import com.hendraanggrian.errorbar.R
import kotlin.annotation.AnnotationRetention.SOURCE

/**
 * A larger Snackbar to display error and empty state.
 *
 * @see Snackbar
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
         * @param duration how long to display the message. Either [LENGTH_SHORT], [LENGTH_LONG],
         * or [LENGTH_INDEFINITE].
         * @see Snackbar.make
         */
        fun make(view: View?, @Duration duration: Int): Errorbar {
            val parent = view.findSuitableParent()
                ?: throw IllegalStateException("No suitable parent")
            val context = parent.context
            val content = LayoutInflater.from(context).inflate(
                R.layout.design_layout_errorbar_include, parent, false) as ErrorbarContentLayout
            return Errorbar(parent, content, content).apply {
                this.duration = duration
                // hack Snackbar's view container
                mView.setPadding(0, 0, 0, 0)
                mView.setBackgroundColor(context.theme.getColor(android.R.attr.windowBackground))
                mView.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        }

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

        @ColorInt private fun Resources.Theme.getColor(@AttrRes attr: Int): Int {
            val a = obtainStyledAttributes(null, intArrayOf(attr), 0, 0)
            if (!a.hasValue(0)) throw Resources.NotFoundException()
            val value = a.getColor(0, 0)
            a.recycle()
            return value
        }
    }

    private inline val layout get() = mView.getChildAt(0) as ErrorbarContentLayout

    /** Set a backdrop from drawable. */
    fun setBackdrop(drawable: Drawable): Errorbar = apply {
        layout.backdropView.run {
            visibility = VISIBLE
            setImageDrawable(drawable)
        }
    }

    /** Set a backdrop from bitmap. */
    fun setBackdrop(bitmap: Bitmap): Errorbar = apply {
        layout.backdropView.run {
            visibility = VISIBLE
            setImageBitmap(bitmap)
        }
    }

    /** Set a backdrop from uri. */
    fun setBackdrop(uri: Uri): Errorbar = apply {
        layout.backdropView.run {
            visibility = VISIBLE
            setImageURI(uri)
        }
    }

    /** Set a backdrop from drawable resource. */
    fun setBackdrop(@DrawableRes resource: Int): Errorbar = apply {
        layout.backdropView.run {
            visibility = VISIBLE
            setImageResource(resource)
        }
    }

    /** Set a backdrop from color state list. */
    fun setBackdropColor(colorStateList: ColorStateList): Errorbar = apply {
        layout.backdropView.run {
            visibility = VISIBLE
            setImageDrawable(ColorDrawable(colorStateList.defaultColor))
        }
    }

    /** Set a backdrop from color. */
    fun setBackdropColor(@ColorInt color: Int): Errorbar =
        setBackdropColor(ColorStateList.valueOf(color))

    /** Set a backdrop from color resource. */
    fun setBackdropColorResource(@ColorRes colorResource: Int): Errorbar =
        setBackdropColor(ContextCompat.getColor(layout.backdropView.context, colorResource))

    /** Set content margin each side. */
    fun setContentMargin(left: Int, top: Int, right: Int, bottom: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams)
            .setMargins(left, top, right, bottom)
    }

    /** Set content left margin. */
    fun setContentMarginLeft(left: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = left
    }

    /** Set content top margin. */
    fun setContentMarginTop(top: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams).topMargin = top
    }

    /** Set content right margin. */
    fun setContentMarginRight(right: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = right
    }

    /** Set content bottom margin. */
    fun setContentMarginBottom(bottom: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = bottom
    }

    /** Set image from drawable. */
    fun setImage(drawable: Drawable): Errorbar = apply {
        layout.imageView.run {
            visibility = VISIBLE
            setImageDrawable(drawable)
        }
    }

    /** Set image from bitmap. */
    fun setImage(bitmap: Bitmap): Errorbar = apply {
        layout.imageView.run {
            visibility = VISIBLE
            setImageBitmap(bitmap)
        }
    }

    /** Set image from uri. */
    fun setImage(uri: Uri): Errorbar = apply {
        layout.imageView.run {
            visibility = VISIBLE
            setImageURI(uri)
        }
    }

    /** Set image from drawable resource. */
    fun setImage(@DrawableRes resource: Int): Errorbar = apply {
        layout.imageView.run {
            visibility = VISIBLE
            setImageResource(resource)
        }
    }

    /** Set text to this Errorbar. */
    fun setText(text: CharSequence): Errorbar = apply {
        layout.textView.run {
            visibility = if (text.isNotEmpty()) VISIBLE else GONE
            if (visibility == VISIBLE) setText(text)
        }
    }

    /** Set text from string resource. */
    fun setText(@StringRes text: Int): Errorbar =
        setText(layout.resources.getString(text))

    /** Set button text and its click listener. */
    fun setAction(text: CharSequence?, action: ((View) -> Unit)?): Errorbar = apply {
        layout.actionView.run {
            visibility = if (!text.isNullOrEmpty() && action != null) VISIBLE else GONE
            when (visibility) {
                VISIBLE -> {
                    setText(text)
                    setOnClickListener {
                        action?.invoke(this)
                        dispatchDismiss(BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION)
                    }
                }
                else -> setOnClickListener(null)
            }
        }
    }

    /** Set button text from string resource and its click listener. */
    fun setAction(@StringRes resId: Int, action: ((View) -> Unit)?): Errorbar =
        setAction(layout.actionView.context.getText(resId), action)

    open class Callback : BaseCallback<Errorbar>() {

        override fun onShown(v: Errorbar) {}

        override fun onDismissed(v: Errorbar, @DismissEvent event: Int) {}

        companion object {
            const val DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE
            const val DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION
            const val DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT
            const val DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL
            const val DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE
        }
    }
}