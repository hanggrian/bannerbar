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
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.hendraanggrian.errorbar.R
import kotlin.annotation.AnnotationRetention.SOURCE

/**
 * A larger Snackbar to display error and empty state.
 *
 * @see Snackbar
 */
@Suppress("NOTHING_TO_INLINE")
class Errorbar private constructor(
    parent: ViewGroup,
    content: View,
    contentViewCallback: ContentViewCallback
) : BaseTransientBottomBar<Errorbar>(parent, content, contentViewCallback), ErrorbarBuilder {

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

    override val backdropView: ImageView get() = layout.backdropView

    override val containerView: ViewGroup get() = layout.containerView

    override val logoView: ImageView get() = layout.logoView

    override val textView: TextView get() = layout.textView

    override val actionView: Button get() = layout.actionView

    /** Set a backdrop from drawable. */
    inline fun setBackdropDrawable(drawable: Drawable?): Errorbar = also {
        it.backdropDrawable = drawable
    }

    /** Set a backdrop from bitmap. */
    inline fun setBackdropBitmap(bitmap: Bitmap?): Errorbar = also {
        it.backdropBitmap = bitmap
    }

    /** Set a backdrop from uri. */
    inline fun setBackdropUri(uri: Uri?): Errorbar = also {
        it.backdropUri = uri
    }

    /** Set a backdrop from drawable resource. */
    inline fun setBackdropResource(@DrawableRes resource: Int): Errorbar = also {
        it.backdropResource = resource
    }

    /** Set a backdrop from color state list. */
    inline fun setBackdropColor(colorStateList: ColorStateList?): Errorbar = also {
        it.backdropColorStateList = colorStateList
    }

    /** Set a backdrop from color. */
    inline fun setBackdropColor(@ColorInt color: Int): Errorbar = also {
        it.backdropColor = color
    }

    /** Set a backdrop from color resource. */
    inline fun setBackdropColorResource(@ColorRes colorResource: Int): Errorbar = also {
        it.backdropColorResource = colorResource
    }

    /** Set content margin each side. */
    fun setContentMargin(left: Int, top: Int, right: Int, bottom: Int): Errorbar = apply {
        (layout.containerView.layoutParams as ViewGroup.MarginLayoutParams)
            .setMargins(left, top, right, bottom)
    }

    /** Set content left margin. */
    inline fun setContentMarginLeft(contentMarginLeft: Int): Errorbar = also {
        it.contentMarginLeft = contentMarginLeft
    }

    /** Set content top margin. */
    inline fun setContentMarginTop(contentMarginTop: Int): Errorbar = also {
        it.contentMarginTop = contentMarginTop
    }

    /** Set content right margin. */
    inline fun setContentMarginRight(contentMarginRight: Int): Errorbar = also {
        it.contentMarginRight = contentMarginRight
    }

    /** Set content bottom margin. */
    inline fun setContentMarginBottom(contentMarginBottom: Int): Errorbar = also {
        it.contentMarginBottom = contentMarginBottom
    }

    /** Set logo from drawable. */
    inline fun setLogoDrawable(drawable: Drawable?): Errorbar = also {
        it.logoDrawable = drawable
    }

    /** Set logo from bitmap. */
    inline fun setLogoBitmap(bitmap: Bitmap?): Errorbar = also {
        it.logoBitmap = bitmap
    }

    /** Set logo from uri. */
    inline fun setLogoUri(uri: Uri?): Errorbar = also {
        it.logoUri = uri
    }

    /** Set logo from drawable resource. */
    inline fun setLogoResource(@DrawableRes resource: Int): Errorbar = also {
        it.logoResource = resource
    }

    /** Set text to this Errorbar. */
    inline fun setText(text: CharSequence?): Errorbar = also {
        it.text = text
    }

    /** Set text from string resource. */
    inline fun setText(@StringRes text: Int): Errorbar = also {
        it.textResource = text
    }

    override fun setAction(text: CharSequence?, action: ((View) -> Unit)?): Errorbar = apply {
        actionView.run {
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