@file:JvmMultifileClass
@file:JvmName("ErrorbarKt")
@file:Suppress("SpellCheckingInspection")

package com.hendraanggrian.material.errorbar

/** Interface to invoke [Errorbar.Callback] Kotlin DSL style. */
interface CallbackBuilder {

    fun onShown(callback: (Errorbar) -> Unit)

    fun onDismissed(callback: (Errorbar, event: Int) -> Unit)
}

internal class CallbackBuilderImpl : Errorbar.Callback(), CallbackBuilder {
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

/**
 * Add callback Kotlin DSL style.
 *
 * @param builder callback to add.
 */
fun Errorbar.addCallback(builder: CallbackBuilder.() -> Unit): Errorbar =
    addCallback(CallbackBuilderImpl().apply(builder))
