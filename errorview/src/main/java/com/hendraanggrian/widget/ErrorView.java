package com.hendraanggrian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hendraanggrian.errorview.R;

import static com.hendraanggrian.errorview.VisibilityUtils.setVisible;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ErrorView extends FrameLayout {

    @NonNull private final ImageView imageViewBackground;
    @NonNull private final ImageView imageViewLogo;
    @NonNull private final TextView textView;
    @NonNull private final Button button;

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
            setBackgroundResource(array.getResourceId(R.styleable.ErrorView_errorBackground, 0));
            setLogoResource(array.getResourceId(R.styleable.ErrorView_errorLogo, R.drawable.ic_errorview_cloud));
            setText(array.getString(R.styleable.ErrorView_errorText));
        } finally {
            array.recycle();
        }
    }

    @Override
    public void setBackground(Drawable background) {
        if (setVisible(imageViewBackground, background != null))
            imageViewBackground.setImageDrawable(background);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        if (setVisible(imageViewBackground, resId != 0))
            imageViewBackground.setImageResource(resId);
    }

    public void setLogoResource(@DrawableRes int resId) {
        if (setVisible(imageViewLogo, resId != 0))
            imageViewLogo.setImageResource(resId);
    }

    public void setText(@Nullable CharSequence text) {
        if (setVisible(textView, !TextUtils.isEmpty(text)))
            textView.setText(text);
    }

    public void setAction(@Nullable CharSequence text, @Nullable final OnClickListener listener) {
        if (setVisible(button, !TextUtils.isEmpty(text))) {
            button.setText(text);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onClick(ErrorView.this);
                }
            });
        }
    }
}