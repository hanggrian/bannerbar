package android.support.design.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.AttrRes
import android.support.design.internal.SnackbarContentLayout
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.hendraanggrian.errorbar.BaseErrorbar
import com.hendraanggrian.errorbar.R
import com.hendraanggrian.kota.view.setVisibleBy

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see SnackbarContentLayout
 */
class ErrorbarLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = R.attr.errorbarStyle) :
        FrameLayout(context, attrs), BaseTransientBottomBar.ContentViewCallback, BaseErrorbar<ErrorbarLayout> {

    override val instance = this
    override val rootView = this

    override lateinit var viewContainer: ViewGroup
    override lateinit var logoView: ImageView
    override lateinit var messageView: TextView
    override lateinit var actionView: Button

    private val mErrorBackdrop: Drawable?
    private val mErrorLogo: Drawable?
    private val mErrorText: CharSequence?
    private val mErrorTextAppearance: Int
    private val mContentMarginLeft: Int
    private val mContentMarginTop: Int
    private val mContentMarginRight: Int
    private val mContentMarginBottom: Int
    private val mMaxWidth: Int
    private val mMaxInlineActionWidth: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ErrorbarLayout, defStyleAttr, 0)
        // collect attrs
        mErrorBackdrop = a.getDrawable(R.styleable.ErrorbarLayout_errorBackdrop)
        mErrorLogo = a.getDrawable(R.styleable.ErrorbarLayout_errorLogo)
        mErrorText = a.getText(R.styleable.ErrorbarLayout_errorText)
        mErrorTextAppearance = a.getResourceId(R.styleable.ErrorbarLayout_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium)
        mContentMarginLeft = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_contentMarginLeft, 0)
        mContentMarginTop = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_contentMarginTop, 0)
        mContentMarginRight = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_contentMarginRight, 0)
        mContentMarginBottom = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_contentMarginBottom, 0)
        // from Snackbar
        mMaxWidth = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_android_maxWidth, -1)
        mMaxInlineActionWidth = a.getDimensionPixelSize(R.styleable.ErrorbarLayout_maxActionInlineWidth, -1)
        a.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // bind views
        viewContainer = findViewById(R.id.errorbar_container) as ViewGroup
        logoView = findViewById(R.id.errorbar_logo) as ImageView
        messageView = findViewById(R.id.errorbar_text) as TextView
        actionView = findViewById(R.id.errorbar_action) as Button
        // apply attrs
        // setBackdropDrawable(mErrorBackdrop)
        setLogoDrawable(mErrorLogo)
        setText(mErrorText)
        if (Build.VERSION.SDK_INT >= 23) {
            messageView.setTextAppearance(mErrorTextAppearance)
        } else {
            @Suppress("deprecation")
            messageView.setTextAppearance(context, mErrorTextAppearance)
        }
        if (mContentMarginLeft != 0 || mContentMarginTop != 0 || mContentMarginRight != 0 || mContentMarginBottom != 0) {
            setContentMargin(mContentMarginLeft, mContentMarginTop, mContentMarginRight, mContentMarginBottom)
        }
    }

    override fun setAction(text: CharSequence?, listener: OnClickListener?): ErrorbarLayout {
        if (actionView.setVisibleBy(!TextUtils.isEmpty(text) && listener != null)) {
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
        /* if (orientation != getOrientation()) {
            setOrientation(orientation)
            changed = true
        } */
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