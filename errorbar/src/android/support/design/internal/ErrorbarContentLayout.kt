@file:JvmName("ErrorbarContentLayout")

package android.support.design.internal

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.Px
import android.support.annotation.StyleRes
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
import com.hendraanggrian.kota.widget.setTextAppearanceBy

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see SnackbarContentLayout
 */
class ErrorbarContentLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        @AttrRes defStyleAttr: Int = R.attr.errorbarStyle,
        @StyleRes defStyleRes: Int = R.style.Widget_Design_Errorbar) :
        FrameLayout(context, attrs, defStyleAttr, defStyleRes), BaseTransientBottomBar.ContentViewCallback {

    lateinit var backdropView: ImageView
    lateinit var containerView: ViewGroup
    lateinit var logoView: ImageView
    lateinit var messageView: TextView
    lateinit var actionView: Button

    // keep TypedArray a little bit longer because views are binded in onFinishInflate()
    @SuppressLint("Recycle", "CustomViewStyleable")
    private val a = context.obtainStyledAttributes(attrs, R.styleable.ErrorbarLayout, defStyleAttr, defStyleRes)
    @Px private val mMaxWidth = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_android_maxWidth, -1)
    @Px private val mMaxInlineActionWidth = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_maxActionInlineWidth, -1)

    override fun onFinishInflate() {
        super.onFinishInflate()
        // bind views
        backdropView = findViewById(R.id.errorbar_backdrop)
        containerView = findViewById(R.id.errorbar_container)
        logoView = findViewById(R.id.errorbar_logo)
        messageView = findViewById(R.id.errorbar_text)
        actionView = findViewById(R.id.errorbar_action)
        // apply attr and finally recycle
        if (a.hasValue(R.styleable.ErrorbarLayout_backdrop)) {
            backdropView.visibility = View.VISIBLE
            backdropView.setImageDrawable(a.getDrawable(R.styleable.ErrorbarLayout_backdrop))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_logo)) {
            logoView.visibility = View.VISIBLE
            logoView.setImageDrawable(a.getDrawable(R.styleable.ErrorbarLayout_logo))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textAppearance)) {
            messageView.setTextAppearanceBy(context, a.getResourceId(R.styleable.ErrorbarLayout_android_textAppearance, 0))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textColor)) {
            messageView.setTextColor(a.getColorStateList(R.styleable.ErrorbarLayout_android_textColor))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_android_textSize)) {
            messageView.textSize = a.getDimension(R.styleable.ErrorbarLayout_android_textSize, 0f)
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextAppearance)) {
            actionView.setTextAppearanceBy(context, a.getResourceId(R.styleable.ErrorbarLayout_actionTextAppearance, 0))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextColor)) {
            actionView.setTextColor(a.getColorStateList(R.styleable.ErrorbarLayout_actionTextColor))
        }
        if (a.hasValue(R.styleable.ErrorbarLayout_actionTextSize)) {
            actionView.textSize = a.getDimension(R.styleable.ErrorbarLayout_actionTextSize, 0f)
        }
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var _widthMeasureSpec = widthMeasureSpec
        super.onMeasure(_widthMeasureSpec, heightMeasureSpec)
        if (mMaxWidth in 1..(measuredWidth - 1)) {
            _widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mMaxWidth, View.MeasureSpec.EXACTLY)
            super.onMeasure(_widthMeasureSpec, heightMeasureSpec)
        }
        val multiLineVPadding = resources.getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines)
        val singleLineVPadding = resources.getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical)
        val isMultiLine = messageView.layout.lineCount > 1
        var remeasure = false
        if (isMultiLine && mMaxInlineActionWidth > 0 && actionView.measuredWidth > mMaxInlineActionWidth) {
            if (updateViewsWithinLayout(multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
                remeasure = true
            }
        } else {
            val messagePadding = if (isMultiLine) multiLineVPadding else singleLineVPadding
            if (updateViewsWithinLayout(messagePadding, messagePadding)) {
                remeasure = true
            }
        }
        if (remeasure) {
            super.onMeasure(_widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun updateViewsWithinLayout(messagePadTop: Int, messagePadBottom: Int): Boolean {
        var changed = false
        if (messageView.paddingTop != messagePadTop || messageView.paddingBottom != messagePadBottom) {
            updateTopBottomPadding(messageView, messagePadTop, messagePadBottom)
            changed = true
        }
        return changed
    }

    private fun updateTopBottomPadding(view: View, topPadding: Int, bottomPadding: Int) = if (ViewCompat.isPaddingRelative(view)) {
        ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), topPadding, ViewCompat.getPaddingEnd(view), bottomPadding)
    } else {
        view.setPadding(view.paddingLeft, topPadding, view.paddingRight, bottomPadding)
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

    private fun View.animateBy(delay: Int, duration: Int, animateIn: Boolean, condition: Boolean = visibility == View.VISIBLE) {
        if (condition) {
            alpha = if (animateIn) 0.0f else 1.0f
            animate().alpha(if (animateIn) 1.0f else 0.0f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
        }
    }
}