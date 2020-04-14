@file:JvmMultifileClass
@file:JvmName("BackdropKt")
@file:Suppress("SpellCheckingInspection")

package com.hendraanggrian.material.backdrop

/** Interface to invoke [Backdrop.Callback] Kotlin DSL style. */
interface CallbackBuilder {

    fun onShown(callback: (Backdrop) -> Unit)

    fun onDismissed(callback: (Backdrop, event: Int) -> Unit)
}

private class CallbackBuilderImpl : Backdrop.Callback(), CallbackBuilder {
    private var onShown: ((Backdrop) -> Unit)? = null
    private var onDismissed: ((Backdrop, Int) -> Unit)? = null

    override fun onShown(callback: (Backdrop) -> Unit) {
        onShown = callback
    }

    override fun onShown(backdrop: Backdrop) {
        onShown?.invoke(backdrop)
    }

    override fun onDismissed(callback: (Backdrop, event: Int) -> Unit) {
        onDismissed = callback
    }

    override fun onDismissed(backdrop: Backdrop, event: Int) {
        onDismissed?.invoke(backdrop, event)
    }
}

/**
 * Add callback Kotlin DSL style.
 *
 * @param callback callback to add.
 */
fun Backdrop.addCallback(callback: CallbackBuilder.() -> Unit): Backdrop =
    addCallback(CallbackBuilderImpl().apply(callback))
