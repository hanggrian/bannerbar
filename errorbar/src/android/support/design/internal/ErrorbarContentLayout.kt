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
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.Px
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.ErrorbarContent
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
) : FrameLayout(context, attrs, defStyleAttr), BaseTransientBottomBar.ContentViewCallback,
    ErrorbarContent<Unit> {

    internal var dismisser: (() -> Unit)? = null

    lateinit var backdropView: ImageView
    lateinit var containerView: ViewGroup
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var actionView: Button

    // keep TypedArray a little bit longer because views are binded in onFinishInflate()
    @SuppressLint("CustomViewStyleable")
    private val a = context.obtainStyledAttributes(attrs, R.styleable.ErrorbarLayout,
        defStyleAttr, R.style.Widget_Design_Errorbar)

    override fun onFinishInflate() {
        super.onFinishInflate()

        // bind views
        backdropView = findViewById(R.id.errorbar_backdrop)
        containerView = findViewById(R.id.errorbar_container)
        imageView = findViewById(R.id.errorbar_image)
        textView = findViewById(R.id.errorbar_text)
        actionView = findViewById(R.id.errorbar_action)

        // apply attr and finally recycle
        if (a.hasValue(R.styleable.ErrorbarLayout_backdrop)) {
            setBackdrop(a.getDrawable(R.styleable.ErrorbarLayout_backdrop))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_image)) {
            setImage(a.getDrawable(R.styleable.ErrorbarLayout_image))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textAppearance)) {
            @Suppress("DEPRECATION") when (Build.VERSION.SDK_INT) {
                23 -> textView.setTextAppearance(
                    a.getResourceId(R.styleable.ErrorbarLayout_android_textAppearance, 0))
                else -> textView.setTextAppearance(context,
                    a.getResourceId(R.styleable.ErrorbarLayout_android_textAppearance, 0))
            }
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textColor)) {
            textView.setTextColor(a.getColorStateList(R.styleable.ErrorbarLayout_android_textColor))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textSize)) {
            textView.textSize = a.getDimension(R.styleable.ErrorbarLayout_android_textSize, 0f)
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextAppearance)) {
            @Suppress("DEPRECATION")
            when (Build.VERSION.SDK_INT) {
                23 -> actionView.setTextAppearance(
                    a.getResourceId(R.styleable.ErrorbarLayout_actionTextAppearance, 0))
                else -> actionView.setTextAppearance(context,
                    a.getResourceId(R.styleable.ErrorbarLayout_actionTextAppearance, 0))
            }
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextColor)) {
            actionView.setTextColor(a.getColorStateList(R.styleable.ErrorbarLayout_actionTextColor))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextSize)) {
            actionView.textSize = a.getDimension(R.styleable.ErrorbarLayout_actionTextSize, 0f)
        }
        a.recycle()
    }

    override fun setBackdrop(@DrawableRes resource: Int) = backdropView {
        it.setImageResource(resource)
    }

    override fun setBackdrop(uri: Uri) = backdropView { it.setImageURI(uri) }

    override fun setBackdrop(drawable: Drawable) = backdropView { it.setImageDrawable(drawable) }

    @RequiresApi(23) override fun setBackdrop(icon: Icon) = backdropView { it.setImageIcon(icon) }

    @RequiresApi(21) override fun setBackdrop(tint: ColorStateList) = backdropView {
        it.imageTintList = tint
    }

    override fun setBackdrop(bitmap: Bitmap) = backdropView { it.setImageBitmap(bitmap) }

    override fun setBackdropColor(@ColorInt color: Int) = backdropView {
        it.setBackgroundColor(color)
    }

    override fun setContentMargin(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int) {
        (containerView.layoutParams as ViewGroup.MarginLayoutParams)
            .setMargins(left, top, right, bottom)
    }

    override fun setContentMarginLeft(@Px left: Int) {
        (containerView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = left
    }

    override fun setContentMarginTop(@Px top: Int) {
        (containerView.layoutParams as ViewGroup.MarginLayoutParams).topMargin = top
    }

    override fun setContentMarginRight(@Px right: Int) {
        (containerView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = right
    }

    override fun setContentMarginBottom(@Px bottom: Int) {
        (containerView.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = bottom
    }

    override fun setImage(@DrawableRes resource: Int) = imageView {
        it.setImageResource(resource)
    }

    override fun setImage(uri: Uri) = imageView { it.setImageURI(uri) }

    override fun setImage(drawable: Drawable) = imageView { it.setImageDrawable(drawable) }

    @RequiresApi(23) override fun setImage(icon: Icon) = imageView { it.setImageIcon(icon) }

    @RequiresApi(21) override fun setImage(tint: ColorStateList) = imageView {
        it.imageTintList = tint
    }

    override fun setImage(bitmap: Bitmap) = backdropView { it.setImageBitmap(bitmap) }

    override fun setText(text: CharSequence) {
        if (text.isNotEmpty()) textView { it.text = text }
    }

    override fun setText(@StringRes text: Int) = setText(resources.getText(text))

    override fun setAction(text: CharSequence?, action: ((View) -> Unit)?) {
        if (!text.isNullOrEmpty() && action != null) actionView {
            it.text = text
            it.setOnClickListener {
                dismisser!!()
                action.invoke(this)
            }
        }
    }

    override fun setAction(@StringRes text: Int, action: ((View) -> Unit)?) =
        setAction(resources.getText(text), action)

    override fun animateContentIn(delay: Int, duration: Int) {
        backdropView.animateBy(delay, duration, true)
        imageView.animateBy(delay, duration, true)
        // inherited from Snackbar
        textView.animateBy(delay, duration, true, true)
        actionView.animateBy(delay, duration, true)
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        backdropView.animateBy(delay, duration, false)
        imageView.animateBy(delay, duration, false)
        // inherited from Snackbar
        textView.animateBy(delay, duration, false, true)
        actionView.animateBy(delay, duration, false)
    }

    private companion object {

        fun View.animateBy(
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

        inline operator fun <T : View> T.invoke(block: (T) -> Unit) {
            visibility = VISIBLE
            block(this)
        }
    }
}