package android.support.design.widget

import android.support.annotation.StringRes
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.hendraanggrian.errorbar.BaseErrorbar
import com.hendraanggrian.errorbar.R
import com.hendraanggrian.kota.content.getColor
import com.hendraanggrian.kota.view.setVisibleBy

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 * @see Snackbar
 */
class Errorbar private constructor(parent: ViewGroup, content: View, contentViewCallback: ContentViewCallback) :
        BaseTransientBottomBar<Errorbar>(parent, content, contentViewCallback), BaseErrorbar<Errorbar> {

    override val instance = this
    override val rootView = mView as ViewGroup

    override val viewContainer = (mView.getChildAt(0) as ErrorbarLayout).viewContainer
    override val logoView = (mView.getChildAt(0) as ErrorbarLayout).logoView
    override val messageView = (mView.getChildAt(0) as ErrorbarLayout).messageView
    override val actionView = (mView.getChildAt(0) as ErrorbarLayout).actionView

    companion object {
        const val LENGTH_INDEFINITE = BaseTransientBottomBar.LENGTH_INDEFINITE
        const val LENGTH_SHORT = BaseTransientBottomBar.LENGTH_SHORT
        const val LENGTH_LONG = BaseTransientBottomBar.LENGTH_LONG

        fun make(view: View, text: CharSequence, @Duration duration: Int): Errorbar {
            val parent = findSuitableParent(view) ?: throw IllegalStateException("Unable to find suitable parent!")
            val context = parent.context
            val content = LayoutInflater.from(context).inflate(R.layout.design_layout_errorbar_include, parent, false) as ErrorbarLayout
            val errorbar = Errorbar(parent, content, content)
            errorbar.setText(text)
            errorbar.duration = duration
            // hack Snackbar's view container
            errorbar.mView.setBackgroundColor(context.theme.getColor(android.R.attr.windowBackground, true))
            errorbar.mView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            return errorbar
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

    open class Callback : BaseCallback<Errorbar>() {
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

    override fun setAction(text: CharSequence?, listener: View.OnClickListener?): Errorbar {
        if (actionView.setVisibleBy(!TextUtils.isEmpty(text) && listener != null)) {
            actionView.text = text
            actionView.setOnClickListener {
                listener!!.onClick(actionView)
                dispatchDismiss(BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION)
            }
        } else {
            actionView.setOnClickListener(null)
        }
        return instance
    }
}