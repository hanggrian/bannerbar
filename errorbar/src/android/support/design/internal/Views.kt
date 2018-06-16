package android.support.design.internal

import android.view.View

/**
 * Make a view visible while also assigning [block] to it.
 */
internal inline operator fun <T : View> T.invoke(block: T.() -> Unit) {
    visibility = View.VISIBLE
    block(this)
}