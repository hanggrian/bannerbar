@file:Suppress("NOTHING_TO_INLINE")

package android.support.design.widget

import android.support.annotation.StringRes
import android.view.View

inline fun View.errorbar(@StringRes message: Int) =
    Errorbar.make(this, message, Errorbar.LENGTH_SHORT).apply { show() }

inline fun View.longErrorbar(@StringRes message: Int) =
    Errorbar.make(this, message, Errorbar.LENGTH_LONG).apply { show() }

inline fun View.indefiniteErrorbar(@StringRes message: Int) =
    Errorbar.make(this, message, Errorbar.LENGTH_INDEFINITE).apply { show() }

inline fun View.errorbar(message: String) =
    Errorbar.make(this, message, Errorbar.LENGTH_SHORT).apply { show() }

inline fun View.longErrorbar(message: String) =
    Errorbar.make(this, message, Errorbar.LENGTH_LONG).apply { show() }

inline fun View.indefiniteErrorbar(message: String) =
    Errorbar.make(this, message, Errorbar.LENGTH_INDEFINITE).apply { show() }