package com.hendraanggrian.errorbar

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.*
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.hendraanggrian.kota.content.getColor
import com.hendraanggrian.kota.content.getColor2
import com.hendraanggrian.kota.view.setVisibleBy

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal interface BaseErrorbar<out E> {

    val instance: E
    val backdropView: ImageView
    val viewContainer: ViewGroup
    val logoView: ImageView
    val messageView: TextView
    val actionView: Button

    fun setBackdropBitmap(backdrop: Bitmap?): E {
        if (backdropView.setVisibleBy(backdrop != null)) {
            backdropView.setImageBitmap(backdrop)
        }
        return instance
    }

    fun setBackdropUri(backdrop: Uri?): E {
        if (backdropView.setVisibleBy(backdrop != null)) {
            backdropView.setImageURI(backdrop)
        }
        return instance
    }

    fun setBackdropDrawable(backdrop: Drawable?): E {
        if (backdropView.setVisibleBy(backdrop != null)) {
            backdropView.setImageDrawable(backdrop)
        }
        return instance
    }

    fun setBackdropDrawable(@DrawableRes backdrop: Int): E {
        if (backdropView.setVisibleBy(backdrop > 0)) {
            backdropView.setImageResource(backdrop)
        }
        return instance
    }

    fun setBackdropColor(@ColorInt color: Int): E = setBackdropDrawable(ColorDrawable(color))
    fun setBackdropColorRes(@ColorRes colorRes: Int): E = setBackdropColor(backdropView.context.getColor2(colorRes))
    fun setBackdropColorAttr(@AttrRes colorAttr: Int): E = setBackdropColor(backdropView.context.theme.getColor(colorAttr))

    fun setContentMargin(left: Int, top: Int, right: Int, bottom: Int): E {
        (viewContainer.layoutParams as FrameLayout.LayoutParams).setMargins(left, top, right, bottom)
        return instance
    }

    fun setContentMarginLeft(left: Int): E {
        (viewContainer.layoutParams as FrameLayout.LayoutParams).leftMargin = left
        return instance
    }

    fun setContentMarginTop(top: Int): E {
        (viewContainer.layoutParams as FrameLayout.LayoutParams).topMargin = top
        return instance
    }

    fun setContentMarginRight(right: Int): E {
        (viewContainer.layoutParams as FrameLayout.LayoutParams).rightMargin = right
        return instance
    }

    fun setContentMarginBottom(bottom: Int): E {
        (viewContainer.layoutParams as FrameLayout.LayoutParams).bottomMargin = bottom
        return instance
    }

    fun setLogoBitmap(logo: Bitmap?): E {
        if (logoView.setVisibleBy(logo != null)) {
            logoView.setImageBitmap(logo)
        }
        return instance
    }

    fun setLogoUri(logo: Uri?): E {
        if (logoView.setVisibleBy(logo != null)) {
            logoView.setImageURI(logo)
        }
        return instance
    }

    fun setLogoDrawable(logo: Drawable?): E {
        if (logoView.setVisibleBy(logo != null)) {
            logoView.setImageDrawable(logo)
        }
        return instance
    }

    fun setLogoDrawable(@DrawableRes logo: Int): E {
        if (logoView.setVisibleBy(logo > 0)) {
            logoView.setImageResource(logo)
        }
        return instance
    }

    fun setText(@StringRes text: Int): E = setText(messageView.resources.getText(text))

    fun setText(text: CharSequence?): E {
        if (messageView.setVisibleBy(!TextUtils.isEmpty(text))) {
            messageView.text = text
        }
        return instance
    }

    fun setText(e: Exception?): E {
        if (messageView.setVisibleBy(e != null)) {
            setText(e!!.message)
        }
        return instance
    }

    fun setAction(@StringRes resId: Int, listener: View.OnClickListener?): E = setAction(actionView.context.getText(resId), listener)
    fun setAction(text: CharSequence?, listener: View.OnClickListener?): E
}