package com.hendraanggrian.errorview.test;

import android.support.test.espresso.IdlingResource;
import android.view.View;

/**
 * https://stackoverflow.com/questions/32995854/wait-until-view-become-visible-with-idleresource
 */
public class ViewVisibilityIdlingResource implements IdlingResource {

    private final View mView;
    private final int mExpectedVisibility;

    private boolean mIdle;
    private ResourceCallback mResourceCallback;

    public ViewVisibilityIdlingResource(final View view, final int expectedVisibility) {
        this.mView = view;
        this.mExpectedVisibility = expectedVisibility;
        this.mIdle = false;
        this.mResourceCallback = null;
    }

    @Override
    public final String getName() {
        return ViewVisibilityIdlingResource.class.getSimpleName();
    }

    @Override
    public final boolean isIdleNow() {
        mIdle = mIdle || mView.getVisibility() == mExpectedVisibility;

        if (mIdle) {
            if (mResourceCallback != null) {
                mResourceCallback.onTransitionToIdle();
            }
        }

        return mIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        mResourceCallback = resourceCallback;
    }

}