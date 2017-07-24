package android.support.design.widget

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.annotation.StringRes
import android.support.design.internal.ErrorbarContentLayout
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.hendraanggrian.errorview.R
import com.hendraanggrian.kota.content.getColor

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see Snackbar
 */
class Errorbar private constructor(parent: ViewGroup, content: View, contentViewCallback: ContentViewCallback) :
        BaseTransientBottomBar<Errorbar>(parent, content, contentViewCallback) {

    companion object {
        const val LENGTH_INDEFINITE = BaseTransientBottomBar.LENGTH_INDEFINITE
        const val LENGTH_SHORT = BaseTransientBottomBar.LENGTH_SHORT
        const val LENGTH_LONG = BaseTransientBottomBar.LENGTH_LONG

        fun make(view: View, text: CharSequence, @Duration duration: Int): Errorbar {
            val parent = findSuitableParent(view) ?: throw IllegalStateException("Unable to find suitable parent!")
            val context = parent.context
            val content = LayoutInflater.from(context).inflate(R.layout.design_layout_errorbar_include, parent, false) as ErrorbarContentLayout
            val errorView = Errorbar(parent, content, content)
            errorView.setText(text)
            errorView.duration = duration
            // hack Snackbar's view container
            errorView.mView.setBackgroundColor(context.theme.getColor(android.R.attr.windowBackground, true))
            errorView.mView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return errorView
        }

        fun make(view: View, @StringRes resId: Int, @Duration duration: Int): Errorbar = make(view, view.resources.getText(resId), duration)

        private fun findSuitableParent(view: View?): ViewGroup? {
            var view = view
            do {
                if (view is ViewGroup && view !is ScrollView && view !is NestedScrollView) {
                    return view
                }
                if (view != null) {
                    // Else, we will loop and crawl up the view hierarchy and try to find a parent
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)
            return null
        }
    }

    class Callback : BaseCallback<Errorbar>() {
        override fun onShown(v: Errorbar) {}
        override fun onDismissed(v: Errorbar, @DismissEvent event: Int) {}

        companion object {
            val DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE
            val DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION
            val DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT
            val DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL
            val DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE
        }
    }

    private var mCallback: BaseCallback<Errorbar>? = null

    fun setText(message: CharSequence): Errorbar {
        val contentLayout = mView.getChildAt(0) as ErrorbarContentLayout
        val tv = contentLayout.messageView
        tv.text = message
        return this
    }

    fun setText(@StringRes resId: Int): Errorbar = setText(context.getText(resId))

    fun setAction(@StringRes resId: Int, listener: View.OnClickListener): Errorbar = setAction(context.getText(resId), listener)

    fun setAction(text: CharSequence, listener: View.OnClickListener?): Errorbar {
        val contentLayout = mView.getChildAt(0) as ErrorbarContentLayout
        val tv = contentLayout.actionView
        if (TextUtils.isEmpty(text) || listener == null) {
            tv.visibility = View.GONE
            tv.setOnClickListener(null)
        } else {
            tv.visibility = View.VISIBLE
            tv.text = text
            tv.setOnClickListener { view ->
                listener.onClick(view)
                dispatchDismiss(BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION)
            }
        }
        return this
    }

    fun setActionTextColor(colors: ColorStateList): Errorbar {
        val contentLayout = mView.getChildAt(0) as ErrorbarContentLayout
        val tv = contentLayout.actionView
        tv.setTextColor(colors)
        return this
    }

    fun setActionTextColor(@ColorInt color: Int): Errorbar {
        val contentLayout = mView.getChildAt(0) as ErrorbarContentLayout
        val tv = contentLayout.actionView
        tv.setTextColor(color)
        return this
    }
}