@file:JvmMultifileClass
@file:JvmName("BannerbarKt")
@file:Suppress("SpellCheckingInspection")

package com.hendraanggrian.material.bannerbar

/** Interface to invoke [Bannerbar.Callback] Kotlin DSL style. */
interface CallbackBuilder {

    fun onShown(callback: (Bannerbar) -> Unit)

    fun onDismissed(callback: (Bannerbar, event: Int) -> Unit)
}

private class CallbackBuilderImpl : Bannerbar.Callback(), CallbackBuilder {
    private var onShown: ((Bannerbar) -> Unit)? = null
    private var onDismissed: ((Bannerbar, Int) -> Unit)? = null

    override fun onShown(callback: (Bannerbar) -> Unit) {
        onShown = callback
    }

    override fun onShown(bannerbar: Bannerbar) {
        onShown?.invoke(bannerbar)
    }

    override fun onDismissed(callback: (Bannerbar, event: Int) -> Unit) {
        onDismissed = callback
    }

    override fun onDismissed(bannerbar: Bannerbar, event: Int) {
        onDismissed?.invoke(bannerbar, event)
    }
}

/**
 * Add callback Kotlin DSL style.
 *
 * @param callback callback to add.
 */
fun Bannerbar.addCallback(callback: CallbackBuilder.() -> Unit): Bannerbar =
    addCallback(CallbackBuilderImpl().apply(callback))
