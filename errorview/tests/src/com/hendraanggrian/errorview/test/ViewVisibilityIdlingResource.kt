package com.hendraanggrian.errorview.test

import android.support.test.espresso.IdlingResource
import android.view.View

/**
 * https://stackoverflow.com/questions/32995854/wait-until-view-become-visible-with-idleresource
 */
class ViewVisibilityIdlingResource(private val mView: View, private val mExpectedVisibility: Int) : IdlingResource {

    private var mIdle: Boolean = false
    private var mResourceCallback: IdlingResource.ResourceCallback? = null

    init {
        this.mIdle = false
        this.mResourceCallback = null
    }

    override fun getName(): String = ViewVisibilityIdlingResource::class.java.simpleName

    override fun isIdleNow(): Boolean {
        mIdle = mIdle || mView.visibility == mExpectedVisibility

        if (mIdle) {
            if (mResourceCallback != null) {
                mResourceCallback!!.onTransitionToIdle()
            }
        }

        return mIdle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        mResourceCallback = resourceCallback
    }
}