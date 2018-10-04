@file:Suppress("NOTHING_TO_INLINE", "ClassName")

package com.hendraanggrian.material.errorbar

import android.view.View
import androidx.annotation.StringRes

/** Interface to invoke [Errorbar.Callback] Kotlin DSL style. */
interface CallbackBuilder {

    fun onShown(callback: (Errorbar) -> Unit)

    fun onDismissed(callback: (Errorbar, event: Int) -> Unit)
}

@PublishedApi
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

/** Add callback Kotlin DSL style. */
fun Errorbar.addCallback(builder: CallbackBuilder.() -> Unit): Errorbar =
    addCallback(_Callback().apply(builder))

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
fun View.errorbar(
    text: CharSequence,
    builder: (Errorbar.() -> Unit)? = null
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_SHORT).apply {
    if (builder != null) builder()
    show()
}

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.errorbar(
    @StringRes text: Int,
    noinline builder: (Errorbar.() -> Unit)? = null
): Errorbar = errorbar(resources.getText(text), builder)

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
fun View.longErrorbar(
    text: CharSequence,
    builder: (Errorbar.() -> Unit)? = null
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_LONG).apply {
    if (builder != null) builder()
    show()
}

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.longErrorbar(
    @StringRes text: Int,
    noinline builder: (Errorbar.() -> Unit)? = null
): Errorbar = longErrorbar(resources.getText(text), builder)

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
fun View.indefiniteErrorbar(
    text: CharSequence,
    builder: (Errorbar.() -> Unit)? = null
): Errorbar = Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE).apply {
    if (builder != null) builder()
    show()
}

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.indefiniteErrorbar(
    @StringRes text: Int,
    noinline builder: (Errorbar.() -> Unit)? = null
): Errorbar = indefiniteErrorbar(resources.getText(text), builder)