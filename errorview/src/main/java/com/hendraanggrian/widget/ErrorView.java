package com.hendraanggrian.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hendraanggrian.errorview.R;
import com.hendraanggrian.errorview.State;
import com.hendraanggrian.errorview.VisibilityUtils;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ErrorView extends FrameLayout {

    @NonNull private final ImageView imageViewBackground;
    @NonNull private final ImageView imageViewSrc;
    @NonNull private final TextView textView;
    @NonNull private final Button button;

    @DrawableRes int errorBackground;
    @DrawableRes int errorSrc;
    @Nullable String errorText;
    @Nullable String errorButtonText;
    @Nullable OnClickListener errorListener;

    @DrawableRes int emptyBackground;
    @DrawableRes int emptySrc;
    @Nullable String emptyText;
    @Nullable String emptyButtonText;
    @Nullable OnClickListener emptyListener;

    @Nullable RecyclerView recyclerView;
    @Nullable RecyclerView.AdapterDataObserver observer;

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.errorview, this, true);
        imageViewBackground = (ImageView) findViewById(R.id.imageview_errorview_background);
        imageViewSrc = (ImageView) findViewById(R.id.imageview_errorview_src);
        textView = (TextView) findViewById(R.id.textview_errorview);
        button = (Button) findViewById(R.id.button_errorview);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, 0, 0);
        try {
            errorBackground = array.getResourceId(R.styleable.ErrorView_errorBackground, -1);
            errorSrc = array.getResourceId(R.styleable.ErrorView_errorSrc, R.drawable.ic_errorview_cloud);
            errorText = array.getString(R.styleable.ErrorView_errorText);
            errorButtonText = array.getString(R.styleable.ErrorView_errorButtonText);
            emptyBackground = array.getResourceId(R.styleable.ErrorView_emptyBackground, -1);
            emptySrc = array.getResourceId(R.styleable.ErrorView_emptySrc, -1);
            emptyText = array.getString(R.styleable.ErrorView_emptyText);
            emptyButtonText = array.getString(R.styleable.ErrorView_emptyButtonText);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() instanceof ViewGroup && ((ViewGroup) getParent()).getLayoutTransition() == null)
            ((ViewGroup) getParent()).setLayoutTransition(new LayoutTransition());
    }

    public void setState(@NonNull State state) {
        switch (state) {
            case ERROR:
                if (recyclerView != null)
                    VisibilityUtils.setVisible(recyclerView, false);
                VisibilityUtils.setVisible(this, true);
                VisibilityUtils.setImage(imageViewBackground, errorBackground);
                VisibilityUtils.setImage(imageViewSrc, errorSrc);
                VisibilityUtils.setText(textView, errorText);
                VisibilityUtils.setText(button, errorButtonText);
                button.setOnClickListener(errorListener);
                break;
            case EMPTY:
                if (recyclerView != null)
                    VisibilityUtils.setVisible(recyclerView, false);
                VisibilityUtils.setVisible(this, true);
                VisibilityUtils.setImage(imageViewBackground, emptyBackground);
                VisibilityUtils.setImage(imageViewSrc, emptySrc);
                VisibilityUtils.setText(textView, emptyText);
                VisibilityUtils.setText(button, emptyButtonText);
                button.setOnClickListener(emptyListener);
                break;
            case HIDDEN:
                if (recyclerView != null)
                    VisibilityUtils.setVisible(recyclerView, true);
                VisibilityUtils.setVisible(this, false);
                break;
        }
    }

    public void attachRecyclerView(@NonNull RecyclerView view) {
        if (view.getAdapter() == null)
            throw new IllegalArgumentException("Must set adapter to RecyclerView before attaching this ErrorView!");
        recyclerView = view;
        observer = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (recyclerView.getAdapter().getItemCount() > 0)
                    setState(State.HIDDEN);
                else if (recyclerView.getAdapter().getItemCount() == 0)
                    setState(State.EMPTY);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                onChanged();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                onChanged();
            }
        };
        observer.onChanged();
        observer.onChanged(); // quick-fix for layout transition
        recyclerView.getAdapter().registerAdapterDataObserver(observer);
    }

    public void detachRecyclerView() {
        if (recyclerView == null || observer == null)
            throw new RuntimeException("No RecyclerView attached to this ErrorView!");
        recyclerView.getAdapter().unregisterAdapterDataObserver(observer);
        observer = null;
        recyclerView = null;
    }

    public void setErrorOnClickListener(@Nullable OnClickListener listener) {
        errorListener = listener;
    }

    public void setEmptyOnClickListener(@Nullable OnClickListener listener) {
        emptyListener = listener;
    }
}