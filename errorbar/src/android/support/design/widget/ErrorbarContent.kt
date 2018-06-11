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

interface ErrorbarContent<T> {

    companion object {
        internal const val NO_GETTER = "Property does not have a getter"

        internal fun noGetter(): Nothing = throw UnsupportedClassVersionError(NO_GETTER)
    }

    val backdropView: ImageView

    val containerView: ViewGroup

    val logoView: ImageView

    val textView: TextView

    val actionView: Button

    var backdropDrawable: Drawable?
        get() = backdropView.drawable
        set(value) = backdropView.setImageDrawable(value)

    var backdropBitmap: Bitmap?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = backdropView.setImageBitmap(value)

    var backdropUri: Uri?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = backdropView.setImageURI(value)

    var backdropResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@DrawableRes value) = backdropView.setImageResource(value)

    var backdropColorStateList: ColorStateList?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = backdropView.setImageDrawable(ColorDrawable(value?.defaultColor ?: WHITE))

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
        get() = (containerView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin
        set(@Px value) {
            (containerView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = value
        }

    var contentMarginTop: Int
        get() = (containerView.layoutParams as ViewGroup.MarginLayoutParams).topMargin
        set(@Px value) {
            (containerView.layoutParams as ViewGroup.MarginLayoutParams).topMargin = value
        }

    var contentMarginRight: Int
        get() = (containerView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin
        set(@Px value) {
            (containerView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = value
        }

    var contentMarginBottom: Int
        get() = (containerView.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
        set(@Px value) {
            (containerView.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = value
        }

    var logoDrawable: Drawable?
        get() = logoView.drawable
        set(value) = logoView.run {
            visibility = value?.let { VISIBLE } ?: GONE
            if (visibility == VISIBLE) setImageDrawable(value)
        }

    var logoBitmap: Bitmap?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = logoView.setImageBitmap(value)

    var logoUri: Uri?
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(value) = logoView.setImageURI(value)

    var logoResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@DrawableRes value) = logoView.setImageResource(value)

    var text: CharSequence?
        get() = textView.text
        set(value) {
            textView.text = value
        }

    var textResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
        set(@StringRes value) {
            text = textView.resources.getText(value)
        }

    /** Set button text and its click listener. */
    fun setAction(text: CharSequence?, action: ((View) -> Unit)? = null): T

    /** Set button text from string resource and its click listener. */
    fun setAction(@StringRes resId: Int, action: ((View) -> Unit)? = null): T =
        setAction(actionView.context.getText(resId), action)
}