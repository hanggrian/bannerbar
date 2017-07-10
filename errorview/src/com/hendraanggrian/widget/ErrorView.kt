package com.hendraanggrian.widget

import android.animation.LayoutTransition
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.hendraanggrian.errorview.HttpErrorCode
import com.hendraanggrian.errorview.R
import com.hendraanggrian.support.utils.content.color
import com.hendraanggrian.support.utils.content.colorAttr
import com.hendraanggrian.support.utils.view.containsView
import com.hendraanggrian.support.utils.view.setVisibleBy
import kotlinx.android.synthetic.main.errorview_layout.view.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@Suppress("unused")
class ErrorView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        @AttrRes defStyleAttr: Int = R.attr.errorViewStyle,
        @StyleRes defStyleRes: Int = 0) : FrameLayout(context, attrs) {

    private var targetParent: ViewGroup? = null
    @Delay private var delay: Int = 0
    private var showAction: ((ErrorView) -> Unit)? = null
    private var dismissAction: ((ErrorView, Int) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.errorview_layout, this, true)
        setBackgroundColor(android.R.attr.windowBackground.colorAttr(context))
        isClickable = true

        val a = context.obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, defStyleRes)
        // content
        setBackdropDrawable(a.getDrawable(R.styleable.ErrorView_errorBackdrop))
        setLogoDrawable(a.getDrawable(R.styleable.ErrorView_errorLogo))
        setText(a.getText(R.styleable.ErrorView_errorText))
        setTextAppearance(a.getResourceId(R.styleable.ErrorView_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium))
        // positioning
        val marginLeft = a.getDimensionPixelSize(R.styleable.ErrorView_contentMarginLeft, 0)
        val marginTop = a.getDimensionPixelSize(R.styleable.ErrorView_contentMarginTop, 0)
        val marginRight = a.getDimensionPixelSize(R.styleable.ErrorView_contentMarginRight, 0)
        val marginBottom = a.getDimensionPixelSize(R.styleable.ErrorView_contentMarginBottom, 0)
        if (marginLeft != 0 || marginTop != 0 || marginRight != 0 || marginBottom != 0) {
            setContentMargin(marginLeft, marginTop, marginRight, marginBottom)
        }
        a.recycle()
    }

    fun setBackdropBitmap(backdrop: Bitmap?): ErrorView {
        if (imageViewBackdrop.setVisibleBy(backdrop != null)) {
            imageViewBackdrop.setImageBitmap(backdrop)
        }
        return this
    }

    fun setBackdropUri(backdrop: Uri?): ErrorView {
        if (imageViewBackdrop.setVisibleBy(backdrop != null)) {
            imageViewBackdrop.setImageURI(backdrop)
        }
        return this
    }

    fun setBackdropDrawable(backdrop: Drawable?): ErrorView {
        if (imageViewBackdrop.setVisibleBy(backdrop != null)) {
            imageViewBackdrop.setImageDrawable(backdrop)
        }
        return this
    }

    fun setBackdropDrawable(@DrawableRes backdrop: Int): ErrorView {
        if (imageViewBackdrop.setVisibleBy(backdrop > 0)) {
            imageViewBackdrop.setImageResource(backdrop)
        }
        return this
    }

    fun setBackdropColor(@ColorInt color: Int): ErrorView = setBackdropDrawable(ColorDrawable(color))
    fun setBackdropColorRes(@ColorRes colorRes: Int): ErrorView = setBackdropColor(colorRes.color(context))
    fun setBackdropColorAttr(@AttrRes colorAttr: Int): ErrorView = setBackdropColor(colorAttr.colorAttr(context))

    fun setLogoBitmap(logo: Bitmap?): ErrorView {
        if (imageViewLogo.setVisibleBy(logo != null)) {
            imageViewLogo.setImageBitmap(logo)
        }
        return this
    }

    fun setLogoUri(logo: Uri?): ErrorView {
        if (imageViewLogo.setVisibleBy(logo != null)) {
            imageViewLogo.setImageURI(logo)
        }
        return this
    }

    fun setLogoDrawable(logo: Drawable?): ErrorView {
        if (imageViewLogo.setVisibleBy(logo != null)) {
            imageViewLogo.setImageDrawable(logo)
        }
        return this
    }

    fun setLogoDrawable(@DrawableRes logo: Int): ErrorView {
        if (imageViewLogo.setVisibleBy(logo > 0)) {
            imageViewLogo.setImageResource(logo)
        }
        return this
    }

    fun setText(@StringRes text: Int): ErrorView = setText(resources.getText(text))
    fun setText(text: CharSequence?): ErrorView {
        if (textViewText.setVisibleBy(!TextUtils.isEmpty(text))) {
            textViewText.text = text
        }
        return this
    }

    fun <E : Exception> setText(e: E?): ErrorView {
        if (textViewText.setVisibleBy(e != null)) {
            setText(e!!.message)
        }
        return this
    }

    fun setTextHttpCode(httpCode: Int): ErrorView = setTextHttpCode(HttpErrorCode.valueOf(httpCode))
    fun setTextHttpCode(code: HttpErrorCode?): ErrorView {
        if (textViewText.setVisibleBy(code != null)) {
            textViewText.text = code!!.toString()
        }
        return this
    }

    @Suppress("deprecation")
    fun setTextAppearance(@StyleRes res: Int): ErrorView {
        if (Build.VERSION.SDK_INT >= 23) {
            textViewText.setTextAppearance(res)
        } else {
            textViewText.setTextAppearance(context, res)
        }
        return this
    }

    fun setTextColorRes(@ColorRes colorRes: Int): ErrorView = setTextColor(colorRes.color(context))
    fun setTextColorAttr(@AttrRes colorAttr: Int): ErrorView = setTextColor(colorAttr.colorAttr(context))
    fun setTextColor(@ColorInt color: Int): ErrorView {
        textViewText.setTextColor(color)
        return this
    }

    fun setAction(@StringRes text: Int, action: ((ErrorView) -> Boolean)?): ErrorView = setAction(resources.getText(text), action)
    fun setAction(text: CharSequence?, action: ((ErrorView) -> Boolean)?): ErrorView {
        buttonAction.setOnClickListener(if (action == null) null else View.OnClickListener {
            val shouldDismiss: Boolean? = action.invoke(this@ErrorView)
            checkNotNull(shouldDismiss, { "Set true to internalDismiss on click, false otherwise. Shouldn't be null." })
            if (shouldDismiss!!) {
                internalDismiss(DISMISS_EVENT_ACTION)
            }
        })
        return setActionText(text)
    }

    fun setActionText(@StringRes text: Int): ErrorView = setActionText(resources.getText(text))
    fun setActionText(text: CharSequence?): ErrorView {
        if (buttonAction.setVisibleBy(!TextUtils.isEmpty(text))) {
            buttonAction.text = text
        }
        return this
    }

    @Suppress("deprecation")
    fun setActionTextAppearance(@StyleRes res: Int): ErrorView {
        if (Build.VERSION.SDK_INT >= 23) {
            buttonAction.setTextAppearance(res)
        } else {
            buttonAction.setTextAppearance(context, res)
        }
        return this
    }

    fun setActionTextColorRes(@ColorRes colorRes: Int): ErrorView = setActionTextColor(ContextCompat.getColor(context, colorRes))
    fun setActionTextColorAttr(@AttrRes colorAttr: Int): ErrorView = setActionTextColor(colorAttr.colorAttr(context))
    fun setActionTextColor(@ColorInt color: Int): ErrorView {
        buttonAction.setTextColor(color)
        return this
    }

    fun setContentMargin(left: Int, top: Int, right: Int, bottom: Int): ErrorView {
        (viewGroupContainer.layoutParams as FrameLayout.LayoutParams).setMargins(left, top, right, bottom)
        return this
    }

    fun setContentMarginLeft(left: Int): ErrorView {
        (viewGroupContainer.layoutParams as FrameLayout.LayoutParams).leftMargin = left
        return this
    }

    fun setContentMarginTop(top: Int): ErrorView {
        (viewGroupContainer.layoutParams as FrameLayout.LayoutParams).topMargin = top
        return this
    }

    fun setContentMarginRight(right: Int): ErrorView {
        (viewGroupContainer.layoutParams as FrameLayout.LayoutParams).rightMargin = right
        return this
    }

    fun setContentMarginBottom(bottom: Int): ErrorView {
        (viewGroupContainer.layoutParams as FrameLayout.LayoutParams).bottomMargin = bottom
        return this
    }

    fun setOnShowListener(action: (ErrorView) -> Unit): ErrorView {
        showAction = action
        return this
    }

    fun setOnDismissListener(action: ((ErrorView, Int) -> Unit)?): ErrorView {
        dismissAction = action
        return this
    }

    fun show(): ErrorView {
        checkNotNull(targetParent, { "ErrorView is not created using make()!" })
        dismissAll(targetParent!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val elevation = getHighestElevation(targetParent!!)
            if (elevation > 0) {
                setElevation(elevation)
            }
        }
        targetParent!!.addView(this)
        showAction?.invoke(this)
        if (delay > 0) {
            HANDLER.postDelayed(Runnable {
                if (context is Activity) {
                    if ((context as Activity).isFinishing) {
                        return@Runnable
                    } else if (Build.VERSION.SDK_INT >= 17 && (context as Activity).isDestroyed) {
                        return@Runnable
                    }
                }
                if (targetParent!!.containsView(this@ErrorView)) {
                    internalDismiss(DISMISS_EVENT_TIMEOUT)
                }
            }, delay.toLong())
        }
        return this
    }

    fun dismiss() = internalDismiss(DISMISS_EVENT_MANUAL)
    private fun internalDismiss(dismissEvent: Int) {
        checkNotNull(targetParent, { "ErrorView is not created using make()!" })
        targetParent!!.removeView(this)
        dismissAction?.invoke(this, dismissEvent)
    }

    companion object {
        private val TAG = "com.hendraanggrian.widget.ErrorView"
        private val HANDLER: Handler = Handler(Looper.getMainLooper())

        const private val DELAY_LONG = 3500
        const private val DELAY_SHORT = 2000

        @IntDef(DELAY_LONG.toLong(),
                DELAY_SHORT.toLong())
        @Retention(AnnotationRetention.SOURCE)
        private annotation class Delay

        const val LENGTH_INDEFINITE = -1 // persistent
        const val LENGTH_LONG = -2       // 3.5 seconds
        const val LENGTH_SHORT = -3      // 2.0 seconds

        @IntDef(LENGTH_INDEFINITE.toLong(),
                LENGTH_LONG.toLong(),
                LENGTH_SHORT.toLong())
        @Retention(AnnotationRetention.SOURCE)
        annotation class Duration

        const val DISMISS_EVENT_ACTION = 1  // dismissed due to action button click
        const val DISMISS_EVENT_TIMEOUT = 2 // dismissed due to timeout
        const val DISMISS_EVENT_MANUAL = 3  // dismissed due to programmatically calling dismiss()
        const val DISMISS_EVENT_FORCED = 4  // dismissed due to programmatically calling dismissAll()

        @IntDef(DISMISS_EVENT_ACTION.toLong(),
                DISMISS_EVENT_TIMEOUT.toLong(),
                DISMISS_EVENT_MANUAL.toLong(),
                DISMISS_EVENT_FORCED.toLong())
        @Retention(AnnotationRetention.SOURCE)
        annotation class DismissEvent

        fun dismissAll(parent: ViewGroup) {
            var child: View? = parent.findViewWithTag(TAG)
            while (child != null && child is ErrorView) {
                child.internalDismiss(DISMISS_EVENT_FORCED)
                child = parent.findViewWithTag(TAG)
            }
        }

        @TargetApi(21)
        @RequiresApi(21)
        private fun getHighestElevation(parent: ViewGroup): Float = (0..parent.childCount - 1)
                .map { parent.getChildAt(it).elevation }
                .max()
                ?: 0.0f

        fun make(parent: RelativeLayout, @StringRes text: Int, @Duration duration: Int): ErrorView = make(parent, parent.resources.getString(text), duration)
        fun make(parent: RelativeLayout, code: HttpErrorCode, @Duration duration: Int): ErrorView = make(parent, code.toString(), duration)
        fun make(parent: RelativeLayout, text: CharSequence, @Duration duration: Int): ErrorView = makeInternal(parent, text, duration).apply {
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        fun make(parent: FrameLayout, @StringRes text: Int, @Duration duration: Int): ErrorView = make(parent, parent.resources.getString(text), duration)
        fun make(parent: FrameLayout, code: HttpErrorCode, @Duration duration: Int): ErrorView = make(parent, code.toString(), duration)
        fun make(parent: FrameLayout, text: CharSequence, @Duration duration: Int): ErrorView = makeInternal(parent, text, duration).apply {
            layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        private fun makeInternal(parent: ViewGroup, text: CharSequence, @Duration duration: Int): ErrorView {
            if (Build.VERSION.SDK_INT >= 11 && parent.layoutTransition == null) {
                parent.layoutTransition = LayoutTransition()
            }
            return ErrorView(parent.context).apply {
                targetParent = parent
                setText(text)
                when (duration) {
                    LENGTH_SHORT -> delay = DELAY_SHORT
                    LENGTH_LONG -> delay = DELAY_LONG
                }
                tag = TAG
            }
        }
    }
}

fun RelativeLayout.errorView(@StringRes text: Int, @ErrorView.Companion.Duration duration: Int): ErrorView = ErrorView.make(this, text, duration)
fun RelativeLayout.errorView(code: HttpErrorCode, @ErrorView.Companion.Duration duration: Int): ErrorView = ErrorView.make(this, code, duration)
fun RelativeLayout.errorView(text: CharSequence, @ErrorView.Companion.Duration duration: Int): ErrorView = ErrorView.make(this, text, duration)

fun FrameLayout.errorView(@StringRes text: Int, @ErrorView.Companion.Duration duration: Int): ErrorView = ErrorView.make(this, text, duration)
fun FrameLayout.errorView(code: HttpErrorCode, @ErrorView.Companion.Duration duration: Int): ErrorView = ErrorView.make(this, code, duration)
fun FrameLayout.errorView(text: CharSequence, @ErrorView.Companion.Duration duration: Int): ErrorView = ErrorView.make(this, text, duration)