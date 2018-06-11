@file:Suppress("NOTHING_TO_INLINE")

package android.support.design.widget

import android.view.View

inline fun View.errorbar(builder: Errorbar.() -> Unit) =
    Errorbar.make(this, Errorbar.LENGTH_SHORT).apply {
        builder()
        show()
    }

inline fun View.longErrorbar(builder: Errorbar.() -> Unit) =
    Errorbar.make(this, Errorbar.LENGTH_SHORT).apply {
        builder()
        show()
    }

inline fun View.indefiniteErrorbar(builder: Errorbar.() -> Unit) =
    Errorbar.make(this, Errorbar.LENGTH_SHORT).apply {
        builder()
        show()
    }