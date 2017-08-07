@file:JvmName("Errorbar")

package android.support.design.widget

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.*
import android.support.design.internal.ErrorbarContentLayout
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.hendraanggrian.errorbar.R
import com.hendraanggrian.kota.content.getColor2
import com.hendraanggrian.kota.text.isNotNullOrEmpty
import com.hendraanggrian.kota.view.setVisibleBy

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see Snackbar
 */
class Errorbar private constructor(
        parent: ViewGroup,
        content: View,
        contentViewCallback: ContentViewCallback
) : BaseTransientBottomBar<Errorbar>(parent, content, contentViewCallback) {

    companion object {
        const val LENGTH_INDEFINITE = BaseTransientBottomBar.LENGTH_INDEFINITE
        const val LENGTH_SHORT = BaseTransientBottomBar.LENGTH_SHORT
        const val LENGTH_LONG = BaseTransientBottomBar.LENGTH_LONG

        fun make(view: View, text: CharSequence, @Duration duration: Int): Errorbar {
            val parent = findSuitableParent(view) ?: throw IllegalStateException("Unable to find suitable parent!")
            val context = parent.context
            val content = LayoutInflater.from(context).inflate(R.layout.design_layout_errorbar_include, parent, false) as ErrorbarContentLayout
            return Errorbar(parent, content, content).apply {
                setText(text)
                this.duration = duration
                // hack Snackbar's view container
                mView.setPadding(0, 0, 0, 0)
                mView.setBackgroundColor(context.theme.getColor2(android.R.attr.windowBackground))
                mView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }

        fun make(view: View, @StringRes resId: Int, @Duration duration: Int): Errorbar = make(view, view.resources.getText(resId), duration)

        /**
         * While [Snackbar] prioritizes [CollapsingToolbarLayout] to be its parent,
         * Errorbar accepts any parent capable of holding more than one child.
         */
        private fun findSuitableParent(view: View?): ViewGroup? {
            var _view = view
            do {
                if (_view is ViewGroup) {
                    // ScrollView can only accept one child, therefore not qualified to be an errorbar's parent
                    if (_view !is ScrollView && _view !is NestedScrollView) {
                        return _view
                    }
                }
                if (_view != null) {
                    // loop to get parents
                    val parent = _view.parent
                    _view = if (parent is View) parent else null
                }
            } while (_view != null)
            return null
        }
    }

    private val errorbarLayout get() = mView.getChildAt(0) as ErrorbarContentLayout

    fun setBackdropBitmap(backdrop: Bitmap?): Errorbar {
        errorbarLayout.backdropView.setVisibleBy(backdrop != null) {
            setImageBitmap(backdrop)
        }
        return this
    }

    fun setBackdropUri(backdrop: Uri?): Errorbar {
        errorbarLayout.backdropView.setVisibleBy(backdrop != null) {
            setImageURI(backdrop)
        }
        return this
    }

    fun setBackdropDrawable(backdrop: Drawable?): Errorbar {
        errorbarLayout.backdropView.setVisibleBy(backdrop != null) {
            setImageDrawable(backdrop)
        }
        return this
    }

    fun setBackdropResource(@DrawableRes backdrop: Int): Errorbar {
        errorbarLayout.backdropView.setVisibleBy(backdrop > 0) {
            setImageResource(backdrop)
        }
        return this
    }

    fun setBackdropColor(backdrop: ColorStateList?): Errorbar {
        errorbarLayout.backdropView.setVisibleBy(backdrop != null) {
            setImageDrawable(ColorDrawable(backdrop!!.defaultColor))
        }
        return this
    }

    fun setBackdropColor(@ColorInt color: Int): Errorbar = setBackdropColor(ColorStateList.valueOf(color))
    fun setBackdropColorRes(@ColorRes colorRes: Int): Errorbar = setBackdropColor(errorbarLayout.backdropView.context.getColor2(colorRes))
    fun setBackdropColorAttr(@AttrRes colorAttr: Int): Errorbar = setBackdropColor(errorbarLayout.backdropView.context.theme.getColor2(colorAttr))

    fun setContentMargin(left: Int, top: Int, right: Int, bottom: Int): Errorbar {
        (errorbarLayout.containerView.layoutParams as ViewGroup.MarginLayoutParams).setMargins(left, top, right, bottom)
        return this
    }

    fun setContentMarginLeft(left: Int): Errorbar {
        (errorbarLayout.containerView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = left
        return this
    }

    fun setContentMarginTop(top: Int): Errorbar {
        (errorbarLayout.containerView.layoutParams as ViewGroup.MarginLayoutParams).topMargin = top
        return this
    }

    fun setContentMarginRight(right: Int): Errorbar {
        (errorbarLayout.containerView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = right
        return this
    }

    fun setContentMarginBottom(bottom: Int): Errorbar {
        (errorbarLayout.containerView.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = bottom
        return this
    }

    fun setLogoBitmap(logo: Bitmap?): Errorbar {
        errorbarLayout.logoView.setVisibleBy(logo != null) {
            setImageBitmap(logo)
        }
        return this
    }

    fun setLogoUri(logo: Uri?): Errorbar {
        errorbarLayout.logoView.setVisibleBy(logo != null) {
            setImageURI(logo)
        }
        return this
    }

    fun setLogoDrawable(logo: Drawable?): Errorbar {
        errorbarLayout.logoView.setVisibleBy(logo != null) {
            setImageDrawable(logo)
        }
        return this
    }

    fun setLogoResource(@DrawableRes logo: Int): Errorbar {
        errorbarLayout.logoView.setVisibleBy(logo > 0) {
            setImageResource(logo)
        }
        return this
    }

    fun setText(@StringRes text: Int): Errorbar = setText(errorbarLayout.messageView.resources.getText(text))

    fun setText(text: CharSequence?): Errorbar {
        errorbarLayout.messageView.setVisibleBy(text.isNotNullOrEmpty()) {
            setText(text)
        }
        return this
    }

    fun setAction(@StringRes resId: Int, action: ((View) -> Unit)?): Errorbar = setAction(errorbarLayout.actionView.context.getText(resId), action)

    fun setAction(text: CharSequence?, action: ((View) -> Unit)?): Errorbar {
        errorbarLayout.actionView.setVisibleBy(text.isNotNullOrEmpty() && action != null, {
            setText(text)
            setOnClickListener {
                action!!.invoke(this)
                dispatchDismiss(BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION)
            }
        }, {
            setOnClickListener(null)
        })
        return this
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