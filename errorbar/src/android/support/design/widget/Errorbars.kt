@file:Suppress("NOTHING_TO_INLINE")

package android.support.design.widget

import android.view.View

/**
 * Display [Errorbar] with [Errorbar.LENGTH_SHORT] duration.
 *
 * @param builder Kotlin DSL builder
 */
inline fun View.errorbar(builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, Errorbar.LENGTH_SHORT).apply {
        builder()
        show()
    }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_LONG] duration.
 *
 * @param builder Kotlin DSL builder
 */
inline fun View.longErrorbar(builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, Errorbar.LENGTH_LONG).apply {
        builder()
        show()
    }

/**
 * Display [Errorbar] with [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param builder Kotlin DSL builder
 */
inline fun View.indefiniteErrorbar(builder: Errorbar.() -> Unit): Errorbar =
    Errorbar.make(this, Errorbar.LENGTH_INDEFINITE).apply {
        builder()
        show()
    }