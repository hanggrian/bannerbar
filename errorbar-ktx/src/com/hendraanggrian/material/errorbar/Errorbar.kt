@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.material.errorbar

import android.view.View
import androidx.annotation.StringRes

/** Interface to invoke [Errorbar.Callback] Kotlin DSL style. */
interface CallbackBuilder {

    fun onShown(callback: (Errorbar) -> Unit)

    fun onDismissed(callback: (Errorbar, event: Int) -> Unit)
}

@PublishedApi
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

/** Add callback Kotlin DSL style. */
inline fun Errorbar.addCallback(builder: CallbackBuilder.() -> Unit): Errorbar =
    addCallback(CallbackBuilderImpl().apply(builder))

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text to show. Can be formatted text.
 */
inline fun View.errorbar(text: CharSequence): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text to show. Can be formatted text.
 */
inline fun View.errorbar(@StringRes text: Int): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text to show. Can be formatted text.
 */
inline fun View.longErrorbar(text: CharSequence): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text to show. Can be formatted text.
 */
inline fun View.longErrorbar(@StringRes text: Int): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text to show. Can be formatted text.
 */
inline fun View.indefiniteErrorbar(text: CharSequence): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text to show. Can be formatted text.
 */
inline fun View.indefiniteErrorbar(@StringRes text: Int): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.errorbar(text: CharSequence, builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_SHORT)
        .apply {
            builder()
            show()
        }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.errorbar(@StringRes text: Int, builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_SHORT)
        .apply {
            builder()
            show()
        }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.longErrorbar(text: CharSequence, builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_LONG)
        .apply {
            builder()
            show()
        }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.longErrorbar(@StringRes text: Int, builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_LONG)
        .apply {
            builder()
            show()
        }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.indefiniteErrorbar(text: CharSequence, builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE)
        .apply {
            builder()
            show()
        }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param text the text to show. Can be formatted text.
 * @param builder Kotlin DSL builder
 */
inline fun View.indefiniteErrorbar(@StringRes text: Int, builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, text, Errorbar.LENGTH_INDEFINITE)
        .apply {
            builder()
            show()
        }
