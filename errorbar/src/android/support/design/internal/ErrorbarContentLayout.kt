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

package android.support.design.internal

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.AnyRes
import android.support.annotation.AttrRes
import android.support.annotation.Px
import android.support.annotation.StyleableRes
import android.support.design.widget.BaseTransientBottomBar
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.hendraanggrian.errorbar.R

/**
 * Actual content of [android.support.design.widget.Errorbar].
 *
 * @see SnackbarContentLayout
 */
open class ErrorbarContentLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.errorbarStyle
) : FrameLayout(context, attrs, defStyleAttr), BaseTransientBottomBar.ContentViewCallback {

    internal lateinit var backdropView: ImageView
    internal lateinit var containerView: ViewGroup
    internal lateinit var logoView: ImageView
    internal lateinit var messageView: TextView
    internal lateinit var actionView: Button

    // keep TypedArray a little bit longer because views are binded in onFinishInflate()
    private val a = context.obtainStyledAttributes(
        attrs,
        R.styleable.ErrorbarLayout,
        defStyleAttr,
        R.style.Widget_Design_Errorbar)
    @Px private val mMaxWidth = a.getDimensionPixelSize(
        R.styleable.ErrorbarLayout_android_maxWidth, -1)
    @Px private val mMaxInlineActionWidth = a.getDimensionPixelSize(
        R.styleable.ErrorbarLayout_maxActionInlineWidth, -1)

    override fun onFinishInflate() {
        super.onFinishInflate()
        // bind views
        backdropView = findViewById(R.id.errorbar_backdrop)
        containerView = findViewById(R.id.errorbar_container)
        logoView = findViewById(R.id.errorbar_logo)
        messageView = findViewById(R.id.errorbar_text)
        actionView = findViewById(R.id.errorbar_action)
        // apply attr and finally recycle
        if (a.hasValue(R.styleable.ErrorbarLayout_backdrop)) backdropView.run {
            visibility = VISIBLE
            setImageDrawable(a.getDrawableNotNull(R.styleable.ErrorbarLayout_backdrop))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_logo)) logoView.run {
            visibility = VISIBLE
            setImageDrawable(a.getDrawableNotNull(R.styleable.ErrorbarLayout_logo))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textAppearance)) {
            @Suppress("DEPRECATION") when (Build.VERSION.SDK_INT) {
                23 -> messageView.setTextAppearance(
                    a.getResourceIdNotNull(R.styleable.ErrorbarLayout_android_textAppearance))
                else -> messageView.setTextAppearance(context,
                    a.getResourceIdNotNull(R.styleable.ErrorbarLayout_android_textAppearance))
            }
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textColor)) {
            messageView.setTextColor(
                a.getColorStateListNotNull(R.styleable.ErrorbarLayout_android_textColor))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textSize)) {
            messageView.textSize = a.getDimensionNotNull(
                R.styleable.ErrorbarLayout_android_textSize)
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextAppearance)) {
            @Suppress("DEPRECATION")
            when (Build.VERSION.SDK_INT) {
                23 -> actionView.setTextAppearance(
                    a.getResourceIdNotNull(R.styleable.ErrorbarLayout_actionTextAppearance))
                else -> actionView.setTextAppearance(context,
                    a.getResourceIdNotNull(R.styleable.ErrorbarLayout_actionTextAppearance))
            }
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextColor)) {
            actionView.setTextColor(
                a.getColorStateListNotNull(R.styleable.ErrorbarLayout_actionTextColor))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextSize)) {
            actionView.textSize = a.getDimensionNotNull(
                R.styleable.ErrorbarLayout_actionTextSize)
        }
        a.recycle()
    }

    @SuppressLint("PrivateResource")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var _widthMeasureSpec = widthMeasureSpec
        super.onMeasure(_widthMeasureSpec, heightMeasureSpec)
        if (mMaxWidth in 1..(measuredWidth - 1)) {
            _widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY)
            super.onMeasure(_widthMeasureSpec, heightMeasureSpec)
        }
        val multiLineVPadding = resources.getDimensionPixelSize(
            R.dimen.design_snackbar_padding_vertical_2lines)
        val singleLineVPadding = resources.getDimensionPixelSize(
            R.dimen.design_snackbar_padding_vertical)
        val isMultiLine = messageView.layout.lineCount > 1
        var remeasure = false
        when {
            isMultiLine && mMaxInlineActionWidth > 0 &&
                actionView.measuredWidth > mMaxInlineActionWidth ->
                if (updateViewsWithinLayout(
                        multiLineVPadding,
                        multiLineVPadding - singleLineVPadding)) {
                    remeasure = true
                }
            else -> {
                val messagePadding = if (isMultiLine) multiLineVPadding else singleLineVPadding
                if (updateViewsWithinLayout(messagePadding, messagePadding)) {
                    remeasure = true
                }
            }
        }
        if (remeasure) {
            super.onMeasure(_widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun updateViewsWithinLayout(messagePadTop: Int, messagePadBottom: Int): Boolean {
        var changed = false
        if (messageView.paddingTop != messagePadTop ||
            messageView.paddingBottom != messagePadBottom) {
            updateTopBottomPadding(messageView, messagePadTop, messagePadBottom)
            changed = true
        }
        return changed
    }

    private fun updateTopBottomPadding(
        view: View,
        topPadding: Int,
        bottomPadding: Int
    ) = when {
        ViewCompat.isPaddingRelative(view) -> ViewCompat.setPaddingRelative(
            view,
            ViewCompat.getPaddingStart(view),
            topPadding,
            ViewCompat.getPaddingEnd(view),
            bottomPadding)
        else -> view.setPadding(view.paddingLeft, topPadding, view.paddingRight, bottomPadding)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        backdropView.animateBy(delay, duration, true)
        logoView.animateBy(delay, duration, true)
        // inherited from Snackbar
        messageView.animateBy(delay, duration, true, true)
        actionView.animateBy(delay, duration, true)
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        backdropView.animateBy(delay, duration, false)
        logoView.animateBy(delay, duration, false)
        // inherited from Snackbar
        messageView.animateBy(delay, duration, false, true)
        actionView.animateBy(delay, duration, false)
    }

    private fun View.animateBy(
        delay: Int,
        duration: Int,
        animateIn: Boolean,
        condition: Boolean = visibility == VISIBLE
    ) {
        if (condition) {
            alpha = if (animateIn) 0.0f else 1.0f
            animate()
                .alpha(if (animateIn) 1.0f else 0.0f)
                .setDuration(duration.toLong())
                .setStartDelay(delay.toLong())
                .start()
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private companion object {

        inline fun TypedArray.getDrawableNotNull(@StyleableRes index: Int) =
            checkNotNull(getDrawable(index))

        @AnyRes
        inline fun TypedArray.getResourceIdNotNull(@StyleableRes index: Int) =
            checkNotNull(getResourceId(index, 0))

        inline fun TypedArray.getColorStateListNotNull(@StyleableRes index: Int) =
            checkNotNull(getColorStateList(index))

        inline fun TypedArray.getDimensionNotNull(@StyleableRes index: Int) =
            checkNotNull(getDimension(index, 0f))
    }
}