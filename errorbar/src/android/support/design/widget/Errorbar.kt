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
import android.graphics.drawable.Icon
import android.net.Uri
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IntDef
import android.support.annotation.IntRange
import android.support.annotation.Px
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.design.internal.ErrorbarContentLayout
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ScrollView
import com.hendraanggrian.errorbar.R
import kotlin.annotation.AnnotationRetention.SOURCE

/**
 * A larger [Snackbar] to display error and empty state.
 *
 * @see Snackbar
 */
class Errorbar private constructor(
    parent: ViewGroup,
    content: View,
    contentViewCallback: ContentViewCallback
) : BaseTransientBottomBar<Errorbar>(parent, content, contentViewCallback),
    ErrorbarContent<Errorbar> {

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
            return Errorbar(parent, content, content).also {
                it.layout.dismisser = {
                    it.dispatchDismiss(BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION)
                }
                it.duration = duration
                // hack Snackbar's view container
                it.mView.setPadding(0, 0, 0, 0)
                it.mView.setBackgroundColor(context.theme.getColor(android.R.attr.windowBackground))
                it.mView.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
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

    val layout get() = mView.getChildAt(0) as ErrorbarContentLayout

    override fun setBackdrop(@DrawableRes resource: Int): Errorbar = apply {
        layout.setBackdrop(resource)
    }

    override fun setBackdrop(uri: Uri): Errorbar = apply {
        layout.setBackdrop(uri)
    }

    override fun setBackdrop(drawable: Drawable): Errorbar = apply {
        layout.setBackdrop(drawable)
    }

    @RequiresApi(23) override fun setBackdrop(icon: Icon): Errorbar = apply {
        layout.setBackdrop(icon)
    }

    @RequiresApi(21) override fun setBackdrop(tint: ColorStateList): Errorbar = apply {
        layout.setBackdrop(tint)
    }

    override fun setBackdrop(bitmap: Bitmap): Errorbar = apply {
        layout.setBackdrop(bitmap)
    }

    override fun setBackdropColor(@ColorInt color: Int): Errorbar = apply {
        layout.setBackdropColor(color)
    }

    override fun setContentMargin(
        @Px left: Int,
        @Px top: Int,
        @Px right: Int,
        @Px bottom: Int
    ): Errorbar = apply {
        layout.setContentMargin(left, top, right, bottom)
    }

    override fun setContentMarginLeft(@Px left: Int): Errorbar = apply {
        layout.setContentMarginLeft(left)
    }

    override fun setContentMarginTop(@Px top: Int): Errorbar = apply {
        layout.setContentMarginTop(top)
    }

    override fun setContentMarginRight(@Px right: Int): Errorbar = apply {
        layout.setContentMarginRight(right)
    }

    override fun setContentMarginBottom(@Px bottom: Int): Errorbar = apply {
        layout.setContentMarginBottom(bottom)
    }

    override fun setImage(@DrawableRes resource: Int): Errorbar = apply {
        layout.setImage(resource)
    }

    override fun setImage(uri: Uri): Errorbar = apply {
        layout.setImage(uri)
    }

    override fun setImage(drawable: Drawable): Errorbar = apply {
        layout.setImage(drawable)
    }

    @RequiresApi(23) override fun setImage(icon: Icon): Errorbar = apply {
        layout.setImage(icon)
    }

    @RequiresApi(21) override fun setImage(tint: ColorStateList): Errorbar = apply {
        layout.setImage(tint)
    }

    override fun setImage(bitmap: Bitmap): Errorbar = apply {
        layout.setImage(bitmap)
    }

    override fun setText(text: CharSequence): Errorbar = apply {
        layout.setText(text)
    }

    override fun setText(@StringRes text: Int): Errorbar = apply {
        layout.setText(text)
    }

    override fun setAction(text: CharSequence?, action: ((View) -> Unit)?): Errorbar = apply {
        layout.setAction(text, action)
    }

    override fun setAction(@StringRes text: Int, action: ((View) -> Unit)?): Errorbar = apply {
        layout.setAction(text, action)
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