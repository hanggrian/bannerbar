package android.support.design.widget

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.Px
import android.support.annotation.StringRes
import android.view.View

/**
 * Base [Errorbar] interface consist of common operations.
 *
 * @param R return type of function.
 */
interface ErrorbarContent<out R> {

    /** Set a backdrop from drawable. */
    fun setBackdrop(drawable: Drawable): R

    /** Set a backdrop from bitmap. */
    fun setBackdrop(bitmap: Bitmap): R

    /** Set a backdrop from uri. */
    fun setBackdrop(uri: Uri): R

    /** Set a backdrop from drawable resource. */
    fun setBackdrop(@DrawableRes resource: Int): R

    /** Set a backdrop from color. */
    fun setBackdropColor(@ColorInt color: Int): R

    /** Set content margin each side. */
    fun setContentMargin(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int): R

    /** Set content left margin. */
    fun setContentMarginLeft(@Px left: Int): R

    /** Set content top margin. */
    fun setContentMarginTop(@Px top: Int): R

    /** Set content right margin. */
    fun setContentMarginRight(@Px right: Int): R

    /** Set content bottom margin. */
    fun setContentMarginBottom(@Px bottom: Int): R

    /** Set image from drawable. */
    fun setImage(drawable: Drawable): R

    /** Set image from bitmap. */
    fun setImage(bitmap: Bitmap): R

    /** Set image from uri. */
    fun setImage(uri: Uri): R

    /** Set image from drawable resource. */
    fun setImage(@DrawableRes resource: Int): R

    /** Set text to this Errorbar. */
    fun setText(text: CharSequence): R

    /** Set text from string resource. */
    fun setText(@StringRes text: Int): R

    /** Set button text and its click listener. */
    fun setAction(text: CharSequence?, action: ((View) -> Unit)?): R

    /** Set button text from string resource and its click listener. */
    fun setAction(@StringRes text: Int, action: ((View) -> Unit)?): R
}