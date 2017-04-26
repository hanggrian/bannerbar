package com.hendraanggrian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @NonNull private final ViewGroup viewGroup;
    @NonNull private final ImageView imageViewBackground;
    @NonNull private final ImageView imageViewLogo;
    @NonNull private final TextView textView;
    @NonNull private final Button button;

    public ErrorView(@NonNull Context context) {
        this(context, null);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.errorViewStyle);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs);
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.errorview, this, true);
        viewGroup = (ViewGroup) findViewById(R.id.viewgroup_errorview);
        imageViewBackground = (ImageView) findViewById(R.id.imageview_errorview_background);
        imageViewLogo = (ImageView) findViewById(R.id.imageview_errorview_logo);
        textView = (TextView) findViewById(R.id.textview_errorview);
        button = (Button) findViewById(R.id.button_errorview);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, defStyleRes);
        try {
            // content
            setBackground(a.getDrawable(R.styleable.ErrorView_errorBackground));
            setLogo(a.getDrawable(R.styleable.ErrorView_errorLogo));
            setText(a.getText(R.styleable.ErrorView_errorText));
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                setTextAppearance(context, a.getResourceId(R.styleable.ErrorView_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium));
            else
                setTextAppearance(a.getResourceId(R.styleable.ErrorView_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium));
            // positioning
            float marginLeft = a.getDimension(R.styleable.ErrorView_contentMarginLeft, 0);
            float marginTop = a.getDimension(R.styleable.ErrorView_contentMarginTop, 0);
            float marginRight = a.getDimension(R.styleable.ErrorView_contentMarginRight, 0);
            float marginBottom = a.getDimension(R.styleable.ErrorView_contentMarginBottom, 0);
            if (marginLeft != 0 || marginTop != 0 || marginRight != 0 || marginBottom != 0)
                setContentMargin((int) marginLeft, (int) marginTop, (int) marginRight, (int) marginBottom);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setBackground(Drawable background) {
        if (imageViewBackground == null)
            throw new RuntimeException("Set background with app:errorBackground!");
        if (setVisible(imageViewBackground, background != null)) {
            imageViewBackground.setImageDrawable(background);
        }
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        if (setVisible(imageViewBackground, resId != 0)) {
            imageViewBackground.setImageResource(resId);
        }
    }

    public void setLogo(@Nullable Drawable logo) {
        if (setVisible(imageViewLogo, logo != null)) {
            imageViewLogo.setImageDrawable(logo);
        }
    }

    public void setLogoResource(@DrawableRes int resId) {
        if (setVisible(imageViewLogo, resId != 0)) {
            imageViewLogo.setImageResource(resId);
        }
    }

    public void setText(@StringRes int text) {
        setText(getResources().getText(text));
    }

    public void setText(@Nullable CharSequence text) {
        if (setVisible(textView, !TextUtils.isEmpty(text))) {
            textView.setText(text);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setTextAppearance(@StyleRes int res) {
        textView.setTextAppearance(res);
    }

    @SuppressWarnings("deprecation")
    public void setTextAppearance(@NonNull Context context, @StyleRes int res) {
        textView.setTextAppearance(context, res);
    }

    public void setAction(@StringRes int text, @Nullable final OnClickListener listener) {
        setAction(getResources().getText(text), listener);
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

    public void setContentMargin(int left, int top, int right, int bottom) {
        ((LayoutParams) viewGroup.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setContentMarginLeft(int left) {
        ((LayoutParams) viewGroup.getLayoutParams()).leftMargin = left;
    }

    public void setContentMarginTop(int top) {
        ((LayoutParams) viewGroup.getLayoutParams()).topMargin = top;
    }

    public void setContentMarginRight(int right) {
        ((LayoutParams) viewGroup.getLayoutParams()).rightMargin = right;
    }

    public void setContentMarginBottom(int bottom) {
        ((LayoutParams) viewGroup.getLayoutParams()).bottomMargin = bottom;
    }
}