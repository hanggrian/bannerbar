package com.hendraanggrian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
    @NonNull private final ImageView imageViewLogo;
    @NonNull private final TextView textView;
    @NonNull private final Button button;
    private final int hideId;

    @DrawableRes int errorBackground;
    @DrawableRes int errorLogo;
    @Nullable String errorText;
    @Nullable String errorButtonText;
    @Nullable OnClickListener errorListener;

    @DrawableRes int emptyBackground;
    @DrawableRes int emptyLogo;
    @Nullable String emptyText;
    @Nullable String emptyButtonText;
    @Nullable OnClickListener emptyListener;

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
        imageViewLogo = (ImageView) findViewById(R.id.imageview_errorview_logo);
        textView = (TextView) findViewById(R.id.textview_errorview);
        button = (Button) findViewById(R.id.button_errorview);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, 0, 0);
        try {
            errorBackground = array.getResourceId(R.styleable.ErrorView_errorBackground, -1);
            errorLogo = array.getResourceId(R.styleable.ErrorView_errorLogo, R.drawable.ic_errorview_cloud);
            errorText = array.getString(R.styleable.ErrorView_errorText);
            errorButtonText = array.getString(R.styleable.ErrorView_errorButtonText);
            emptyBackground = array.getResourceId(R.styleable.ErrorView_emptyBackground, -1);
            emptyLogo = array.getResourceId(R.styleable.ErrorView_emptyLogo, -1);
            emptyText = array.getString(R.styleable.ErrorView_emptyText);
            emptyButtonText = array.getString(R.styleable.ErrorView_emptyButtonText);
            hideId = array.getResourceId(R.styleable.ErrorView_hideId, -1);
        } finally {
            array.recycle();
        }
        setState(State.HIDDEN);
    }

    public void setState(@NonNull State state) {
        switch (state) {
            case ERROR:
                if (hideId != -1)
                    VisibilityUtils.setVisible(((View) getParent()).findViewById(hideId), false);
                VisibilityUtils.setVisible(this, true);
                VisibilityUtils.setImage(imageViewBackground, errorBackground);
                VisibilityUtils.setImage(imageViewLogo, errorLogo);
                VisibilityUtils.setText(textView, errorText);
                VisibilityUtils.setText(button, errorButtonText);
                button.setOnClickListener(errorListener);
                break;
            case EMPTY:
                if (hideId != -1)
                    VisibilityUtils.setVisible(((View) getParent()).findViewById(hideId), false);
                VisibilityUtils.setVisible(this, true);
                VisibilityUtils.setImage(imageViewBackground, emptyBackground);
                VisibilityUtils.setImage(imageViewLogo, emptyLogo);
                VisibilityUtils.setText(textView, emptyText);
                VisibilityUtils.setText(button, emptyButtonText);
                button.setOnClickListener(emptyListener);
                break;
            case HIDDEN:
                if (hideId != -1)
                    VisibilityUtils.setVisible(((View) getParent()).findViewById(hideId), true);
                VisibilityUtils.setVisible(this, false);
                break;
        }
    }

    public void setErrorOnClickListener(@Nullable OnClickListener listener) {
        errorListener = listener;
    }

    public void setEmptyOnClickListener(@Nullable OnClickListener listener) {
        emptyListener = listener;
    }
}