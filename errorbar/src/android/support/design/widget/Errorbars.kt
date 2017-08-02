@file:JvmName("Errorbars")
@file:Suppress("NOTHING_TO_INLINE")

package android.support.design.widget

import android.view.View

/**
 * Display the Errorbar with the [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun errorbar(view: View, message: Int) = Errorbar
        .make(view, message, Errorbar.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display Errorbar with the [Errorbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun longErrorbar(view: View, message: Int) = Errorbar
        .make(view, message, Errorbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display the Errorbar with the [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun errorbar(view: View, message: String) = Errorbar
        .make(view, message, Errorbar.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display Errorbar with the [Errorbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun longErrorbar(view: View, message: String) = Errorbar
        .make(view, message, Errorbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display the Errorbar with the [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun errorbar(view: View, message: Int, actionText: Int, noinline action: (View) -> Unit) = Errorbar
        .make(view, message, Errorbar.LENGTH_INDEFINITE)
        .apply {
            setAction(actionText, action)
            show()
        }

inline fun errorbar(view: View, message: Int, actionText: String, noinline action: (View) -> Unit) = Errorbar
        .make(view, message, Errorbar.LENGTH_INDEFINITE)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display Errorbar with the [Errorbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun longErrorbar(view: View, message: Int, actionText: Int, noinline action: (View) -> Unit) = Errorbar
        .make(view, message, Errorbar.LENGTH_LONG)
        .apply {
            setAction(actionText, action)
            show()
        }

inline fun longErrorbar(view: View, message: Int, actionText: String, noinline action: (View) -> Unit) = Errorbar
        .make(view, message, Errorbar.LENGTH_LONG)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display the Errorbar with the [Errorbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun errorbar(view: View, message: String, actionText: String, noinline action: (View) -> Unit) = Errorbar
        .make(view, message, Errorbar.LENGTH_INDEFINITE)
        .apply {
            setAction(actionText, action)
            show()
        }

inline fun errorbar(view: View, message: String, actionText: Int, noinline action: (View) -> Unit) = Errorbar
        .make(view, message, Errorbar.LENGTH_INDEFINITE)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display Errorbar with the [Errorbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun longErrorbar(view: View, message: String, actionText: String, noinline action: (View) -> Unit) = Errorbar
        .make(view, message, Errorbar.LENGTH_LONG)
        .apply {
            setAction(actionText, action)
            show()
        }

inline fun longErrorbar(view: View, message: String, actionText: Int, noinline action: (View) -> Unit) = Errorbar
        .make(view, message, Errorbar.LENGTH_LONG)
        .apply {
            setAction(actionText, action)
            show()
        }