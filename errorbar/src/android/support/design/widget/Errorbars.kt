@file:Suppress("NOTHING_TO_INLINE")

package android.support.design.widget

import android.view.View

/**
 * Display the [Errorbar] with the [Errorbar.LENGTH_SHORT] duration.
 *
 * @param content Kotlin DSL builder
 */
inline fun View.errorbar(content: ErrorbarContent<Unit>.() -> Unit): Errorbar =
    Errorbar.make(this, Errorbar.LENGTH_SHORT).apply {
        layout.content()
        show()
    }

/**
 * Display the [Errorbar] with the [Errorbar.LENGTH_LONG] duration.
 *
 * @param content Kotlin DSL builder
 */
inline fun View.longErrorbar(content: ErrorbarContent<Unit>.() -> Unit): Errorbar =
    Errorbar.make(this, Errorbar.LENGTH_LONG).apply {
        layout.content()
        show()
    }

/**
 * Display the [Errorbar] with the [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param content Kotlin DSL builder
 */
inline fun View.indefiniteErrorbar(content: ErrorbarContent<Unit>.() -> Unit): Errorbar =
    Errorbar.make(this, Errorbar.LENGTH_INDEFINITE).apply {
        layout.content()
        show()
    }