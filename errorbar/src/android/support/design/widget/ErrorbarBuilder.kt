package android.support.design.widget

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color.WHITE
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.Px
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.DeprecationLevel.ERROR

interface ErrorbarBuilder {

    private companion object {
        const val NO_GETTER = "Property does not have a getter"

        fun noGetter(): Nothing = throw UnsupportedClassVersionError(NO_GETTER)
    }

    val backdropView: ImageView

    val containerView: ViewGroup

    val imageView: ImageView

    val textView: TextView

    val actionView: Button

    var backdropDrawable: Drawable?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = backdropView.run {
            setImageDrawable(value)
            visibility = value?.let { VISIBLE } ?: GONE
        }

    var backdropBitmap: Bitmap?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = backdropView.run {
            setImageBitmap(value)
            visibility = value?.let { VISIBLE } ?: GONE
        }

    var backdropUri: Uri?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = backdropView.run {
            setImageURI(value)
            visibility = value?.let { VISIBLE } ?: GONE
        }

    var backdropResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@DrawableRes value) = backdropView.run {
            setImageResource(value)
            visibility = if (value != -1) VISIBLE else GONE
        }

    var backdropColorStateList: ColorStateList?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = backdropView.run {
            setImageDrawable(ColorDrawable(value?.defaultColor ?: WHITE))
            visibility = value?.let { VISIBLE } ?: GONE
        }

    var backdropColor: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@DrawableRes value) {
            backdropColorStateList = ColorStateList.valueOf(value)
        }

    var backdropColorResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@ColorRes value) {
            backdropColor = ContextCompat.getColor(backdropView.context, value)
        }

    var contentMarginLeft: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@Px value) {
            (containerView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = value
        }

    var contentMarginTop: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@Px value) {
            (containerView.layoutParams as ViewGroup.MarginLayoutParams).topMargin = value
        }

    var contentMarginRight: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@Px value) {
            (containerView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = value
        }

    var contentMarginBottom: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@Px value) {
            (containerView.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = value
        }

    var imageDrawable: Drawable?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = imageView.run {
            setImageDrawable(value)
            visibility = value?.let { VISIBLE } ?: GONE
        }

    var imageBitmap: Bitmap?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = imageView.run {
            setImageBitmap(value)
            visibility = value?.let { VISIBLE } ?: GONE
        }

    var imageUri: Uri?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = imageView.run {
            setImageURI(value)
            visibility = value?.let { VISIBLE } ?: GONE
        }

    var imageResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@DrawableRes value) = imageView.run {
            setImageResource(value)
            visibility = if (value != -1) VISIBLE else GONE
        }

    var text: CharSequence?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = textView.run {
            text = value
            visibility = value?.let { VISIBLE } ?: GONE
        }

    var textResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@StringRes value) {
            text = textView.resources.getText(value)
        }

    /** Set button text and its click listener. */
    fun setAction(text: CharSequence?, action: ((View) -> Unit)? = null): Errorbar

    /** Set button text from string resource and its click listener. */
    fun setAction(@StringRes resId: Int, action: ((View) -> Unit)? = null): Errorbar =
        setAction(actionView.context.getText(resId), action)
}