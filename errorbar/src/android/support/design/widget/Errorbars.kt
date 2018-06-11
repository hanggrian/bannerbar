@file:Suppress("NOTHING_TO_INLINE")

package android.support.design.widget

import android.view.View

/** Show a short [Errorbar] while also invoking its builder. */
inline fun View.errorbar(builder: ErrorbarContent<Errorbar>.() -> Unit) =
    Errorbar.make(this, Errorbar.LENGTH_SHORT).apply {
        builder()
        show()
    }

/** Show a long [Errorbar] while also invoking its builder. */
inline fun View.longErrorbar(builder: ErrorbarContent<Errorbar>.() -> Unit) =
    Errorbar.make(this, Errorbar.LENGTH_LONG).apply {
        builder()
        show()
    }

/** Show an indefinite [Errorbar] while also invoking its builder. */
inline fun View.indefiniteErrorbar(builder: ErrorbarContent<Errorbar>.() -> Unit) =
    Errorbar.make(this, Errorbar.LENGTH_INDEFINITE).apply {
        builder()
        show()
    }