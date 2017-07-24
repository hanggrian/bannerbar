package android.support.design.widget

import android.content.Context
import android.os.Build
import android.support.annotation.AttrRes
import android.support.design.internal.SnackbarContentLayout
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hendraanggrian.errorbar.BaseErrorbar
import com.hendraanggrian.errorbar.R
import com.hendraanggrian.kota.view.setVisibleBy

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see SnackbarContentLayout
 */
class ErrorbarLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = R.attr.errorbarStyle) :
        LinearLayout(context, attrs), BaseTransientBottomBar.ContentViewCallback, BaseErrorbar<ErrorbarLayout> {

    override val instance = this
    override lateinit var backdropView: ImageView
    override lateinit var viewContainer: ViewGroup
    override lateinit var logoView: ImageView
    override lateinit var messageView: TextView
    override lateinit var actionView: Button

    private val mMaxWidth: Int
    private val mMaxInlineActionWidth: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ErrorbarLayout, defStyleAttr, 0)
        // content
        setBackdropDrawable(a.getDrawable(R.styleable.ErrorbarLayout_errorBackdrop))
        setLogoDrawable(a.getDrawable(R.styleable.ErrorbarLayout_errorLogo))
        setText(a.getText(R.styleable.ErrorbarLayout_errorText))
        a.getResourceId(R.styleable.ErrorbarLayout_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium).let {
            if (Build.VERSION.SDK_INT >= 23) {
                messageView.setTextAppearance(it)
            } else {
                @Suppress("deprecation")
                messageView.setTextAppearance(context, it)
            }
        }
        // positioning
        val marginLeft = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_contentMarginLeft, 0)
        val marginTop = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_contentMarginTop, 0)
        val marginRight = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_contentMarginRight, 0)
        val marginBottom = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_contentMarginBottom, 0)
        if (marginLeft != 0 || marginTop != 0 || marginRight != 0 || marginBottom != 0) {
            setContentMargin(marginLeft, marginTop, marginRight, marginBottom)
        }
        // from Snackbar
        mMaxWidth = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_android_maxWidth, -1)
        mMaxInlineActionWidth = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_maxActionInlineWidth, -1)
        a.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        backdropView = findViewById(R.id.errorbar_backdrop) as ImageView
        viewContainer = findViewById(R.id.errorbar_container) as ViewGroup
        logoView = findViewById(R.id.errorbar_logo) as ImageView
        messageView = findViewById(R.id.errorbar_text) as TextView
        actionView = findViewById(R.id.errorbar_action) as Button
    }

    override fun setAction(text: CharSequence?, listener: OnClickListener?): ErrorbarLayout {
        if (actionView.setVisibleBy(!TextUtils.isEmpty(text) && listener == null)) {
            actionView.text = text
            actionView.setOnClickListener {
                listener!!.onClick(actionView)
                // dispatchDismiss(BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION)
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
            if (updateViewsWithinLayout(LinearLayout.VERTICAL, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
                remeasure = true
            }
        } else {
            val messagePadding = if (isMultiLine) multiLineVPadding else singleLineVPadding
            if (updateViewsWithinLayout(LinearLayout.HORIZONTAL, messagePadding, messagePadding)) {
                remeasure = true
            }
        }
        if (remeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun updateViewsWithinLayout(orientation: Int, messagePadTop: Int, messagePadBottom: Int): Boolean {
        var changed = false
        if (orientation != getOrientation()) {
            setOrientation(orientation)
            changed = true
        }
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
        ViewCompat.setAlpha(messageView, 0f)
        ViewCompat.animate(messageView).alpha(1f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
        if (actionView.visibility == View.VISIBLE) {
            ViewCompat.setAlpha(actionView, 0f)
            ViewCompat.animate(actionView).alpha(1f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
        }
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        ViewCompat.setAlpha(messageView, 1f)
        ViewCompat.animate(messageView).alpha(0f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
        if (actionView.visibility == View.VISIBLE) {
            ViewCompat.setAlpha(actionView, 1f)
            ViewCompat.animate(actionView).alpha(0f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
        }
    }
}