package com.hendraanggrian.errorbar.internal

import com.hendraanggrian.errorbar.CallbackBuilder
import com.hendraanggrian.errorbar.Errorbar
import android.view.View

@PublishedApi
@Suppress("ClassName")
internal class _Callback : Errorbar.Callback(), CallbackBuilder {
    private var onShown: ((Errorbar) -> Unit)? = null
    private var onDismissed: ((Errorbar, Int) -> Unit)? = null

    override fun onShown(callback: (Errorbar) -> Unit) {
        onShown = callback
    }

    override fun onShown(errorbar: Errorbar) {
        onShown?.invoke(errorbar)
    }

    override fun onDismissed(callback: (Errorbar, event: Int) -> Unit) {
        onDismissed = callback
    }

    override fun onDismissed(errorbar: Errorbar, event: Int) {
        onDismissed?.invoke(errorbar, event)
    }
}

internal inline operator fun <T : View> T.invoke(block: T.() -> Unit) {
    visibility = View.VISIBLE
    block(this)
}