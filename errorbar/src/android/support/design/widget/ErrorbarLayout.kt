package android.support.design.widget

import android.content.Context
import android.support.design.internal.SnackbarContentLayout
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.hendraanggrian.errorbar.BaseErrorbar
import com.hendraanggrian.errorbar.R
import com.hendraanggrian.kota.view.setVisibleBy

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see SnackbarContentLayout
 */
class ErrorbarLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
        FrameLayout(context, attrs), BaseTransientBottomBar.ContentViewCallback, BaseErrorbar<ErrorbarLayout> {

    override val instance = this
    override lateinit var backdropView: ImageView
    override lateinit var containerView: ViewGroup
    override lateinit var logoView: ImageView
    override lateinit var messageView: TextView
    override lateinit var actionView: Button

    private val mMaxWidth: Int
    private val mMaxInlineActionWidth: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ErrorbarLayout)
        mMaxWidth = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_android_maxWidth, -1)
        mMaxInlineActionWidth = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_maxActionInlineWidth, -1)
        a.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // bind views
        backdropView = findViewById(R.id.errorbar_backdrop)
        containerView = findViewById(R.id.errorbar_container)
        logoView = findViewById(R.id.errorbar_logo)
        messageView = findViewById(R.id.errorbar_text)
        actionView = findViewById(R.id.errorbar_action)
    }

    override fun setAction(text: CharSequence?, listener: OnClickListener?): ErrorbarLayout {
        if (actionView.setVisibleBy(!TextUtils.isEmpty(text) && listener != null)) {
            actionView.text = text
            actionView.setOnClickListener {
                listener!!.onClick(actionView)
                // Errorbar created with xml will not dismiss on click
            }
        } else {
            actionView.setOnClickListener(null)
        }
        return instance
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mMaxWidth in 1..(measuredWidth - 1)) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mMaxWidth, View.MeasureSpec.EXACTLY)
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
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
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
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