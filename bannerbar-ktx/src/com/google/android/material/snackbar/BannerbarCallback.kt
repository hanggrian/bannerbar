@file:JvmMultifileClass
@file:JvmName("BannerbarKt")

package com.google.android.material.snackbar

import androidx.annotation.NonNull

/**
 * Add callback Kotlin DSL style.
 *
 * @param callback callback to add.
 */
fun Bannerbar.addCallback(@NonNull callback: CallbackBuilder.() -> Unit): Bannerbar =
    addCallback(CallbackBuilderImpl().apply(callback))

/** Interface to invoke [Bannerbar.Callback] Kotlin DSL style. */
interface CallbackBuilder {

    /**
     * Called when a [Bannerbar] is shown.
     *
     * @param action to be invoked.
     */
    fun onShown(action: (Bannerbar) -> Unit)

    /**
     * Called when a [Bannerbar] is dismissed.
     *
     * @param action to be invoked.
     */
    fun onDismissed(action: (Bannerbar, event: Int) -> Unit)
}

internal class CallbackBuilderImpl : Bannerbar.Callback(), CallbackBuilder {
    private var onShown: ((Bannerbar) -> Unit)? = null
    private var onDismissed: ((Bannerbar, Int) -> Unit)? = null

    override fun onShown(action: (Bannerbar) -> Unit) {
        onShown = action
    }

    override fun onShown(bannerbar: Bannerbar) {
        onShown?.invoke(bannerbar)
    }

    override fun onDismissed(action: (Bannerbar, event: Int) -> Unit) {
        onDismissed = action
    }

    override fun onDismissed(bannerbar: Bannerbar, event: Int) {
        onDismissed?.invoke(bannerbar, event)
    }
}
